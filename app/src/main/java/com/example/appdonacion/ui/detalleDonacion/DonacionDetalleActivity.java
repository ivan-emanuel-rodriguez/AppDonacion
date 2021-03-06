package com.example.appdonacion.ui.detalleDonacion;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdonacion.DonacionSharePreferences;
import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonacionDetalleActivity extends AppCompatActivity {


    private DonacionesViewObject donacion;
    private Button notifier;
    private Button whatsapp;
    private Button mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donacion_detalle_activity);
        mapa= (Button)findViewById(R.id.btn_mapa);
        mapa.setOnClickListener(obtenerUbicacion);
        notifier = (Button)findViewById(R.id.btn_notifier);
        notifier.setOnClickListener(EnviarNotificacion);
        whatsapp = (Button)findViewById(R.id.btn_whatsapp);
        whatsapp.setOnClickListener(sendWhatsapp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        donacion = (extras != null) ? (DonacionesViewObject) extras.get("donacion") : null;

        setupView();
    }

    private void setupView() {
        TextView textNombre = findViewById(R.id.nombreDetalleId);
        TextView textDescripcion = findViewById(R.id.descripcionId);
        TextView textDetalles = findViewById(R.id.detalleId);
        TextView textCantidad = findViewById(R.id.cantidadId);
        ImageView imagenDetalle = findViewById(R.id.imagenDetalleId);

        textNombre.setText(donacion.getNombre());
        textDescripcion.setText(donacion.getDescripcionDetallada());
        textDetalles.setText(donacion.getDetalles());
        textCantidad.setText(donacion.getCantidad());

        if (!donacion.getUrlImagen().equals("")) {

            Glide.with(getApplicationContext())
                    .load(String.valueOf(donacion.getUrlImagen()))
                    .fitCenter()
                    .centerCrop()
                    .into(imagenDetalle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    private View.OnClickListener obtenerUbicacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("geo:"+donacion.getLatitud().toString()+","+donacion.getLongitud().toString()
                            +"?z=16&q="+donacion.getLatitud().toString()+","+donacion.getLongitud().toString()+"(MiUbicacion)"));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    };


    private View.OnClickListener EnviarNotificacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llamarNotificacion();
        }
    };
    private void llamarNotificacion(){
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            if(donacion.getRegistrationToken().equals(DonacionSharePreferences.getRegistationId(getApplicationContext()))){
                Toast.makeText(this, R.string.es_tuyo, Toast.LENGTH_LONG).show();
            }
            else {
                String token = donacion.getRegistrationToken();
                json.put("to",token);
                JSONObject notificacion = new JSONObject();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> map = new HashMap<>();
                map.put("nombreUsuario", DonacionSharePreferences.getUsuario(getApplicationContext()));
                map.put("correo", DonacionSharePreferences.getCorreo(getApplicationContext()));
                map.put("nombre", donacion.getNombre());
                map.put("descripcion", donacion.getInfo());
                map.put("detalles", donacion.getDescripcionDetallada());
                map.put("imagen", donacion.getUrlImagen());
                map.put("tokenID", donacion.getTokenID());
                map.put("telefono", DonacionSharePreferences.getTelefono(getApplicationContext()));

                db.collection("notificacion").document(donacion.getTokenID()).set(
                        map
                );
                notificacion.put("imagen",donacion.getUrlImagen());
                notificacion.put("telefono", donacion.getTelefono());

                json.put("data",notificacion);
                String URL = "https://fcm.googleapis.com/fcm/send";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                    @Override
                    public Map<String, String> getHeaders()  {
                        Map<String,String> header = new HashMap<>();
                        header.put("content-type","application/json");
                        header.put("authorization","key=AAAAyj1fWt4:APA91bEjoU8AxAGS4jS8tttXFC7Ks5eHIsBfjBloVqAwF0wwgx0lNNEYdHPaQ4vS_NwliemFwZfYWNYX6Wx7dCiEeVIg2znBvP0wGZpQxTP5LFRTfQ4fjqJEkTTdIDdYpTThgei07iw1");
                        return header;
                    }
                };
                myrequest.add(request);
                Toast.makeText(this, R.string.usuario_notificado, Toast.LENGTH_LONG).show();

            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    private View.OnClickListener sendWhatsapp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String cadena = "Hola! me comunico con usted porque me interes?? su" +
                    " producto de donaci??n "+donacion.getNombre()+", "+donacion.getDescripcionDetallada() +" en la aplicaci??n Donandoando!.";
            boolean installed = appInstalledOrNot("com.whatsapp");
            String l2 = donacion.getTelefono();
            long l =Long.parseLong(donacion.getTelefono());
            if(installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+549"+Long.parseLong(donacion.getTelefono())+"&text="+
                        cadena));
                startActivity(intent);
            }
            else {
                Toast.makeText(DonacionDetalleActivity.this,
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