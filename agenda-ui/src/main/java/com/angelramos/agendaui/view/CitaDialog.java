package com.angelramos.agendaui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.angelramos.agendacore.exception.ValidacionException;
import com.angelramos.agendacore.model.Cita;
import com.angelramos.agendacore.model.EstadoCita;
import com.angelramos.agendacore.service.CitaService;

public class CitaDialog extends JDialog {

    private JTextField campoCliente;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JTextField campoServicio;
    private JTextField campoDuracion;
    private JTextField campoPrecio;
    private JComboBox<EstadoCita> comboEstado;

    private CitaService citaService;
    private Cita citaEditar;

    public CitaDialog() {
        this(null);
    }

    public CitaDialog(Cita cita) {

        setTitle(cita == null ? "Nueva cita" : "Editar cita");
        setModal(true);
        setSize(450, 400);
        setLocationRelativeTo(null);

        citaService = new CitaService();
        citaEditar = cita;

        inicializarComponentes();

        if (citaEditar != null) {
            cargarCita(citaEditar);
        }
    }

    private void inicializarComponentes() {

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaCliente = new JLabel("Cliente:");
        campoCliente = new JTextField(20);

        JLabel etiquetaFecha = new JLabel("Fecha (yyyy-MM-dd):");
        campoFecha = new JTextField(20);

        JLabel etiquetaHora = new JLabel("Hora (HH:mm):");
        campoHora = new JTextField(20);

        JLabel etiquetaServicio = new JLabel("Servicio:");
        campoServicio = new JTextField(20);

        JLabel etiquetaDuracion = new JLabel("Duración (minutos):");
        campoDuracion = new JTextField(20);

        JLabel etiquetaPrecio = new JLabel("Precio:");
        campoPrecio = new JTextField(20);

        JLabel etiquetaEstado = new JLabel("Estado:");
        comboEstado = new JComboBox<>(EstadoCita.values());
        comboEstado.setSelectedItem(EstadoCita.PENDIENTE);

        JButton botonGuardar = new JButton("Guardar");
        JButton botonCancelar = new JButton("Cancelar");

        int fila = 0;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaCliente, gbc);

        gbc.gridx = 1;
        add(campoCliente, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaFecha, gbc);

        gbc.gridx = 1;
        add(campoFecha, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaHora, gbc);

        gbc.gridx = 1;
        add(campoHora, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaServicio, gbc);

        gbc.gridx = 1;
        add(campoServicio, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaDuracion, gbc);

        gbc.gridx = 1;
        add(campoDuracion, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaPrecio, gbc);

        gbc.gridx = 1;
        add(campoPrecio, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(etiquetaEstado, gbc);

        gbc.gridx = 1;
        add(comboEstado, gbc);

        fila++;

        gbc.gridx = 0;
        gbc.gridy = fila;
        add(botonGuardar, gbc);

        gbc.gridx = 1;
        add(botonCancelar, gbc);

        botonGuardar.addActionListener(e -> guardarCita());
        botonCancelar.addActionListener(e -> dispose());
    }

    private void cargarCita(Cita cita) {

        campoCliente.setText(cita.getNombreCliente());

        campoFecha.setText(
                cita.getFechaHora().toLocalDate().toString()
        );

        campoHora.setText(
                cita.getFechaHora().toLocalTime().toString()
        );

        campoServicio.setText(cita.getServicio());

        campoDuracion.setText(
                String.valueOf(cita.getDuracionMinutos())
        );

        campoPrecio.setText(
                String.valueOf(cita.getPrecio())
        );

        comboEstado.setSelectedItem(cita.getEstado());
    }

    private Cita obtenerCita() {

        String nombreCliente = campoCliente.getText();
        String fecha = campoFecha.getText();
        String hora = campoHora.getText();
        String servicio = campoServicio.getText();

        int duracion = Integer.parseInt(
                campoDuracion.getText()
        );

        double precio = Double.parseDouble(
                campoPrecio.getText()
        );

        LocalDateTime fechaHora = LocalDateTime.parse(
                fecha + "T" + hora
        );

        if (citaEditar == null) {

            return new Cita(
                    nombreCliente,
                    fechaHora,
                    servicio,
                    duracion,
                    precio
            );

        } else {

            citaEditar.setNombreCliente(nombreCliente);
            citaEditar.setFechaHora(fechaHora);
            citaEditar.setServicio(servicio);
            citaEditar.setDuracionMinutos(duracion);
            citaEditar.setPrecio(precio);
            citaEditar.setEstado(
                    (EstadoCita) comboEstado.getSelectedItem()
            );

            return citaEditar;
        }
    }

    private void guardarCita() {

        try {

            Cita cita = obtenerCita();

            if (citaEditar == null) {

                citaService.crearCita(cita);

                JOptionPane.showMessageDialog(
                        this,
                        "La cita se guardó correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                citaService.actualizarCita(cita);

                JOptionPane.showMessageDialog(
                        this,
                        "La cita se actualizó correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La duración y el precio deben ser valores numéricos.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha o la hora no tienen un formato válido.",
                    "Fecha u hora inválida",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (ValidacionException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible guardar la cita.\n"
                    + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}