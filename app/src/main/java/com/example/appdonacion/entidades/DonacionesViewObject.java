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
    private String direccion;
    private String nombreUsuario;
    private String nombreUbi;
    private Double longitud;
    private Double latitud;


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
}
