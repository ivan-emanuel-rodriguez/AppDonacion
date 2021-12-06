package com.example.appdonacion.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;

public class DetalleDonacionesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public DetalleDonacionesFragment() {
    }

    public static DetalleDonacionesFragment newInstance(String param1, String param2) {
        DetalleDonacionesFragment fragment = new DetalleDonacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_detalle_donaciones, container, false);

        Bundle objetoDonacion=getArguments();
        DonacionesViewObject donacion=null;
        if (objetoDonacion != null) {
            donacion= (DonacionesViewObject) objetoDonacion.getSerializable("objeto");
        }

        return vista;
    }
}