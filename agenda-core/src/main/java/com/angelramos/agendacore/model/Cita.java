package com.angelramos.agendacore.model;

import java.time.LocalDateTime;

public class Cita {

    private int id;
    private String nombreCliente;
    private LocalDateTime fechaHora;
    private String servicio;
    private int duracionMinutos;
    private double precio;
    private EstadoCita estado;

    public Cita(String nombreCliente, LocalDateTime fechaHora, String servicio,
            int duracionMinutos, double precio) {

        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.estado = EstadoCita.PENDIENTE;
    }

    public Cita(String nombreCliente, LocalDateTime fechaHora, String servicio,
            int duracionMinutos) {

        this(nombreCliente, fechaHora, servicio, duracionMinutos, 0.00);
    }

    public Cita(int id, String nombreCliente, LocalDateTime fechaHora,
            String servicio, int duracionMinutos, double precio,
            EstadoCita estado) {

        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.estado = estado;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
}


