package nl.dtt.rsr_pechhulp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap googleMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.rsr_pechhulp);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        isGPSEnabled();
        isNetworkEnabled();
    }

    //System checks wifi and gps state
    @Override
    protected void onStart() {
        super.onStart();
        //registering broadcaast receriver dynamically
        IntentFilter filterGPS = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filterGPS.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(locationSwitchStateReceiver, filterGPS);

        IntentFilter filterWifi = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(wifiSwitchStateReceiver, filterWifi);

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(locationSwitchStateReceiver);
        this.unregisterReceiver(wifiSwitchStateReceiver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onContactClicked(View view) {
        final Dialog dialog = new Dialog(MapsActivity.this);
        dialog.setContentView(R.layout.contact_dialog);
        //Making the dialog transparent
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        //Making it unable to cancel when touching outside
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final LinearLayout mapsLinearLayout = findViewById(R.id.maps_linear_layout);
        mapsLinearLayout.setVisibility(View.GONE);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = 100;
        window.setAttributes(wlp);

        Button cancelButton = dialog.findViewById(R.id.cancel_dialog_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mapsLinearLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        String title = getResources().getString(R.string.map_info_title);
        String fullAddress = "";
        String additionalText = getResources().getString(R.string.map_info_snippet);
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            String addressName = addresses.get(0).getThoroughfare();
            String addressNumber = addresses.get(0).getFeatureName();
            String postalCode = addresses.get(0).getPostalCode();
            String town = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();

            fullAddress += addressName + " " + addressNumber + ", " + postalCode + "\n" + town + ", " + country + "\n" + "\n" + additionalText;
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title).snippet(fullAddress);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        googleMap.setInfoWindowAdapter(new MapInfoWindow(this));
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        googleMap.addMarker(markerOptions).showInfoWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    private void isGPSEnabled() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = Objects.requireNonNull(service)
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            openGPSDialog();
        }
    }

    public void isNetworkEnabled() {
        boolean networkEnabled = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        networkEnabled = true;
                    }
                }
            } else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        networkEnabled = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!networkEnabled) {
            openNetworkDialog();
        }
    }

    //Instantiating MainMenuDialog fragment to display dialog
    private void openGPSDialog() {
        GPSDialog gpsDialog = new GPSDialog(MapsActivity.this);
        gpsDialog.setCancelable(false);
        gpsDialog.show(getSupportFragmentManager(), "gps dialog");
    }

    private void openNetworkDialog() {
        NetworkDialog networkDialog = new NetworkDialog(MapsActivity.this);
        networkDialog.setCancelable(false);
        networkDialog.show(getSupportFragmentManager(), "network dialog");
    }

    //Popup if gps GPS is turned off
    private BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = false;
                if (locationManager != null) {
                    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                }
                if (!isGPSEnabled) {
                    openGPSDialog();
                }
            }
        }
    };

    private BroadcastReceiver wifiSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            if (wifiStateExtra == WifiManager.WIFI_STATE_DISABLED) {
                openNetworkDialog();
            }

        }
    };

}
