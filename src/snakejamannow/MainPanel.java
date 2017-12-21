package snakejamannow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    
    private SnakeModel snakeModel;
    private int keyCodePressed;
    
    public MainPanel() {
        snakeModel = new SnakeModel();
        
        setPreferredSize(new Dimension(500, 500));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyCodePressed = e.getKeyCode();
            }
        });
    }
    
    public void start() {
        Thread thread = new Thread(() -> {
            while (true) {
                long beginTime = System.currentTimeMillis();
                update();
                repaint();
                long duration = System.currentTimeMillis()-beginTime;
                long realDelay = SnakeModel.DELAY-duration;
                sleep(realDelay);
            }
        });
        thread.start();
    }
    
    public void update() {
        int updateCode = snakeModel.update(keyCodePressed);
        if (updateCode == SnakeModel.HEAD_EAT_BODY) {
            JOptionPane.showMessageDialog(
                    null, 
                    "You ate your own body",
                    "Game Over", 
                    JOptionPane.OK_OPTION
            );
            System.exit(0);
        } else if (updateCode == SnakeModel.HEAD_TOUCH_WALL) {
            JOptionPane.showMessageDialog(
                    null, 
                    "You crashed your head to the wall", 
                    "Game Over", 
                    JOptionPane.OK_OPTION
            );
            System.exit(0);
        }
    }
    
    @Override
    public void paint(Graphics oldG) {
        super.paint(oldG);
        Graphics2D g = (Graphics2D)oldG;
        snakeModel.draw(g);
    }
    
    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {}
    }
}
