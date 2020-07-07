
import java.awt.*;
import java.awt.Font;
import java.awt.Polygon;

public class Prim_Edge {
    
    private Prim_Vertex start, end;
    
    private double xMidpoint, yMidpoint, angle;
    
    private int weight = 1;
    
    private Polygon area;
    
    private Boolean selected = false, showSelectionArea = false,
            MSTcolour = false;
    
    private Font myFont = new Font("Serif", Font.BOLD, 12);
    
   
    public Prim_Edge(Prim_Vertex v, Prim_Vertex w) {
        if (!(v.equals(w))) {
            if (v.getX() < w.getX()) {
                start = v;
                end = w;
            }
            else {
                start = w;
                end = v;
            }
        }
        
        if (end.getX() == start.getX()) 
            end.setCoordinates(end.getX() + 1, end.getY());
        
        xMidpoint = start.getX() + ((end.getX() - start.getX()) / 2.0);
        yMidpoint = start.getY() + ((end.getY() - start.getY()) / 2.0);
        angle = (Math.PI / 2.0) - (Math.atan((end.getY() - start.getY()) / 
                (end.getX() - start.getX()))); 
        
     
        double negLow, negUp, posLow, posUp;
        negLow = (Math.PI) / (-3.0) - 0.3;
        negUp = (Math.PI) / (-3.0) + 0.3;
        posLow = (Math.PI) / 3.0 - 0.3;
        posUp = (Math.PI) / 3.0 + 0.3;
        
        if (((negLow < angle) && (negUp > angle)) || ((posLow < angle) && (posUp > angle)))
            angle =+ 0.8;
        
        int[] xpoints = new int[4];
        int[] ypoints = new int[4];
        int npoints = 4;
        xpoints[0] = start.getX();
        xpoints[1] = (int)((10 * Math.cos(angle)) + xMidpoint);
        xpoints[2] = end.getX();
        xpoints[3] = (int)(((-10) * Math.cos(angle)) + xMidpoint);
        ypoints[0] = start.getY();
        ypoints[1] = (int)((10 * Math.sin(angle)) + yMidpoint);
        ypoints[2] = end.getY();
        ypoints[3] = (int)(((-10) * Math.sin(angle)) + yMidpoint);
        
        area = new Polygon(xpoints, ypoints, npoints); 
    }
    
    
    public Boolean checkEndpoints(Prim_Vertex v) {
        if (v.equals(start) || v.equals(end))
            return true;
        return false;
    }
    
   
    public Boolean checkEndpoints(Prim_Vertex v, Prim_Vertex w) {
        if ((v.equals(start) && w.equals(end)) || ((v.equals(end) && w.equals(start))))
            return true;
        return false;
    }
    
   
    public Prim_Vertex getOtherEndpoint(Prim_Vertex v) {
        if (start.equals(v))
            return end;
        else
            return start;
    }
    
  
    public Prim_Vertex getStart() {
        return start;
    }
    
    
    public Prim_Vertex getEnd() {
        return end;
    }
    
  
    public int getWeight() {
        return weight;
    }
        
 
    public void setWeight(int a) {
        weight = a;
    }
    
   
    public int getMidX() {
        return (int) xMidpoint;
    }
    
  
    public int getMidY() {
        return (int) yMidpoint;
    }
    
   
    public Boolean isNearTo(Point p) {
        if (area.contains(p.x, p.y))
            return true;
        else
            return false;
    }
    
   
    public Boolean isWithin(Prim_Edge[] e) {
        int index = e.length;
        for (int count=0; count < index; count++)
            if (this.equals(e[count]))
                return true;
        return false;
    }
    
  
    public void selected(Boolean b) {
        selected = b;
    }
    
   
    public Boolean isSelected() {
        if (selected)
            return true;
        else
            return false;
    }
    
   
    public void showSelectionArea() {
        if (showSelectionArea)
            showSelectionArea = false;
        else
            showSelectionArea = true;
    }
    
   
    public void showSelectionArea(Boolean b) {
        showSelectionArea = b;
    }
    
   
    public boolean isShowingArea() {
        return showSelectionArea;
    }
    
   
    public void MSTcolour(Boolean b) {
        MSTcolour = b;
    }
    
    
    public void draw(Graphics page) {    
        page.setFont(myFont);
        
        if (MSTcolour) {
            page.setColor(Color.GREEN);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            page.setColor(Color.MAGENTA);
            page.drawString(("" + weight), (int) (xMidpoint - 7), (int) yMidpoint);
            return;
        }
        
        if (showSelectionArea) {
            int[] xpoints = new int[4];
            int[] ypoints = new int[4];
            int npoints = 4;
            xpoints[0] = start.getX();
            xpoints[1] = (int)((10 * Math.cos(angle)) + xMidpoint);
            xpoints[2] = end.getX();
            xpoints[3] = (int)(((-10) * Math.cos(angle)) + xMidpoint);
            ypoints[0] = start.getY();
            ypoints[1] = (int)((10 * Math.sin(angle)) + yMidpoint);
            ypoints[2] = end.getY();
            ypoints[3] = (int)(((-10) * Math.sin(angle)) + yMidpoint);
            if (selected) {
            page.setColor(Color.PINK);
            page.fillPolygon(xpoints, ypoints, npoints);
            }
            else {
            page.setColor(Color.CYAN);
            page.fillPolygon(xpoints, ypoints, npoints);
            }    
        }
        
        if (selected) {
            page.setColor(Color.RED);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        else {
            page.setColor(Color.BLUE);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        
        page.setColor(Color.BLACK);
        page.drawString(("" + weight), (int) (xMidpoint - 7), (int) yMidpoint);
    }
}
