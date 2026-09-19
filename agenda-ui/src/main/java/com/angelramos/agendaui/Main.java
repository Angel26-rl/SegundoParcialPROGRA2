package com.angelramos.agendaui;

import javax.swing.SwingUtilities;

import com.angelramos.agendaui.view.MainFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainFrame ventana = new MainFrame();

            ventana.setVisible(true);
        });
    }
}


