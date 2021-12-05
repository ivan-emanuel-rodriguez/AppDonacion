package com.example.appdonacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdonacion.entidades.DonacionesViewObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText correo, nombreUsuario, localidad, contrasena, contrasenaConfirmacion, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correo);
        nombreUsuario = findViewById(R.id.nombre);
        localidad = findViewById(R.id.localidad);
        contrasena = findViewById(R.id.contrasena);
        contrasenaConfirmacion = findViewById(R.id.contrasenaConfirmacion);
        telefono = findViewById(R.id.telefono_et);

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){
        //    reload();
        //}
    }

    public void registrarUsuario(View view) {
        if (correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty() || contrasenaConfirmacion.getText().toString().isEmpty()
                || nombreUsuario.getText().toString().isEmpty() || localidad.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.error_auth, Toast.LENGTH_SHORT).show();
        } else {
            if (contrasena.getText().toString().equals(contrasenaConfirmacion.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(getApplicationContext(), R.string.usuario_creado, Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //Shared preferences
                                    DonacionSharePreferences.setCorreo(getApplicationContext(), correo.getText().toString());
                                    DonacionSharePreferences.setNombreUsuario(getApplicationContext(), user.getDisplayName());
                                    DonacionSharePreferences.setRecordarUser(getApplicationContext(), true);
                                    DonacionSharePreferences.setTokenId(getApplicationContext(), user.getUid());
                                    DonacionSharePreferences.setTelefono(getApplicationContext(), telefono.getText().toString());


                                    //ir a la base de usuarios, y guardar uno nuevo con el user uid ,ubicación y lo que quieran
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();






                                    Map<String,Object> map = new HashMap<>();
                                    map.put("registrationToken",DonacionSharePreferences.getRegistationId(getApplicationContext()));
                                    map.put("telefono",telefono.getText().toString());
                                    db.collection("usuarios").document(user.getUid()).set(
                                            map
                                    );


                                    //DonacionSharePreferences.setUsuario(getApplicationContext(),
                                    //nombreUsuario.getText().toString(),localidad.getText().toString(),correo.getText().toString());
                                    //DonacionSharePreferences.setNombreUsuario(getApplicationContext(), user.getDisplayName());
                                    //DonacionSharePreferences.setRecordarUser(getApplicationContext(), true);
                                    Intent i = new Intent(getApplicationContext(), ListaDonacionActivity.class);
                                    startActivity(i);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            } else {
                Toast.makeText(this, R.string.no_coinciden, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void irInicio(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}