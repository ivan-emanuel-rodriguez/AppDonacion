package com.example.appdonacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import com.example.appdonacion.entidades.DonacionesViewObject;
import com.example.appdonacion.fragments.DetalleDonacionesFragment;
import com.example.appdonacion.fragments.ListaDonacionesFragment;

public class PaginaPrincipalActivity extends AppCompatActivity implements InterfaceComunicaFragments {

    //Creo los com.example.appdonacion.fragments
    ListaDonacionesFragment listaFragment;
    DetalleDonacionesFragment detalleFragment;



    private FirebaseAuth mAuth;
   // private TextView textNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //textNombre = findViewById(R.id.textUsuarioId);

        //if (mAuth.getCurrentUser()!= null){
        //    textNombre.setText(DonacionSharePreferences.getUsuario(getApplicationContext()));
       // }

        //Instancio el fragment de lista de donaciones
        listaFragment=new ListaDonacionesFragment();


        //Cargo en la vista de la pagina principal la lista de donaciones del fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, listaFragment).commit();


    }


    public void cerrarSesion(View view){
      //  if (mAuth.getCurrentUser() != null) {
        //      mAuth.signOut();
        //  Toast.makeText(getApplicationContext(), "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
        //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //  startActivity(i);
           // finish();
       // }
        Intent i = new Intent(getApplicationContext(), ListaDonacionActivity.class);
        startActivity(i);

    }

    @Override
    public void enviarDonacion(DonacionesViewObject donacion) {
        //instancio el fragment de detalle de donaciones
        detalleFragment=new DetalleDonacionesFragment();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putSerializable("objeto",donacion);
        detalleFragment.setArguments(bundleEnvio);

        //Cargamos el fragment en el activity

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, detalleFragment).addToBackStack(null).commit();

    }
}