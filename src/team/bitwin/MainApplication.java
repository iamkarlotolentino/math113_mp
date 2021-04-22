package team.bitwin;

import team.bitwin.ui.MainWindow;

import javax.swing.*;

public class MainApplication {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("A problem occurred while initializing system look and feel");
        }

        new MainWindow().createWindow();
    }
}
