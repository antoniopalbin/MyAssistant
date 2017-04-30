package com.master.antonio.myassistant.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Antonio on 29/04/2017.
 */
@Table(name = "Aviso")
public class Aviso extends Model {
    @Column(name = "idAviso")
    public String idAviso;
    @Column(name = "tipoAviso1")
    public boolean tipoAviso1;
    @Column(name = "tipoAviso2")
    public boolean tipoAviso2;
    @Column(name = "tipoAviso3")
    public boolean tipoAviso3;
    @Column(name = "notificarEmail")
    public boolean notificarEmail;
    @Column(name = "notificarMovil")
    public boolean notificarMovil;
    @Column(name = "email")
    public String email;
    @Column(name = "movil")
    public String movil;
}
