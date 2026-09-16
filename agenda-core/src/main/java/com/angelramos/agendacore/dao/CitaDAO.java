package com.angelramos.agendacore.dao;

import java.sql.SQLException;
import java.util.List;

import com.angelramos.agendacore.model.Cita;

public interface CitaDAO {

    List<Cita> listar() throws SQLException;

    Cita buscarPorId(int id) throws SQLException;

    void guardar(Cita cita) throws SQLException;

    void actualizar(Cita cita) throws SQLException;

    void eliminar(int id) throws SQLException;
}
