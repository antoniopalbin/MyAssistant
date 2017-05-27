package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Id;
import com.siimkinks.sqlitemagic.annotation.Table;

import java.util.Date;
/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Actividad {
    @Id(autoIncrement = true)
    public long id;
    public String idActividad;
    public Date timestamp;
    public Beacon beacon;

    public Actividad() {
        super();
    }

    public Actividad(String idActividad, Date timestamp, Beacon beacon) {
        super();
        this.idActividad = idActividad;
        this.timestamp = timestamp;
        this.beacon = beacon;
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

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
