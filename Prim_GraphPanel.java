

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Prim_GraphPanel extends JPanel {

    private final Prim_Vertex[] vertices = new Prim_Vertex[100];
    
    private Prim_Edge[] edges = new Prim_Edge[100];
    
    private int vCount = 0, eCount = 0; 
    
    private int next = 1;
    
    int trongso = 0;
    
    private boolean selectionEmpty = true, userDragging = false,
            edgeSelected = false, userClickedAway = true;
    
    private Point dragPoint = null, releasePoint = null,
            pressPoint = null;
    
    private JButton clear, clearEdges, showSelectionArea, prim;
    
    private JTextField weight;
    
    
    private JLabel weightPrompt;
    
    private JLabel trongso1,nhantrongso;
    
    private Font myFont = new Font("Serif", Font.BOLD, 14);
 
    public Prim_GraphPanel() {
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        CoordinatesListener listener = new CoordinatesListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        ButtonListener buttonListener = new ButtonListener();
        clear = new JButton("Xoa canh va nut");
        clear.addActionListener(buttonListener);
        clearEdges = new JButton("Xoa canh");
        clearEdges.addActionListener(buttonListener);
        
        prim = new JButton("Tim cay bao trum nho nhat");
        prim.addActionListener(buttonListener);
        
        weightPrompt = new JLabel("Dien trong so");
        
        nhantrongso = new JLabel("Trong so cay bao trum nho nhat: ");
        trongso1 = new JLabel(" ");
        
        KeyListener keyListener = new KeyListener();
        weight = new JTextField(5);
        weight.addActionListener(keyListener);
        
       
        
        setBackground(Color.white);
        setPreferredSize(new Dimension(1200, 600));
        
        add(prim);
        add(clear);
        add(clearEdges);
      

        add(weightPrompt);
        add(weight);
        add(nhantrongso);
        add(trongso1);
        
       
    }
    
    
    public void PrimMST() {
        int startV = (int) (Math.random() * vCount); 
        int iterations = 0, fCount = 0, action, MSTvCount = 0, nonMSTvCount = 0;
        Prim_Vertex currentV = vertices[startV], v, a, b;
        Prim_Edge bestEdge = null, e; 
        
      
        Prim_Vertex[] MSTv = new Prim_Vertex[1000];
        Prim_Vertex[] nonMSTv = new Prim_Vertex[1000];
        Prim_Edge[] MSTe = new Prim_Edge[vCount - 1];
        Prim_Edge[] fringe = new Prim_Edge[1000];
        
       
        while (iterations < vCount - 1) {
        action = 1;
        
        fringe = new Prim_Edge[1000];
        fCount = 0;
        MSTv = new Prim_Vertex[1000];
        MSTvCount = 0;
        nonMSTv = new Prim_Vertex[1000];
        nonMSTvCount = 0;
        
        if (iterations == 0) {
            action = 0;
            for (int count=0; count < eCount; count++) {
                if (edges[count].checkEndpoints(currentV)) {
                    v = edges[count].getOtherEndpoint(currentV);
                    fringe[fCount] = currentV.getEdge(edges, v);
                    fCount++;
                }
            }
        }
        
       
        switch (action) {
            case 1:
                
               
                MSTv[MSTvCount] = MSTe[0].getStart();
                MSTvCount++;
                MSTv[MSTvCount] = MSTe[0].getEnd();
                MSTvCount++;
                for (int count1=1; count1 < iterations; count1++) {
                    a = MSTe[count1].getStart();
                    b = MSTe[count1].getEnd();
                    boolean adda, addb;
                    adda = true; 
                    addb = true;
                    for (int count2=0; count2 <= iterations; count2++) {    
                        if (MSTv[count2] != null) {
                            if (MSTv[count2].equals(a))
                                adda = false;
                            if (MSTv[count2].equals(b))
                                addb = false;
                        }
                    }
                    if (adda) {
                        MSTv[MSTvCount] = a;
                        MSTvCount++;
                    }
                    if (addb) {
                        MSTv[MSTvCount] = b;
                        MSTvCount++;
                    }
                }
                
           
                for (int count1=0; count1 < vCount; count1++) {
                    boolean addMe = true;
                    for (int count2=0; count2 < MSTvCount; count2++) {
                        if (vertices[count1].equals(MSTv[count2]))
                            addMe = false;
                    }
                    if (addMe) {
                        nonMSTv[nonMSTvCount] = vertices[count1];
                        nonMSTvCount++;
                    }
                }
                
                
                for (int count1=0; count1 < eCount; count1++) {
                    e = edges[count1];
                    for (int count2=0; count2 < MSTvCount; count2++) {
                        a = MSTv[count2];
                        for (int count3=0; count3 < nonMSTvCount; count3++) {
                            b = nonMSTv[count3];
                            if (e.checkEndpoints(a, b)) {
                                fringe[fCount] = a.getEdge(edges, b);
                                fCount++;
                                
                            }
                        }
                    }
                }
                break;
            
            default:
                break;
        }
        
      
        bestEdge = fringe[0];
        for (int count=0; count < fCount; count++) {
            if (bestEdge.getWeight() > fringe[count].getWeight())
                bestEdge = fringe[count];
        }
            
        MSTe[iterations] = bestEdge;
        iterations++;
        }
        
        for(int i=0;i<iterations;i++) {
        	trongso += MSTe[i].getWeight();
        }
        String trongso2 = String.valueOf(trongso);
        trongso1.setText(trongso2);
        
     
        edges = MSTe;
        eCount = vCount - 1;
        for (int count = 0; count < eCount; count++)
            edges[count].MSTcolour(true);
        repaint();
    } 
    
 
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        page.setColor(Color.red);
        
        for (int count=0; count < vCount; count++) 
            vertices[count].draw(page);    
        
        for (int count=0; count < eCount; count++) 
            edges[count].draw(page);
        
 
        if (dragPoint != null) {
            for (int count=0; count < vCount; count++)
                if (vertices[count].isSelected()) 
                    page.drawLine(vertices[count].getX(), 
                    vertices[count].getY(), dragPoint.x, dragPoint.y);
        }
        
        page.setFont(myFont);
        page.setColor(Color.red);
        
    }
    
   
    private class CoordinatesListener implements MouseListener,
                                                 MouseMotionListener {
        
     
        public void mousePressed(MouseEvent event) {
            pressPoint = event.getPoint();
           
            selectionEmpty = true;
            userClickedAway = true;
            edgeSelected = false;
            
            for (int count=0; count < eCount; count++)
                edges[count].selected(false);
            
            for (int count=0; count < vCount; count++) 
                if (vertices[count].isNearTo(pressPoint)) {
                    vertices[count].selected(true);
                    selectionEmpty = false;
                    userClickedAway = false;
                    edgeSelected = false;
                }
            
            for (int count=0; count < eCount; count++) {
                if ((edges[count].isNearTo(pressPoint)) && (selectionEmpty)) {
                    edges[count].selected(true);
                    userClickedAway = false;
                    edgeSelected = true;
                }
            }
            
            if (edgeSelected)
                weightPrompt.setText("Dien trong so: ");
            else
                weightPrompt.setText("Chon canh ");
            
            switch (next) {
                
                case 1: 
                    if ((selectionEmpty) && (!edgeSelected)) {
                        Prim_Vertex v = new Prim_Vertex(pressPoint.x, pressPoint.y);
                        vertices[vCount] = v;
                        vCount++;
                        next = 1;
                    }
                    else
                        next = 2;
                    break;
                    
                case 2:
                    if (userClickedAway) {
                        edgeSelected = false;
                        for (int count=0; count < vCount; count++) 
                            vertices[count].selected(false);
                        for (int count=0; count < eCount; count++) 
                            edges[count].selected(false);
                        next = 1;
                        }
                    else
                        next = 2;  
                    break;
                    
                default:
                    next = 1;
                }
            repaint();
        }
        
     
        public void mouseDragged(MouseEvent event) {
            if (!selectionEmpty) {
                dragPoint = event.getPoint();
                userDragging = true;
            }
            repaint();
        }
        
   
        public void mouseReleased(MouseEvent event) {
            releasePoint = event.getPoint();
            if ((!selectionEmpty) && (userDragging)) {
                for (int count=0; count < vCount; count++) {
                    if (vertices[count].isNearTo(releasePoint) && 
                            !(vertices[count].isSelected())) {
                        for (int index=0; index < vCount; index++) {
                            if (vertices[index].isSelected()) {
                                Prim_Edge e = new 
                                Prim_Edge(vertices[index], vertices[count]);
                                edges[eCount] = e;
                                eCount++;
                                vertices[index].selected();
                            }
                        }
                    }
                }
            dragPoint = null;
            userDragging = false;
            repaint();
            }
        }
        
      
        public void mouseClicked (MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseMoved(MouseEvent event) {}
    }
    
   
    private class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if (event.getSource() == showSelectionArea) {
                if (edges[0].isShowingArea()) {
                for (int count = 0; count < eCount; count++)
                        edges[count].showSelectionArea(false);
                }
                else {
                    for (int count = 0; count < eCount; count++)
                    edges[count].showSelectionArea(true);
                }  
                repaint();
                return;
            }

            if (event.getSource() == prim) {
                PrimMST();
                repaint();
                return;
            }
            
            for (int count=0; count < eCount; count++)
                    edges = new Prim_Edge[100];
                eCount = 0;
            if (event.getSource() == clear) {
                for (int count=0; count < vCount; count++) 
                    vertices[count] = null;
                vCount = 0;
            }
            repaint();
        }
    }
    
   
    
   
    private class KeyListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            int w;
            String text = weight.getText();
            w = Integer.parseInt(text);
            
            for (int count=0; count < eCount; count++) {
                if (edges[count].isSelected()) {
                    edges[count].setWeight(w);
                    edges[count].selected(false);
                }
            }
            edgeSelected = false;
            repaint();
        }
    }
    
}

