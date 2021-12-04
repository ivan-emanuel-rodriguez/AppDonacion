package com.example.appdonacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText correo;
    private EditText contrasena;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
            //tabla de usuarios, te traes los datos de ese usaurio y lo guardar sharepreferences

            Intent i = new Intent(getApplicationContext(), ListaDonacionActivity.class);
            startActivity(i);
        }
    }


    public void iniciarSesion(View view) {
        if (correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error: Debe ingresar un usuario y contraseña validos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String cadena = user.getUid();
                                //String cadena2 = user.getIdToken(true).getResult().getToken();
                                DonacionSharePreferences.setCorreo(getApplicationContext(), correo.getText().toString());
                                DonacionSharePreferences.setNombreUsuario(getApplicationContext(), user.getDisplayName());
                                DonacionSharePreferences.setRecordarUser(getApplicationContext(), true);
                                DonacionSharePreferences.setTokenId(getApplicationContext(), user.getUid());


                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                String token = DonacionSharePreferences.getTokenId(getApplicationContext());
                                Map<String,Object> map = new HashMap<>();
                                map.put("registrationToken",DonacionSharePreferences.getRegistationId(getApplicationContext()));
                                String a = DonacionSharePreferences.getRegistationId(getApplicationContext());
                                String b = DonacionSharePreferences.getCorreo(getApplicationContext());
                                db.collection("usuarios").document(user.getUid()).set(
                                        map
                                );


                                //Inicio sesion correctamente
                                Toast.makeText(getApplicationContext(), "Ha iniciado sesion correctamente", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), ListaDonacionActivity.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Error: compruebe su usuario y contraseña",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }


    }


    public void irRegistrarse(View view) {
        Intent i = new Intent(this, RegistrarseActivity.class);
        startActivity(i);

    }
}