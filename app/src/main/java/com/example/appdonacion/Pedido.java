package com.example.appdonacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.example.appdonacion.ui.nuevaDonacion.AddProductoActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Pedido extends AppCompatActivity {
    private Button enviar;
    private TextView información;


    private String telefono;
    private String correo;
    private String nombre;
    private String descripcion;
    private String detalles;
    private String imagenUrl;
    private String tokenID;
    
    private ImageView imagen;
    private StorageReference myStorage;
    private TextView información_tv;
    private TextView correo_tv;
    private TextView información2_tv;
    private TextView nombre_tv;
    private TextView detalle_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        enviar = (Button)findViewById(R.id.btn_send);
        imagen = (ImageView) findViewById(R.id.imagenpedido);
        enviar.setOnClickListener(sendListener);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        información_tv = findViewById(R.id.informaciontv);
        correo_tv = findViewById(R.id.correotv);
        información2_tv = findViewById(R.id.información2tv);
        nombre_tv = findViewById(R.id.nombretv);
        detalle_tv = findViewById(R.id.detalletv);

        db.collection("notificacion").document(DonacionSharePreferences.getTokenId(getApplicationContext())).get().addOnSuccessListener(task -> {
            if (task.exists()) {
                telefono = task.getString("nombreUsuario");
                correo = task.getString("correo");
                nombre = task.getString("nombre");
                descripcion = task.getString("descripcion");
                detalles = task.getString("detalles");
                imagenUrl = task.getString("imagen");
                tokenID = task.getString("tokenID");

                información_tv.setText("El usuario ");
                correo_tv.setText("Correo: "+correo);
                información2_tv.setText("Solicitó tu");
                nombre_tv.setText(nombre);
                detalle_tv.setText(detalles);
                Glide.with(Pedido.this.getApplicationContext())
                        .load(imagenUrl)
                        .fitCenter()
                        .centerCrop()
                        .into(imagen);
                //addToStorage(java.lang.String.valueOf(uri1));
                myStorage = FirebaseStorage.getInstance().getReference();
                StorageReference filepath = myStorage.child("fotos");
                filepath.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri1) {
                                Glide.with(Pedido.this.getApplicationContext())
                                        .load(imagenUrl)
                                        .fitCenter()
                                        .centerCrop()
                                        .into(imagen);
                            }
                        }
                );
            }
            else{

            }
        });












        /*
        Bundle bundeR = getIntent().getExtras();
        //Bundle bunde = getIntent().getExtras().toString();
        correo = bundeR.getString("detalle");
        //correo = bundeR.getString("detalle").toString();
        //correo = bundeR.getString("telefono");
        correo = getIntent().getExtras().getString("correo");
        usuario = getIntent().getExtras().getString("usuario");
        nombre_usuario = getIntent().getExtras().getString("nombre_usuario");
        telefono = getIntent().getExtras().getDouble("telefono");
        String tel1 = getIntent().getExtras().getString("telefono");
        Double tel2 = getIntent().getExtras().getDouble("telefono");
        //Double tel3 = Double.valueOf(getIntent().getExtras().getString("telefono"));
        //telefono = getIntent().getExtras().getD("telefono");
        nombre = getIntent().getExtras().getString("titulo");
        detalle = getIntent().getExtras().getString("detalle");
        imagenUrl = getIntent().getExtras().getString("imagen");

        enviar = (Button)findViewById(R.id.btn_send);
        imagen = (ImageView) findViewById(R.id.imagenpedido);
        información_tv = findViewById(R.id.informaciontv);
        correo_tv = findViewById(R.id.correotv);
        telefono_tv = findViewById(R.id.telefonotv);
        información2_tv = findViewById(R.id.información2tv);
        nombre_tv = findViewById(R.id.nombretv);
        detalle_tv = findViewById(R.id.detalletv);


        enviar.setOnClickListener(sendListener);

        información_tv.setText("El usuario "+usuario);
        correo_tv.setText("Correo: "+correo);
        telefono_tv.setText("Teléfono "+telefono);
        información2_tv.setText("Solicitó tu");
        nombre_tv.setText(nombre);
        detalle_tv.setText(detalle);





        Glide.with(Pedido.this.getApplicationContext())
                .load(imagenUrl)
                .fitCenter()
                .centerCrop()
                .into(imagen);
        //addToStorage(java.lang.String.valueOf(uri1));
        myStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filepath = myStorage.child("fotos");
        filepath.getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri1) {
                        Glide.with(Pedido.this.getApplicationContext())
                                .load(String.valueOf(uri1))
                                .fitCenter()
                                .centerCrop()
                                .into(imagen);
                        //addToStorage(java.lang.String.valueOf(uri1));
                        urlImagen = String.valueOf(uri1);
                    }
                }
        );*/
    }


    private View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean installed = appInstalledOrNot("com.whatsapp");
            if(installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+549"+1123456789+"&text="+
                        "Producto"));
                startActivity(intent);
            }
            else {
                Toast.makeText(Pedido.this,
                        "No tienes whatsapp instalado en tu teléfono" , Toast.LENGTH_SHORT).show();
            }
        }
    };
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}