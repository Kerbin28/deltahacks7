import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class StartWindow extends JPanel implements ActionListener {
    public static final ImageIcon deltahacksLogo=new ImageIcon("src/DeltahacksLogo.png");
    public static final ImageIcon mcMasterLogo=new ImageIcon("src/McMasterLogo.png");
    private JLabel title;
    private JLabel dhLogo;
    private JLabel macLogo;
    private JButton start = new JButton("Start");
    private MainWindow main;
    private Font font = new Font("Comic Sans",Font.BOLD,30);
    public StartWindow(MainWindow main){

        this.main=main;
        this.setBackground(MainWindow.BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        title = new JLabel("Welcome to the doorknob disinfector project",JLabel.CENTER);
        title.setFont(font);
        dhLogo= new JLabel(deltahacksLogo);
        macLogo= new JLabel(mcMasterLogo);
        topPanel.add(dhLogo,BorderLayout.EAST);
        topPanel.add(macLogo,BorderLayout.WEST);
        topPanel.setBackground(MainWindow.BACKGROUND_COLOR);
        this.add(title);
        this.add(topPanel,BorderLayout.NORTH);
        start.addActionListener(this);
        JPanel southPanel = new JPanel();
        southPanel.setBackground(MainWindow.BACKGROUND_COLOR);
        southPanel.add(start);
        this.add(southPanel,BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(start)){
            main.setMainUI();
        }
    }
}
