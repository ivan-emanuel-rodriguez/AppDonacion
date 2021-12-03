package com.example.appdonacion;

import android.content.Context;

public abstract class DonacionSharePreferences {
    private static final String CORREO = "correo";
    private static final String USUARIO = "usuario";
    private static final String UBICACION = "ubicacion";
    private static final String NOMBRE_USUARIO = "nombre_usuario";
    private static final String RECORDAR_USER = "recordar_user";
    private static final String TOKEN_ID = "token_id";
    private static final String REGISTATION_ID = "registration_id";
    private static final String TELEFONO = "telefono_id";


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
    public static void setTokenId(Context context, String tokenId) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(TOKEN_ID, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TOKEN_ID, tokenId);
        editor.apply();
    }
    public static String getTokenId(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(TOKEN_ID, Context.MODE_PRIVATE);
        String tok = sharedPrefRead.getString(TOKEN_ID, "");
        return sharedPrefRead.getString(TOKEN_ID, "");
    }
    public static void setRegistationId(Context context, String tokenId) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(REGISTATION_ID, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(REGISTATION_ID, tokenId);
        editor.apply();
    }
    public static String getRegistationId(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(REGISTATION_ID, Context.MODE_PRIVATE);
        //String tok = sharedPrefRead.getString(REGISTATION_ID, "");
        return sharedPrefRead.getString(REGISTATION_ID, "");
    }

    public static void setTelefono(Context context, String tokenId) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(TELEFONO, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TELEFONO, tokenId);
        editor.apply();
    }
    public static String getTelefono(Context context) {
        android.content.SharedPreferences sharedPrefRead = context.getSharedPreferences(TELEFONO, Context.MODE_PRIVATE);
        //String tok = sharedPrefRead.getString(REGISTATION_ID, "");
        return sharedPrefRead.getString(TELEFONO, "");
    }
}