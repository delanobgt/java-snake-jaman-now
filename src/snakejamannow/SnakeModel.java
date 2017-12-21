package snakejamannow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SnakeModel {
    
    public static final int DELAY = 150;
    public static final int HEAD_EAT_BODY = 1;
    public static final int HEAD_TOUCH_WALL = 2;
    private static final int FOOD_STATUS = 1;
    private static final int BODY_STATUS = 2;
    private static final Map<Integer, Point> keyCodeToActionMap = new HashMap<>();
    
    private int[][] mapStatus = new int[20][20];
    private Deque<Point> bodyPoints = new LinkedList<>();
    private Integer currentKeyCode =  KeyEvent.VK_RIGHT;
    private Point foodPoint = new Point(19, 19);
    
    static {
        keyCodeToActionMap.put(KeyEvent.VK_UP, new Point(0, -1));
        keyCodeToActionMap.put(KeyEvent.VK_DOWN, new Point(0, 1));
        keyCodeToActionMap.put(KeyEvent.VK_LEFT, new Point(-1, 0));
        keyCodeToActionMap.put(KeyEvent.VK_RIGHT, new Point(1, 0));
    }
    
    public SnakeModel() {
        bodyPoints.addFirst(new Point(0, 0));
        generateNewFoodPoint();
    }
    
    public int update(int pressedKeyCode) {
        if (keyCodeToActionMap.keySet().contains(pressedKeyCode)) {
            if (bodyPoints.size() == 1) currentKeyCode = pressedKeyCode;
            else {
                Map<Integer, Integer> forbidden = new HashMap<>();
                forbidden.put(KeyEvent.VK_UP, KeyEvent.VK_DOWN);
                forbidden.put(KeyEvent.VK_DOWN, KeyEvent.VK_UP);
                forbidden.put(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
                forbidden.put(KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
                if (!forbidden.get(pressedKeyCode).equals(currentKeyCode))
                    currentKeyCode = pressedKeyCode;
            }
        }
            
        
        Point nextDirPoint = keyCodeToActionMap.get(currentKeyCode);
        Point nextHeadPoint = addPoint(bodyPoints.getFirst(), nextDirPoint);
        
        if (bodyPoints.contains(nextHeadPoint)) {
            return HEAD_EAT_BODY;
        } else if (nextHeadPoint.x < 0 || nextHeadPoint.x > 19
                || nextHeadPoint.y < 0 || nextHeadPoint.y > 19) {
            return HEAD_TOUCH_WALL;
        } else if (nextHeadPoint.equals(foodPoint)) {
            generateNewFoodPoint();
        } else {
            Point lastBodyPoint = bodyPoints.getLast();
            bodyPoints.removeLast();
            mapStatus[lastBodyPoint.x][lastBodyPoint.y] = 0;
        }
        
        bodyPoints.addFirst(nextHeadPoint);
        mapStatus[nextHeadPoint.x][nextHeadPoint.y] = BODY_STATUS;
        return 0;
    }
    
    public void draw(Graphics2D g) {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if (mapStatus[x][y] == BODY_STATUS) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x*25+1, y*25+1, 24, 24);
                } else if (mapStatus[x][y] == FOOD_STATUS) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x*25+1, y*25+1, 24, 24);
                }
                //empty cell
//                g.setColor(Color.LIGHT_GRAY);
//                g.drawRect(x*25, y*25, 25, 25);
            }
        }
        //snake's head
        Point headPoint = bodyPoints.getFirst();
        g.setColor(Color.MAGENTA);
        g.fillRect(headPoint.x*25+1, headPoint.y*25+1, 24, 24);
    }
    
    private void generateNewFoodPoint() {
        mapStatus[foodPoint.x][foodPoint.y] = 0;
        do {
            foodPoint = new Point(
                    Tool.getRandomIntegerInRange(0, 19),
                    Tool.getRandomIntegerInRange(0, 19)
            );
        } while (bodyPoints.contains(foodPoint));
        mapStatus[foodPoint.x][foodPoint.y] = FOOD_STATUS;
    }
    
    private Point addPoint(Point a, Point b) {
        return new Point(a.x+b.x, a.y+b.y);
    }
}
