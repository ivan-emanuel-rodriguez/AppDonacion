package com.example.appdonacion.ui.detalleDonacion;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.appdonacion.Pedido;
import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class DonacionDetalleActivity extends AppCompatActivity {


    private DonacionesViewObject donacion;
    private Button notifier;
    private Button whatsapp;
    private Button mapa;
    private long numero_whatsapp = 3515193923L;
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

        // add back arrow to toolbar
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


//        donacion.getLongitud();
//        donacion.getLatitud();
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
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
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
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            //        Uri.parse("geo:41.3825581,2.1704375?z=16&q=41.3825581,2.1704375(Barcelona)"));
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
            //Toast.makeText(this, donacion.getRegistrationToken(), Toast.LENGTH_LONG).show();


            //String token = "ecyujSBbSyG0woPamGlDXF:APA91bGbsJm4U_nDA29EAkii0dhEJH1Ytjj0vXNnLWYOkfQb72qPtw1jtPaOE7u5Hp2KjvpG6ePROUiovJxI9PyZyrDEGDXPYyeUR0SCJWxM6ezeuOQxuZRBPjoqfVrdm8RR4VmqV9Ck";
            //String token = DonacionSharePreferences.getRegistationId(getApplicationContext());
            String compareA =donacion.getRegistrationToken();
            String compareB = DonacionSharePreferences.getRegistationId(getApplicationContext());
            if(donacion.getRegistrationToken().equals(DonacionSharePreferences.getRegistationId(getApplicationContext()))){
                Toast.makeText(this, "Este producto es tuyo!", Toast.LENGTH_LONG).show();
            }
            else {
                String token = donacion.getRegistrationToken();
                json.put("to",token);
                JSONObject notificacion = new JSONObject();
                notificacion.put("nombre",donacion.getNombre());
                notificacion.put("detalle", donacion.getInfo());
                notificacion.put("imagen", donacion.getUrlImagen());
                notificacion.put("correo", DonacionSharePreferences.getCorreo(getApplicationContext()));
                notificacion.put("usuario", DonacionSharePreferences.getUsuario(getApplicationContext()));
                notificacion.put("telefono", 1123456789);


                String imagen = donacion.getUrlImagen();
                String n = donacion.getNombre();
                String d = donacion.getDescripcionDetallada();
                String c = DonacionSharePreferences.getCorreo(getApplicationContext());
                String u = DonacionSharePreferences.getUsuario(getApplicationContext());

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
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    private View.OnClickListener sendWhatsapp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String cadena = "Hola! me comunico con usted porque me interesó su" +
                    " producto de donación "+donacion.getNombre()+", "+donacion.getDescripcionDetallada() +" en la aplicación Donandoando!.";
            boolean installed = appInstalledOrNot("com.whatsapp");
            if(installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+549"+numero_whatsapp+"&text="+
                        cadena));
                startActivity(intent);
            }
            else {
                Toast.makeText(DonacionDetalleActivity.this,
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