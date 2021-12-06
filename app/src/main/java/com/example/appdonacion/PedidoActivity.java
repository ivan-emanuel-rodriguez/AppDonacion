package com.example.appdonacion;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PedidoActivity extends AppCompatActivity {
    private Button enviar;
    private TextView información;
    private String telefono;
    private String correo;
    private String nombre_usuario;
    private String nombre;

    private String descripcion;
    private String detalles;
    private String tokenID;

    private ImageView imagen;
    private String imagenUrl;
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
                nombre_usuario = task.getString("nombreUsuario");
                correo = task.getString("correo");
                nombre = task.getString("nombre");
                descripcion = task.getString("descripcion");
                detalles = task.getString("detalles");
                imagenUrl = task.getString("imagen");
                tokenID = task.getString("tokenID");
                telefono = task.getString("telefono");

                información_tv.setText("El usuario ");
                correo_tv.setText("Correo: "+correo);
                información2_tv.setText("Solicitó tu");
                nombre_tv.setText("Producto: "+nombre);
                detalle_tv.setText(detalles);
                Glide.with(PedidoActivity.this.getApplicationContext())
                        .load(imagenUrl)
                        .fitCenter()
                        .centerCrop()
                        .into(imagen);
                myStorage = FirebaseStorage.getInstance().getReference();
                StorageReference filepath = myStorage.child("fotos");
                filepath.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri1) {
                                Glide.with(PedidoActivity.this.getApplicationContext())
                                        .load(imagenUrl)
                                        .fitCenter()
                                        .centerCrop()
                                        .into(imagen);
                            }
                        }
                );
            }
            else{
                Toast.makeText(PedidoActivity.this,
                        R.string.error , Toast.LENGTH_SHORT).show();
            }
        });
    }


    private View.OnClickListener sendListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String cadena = "Hola! me comunico con usted porque me interesó su" +
                    " producto de donación "+ nombre +", "+detalles +" en la aplicación Donandoando!.";

            boolean installed = appInstalledOrNot("com.whatsapp");
            if(installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+549"+Long.parseLong(telefono)+"&text="+
                        cadena));
                startActivity(intent);
            }
            else {
                Toast.makeText(PedidoActivity.this,
                        R.string.no_tienes_ws , Toast.LENGTH_SHORT).show();
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