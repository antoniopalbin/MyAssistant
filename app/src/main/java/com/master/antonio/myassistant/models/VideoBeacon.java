package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Id;
import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class VideoBeacon {
    @Id(autoIncrement = true)
    public long id;
    public Video video;
    public Beacon beacon;
    public String descripcion;
    public byte[] imagen;

    public VideoBeacon() {
        super();
    }

    public VideoBeacon(Video video, Beacon beacon, String descripcion, byte[] imagen) {
        super();
        this.video = video;
        this.beacon = beacon;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(String idVideo) {
        this.video = video;
    }

    public Beacon getIdBeacon() {
        return beacon;
    }

    public void setIdBeacon(Beacon beacon) {
        this.beacon = beacon;
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
