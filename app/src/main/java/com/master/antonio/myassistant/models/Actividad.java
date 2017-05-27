package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Table;

import java.util.Date;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Actividad {
    public String idActividad;
    public Date timestamp;
    public String idBeacon;

    public Actividad() {
        super();
    }

    public Actividad(String idActividad, Date timestamp, String idBeacon) {
        super();
        this.idActividad = idActividad;
        this.timestamp = timestamp;
        this.idBeacon = idBeacon;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdBeacon() {
        return idBeacon;
    }

    public void setIdBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
    }
}
