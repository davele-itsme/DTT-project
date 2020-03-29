package nl.dtt.rsr_pechhulp.view.maps.changereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;


import nl.dtt.rsr_pechhulp.presenter.maps.MapsContract;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private MapsContract.MapsView mapsView;
    public NetworkChangeReceiver(MapsContract.MapsView mapsView)
    {
        this.mapsView = mapsView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            if (wifiStateExtra == WifiManager.WIFI_STATE_DISABLED) {
                mapsView.openNetworkDialog();
            }
    }
}
