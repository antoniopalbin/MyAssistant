package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class VideoBeacon {
    public String idVideo;
    public String idBeacon;
    public String descripcion;
    public byte[] imagen;

    public VideoBeacon() {
        super();
    }

    public VideoBeacon(String idVideo, String idBeacon, String descripcion, byte[] imagen) {
        super();
        this.idVideo = idVideo;
        this.idBeacon = idBeacon;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getIdBeacon() {
        return idBeacon;
    }

    public void setIdBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
