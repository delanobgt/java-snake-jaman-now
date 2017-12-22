package snakejamannow;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    
    private MainPanel mainPanel;
    
    public MainFrame() {
        super("Snake Jaman Now");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        this.add(mainPanel);
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public void start() {
        this.setVisible(true);
        mainPanel.start();
    }
}
