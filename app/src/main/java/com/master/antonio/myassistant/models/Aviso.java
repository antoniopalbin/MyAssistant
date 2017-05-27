package com.master.antonio.myassistant.models;

import com.siimkinks.sqlitemagic.annotation.Id;
import com.siimkinks.sqlitemagic.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(persistAll = true)
public class Aviso {
    @Id(autoIncrement = true)
    public long id;
    public String idAviso;
    public boolean tipoAviso1;
    public boolean tipoAviso2;
    public boolean tipoAviso3;
    public boolean notificarEmail;
    public boolean notificarMovil;
    public String email;
    public String movil;

    public Aviso() {
        super();
    }

    public Aviso(String idAviso, boolean tipoAviso1, boolean tipoAviso2, boolean tipoAviso3, boolean notificarEmail, boolean notificarMovil, String email, String movil) {
        super();
        this.idAviso = idAviso;
        this.tipoAviso1 = tipoAviso1;
        this.tipoAviso2 = tipoAviso2;
        this.tipoAviso3 = tipoAviso3;
        this.notificarEmail = notificarEmail;
        this.notificarMovil = notificarMovil;
        this.email = email;
        this.movil = movil;
    }

    public String getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(String idAviso) {
        this.idAviso = idAviso;
    }

    public boolean isTipoAviso1() {
        return tipoAviso1;
    }

    public void setTipoAviso1(boolean tipoAviso1) {
        this.tipoAviso1 = tipoAviso1;
    }

    public boolean isTipoAviso2() {
        return tipoAviso2;
    }

    public void setTipoAviso2(boolean tipoAviso2) {
        this.tipoAviso2 = tipoAviso2;
    }

    public boolean isTipoAviso3() {
        return tipoAviso3;
    }

    public void setTipoAviso3(boolean tipoAviso3) {
        this.tipoAviso3 = tipoAviso3;
    }

    public boolean isNotificarEmail() {
        return notificarEmail;
    }

    public void setNotificarEmail(boolean notificarEmail) {
        this.notificarEmail = notificarEmail;
    }

    public boolean isNotificarMovil() {
        return notificarMovil;
    }

    public void setNotificarMovil(boolean notificarMovil) {
        this.notificarMovil = notificarMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }
}
