package com.unimaginablecode.mapclustertest;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ClusterManager mClusterManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager = new ClusterManager(this, mMap);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.514,127.102716),14.0f));

        final List<PositionItem> list = new ArrayList<>()/* 좌표정보 데이터 리스트 */;
        list.add(new PositionItem(37.514,127.102716, 1));
        list.add(new PositionItem(37.5146,127.099753, 2));
        list.add(new PositionItem(37.5130,127.102522, 3));
        list.add(new PositionItem(37.5179,127.104429, 4));
        list.add(new PositionItem(37.5132,127.09993, 5));
        for(PositionItem tmp: list)
            mClusterManager.addItem(tmp);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<PositionItem>() {
            @Override
            public boolean onClusterClick(Cluster<PositionItem> cluster) {
                LatLngBounds.Builder builder_c = LatLngBounds.builder();
                for (ClusterItem item : cluster.getItems()) {
                    builder_c.include(item.getPosition());
                }
                LatLngBounds bounds_c = builder_c.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds_c, 4));
                float zoom = mMap.getCameraPosition().zoom - 0.5f;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                return true;
            }
        });

        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
    }

    
}
