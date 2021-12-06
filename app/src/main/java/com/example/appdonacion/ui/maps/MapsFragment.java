package com.example.appdonacion.ui.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
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

import java.util.HashMap;


public class MapsFragment extends Fragment {
    private GoogleMap maps;
    ImageView imgmarker;
    private static final float camera_zoom = 15;
    TextView txtDireccion;
    HashMap<String,DonacionesViewObject> map;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            maps = googleMap;

            LatLng facuUbicacion = new LatLng(-34.77476, -58.26977);
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(facuUbicacion, 12));

            maps.setOnMapClickListener(latLng -> {
            });

            DonacionRepo.getDonaciones(getActivity(), lista -> {
                for (DonacionesViewObject donacionesViewObject : lista) {
                    if (donacionesViewObject.getLatitud() != null && donacionesViewObject.getLongitud() != null) {

                        map.put(donacionesViewObject.getNombreUbi(), donacionesViewObject);

                        LatLng ubicacion = new LatLng(donacionesViewObject.getLatitud(), donacionesViewObject.getLongitud());
                        Marker marker = maps.addMarker(new MarkerOptions().position(ubicacion)
                                .title(donacionesViewObject.getNombreUbi()));
                        marker.showInfoWindow();
                    }
                }
            });
            maps.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    DonacionesViewObject datos = map.get(marker.getTitle());
                    String nombre_usuario = datos.getNombreUsuario();
                    String email = datos.getCorreo();
                    String ubicacion = datos.getNombreUbi();
                    String telefono = datos.getTelefono();
                    String imagenUrl = datos.getUrlImagen();

                    String texto = nombre_usuario+" " + email + "\n"
                            + "Ubicación: " + ubicacion +"\n Telefono: "+ telefono;
                    txtDireccion.setTextSize(17);
                    txtDireccion.setText(texto);

                    Glide.with(getActivity())
                            .load(String.valueOf(imagenUrl))
                            .fitCenter()
                            .centerCrop()
                            .into(imgmarker);
                    return false;
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

        imgmarker = (ImageView) getView().findViewById(R.id.ImgMarker);
        txtDireccion = (TextView) getView().findViewById(R.id.tvMensaje);
        map = new HashMap<String,DonacionesViewObject>();
    }


}