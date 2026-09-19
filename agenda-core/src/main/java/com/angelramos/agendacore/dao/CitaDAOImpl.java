package com.angelramos.agendacore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.angelramos.agendacore.model.Cita;
import com.angelramos.agendacore.model.EstadoCita;
import com.angelramos.agendacore.util.ConexionBD;

public class CitaDAOImpl implements CitaDAO {

    @Override
    public List<Cita> listar() throws SQLException {

        String sql = "SELECT id, nombre_cliente, fecha_hora, servicio, "
                + "duracion_minutos, precio, estado FROM citas";

        List<Cita> citas = new ArrayList<>();

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
                + "duracion_minutos, precio, estado FROM citas WHERE id = ?";

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
                + "(nombre_cliente, fecha_hora, servicio, duracion_minutos, precio, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cita.getNombreCliente());
            sentencia.setTimestamp(2, Timestamp.valueOf(cita.getFechaHora()));
            sentencia.setString(3, cita.getServicio());
            sentencia.setInt(4, cita.getDuracionMinutos());
            sentencia.setDouble(5, cita.getPrecio());
            sentencia.setString(6, cita.getEstado().name());

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
                + "precio = ?, "
                + "estado = ? "
                + "WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cita.getNombreCliente());
            sentencia.setTimestamp(2, Timestamp.valueOf(cita.getFechaHora()));
            sentencia.setString(3, cita.getServicio());
            sentencia.setInt(4, cita.getDuracionMinutos());
            sentencia.setDouble(5, cita.getPrecio());
            sentencia.setString(6, cita.getEstado().name());
            sentencia.setInt(7, cita.getId());

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
        Timestamp timestamp = resultado.getTimestamp("fecha_hora");
        String servicio = resultado.getString("servicio");
        int duracionMinutos = resultado.getInt("duracion_minutos");
        double precio = resultado.getDouble("precio");
        EstadoCita estado = EstadoCita.valueOf(resultado.getString("estado"));

        return new Cita(
                id,
                nombreCliente,
                timestamp.toLocalDateTime(),
                servicio,
                duracionMinutos,
                precio,
                estado
        );
    }
}
