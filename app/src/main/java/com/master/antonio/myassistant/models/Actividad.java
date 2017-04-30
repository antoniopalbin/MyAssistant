package com.master.antonio.myassistant.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(name = "Actividad")
public class Actividad extends Model {
    @Column(name = "idActividad")
    public String idActividad;
    @Column(name = "timestamp")
    public Date timestamp;
    @Column(name = "idBeacon")
    public String idBeacon;
}
