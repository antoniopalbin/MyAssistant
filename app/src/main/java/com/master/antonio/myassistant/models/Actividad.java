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
    public long timestamp;
    public Beacon beacon;

    public Actividad() {
        super();
    }

    public Actividad(long timestamp, Beacon beacon) {
        super();
        this.timestamp = timestamp;
        this.beacon = beacon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
