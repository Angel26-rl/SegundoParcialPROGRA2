package com.angelramos.agendaui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.angelramos.agendacore.model.Cita;

import com.angelramos.agendacore.model.EstadoCita;

public class CitaDialog extends JDialog {

    private JTextField campoCliente;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JTextField campoServicio;
    private JTextField campoDuracion;

    private JComboBox<EstadoCita> comboEstado;
    private Cita citaExistente;

    private JButton botonGuardar;
    private JButton botonCancelar;

    public CitaDialog() {

        configurarVentana();
        inicializarComponentes();
    }

    public CitaDialog(Cita cita) {

        configurarVentana();
        inicializarComponentes();

        this.citaExistente = cita;

        cargarCita(cita);
    }
    

    private void configurarVentana() {

        setTitle("Nueva cita");

        setSize(450, 400);

        setLocationRelativeTo(null);

        setModal(true);

        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {

        // =====================================================
        // FORMULARIO
        // =====================================================

        JPanel panelFormulario = new JPanel(new GridBagLayout());

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 5, 15)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.fill = GridBagConstraints.HORIZONTAL;


        // =====================================================
        // CLIENTE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Cliente:"),
                gbc
        );

        campoCliente = new JTextField(20);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                campoCliente,
                gbc
        );


        // =====================================================
        // FECHA
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Fecha:"),
                gbc
        );

        campoFecha = new JTextField(10);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                campoFecha,
                gbc
        );


        // =====================================================
        // HORA
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Hora:"),
                gbc
        );

        campoHora = new JTextField(10);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                campoHora,
                gbc
        );


        // =====================================================
        // SERVICIO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Servicio:"),
                gbc
        );

        campoServicio = new JTextField(20);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                campoServicio,
                gbc
        );


        // =====================================================
        // DURACIÓN
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Duración (min):"),
                gbc
        );

        campoDuracion = new JTextField(10);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                campoDuracion,
                gbc
        );


        // =====================================================
        // ESTADO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.weightx = 0;

        panelFormulario.add(
                new JLabel("Estado:"),
                gbc
        );

        comboEstado = new JComboBox<>(
                EstadoCita.values()
        );

        comboEstado.setSelectedItem(
                EstadoCita.PENDIENTE
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        panelFormulario.add(
                comboEstado,
                gbc
        );


        add(
                panelFormulario,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTONES
        // =====================================================

        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarCita());

        botonCancelar = new JButton("Cancelar");

        botonCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 10)
        );

        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );
    }
    private void cargarCita(Cita cita) {

        if (cita == null) {
            return;
        }

        setTitle("Editar cita");

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

        comboEstado.setSelectedItem(cita.getEstado());
    }
    
    public Cita obtenerCita() {

        String nombreCliente = campoCliente.getText().trim();
        String fecha = campoFecha.getText().trim();
        String hora = campoHora.getText().trim();
        String servicio = campoServicio.getText().trim();

        int duracionMinutos =
                Integer.parseInt(campoDuracion.getText().trim());

        java.time.LocalDateTime fechaHora =
                java.time.LocalDateTime.parse(fecha + "T" + hora);

        EstadoCita estado =
                (EstadoCita) comboEstado.getSelectedItem();

        Cita cita = new Cita(
                nombreCliente,
                fechaHora,
                servicio,
                duracionMinutos
        );

        cita.setEstado(estado);

        return cita;
    }
    
    private void guardarCita() {

        try {

        	Cita cita = obtenerCita();

        	com.angelramos.agendacore.service.CitaService servicio =
        	        new com.angelramos.agendacore.service.CitaService();

        	if (citaExistente == null) {

        	    servicio.crearCita(cita);

        	} else {

        	    cita.setId(citaExistente.getId());

        	    servicio.actualizarCita(cita);
        	}
        	
        	
        	javax.swing.JOptionPane.showMessageDialog(
        	        this,
        	        citaExistente == null
        	                ? "La cita se creó correctamente."
        	                : "La cita se actualizó correctamente.",
                    "Éxito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (NumberFormatException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "La duración debe ser un número entero.",
                    "Dato inválido",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

        } catch (java.time.format.DateTimeParseException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "La fecha o la hora no tienen un formato válido.\n"
                    + "Fecha: AAAA-MM-DD\n"
                    + "Hora: HH:MM",
                    "Fecha u hora inválida",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

        } catch (com.angelramos.agendacore.exception.ValidacionException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Validación",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

        } catch (java.sql.SQLException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No fue posible guardar la cita en la base de datos.\n"
                    + e.getMessage(),
                    "Error de base de datos",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    
}