package com.example.lab7_20196324_iot.entity;

import java.io.Serializable;

public class CitasDto implements Serializable {


    private String id;
    private String correoCliente;
    private String servicio;
    private String fecha;
    private String hora;


    public CitasDto(){

    }
    public CitasDto(String id, String correoCliente, String servicio, String fecha, String hora){


        this.id=id;
        this.correoCliente=correoCliente;
        this.servicio=servicio;
        this.fecha=fecha;
        this.hora=hora;
    }
    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
