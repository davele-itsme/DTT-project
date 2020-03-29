package nl.dtt.rsr_pechhulp.presenter.maps;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import nl.dtt.rsr_pechhulp.R;


/**
 * The type Maps presenter.
 */
public class MapsPresenter implements MapsContract.Presenter {
    private MapsContract.MapsView mView;
    private Activity activity;

    /**
     * Creating a Maps presenter constructor.
     *
     * @param view the view
     */
    public MapsPresenter(MapsContract.MapsView view) {
        mView = view;
        activity = mView.getViewActivity();
    }

    @Override
    public void handleCancelBtnClick(View view) {
        mView.closeContactDialog();
    }

    @Override
    public void handleOpenContactDialogBtnClick(View view) {
        mView.openContactDialog();
    }

    @Override
    public void handleCallBtnClick(View view) {
        mView.makeCall();
    }

    @Override
    public void handleCallTextClick(View view)
    {
        mView.makeCall();
    }

    @Override
    public boolean getNetworkConnection() {
        boolean networkEnabled = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            //Checking if user's android version is higher or the same as android Q - 10.0
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
        return networkEnabled;
    }

    @Override
    public String getAddress(LatLng latLng)
    {
        String fullAddress = ""; //The final String that will be displayed in info window
        String additionalText = activity.getResources().getString(R.string.map_info_snippet);
        Geocoder geocoder = new Geocoder(activity.getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String addressName = addresses.get(0).getThoroughfare();
            String addressNumber = addresses.get(0).getFeatureName();
            String postalCode = addresses.get(0).getPostalCode();
            String town = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();

            fullAddress += addressName + " " + addressNumber + ", " + postalCode + "\n" + town + ", " + country + "\n" + "\n" + additionalText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullAddress;
    }
}
