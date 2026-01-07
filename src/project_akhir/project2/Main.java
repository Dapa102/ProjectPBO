package project_akhir.project2;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.setVisible(true);
        });
    }
}
