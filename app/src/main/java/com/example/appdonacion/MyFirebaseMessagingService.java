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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mayorqueoreo();
    }

    private void mayorqueoreo(){
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
        Log.e("token","Mi Token de registro es=" + registrationToken);

        DonacionSharePreferences.setRegistationId(getApplicationContext(),registrationToken);
    }
    public PendingIntent clicknotify(){
        Intent nf = new Intent(getApplicationContext(), PedidoActivity.class);

        Bundle bolsa = new Bundle();
        bolsa.putString("detalle","Hola");
        nf.putExtras(bolsa);

        nf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//Si está abierto que no lo abra de vuelta;
        return PendingIntent.getActivity(this,0,nf,0);
    }
}
