package com.master.antonio.myassistant.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(name = "VideoBeacon")
public class VideoBeacon extends Model {
    @Column(name = "idVideo")
    public String idVideo;
    @Column(name = "idBeacon")
    public String idBeacon;
    @Column(name = "descripcion")
    public String descripcion;
    @Column(name = "imagen")
    public byte[] imagen;
}
