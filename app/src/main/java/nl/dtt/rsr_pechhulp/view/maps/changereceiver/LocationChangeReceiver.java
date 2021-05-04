package nl.dtt.rsr_pechhulp.view.maps.changereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import nl.dtt.rsr_pechhulp.presenter.maps.MapsContract;

public class LocationChangeReceiver extends BroadcastReceiver {
    private MapsContract.MapsView mapsView;

    public LocationChangeReceiver(MapsContract.MapsView mapsView)
    {
        this.mapsView = mapsView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = false;
            if (locationManager != null) {
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
            if (!isGPSEnabled) {
                mapsView.openGPSDialog();
            }
        }
    }
}
