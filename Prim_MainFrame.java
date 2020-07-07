import javax.swing.*;

public class Prim_MainFrame {

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chuong Trinh Tim Cay Bao Trum Nho Nhat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane();
       
        tp.addTab("Graph", new Prim_GraphPanel());
        
        frame.getContentPane().add(tp);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
