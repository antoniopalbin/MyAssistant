package com.master.antonio.myassistant.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(name = "Video")
public class Video extends Model{
    @Column(name = "idVideo")
    public String idVideo;
    @Column(name = "titulo")
    public String titulo;
    @Column(name = "idYoutube")
    public String idYoutube;
    @Column(name = "thumbnail")
    public byte[] thumbnail;
    @Column(name = "transcripcion")
    public String transcripcion;
    @Column(name = "marca")
    public String marca;
    @Column(name = "modelo")
    public String modelo;
}
