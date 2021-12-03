package com.example.appdonacion.ui.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.example.appdonacion.repo.DonacionRepo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    private GoogleMap maps;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            maps = googleMap;

            LatLng facuUbicacion = new LatLng(-34.77476, -58.26977);
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(facuUbicacion, 12));

            maps.setOnMarkerClickListener(marker -> true);
            maps.setOnMapClickListener(latLng -> {
            });


            DonacionRepo.getDonaciones(getActivity(), lista -> {
                for (DonacionesViewObject donacionesViewObject : lista) {
                    if (donacionesViewObject.getLatitud() != null && donacionesViewObject.getLongitud() != null) {

                        LatLng ubicacion = new LatLng(donacionesViewObject.getLatitud(), donacionesViewObject.getLongitud());
                        Marker marker = maps.addMarker(new MarkerOptions().position(ubicacion)
                                .title(donacionesViewObject.getDireccion()));
                        marker.showInfoWindow();

                    }
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}