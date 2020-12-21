package com.example.tfgv2;

import java.util.ArrayList;

public class Perfil {
    String usuario;
    String pass;
    ArrayList<Actividad> lst_Act;

    public Perfil(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

    public Perfil(String usuario, String pass, ArrayList<Actividad> lst_Act) {
        this.usuario = usuario;
        this.pass = pass;
        this.lst_Act = lst_Act;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ArrayList<Actividad> getLst_Act() {
        return lst_Act;
    }

    public void setLst_Act(ArrayList<Actividad> lst_Act) {
        this.lst_Act = lst_Act;
    }
}
