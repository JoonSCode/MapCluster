package com.unimaginablecode.mapclustertest;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class PositionItem implements ClusterItem {

    private final LatLng mPosition;
    public int bg;

    public PositionItem(double lat, double lng, int bg) {
        mPosition = new LatLng(lat, lng);
        this.bg = bg;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
