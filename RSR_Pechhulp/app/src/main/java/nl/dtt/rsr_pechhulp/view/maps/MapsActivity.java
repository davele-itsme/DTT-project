package nl.dtt.rsr_pechhulp.view.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import nl.dtt.rsr_pechhulp.R;
import nl.dtt.rsr_pechhulp.databinding.ActivityMapsBinding;
import nl.dtt.rsr_pechhulp.presenter.maps.MapsContract;
import nl.dtt.rsr_pechhulp.presenter.maps.MapsPresenter;
import nl.dtt.rsr_pechhulp.view.maps.changereceiver.LocationChangeReceiver;
import nl.dtt.rsr_pechhulp.view.maps.changereceiver.NetworkChangeReceiver;

/**
 * The type Maps activity.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, MapsContract.MapsView, GoogleMap.OnInfoWindowClickListener {
    private static final int REQUEST_PHONE_CALL = 1;
    private static final int REQUEST_LOCATION = 101;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Dialog contactDialog;
    private ActivityMapsBinding mapsBinding;
    private LocationChangeReceiver locationChangeReceiver;
    private NetworkChangeReceiver networkChangeReceiver;
    /**
     * The Maps presenter.
     */
    MapsPresenter mapsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapsBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        mapsPresenter = new MapsPresenter(this);
        mapsBinding.setPresenter(mapsPresenter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.rsr_pechhulp);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Check whether GPS and network is enabled
        isGPSEnabled();
        isNetworkEnabled();

        locationChangeReceiver = new LocationChangeReceiver(this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
    }

    //System checks wifi and gps state
    @Override
    protected void onStart() {
        super.onStart();
        //registering broadcast receiver dynamically
        IntentFilter filterGPS = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filterGPS.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(locationChangeReceiver, filterGPS);

        IntentFilter filterWifi = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(networkChangeReceiver, filterWifi);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregistering broadcast receivers for Wifi and GPS
        this.unregisterReceiver(locationChangeReceiver);
        this.unregisterReceiver(networkChangeReceiver);
    }

    @Override
    //finish on activity when up navigation is clicked
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void fetchLocation() {
        //checking the permission for GPS
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(MapsActivity.this);
            }
        });
    }

    /**
     * method called when maps are ready
     * getting string address using Geocoder and putting the string to custom information window {@link MapInfoWindow}
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        String title = getResources().getString(R.string.map_info_title);
        String fullAddress = mapsPresenter.getAddress(latLng);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title).snippet(fullAddress);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        //set info window adapter
        googleMap.setInfoWindowAdapter(new MapInfoWindow(this));
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        googleMap.addMarker(markerOptions).showInfoWindow();
    }

    // This function is called when user accept or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                } else {
                    finish();
                }
                return;
            }
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                }
                return;
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    //Creating dialog and for contacting on mobile devices
    @Override
    public void openContactDialog() {
        contactDialog = new Dialog(this);
        nl.dtt.rsr_pechhulp.databinding.ContactDialogBinding contactBinding = DataBindingUtil.inflate(contactDialog.getLayoutInflater(), R.layout.contact_dialog, null, true);
        contactBinding.setPresenter(mapsPresenter);
        contactDialog.setContentView(contactBinding.getRoot());
        //Making it unable to cancel when touching outside
        contactDialog.setCanceledOnTouchOutside(false);
        //Making the dialog transparent
        Objects.requireNonNull(contactDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        contactDialog.show();

        //sets invisible prior button
        assert mapsBinding.mapsLinearLayout != null;
        mapsBinding.mapsLinearLayout.setVisibility(View.GONE);
        setContactDialogPosition();
    }

    @Override
    public void closeContactDialog() {
        contactDialog.dismiss();
        assert mapsBinding.mapsLinearLayout != null;
        mapsBinding.mapsLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void makeCall() {
        makePhoneCall();
    }

    @Override
    public Activity getViewActivity()
    {
        return MapsActivity.this;
    }

    //Instantiating MainMenuDialog fragment to display dialog
    @Override
    public void openGPSDialog() {
        GPSDialog gpsDialog = new GPSDialog(MapsActivity.this);
        gpsDialog.setCancelable(false);
        gpsDialog.show(getSupportFragmentManager(), "gps dialog");
    }

    //Instantiating NetworkDialog fragment to display dialog
    @Override
    public void openNetworkDialog() {
        NetworkDialog  networkDialog = new NetworkDialog(MapsActivity.this);
        networkDialog.setCancelable(false);
        networkDialog.show(getSupportFragmentManager(), "network dialog");
    }

    //Method to call a specific phone number for the assistance
    //It checks for the permission from the user firstly
    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getResources().getString(R.string.phone_number)));
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            return;
        } else {
            startActivity(intent);
        }
    }

    //Checks when the maps screen is opened to check if GPS is enabled
    private void isGPSEnabled() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = Objects.requireNonNull(service)
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            openGPSDialog();
        }
    }

    /**
     * Is network enabled.
     * Using two approaches - using the deprecated NetworkInfo for older versions than Android Q, using NetworkCapabilities for Android Q and higher
     */
    private void isNetworkEnabled() {
        boolean networkEnabled =  mapsPresenter.getNetworkConnection();
        if (!networkEnabled) {
            openNetworkDialog();
        }
    }

    //sets the position for the dialog
    private void setContactDialogPosition()
    {
        Window window = contactDialog.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.y = 60;
            window.setAttributes(wlp);
        }
    }
}
