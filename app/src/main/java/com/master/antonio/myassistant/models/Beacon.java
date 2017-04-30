package com.master.antonio.myassistant.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(name = "Beacon")
public class Beacon extends Model {
    @Column(name = "idBeacon")
    public String idBeacon;
    @Column(name = "estancia")
    public String estancia;
    @Column(name = "descripcion")
    public String descripcion;
    @Column(name = "icono")
    public byte[] icono;
}
