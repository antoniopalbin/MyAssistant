package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Video {
    public String idVideo;
    public String titulo;
    public String idYoutube;
    public byte[] thumbnail;
    public String transcripcion;
    public String marca;
    public String modelo;

    public String getIdVideo() {
        return idVideo;
    }

    public Video() {
        super();
    }

    public Video(String idVideo, String titulo, String idYoutube, byte[] thumbnail, String transcripcion, String marca, String modelo) {
        super();
        this.idVideo = idVideo;
        this.titulo = titulo;
        this.idYoutube = idYoutube;
        this.thumbnail = thumbnail;
        this.transcripcion = transcripcion;
        this.marca = marca;
        this.modelo = modelo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdYoutube() {
        return idYoutube;
    }

    public void setIdYoutube(String idYoutube) {
        this.idYoutube = idYoutube;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTranscripcion() {
        return transcripcion;
    }

    public void setTranscripcion(String transcripcion) {
        this.transcripcion = transcripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
