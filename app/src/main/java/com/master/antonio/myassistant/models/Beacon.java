package com.master.antonio.myassistant.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Beacon {
    public String idBeacon;
    public String estancia;
    public String descripcion;
    public byte[] icono;

    public Beacon() {
        super();
    }

    public Beacon(String idBeacon, String estancia, String descripcion, byte[] icono) {
        super();
        this.idBeacon = idBeacon;
        this.estancia = estancia;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    public String getIdBeacon() {
        return idBeacon;
    }

    public void setIdBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
    }

    public String getEstancia() {
        return estancia;
    }

    public void setEstancia(String estancia) {
        this.estancia = estancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getIcono() {
        return icono;
    }

    public void setIcono(byte[] icono) {
        this.icono = icono;
    }
}
