package com.example.appdonacion.repo;


import android.content.Context;

import com.example.appdonacion.DonacionSharePreferences;
import com.example.appdonacion.R;
import com.example.appdonacion.entidades.DonacionesViewObject;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DonacionRepo {

    public static void getDonaciones(Context context, DonacionCallback callback) {
        final ArrayList<DonacionesViewObject> listaDonaciones = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("producto").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    String nombre = document.getString("nombre");
                    String descripcion = document.getString("descripcion");
                    String imagen = document.getString("imagen");
                    String cantidad = document.getString("cantidad");
                    String reg_token = document.getString("registrationToken");

                    DonacionesViewObject donacion = new DonacionesViewObject();

                    donacion.setNombre(nombre);
                    donacion.setDescripcionDetallada(descripcion);
                    donacion.setUrlImagen(imagen);
                    donacion.setRegistrationToken(reg_token);
//                    donacion.setCantidad(cantidad);

                    listaDonaciones.add(donacion);
                }
            }
                callback.showLista(listaDonaciones);
//            } else {
//               getDonacionesDemo(context, callback);
//            }
        });
    }

    public static void getDonacionesDemo(Context context, DonacionCallback callback) {

        final ArrayList<DonacionesViewObject> listaDonaciones = new ArrayList<>();

        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.aceite), context.getString(R.string.aceiteInfo), context.getString(R.string.aceiteDesc), R.drawable.aceite, R.drawable.aceite));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.arroz), context.getString(R.string.arrozInfo), context.getString(R.string.arrozDesc), R.drawable.arroz, R.drawable.arroz));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.fideo), context.getString(R.string.fideoInfo), context.getString(R.string.fideoDesc), R.drawable.fideo, R.drawable.fideo));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.lenteja), context.getString(R.string.lentejaInfo), context.getString(R.string.lentejaDesc), R.drawable.lentejas, R.drawable.lentejas));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.polenta), context.getString(R.string.polentaInfo), context.getString(R.string.polentaDesc), R.drawable.polenta, R.drawable.polenta));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.polenta), context.getString(R.string.polentaInfo), context.getString(R.string.polentaDesc), R.drawable.polenta, R.drawable.polenta));
        listaDonaciones.add(new DonacionesViewObject(context.getString(R.string.polenta), context.getString(R.string.polentaInfo), context.getString(R.string.polentaDesc), R.drawable.polenta, R.drawable.polenta));

        callback.showLista(listaDonaciones);
    }

    public static void guardarDonacion(String nombre_User, String correo_User,
                                       String ubi_User, String name, String desc,
                                       String detalles,String cant, String url,
                                       String reg_token) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> map = new HashMap<>();
        map.put("nombreUsuario",nombre_User);
        map.put("correo",correo_User);
        map.put("direccion",ubi_User);
        map.put("nombre",name);
        map.put("descripcion",desc);
        map.put("detalles",detalles);
        map.put("cantidad",cant);
        map.put("imagen",url);
        map.put("registrationToken", reg_token);

        db.collection("producto").document().set(
                map
        );
    }


    public interface DonacionCallback {
        void showLista(ArrayList<DonacionesViewObject> lista);
    }

}

