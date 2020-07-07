
import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class Prim_Vertex {
    
    private int xCoordinate, yCoordinate;
    
    private final int SIZE = 8; 
    
    private String textpoin="diem";
    
    private final int SELECTED_SIZE = 12; 
    
    private Boolean selected = false; 
    
 
    public Prim_Vertex(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
    
   
    public int getX() {
        return xCoordinate;
    }
    
  
    public int getY() {
        return yCoordinate;
    }
    
    public String gettextpoin() {
        return textpoin;
    }
        
 
    public void settextpoin(String a) {
        textpoin = a;
    }
   
    public int getWeight(Prim_Edge[] e, Prim_Vertex v) {
        if (v != null) {
            boolean matchFound = false;
            int count = 0;
            while (!matchFound) {
                if (e[count].checkEndpoints(this, v)) {
                    matchFound = true;
                    return e[count].getWeight();
                }
                count++;
            }
        }
        return 1000;
    }
    
    
    public Prim_Edge getEdge(Prim_Edge[] e, Prim_Vertex v) {
        Prim_Edge match = null;
        boolean matchFound = false;
        int count = 0;
        while ((!matchFound) && (count < e.length)) {
            if (e[count].checkEndpoints(this, v)) {
                match = e[count];
                matchFound = true;
            }
            count++;
        }
        return match;
    }
    
  
    public void setCoordinates(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
    
  
    public void selected() {
        if (selected)
            selected = false;
        else
            selected = true;
    }
    
   
    public void selected(Boolean b) {
        selected = b;
    }
    
    
    public Boolean isSelected() {
        return selected;
    }
    
  
    public boolean isNearTo(Point p) {
        boolean near = false;
        int lowXbound = (xCoordinate - 20);
        int upXbound = (xCoordinate + 20);
        int lowYbound = (yCoordinate - 20);
        int upYbound = (yCoordinate + 20);
        if (((p.x >= lowXbound) && (p.x <= upXbound)) && ((p.y >= lowYbound) && 
                (p.y <= upYbound))) {
            near = true;
        }
        return near;
    }
   
    
  
    public void draw(Graphics page) {
        if (selected) {
            page.setColor(Color.red);
            page.fillOval(xCoordinate, yCoordinate, SELECTED_SIZE, 
                    SELECTED_SIZE);
        }
        else {
            page.setColor(Color.lightGray);
            page.fillOval(xCoordinate, yCoordinate, SIZE, SIZE);
        }
        page.drawString(textpoin, (int) (xCoordinate -7), (int) yCoordinate-5);
        
    } 
    
    
}
