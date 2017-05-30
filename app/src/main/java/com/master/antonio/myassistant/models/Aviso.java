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
    public String emails;

    public Aviso() {
        super();
    }

    public Aviso(String idAviso, boolean tipoAviso1, boolean tipoAviso2, boolean tipoAviso3, boolean notificarEmail, String emails) {
        super();
        this.idAviso = idAviso;
        this.tipoAviso1 = tipoAviso1;
        this.tipoAviso2 = tipoAviso2;
        this.tipoAviso3 = tipoAviso3;
        this.notificarEmail = notificarEmail;
        this.emails = emails;
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

    public boolean isNotificarEmail() { return notificarEmail;     }

    public void setNotificarEmail(boolean notificarEmail) {
        this.notificarEmail = notificarEmail;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
