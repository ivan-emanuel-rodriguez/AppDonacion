package com.example.appdonacion.ui.nuevaDonacion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.appdonacion.DonacionSharePreferences;
import com.example.appdonacion.R;
import com.example.appdonacion.repo.DonacionRepo;
import com.example.appdonacion.ui.maps.LocationProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


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
    private TextView correoUser;
    private String urlImagen = "";
    private EditText ubiUser;
    private EditText nombreUser;
    private EditText detailDon;
    private boolean addToStorage;


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


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
        correoUser = (TextView) findViewById(R.id.correoUser);

        btnsubir = (Button) findViewById(R.id.btn_subir_producto);
        nombre = findViewById(R.id.nombretv);
        nombreUser = findViewById(R.id.nombreUser);
        ubiUser = findViewById(R.id.ubiUser);
        detailDon = findViewById(R.id.detailDon);


        descripcion = findViewById(R.id.descripciontv);
        cantidad = findViewById(R.id.cantidadtv);
        btn_imagen = (Button) findViewById(R.id.btn_imagen);
        myImageView = (ImageView) findViewById(R.id.subir_imagen);
        progressDialog = new ProgressDialog(this);

        btnsubir.setOnClickListener(pushListener);
        btn_imagen.setOnClickListener(ImagenUpload);
        correoUser.setText(DonacionSharePreferences.getCorreo(getApplicationContext()));
        getCurrentLocation();

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
            //getCurrentLocation();
            addStorage();

        }
    };

    private View.OnClickListener ImagenUpload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
            /*Intent i = new Intent(AddProductoActivity.this, PedidoActivity.class);
            startActivity(i);*/
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

    private void addStorage() {
        addToStorage = true;

        LocationProvider.requestCurrentLocation(getApplicationContext(), location -> {

            double latitud = -34.7747628;
            double longitud = -58.2697733;


            if (location != null) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }

            String nombre_User = nombreUser.getText().toString();
            String correo_User = correoUser.getText().toString();
            String ubi_User = ubiUser.getText().toString();
            String name = nombre.getText().toString();
            String desc = descripcion.getText().toString();
            String detalles = detailDon.getText().toString();
            String cant = cantidad.getText().toString();
            String reg_token = DonacionSharePreferences.getRegistationId(getApplicationContext());
            String tokenId = DonacionSharePreferences.getTokenId(getApplicationContext());
            String telefono = DonacionSharePreferences.getTelefono(getApplicationContext());
            if(addToStorage){
                DonacionRepo.guardarDonacion(nombre_User, correo_User, ubi_User,
                        name, desc, detalles, cant, urlImagen,reg_token, latitud, longitud,tokenId,
                        telefono);
            }

            Toast.makeText(getApplicationContext(), R.string.producto_agregado,
                    Toast.LENGTH_LONG).show();
            addToStorage = false;

            finish();

        });
    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //addStorage();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addStorage();
                } else {
                    finish();
                }
                return;
            }
        }
    }

}