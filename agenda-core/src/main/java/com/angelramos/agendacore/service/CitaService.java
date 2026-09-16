package com.angelramos.agendacore.service;

import java.sql.SQLException;
import java.util.List;

import com.angelramos.agendacore.dao.CitaDAO;
import com.angelramos.agendacore.dao.CitaDAOImpl;
import com.angelramos.agendacore.exception.ValidacionException;
import com.angelramos.agendacore.model.Cita;

public class CitaService {

    private final CitaDAO citaDAO;

    public CitaService() {
        this.citaDAO = new CitaDAOImpl();
    }

    public List<Cita> listarCitas() throws SQLException {
        return citaDAO.listar();
    }

    public Cita buscarCitaPorId(int id) throws SQLException {
        return citaDAO.buscarPorId(id);
    }
    
    public void crearCita(Cita cita) throws SQLException, ValidacionException {

        validarCita(cita, true);

        citaDAO.guardar(cita);
    }

    private void validarCita(Cita cita, boolean esNueva) throws ValidacionException {

        if (cita == null) {
            throw new ValidacionException("La cita no puede ser nula.");
        }

        if (cita.getNombreCliente() == null
                || cita.getNombreCliente().trim().isEmpty()) {
            throw new ValidacionException(
                    "El nombre del cliente no puede estar vacío.");
        }

        if (cita.getServicio() == null
                || cita.getServicio().trim().isEmpty()) {
            throw new ValidacionException(
                    "La descripción del servicio no puede estar vacía.");
        }

        if (cita.getFechaHora() == null) {
            throw new ValidacionException(
                    "La fecha y hora de la cita son obligatorias.");
        }

        if (esNueva && cita.getFechaHora().isBefore(java.time.LocalDateTime.now())) {
            throw new ValidacionException(
                    "La fecha y hora no pueden estar en el pasado.");
        }

        if (cita.getDuracionMinutos() <= 0) {
            throw new ValidacionException(
                    "La duración debe ser mayor que 0 minutos.");
        }

        if (cita.getEstado() == null) {
            throw new ValidacionException(
                    "El estado de la cita es obligatorio.");
        }
    }
    
    public void actualizarCita(Cita cita) throws SQLException, ValidacionException {

        validarCita(cita, false);

        if (cita.getId() <= 0) {
            throw new ValidacionException(
                    "El ID de la cita debe ser válido.");
        }

        citaDAO.actualizar(cita);
    }
    
    public void eliminarCita(int id) throws SQLException, ValidacionException {

        if (id <= 0) {
            throw new ValidacionException(
                    "El ID de la cita debe ser válido.");
        }

        citaDAO.eliminar(id);
    }
}


