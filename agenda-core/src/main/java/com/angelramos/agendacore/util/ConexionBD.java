package com.angelramos.agendacore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/agenda_servicios";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "TU_CONTRASEÑA";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}

