package nl.dtt.rsr_pechhulp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class MapInfoWindow implements GoogleMap.InfoWindowAdapter {

    private View view;

    @SuppressLint("InflateParams")
    public MapInfoWindow(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.map_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView title = view.findViewById(R.id.textTitle);
        TextView snippet = view.findViewById(R.id.textSnippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
