package com.example.appdonacion;

import android.content.Context;

public abstract class DonacionSharePreferences {
    private static final String CORREO = "correo";
    private static final String USUARIO = "usuario";
    private static final String UBICACION = "ubicacion";
    private static final String NOMBRE_USUARIO = "nombre_usuario";
    private static final String RECORDAR_USER = "recordar_user";


    public static void setUsuario(Context context, String usuario, String ubicacion, String correo) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(UBICACION, ubicacion);
        editor.putString(USUARIO, usuario);
        editor.putString(CORREO, correo);
        editor.apply();
    }

    public static String getUsuario(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(USUARIO, Context.MODE_PRIVATE);
        return sharedPrefRead.getString(USUARIO, "");
    }


    public static void setNombreUsuario(Context context, String nombre) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(NOMBRE_USUARIO, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NOMBRE_USUARIO, nombre);
        editor.apply();
    }

    public static String getNombreUsuario(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(NOMBRE_USUARIO, Context.MODE_PRIVATE);
        return sharedPrefRead.getString(NOMBRE_USUARIO, "");
    }
    public static void setCorreo(Context context, String correo) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(CORREO, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CORREO, correo);
        editor.apply();
    }
    public static String getCorreo(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(CORREO, Context.MODE_PRIVATE);
        return sharedPrefRead.getString(CORREO, "");
    }

    public static void setRecordarUser(Context context, Boolean isRemember) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(NOMBRE_USUARIO, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(RECORDAR_USER, isRemember);
        editor.apply();
    }

    public static Boolean getRecordarUser(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(NOMBRE_USUARIO, Context.MODE_PRIVATE);
        return sharedPrefRead.getBoolean(RECORDAR_USER, false);
    }

}