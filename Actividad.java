package com.example.tfgv2;

public class Actividad {
    private String key;//KeyActividad
    private String name;//Nombre de la actividad.
    private String description;//Descripcion de la actividad.
    private String startTime;//Fecha de inicio
    private String finishTime;//Fecha de fin
    private String permisos;//Permisos que se le deben dar
    public static String space = System.getProperty("line.separator");

    @Override
    public String toString() {
        return space
                + "Nombre:'" + name + space
                + "Description: " + description + space
                + "Fecha de Inicio : " + startTime + space
                + "Fecha de Finalización: " + finishTime + space
                + "¿Activar Notificaciones: " + permisos;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public Actividad(String Key, String name, String description, String startTime, String finishTime, String permisos) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.permisos = permisos;
    }

    public Actividad() {
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

}
