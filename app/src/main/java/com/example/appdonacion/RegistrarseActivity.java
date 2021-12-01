package com.example.appdonacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText correo,nombreUsuario,localidad,contrasena,contrasenaConfirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correo);
        nombreUsuario= findViewById(R.id.nombre);
        localidad = findViewById(R.id.localidad);
        contrasena = findViewById(R.id.contrasena);
        contrasenaConfirmacion = findViewById(R.id.contrasenaConfirmacion);

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){
        //    reload();
        //}
    }

    public void registrarUsuario (View view){
        if (correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty() || contrasenaConfirmacion.getText().toString().isEmpty()
                || nombreUsuario.getText().toString().isEmpty() || localidad.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error: Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            if(contrasena.getText().toString().equals(contrasenaConfirmacion.getText().toString())){
                mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(getApplicationContext(), "Usuario creado.",Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //Shared preferences
                                    DonacionSharePreferences.setUsuario(getApplicationContext(),
                                    nombreUsuario.getText().toString(),localidad.getText().toString(),correo.getText().toString());
                                    DonacionSharePreferences.setNombreUsuario(getApplicationContext(), user.getDisplayName());
                                    DonacionSharePreferences.setRecordarUser(getApplicationContext(), true);
                                    Intent i = new Intent(getApplicationContext(), PaginaPrincipalActivity.class);
                                    startActivity(i);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void irInicio(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}