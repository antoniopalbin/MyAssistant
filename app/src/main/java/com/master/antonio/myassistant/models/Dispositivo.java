package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Id;
import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Dispositivo {
    @Id(autoIncrement = true)
    public long id;
    public String idDispositivo;
    public String marca;
    public String modelo;
    public String idYoutube;
    public byte[] thumbnail;
    public byte[] foto;
    public String manual;
    public Beacon beacon;

    public Dispositivo() {
        super();
    }

    public Dispositivo(String idDispositivo, String marca, String modelo, String idYoutube, byte[] thumbnail, byte[] foto, String manual, Beacon beacon){
        super();

        this.idDispositivo = idDispositivo;
        this.marca = marca;
        this.modelo = modelo;
        this.idYoutube = idYoutube;
        this.thumbnail = thumbnail;
        this.foto = foto;
        this.manual = manual;
        this.beacon = beacon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
