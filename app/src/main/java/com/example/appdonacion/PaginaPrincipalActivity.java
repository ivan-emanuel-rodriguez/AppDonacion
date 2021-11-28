package com.example.appdonacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void cerrarSesion(View view){
        if (mAuth.getCurrentUser()!= null){
            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Se ha cerrado la sesión",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}