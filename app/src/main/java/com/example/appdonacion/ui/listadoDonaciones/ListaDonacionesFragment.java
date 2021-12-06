package com.example.appdonacion.ui.listadoDonaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appdonacion.R;
import com.example.appdonacion.adaptadores.AdaptadorDonaciones;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.example.appdonacion.repo.DonacionRepo;
import com.example.appdonacion.ui.detalleDonacion.DonacionDetalleActivity;
import com.example.appdonacion.ui.nuevaDonacion.AddProductoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaDonacionesFragment extends Fragment {

    private static final int NEW_DONACION = 213;
    ArrayList<DonacionesViewObject> listaDonaciones;
    RecyclerView recyclerDonaciones;
    AdaptadorDonaciones adapter;

    public ListaDonacionesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_DONACION) {
            llenarDonaciones();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_lista_donaciones, container, false);

        FloatingActionButton fab = vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddProductoActivity.class);
                startActivityForResult(i, NEW_DONACION);
            }
        });

        listaDonaciones = new ArrayList<>();
        recyclerDonaciones = vista.findViewById(R.id.recyclerId);
        recyclerDonaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        //metodo para cargar la lista de donaciones
        llenarDonaciones();

        return vista;
    }

    private void llenarDonaciones() {

        DonacionRepo.getDonaciones(getContext(), (DonacionRepo.DonacionCallback) lista -> {

            listaDonaciones = lista;
            adapter = new AdaptadorDonaciones(listaDonaciones, getContext());
            recyclerDonaciones.setAdapter(adapter);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), DonacionDetalleActivity.class);
                    i.putExtra("donacion", listaDonaciones.get(recyclerDonaciones.getChildAdapterPosition(view)));
                    startActivity(i);

                }
            });
        });

    }
}