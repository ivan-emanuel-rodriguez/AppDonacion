package com.example.appdonacion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.appdonacion.entidades.DonacionesViewObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//import java.util.logging.Handler;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String nombre;
    private String detalle;
    private String imagen;
    private String correo;
    private String usuario;
    private String telefono;
    //private String telefono;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();
        int j = remoteMessage.getData().size();
        /*if(remoteMessage.getData().size()>0){
            nombre = remoteMessage.getData().get("nombre");
            detalle = remoteMessage.getData().get("detalle");
            imagen = remoteMessage.getData().get("imagen");
            correo = remoteMessage.getData().get("correo");
            usuario = remoteMessage.getData().get("usuario");
            telefono = remoteMessage.getData().get("telefono");
            //telefono = remoteMessage.getData().get("telefono");
            mayorqueoreo(nombre,detalle,imagen);
        }*/
        String a = remoteMessage.getData().toString();
        imagen = remoteMessage.getData().get("imagen");
        telefono = remoteMessage.getData().get("telefono");

        mayorqueoreo(nombre,detalle,imagen);
    }

    private void mayorqueoreo(String nombre, String detalle,String imagen){
        String id = "mensaje";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Hola!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Una persona se interesó en una publicación tuya!")
                .setContentIntent(clicknotify())
                .setContentInfo("nuevo");
        Random random = new Random();//para que no se pisen las notificaciones
        int idNotify = random.nextInt(8000);
        assert nm!=null; //para que no colapse
        nm.notify(idNotify,builder.build());
        //Pending item para que cuando se presiona vaya al activity
    }
    @Override
    public void onNewToken(String registrationToken) {

        Log.e("token","Firebase #onNewToken registrationToken=" + registrationToken);

        DonacionSharePreferences.setRegistationId(getApplicationContext(),registrationToken);
         /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String token = DonacionSharePreferences.getTokenId(getApplicationContext());
        Map<String,Object> map = new HashMap<>();
        map.put("registrationToken",registrationToken);
        db.collection("usuarios").document(token).set(
                map
        );
                 */
/*
        Log.e("token","Firebase #onNewToken registrationToken=" + registrationToken);
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(DonacionSharePreferences.getTokenId(getApplicationContext()));
        //ref.child("mitoken").setValue(registrationToken);
        //ref.child(DonacionSharePreferences.getTokenId(getApplicationContext())).setValue(registrationToken);
        //startService(new Intent(this, MyFirebaseMessagingService.class));
        String valor = DonacionSharePreferences.getTokenId(getApplicationContext());
        ref.child(DonacionSharePreferences.getTokenId(getApplicationContext())).setValue(registrationToken);
        */
    }
    public PendingIntent clicknotify(){
        //Intent nf = new Intent(getApplicationContext(),PaginaPrincipalActivity.class);
        Intent nf = new Intent(getApplicationContext(),Pedido.class);

        Bundle bolsa = new Bundle();
        bolsa.putString("detalle","Hola");
        nf.putExtras(bolsa);
        //String correo =
        /*nf.putExtra("correo",correo);
        nf.putExtra("usuario",usuario);
        nf.putExtra("nombre_usuario","Usuario");
        nf.putExtra("telefono",telefono);
        nf.putExtra("nombre",nombre);
        nf.putExtra("detalle",detalle);
        nf.putExtra("imagen",imagen);*/
        /*
        nf.putExtra("correo","a");
        nf.putExtra("usuario","b");
        nf.putExtra("nombre_usuario","c");
        nf.putExtra("telefono","d");
        nf.putExtra("nombre","e");
        nf.putExtra("detalle","f");
        nf.putExtra("imagen","g");*/

        nf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//Si está abierto que no lo abra de vuelta;
        return PendingIntent.getActivity(this,0,nf,0);
    }
}
