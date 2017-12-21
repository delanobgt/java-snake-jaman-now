package snakejamannow;

import javax.swing.SwingUtilities;

public class SnakeJamanNow {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.start();
        });
    }
    
}
