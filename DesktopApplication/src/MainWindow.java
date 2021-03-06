import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener{
    public static final int WIDTH = 680;
    public static final int HEIGHT = 420;
    public static final Color BACKGROUND_COLOR = Color.CYAN;
    private JLabel statusLabel=new JLabel("Status");
    private JLabel peopleInside = new JLabel("Number of people inside");
    private StatusPanel statusPanel;
    public MainWindow(){
        this.setSize(WIDTH,HEIGHT);
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        statusPanel=new StatusPanel();
        this.add(statusPanel);

        this.setBackground(BACKGROUND_COLOR);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
