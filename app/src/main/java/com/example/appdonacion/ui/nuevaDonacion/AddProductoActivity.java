package com.example.appdonacion.ui.nuevaDonacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.appdonacion.R;
import com.example.appdonacion.repo.DonacionRepo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by reino on 1/12/2021.
 */
public class AddProductoActivity extends AppCompatActivity {
    private Button btnsubir;
    private EditText nombre;
    private EditText descripcion;
    private EditText cantidad;
    private Button btn_imagen;
    private StorageReference myStorage;
    private static final int GALLERY_INTENT = 1;
    private ImageView myImageView;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String urlImagen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myStorage = FirebaseStorage.getInstance().getReference();

        btnsubir = (Button) findViewById(R.id.btn_subir_producto);
        nombre = findViewById(R.id.nombretv);
        descripcion = findViewById(R.id.descripciontv);
        cantidad = findViewById(R.id.cantidadtv);
        btn_imagen = (Button) findViewById(R.id.btn_imagen);
        myImageView = (ImageView) findViewById(R.id.subir_imagen);
        progressDialog = new ProgressDialog(this);

        btnsubir.setOnClickListener(pushListener);
        btn_imagen.setOnClickListener(ImagenUpload);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener pushListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Muestro el mensaje "De nada"
            /*
            Toast.makeText(AddProduct.this, "Holaa",

                    Toast.LENGTH_LONG).show();
            String name = nombre.getText().toString();
            String description = descripcion.getText().toString();
            String cant = cantidad.getText().toString();

            Map<String,Object> map = new HashMap<>();
            map.put("nombre",name);
            map.put("descripcion",description);
            map.put("cantidad",cant);
            db.collection("producto").document().set(
                    map
            );
            */
            addToStorage(urlImagen);
        }
    };

    private View.OnClickListener ImagenUpload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null) {
            progressDialog.setTitle("Subiendo...");
            progressDialog.setMessage("Subiendo Foto a Firebase");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //Si se obtuvo la foto y está en condiciones
            Uri uri = data.getData();
            //Creo la carpeta fotos dentro de ese uri
            StorageReference filepath = myStorage.child("fotos").child(uri.getLastPathSegment());
            //pongo la foto en la carpeta
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                           @Override
                                                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                               filepath.getDownloadUrl().addOnSuccessListener(
                                                                       new OnSuccessListener<Uri>() {
                                                                           @Override
                                                                           public void onSuccess(Uri uri1) {
                                                                               progressDialog.dismiss();
                                                                               Glide.with(AddProductoActivity.this.getApplicationContext())
                                                                                       .load(String.valueOf(uri1))
                                                                                       .fitCenter()
                                                                                       .centerCrop()
                                                                                       .into(myImageView);
                                                                               //addToStorage(java.lang.String.valueOf(uri1));
                                                                               urlImagen = String.valueOf(uri1);
                                                                           }
                                                                       }
                                                               );
                                                           }
                                                       }
            );
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addToStorage(String url) {

        String name = nombre.getText().toString();
        String desc = descripcion.getText().toString();
        String cant = cantidad.getText().toString();

        DonacionRepo.guardarDonacion(name, desc, cant, url);

        Toast.makeText(getApplicationContext(), "Producto Agregado",
                Toast.LENGTH_LONG).show();
        finish();
    }
}