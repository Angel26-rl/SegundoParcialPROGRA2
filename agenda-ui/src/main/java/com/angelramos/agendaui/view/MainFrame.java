package com.angelramos.agendaui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.angelramos.agendacore.model.Cita;
import com.angelramos.agendacore.service.CitaService;

public class MainFrame extends JFrame {

    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private CitaService citaService;

    private JButton botonNueva;
    private JButton botonEditar;
    private JButton botonEliminar;
    
    private JLabel etiquetaPrecioMaximo;
    private JLabel etiquetaPrecioMinimo;

    public MainFrame() {
        citaService = new CitaService();

        configurarVentana();

        inicializarComponentes();

        cargarCitas();
    }

    private void configurarVentana() {

        setTitle("AgendaServicios");
        setSize(1000, 550);
        setMinimumSize(new Dimension(900, 500));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {

        // =====================================================
        // TÍTULO
        // =====================================================

        JLabel titulo = new JLabel("Gestión de citas");

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        );

        panelTitulo.add(titulo);

        JPanel panelSuperior = new JPanel(
                new BorderLayout()
        );

        panelSuperior.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelPrecios = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 20, 5)
        );

        etiquetaPrecioMaximo = new JLabel("Precio máximo: Q 0.00");
        etiquetaPrecioMinimo = new JLabel("Precio mínimo: Q 0.00");

        panelPrecios.add(etiquetaPrecioMaximo);
        panelPrecios.add(etiquetaPrecioMinimo);

        panelSuperior.add(panelPrecios, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // =====================================================
        // TABLA
        // =====================================================

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "Cliente",
                        "Fecha y hora",
                        "Servicio",
                        "Duración (min)",
                        "Precio",
                        "Estado"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCitas = new JTable(modeloTabla);

        tablaCitas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaCitas.setRowHeight(25);

        tablaCitas.getTableHeader().setReorderingAllowed(false);

        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(220);
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(120);

        DefaultTableCellRenderer centrado =
                new DefaultTableCellRenderer();

        centrado.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        tablaCitas.getColumnModel().getColumn(3)
                .setCellRenderer(centrado);

        tablaCitas.getColumnModel().getColumn(4)
                .setCellRenderer(centrado);

        tablaCitas.getColumnModel().getColumn(5)
                .setCellRenderer(centrado);

        tablaCitas.getSelectionModel().addListSelectionListener(e -> {

            boolean haySeleccion =
                    tablaCitas.getSelectedRow() != -1;

            botonEditar.setEnabled(haySeleccion);
            botonEliminar.setEnabled(haySeleccion);
        });

        JScrollPane scrollTabla = new JScrollPane(tablaCitas);

        JPanel panelTabla = new JPanel(new BorderLayout());

        panelTabla.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        panelTabla.add(
                scrollTabla,
                BorderLayout.CENTER
        );

        add(panelTabla, BorderLayout.CENTER);
   
        // =====================================================
        // BOTONES
        // =====================================================

        botonNueva = new JButton("Nueva cita");
        botonEditar = new JButton("Editar");
        botonEliminar = new JButton("Eliminar");

        botonEditar.setEnabled(false);
        botonEliminar.setEnabled(false);

        botonNueva.addActionListener(
                e -> abrirDialogoNuevaCita()
        );

        botonEditar.addActionListener(
                e -> editarCita()
        );

        botonEliminar.addActionListener(
                e -> eliminarCita()
        );

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        10
                )
        );

        panelBotones.add(botonNueva);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void abrirDialogoNuevaCita() {

        CitaDialog dialogo = new CitaDialog();

        dialogo.setVisible(true);

        cargarCitas();
    }

    private void cargarCitas() {

        try {

        	List<Cita> citas =
        	        citaService.listarCitas();

        	modeloTabla.setRowCount(0);

        	double precioMaximo = 0;
        	double precioMinimo = 0;

        	if (!citas.isEmpty()) {

        	    precioMaximo = citas.get(0).getPrecio();
        	    precioMinimo = citas.get(0).getPrecio();

        	    for (Cita cita : citas) {

        	        if (cita.getPrecio() > precioMaximo) {
        	            precioMaximo = cita.getPrecio();
        	        }

        	        if (cita.getPrecio() < precioMinimo) {
        	            precioMinimo = cita.getPrecio();
        	        }
        	    }
        	}

        	for (Cita cita : citas) {
        		

                Object[] fila = {

                        cita.getNombreCliente(),

                        cita.getFechaHora().format(
                                java.time.format.DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm")
                        ),

                        cita.getServicio(),

                        cita.getDuracionMinutos(),

                        String.format(
                                "Q %.2f",
                                cita.getPrecio()
                        ),

                        cita.getEstado()
                };

                modeloTabla.addRow(fila);
            }
        	
        	
        	etiquetaPrecioMaximo.setText(
        	        String.format("Precio máximo: Q %.2f", precioMaximo)
        	);

        	etiquetaPrecioMinimo.setText(
        	        String.format("Precio mínimo: Q %.2f", precioMinimo)
        	);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible cargar las citas.\n"
                    + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editarCita() {

        int filaSeleccionada =
                tablaCitas.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona una cita para editar.",
                    "Cita no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Cita> citas =
                    citaService.listarCitas();

            Cita cita =
                    citas.get(filaSeleccionada);

            CitaDialog dialogo =
                    new CitaDialog(cita);

            dialogo.setVisible(true);

            cargarCitas();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible cargar la cita.\n"
                    + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarCita() {

        int filaSeleccionada =
                tablaCitas.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona una cita para eliminar.",
                    "Cita no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Cita> citas =
                    citaService.listarCitas();

            Cita cita =
                    citas.get(filaSeleccionada);

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas eliminar la cita de "
                            + cita.getNombreCliente() + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            citaService.eliminarCita(
                    cita.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "La cita se eliminó correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarCitas();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible eliminar la cita.\n"
                    + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (
                com.angelramos.agendacore.exception.ValidacionException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}


