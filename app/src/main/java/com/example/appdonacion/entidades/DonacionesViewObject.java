package com.example.appdonacion.entidades;

import java.io.Serializable;

public class DonacionesViewObject implements Serializable {
    //Parametros
    private String nombre;
    private String info;
    private int imagenId;
    private String descripcionDetallada;
    private int imagenDetallada;
    private String urlImagen;
    private String registrationToken;
    private String direccion;
    private String detalles;
    private String cantidad;
    private String nombreUsuario;
    private String nombreUbi;
    private String correo;
    private Double longitud;
    private Double latitud;
    private String tokenID;
    private String telefono;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalle) {
        this.detalles = detalle;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DonacionesViewObject() {
    }

    public DonacionesViewObject(String nombre, String info, String descripcionDetallada, int imagenId, int imagenDetallada) {
        this.nombre = nombre;
        this.info = info;
        this.descripcionDetallada = descripcionDetallada;
        this.imagenId = imagenId;
        this.imagenDetallada = imagenDetallada;
    }

    public String getDescripcionDetallada() {
        return descripcionDetallada;
    }

    public void setDescripcionDetallada(String descripcionDetallada) {
        this.descripcionDetallada = descripcionDetallada;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUbi() {
        return nombreUbi;
    }

    public void setNombreUbi(String nombreUbi) {
        this.nombreUbi = nombreUbi;
    }

    public int getImagenDetallada() {
        return imagenDetallada;
    }

    public void setImagenDetallada(int imagenDetallada) {
        this.imagenDetallada = imagenDetallada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public int getImagenId() {
        return imagenId;
    }

    public void setImagenId(int imagenId) {
        this.imagenId = imagenId;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}