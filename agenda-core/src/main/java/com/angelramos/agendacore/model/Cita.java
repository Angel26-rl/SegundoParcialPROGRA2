package com.angelramos.agendacore.model;

import java.time.LocalDateTime;

public class Cita {

    private int id;

    private String nombreCliente;

    private LocalDateTime fechaHora;

    private String servicio;

    private int duracionMinutos;

    private EstadoCita estado;

    public Cita(String nombreCliente, LocalDateTime fechaHora, String servicio, int duracionMinutos) {

        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.duracionMinutos = duracionMinutos;
        this.estado = EstadoCita.PENDIENTE;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Cita(int id, String nombreCliente, LocalDateTime fechaHora, String servicio,
            int duracionMinutos, EstadoCita estado) {

        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;

    }

}

