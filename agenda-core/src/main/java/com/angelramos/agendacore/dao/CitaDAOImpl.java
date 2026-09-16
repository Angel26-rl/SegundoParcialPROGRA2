package com.angelramos.agendacore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.angelramos.agendacore.model.Cita;
import com.angelramos.agendacore.model.EstadoCita;
import com.angelramos.agendacore.util.ConexionBD;

public class CitaDAOImpl implements CitaDAO {

    @Override
    public List<Cita> listar() throws SQLException {

        List<Cita> citas = new ArrayList<>();

        String sql = "SELECT id, nombre_cliente, fecha_hora, servicio, "
                   + "duracion_minutos, estado FROM citas";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                citas.add(convertirCita(resultado));
            }
        }

        return citas;
    }

    @Override
    public Cita buscarPorId(int id) throws SQLException {

        String sql = "SELECT id, nombre_cliente, fecha_hora, servicio, "
                   + "duracion_minutos, estado FROM citas WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {
                    return convertirCita(resultado);
                }
            }
        }

        return null;
    }

    @Override
    public void guardar(Cita cita) throws SQLException {

        String sql = "INSERT INTO citas "
                   + "(nombre_cliente, fecha_hora, servicio, duracion_minutos, estado) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cita.getNombreCliente());
            sentencia.setTimestamp(2, java.sql.Timestamp.valueOf(cita.getFechaHora()));
            sentencia.setString(3, cita.getServicio());
            sentencia.setInt(4, cita.getDuracionMinutos());
            sentencia.setString(5, cita.getEstado().name());

            sentencia.executeUpdate();
        }
    }

    @Override
    public void actualizar(Cita cita) throws SQLException {

        String sql = "UPDATE citas SET "
                   + "nombre_cliente = ?, "
                   + "fecha_hora = ?, "
                   + "servicio = ?, "
                   + "duracion_minutos = ?, "
                   + "estado = ? "
                   + "WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cita.getNombreCliente());
            sentencia.setTimestamp(2, java.sql.Timestamp.valueOf(cita.getFechaHora()));
            sentencia.setString(3, cita.getServicio());
            sentencia.setInt(4, cita.getDuracionMinutos());
            sentencia.setString(5, cita.getEstado().name());
            sentencia.setInt(6, cita.getId());

            sentencia.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM citas WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            sentencia.executeUpdate();
        }
    }

    private Cita convertirCita(ResultSet resultado) throws SQLException {

        int id = resultado.getInt("id");
        String nombreCliente = resultado.getString("nombre_cliente");
        java.time.LocalDateTime fechaHora =
                resultado.getTimestamp("fecha_hora").toLocalDateTime();
        String servicio = resultado.getString("servicio");
        int duracionMinutos = resultado.getInt("duracion_minutos");

        EstadoCita estado =
                EstadoCita.valueOf(resultado.getString("estado"));

        return new Cita(
                id,
                nombreCliente,
                fechaHora,
                servicio,
                duracionMinutos,
                estado
        );
    }
}