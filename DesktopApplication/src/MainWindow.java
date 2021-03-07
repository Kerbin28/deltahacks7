import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener{
    public static final int WIDTH = 720;
    public static final int HEIGHT = 420;
    public static final int DELAY = 100;
    public static final Font defaultFont = new Font("Font",Font.LAYOUT_LEFT_TO_RIGHT,14);
    public static final int COMPONENT_WIDTH=MainWindow.WIDTH/100;
    public static final int COMPONENT_HEIGHT=MainWindow.HEIGHT/20;
    public static final Color BACKGROUND_COLOR = Color.CYAN;
    public static final Color COMPONENT_COLOR = Color.CYAN;
    public static final BluetoothCommunicator bc = new BluetoothCommunicator();
    public static final JLabel title = new JLabel("Doorhandle Cleaner");
    private long time;
    private JPanel centerPanel;
    private JPanel contentPanel;
    private JPanel padding;
    private JPanel timerPanel;
    private PeoplePanel peoplePanel;
    private StatusPanel statusPanel;
    private Timer timer =new Timer(DELAY,this);
    private long lastSprayTime;
    private JLabel sprayTime;
    public MainWindow(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        time=System.currentTimeMillis();
        lastSprayTime=0;
        sprayTime=new JLabel("Last spray time: "+lastSprayTime);
        sprayTime.setFont(defaultFont);
        timer.start();
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        padding=new JPanel();
        timerPanel=new JPanel();
        timerPanel.setBorder(new EmptyBorder((int) (COMPONENT_HEIGHT*1.83),COMPONENT_WIDTH, (int) (COMPONENT_HEIGHT*1.83),COMPONENT_WIDTH));
        timerPanel.setVisible(true);
        padding.setBackground(BACKGROUND_COLOR);
        padding.setBorder(new EmptyBorder(50,50,50,50));

        contentPanel = new JPanel();
        centerPanel=new JPanel();
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setLayout(new BorderLayout());

        statusPanel=new StatusPanel();
        statusPanel.setBorder(new EmptyBorder(COMPONENT_HEIGHT,COMPONENT_WIDTH,COMPONENT_HEIGHT,COMPONENT_WIDTH));

        contentPanel.add(statusPanel);

        peoplePanel=new PeoplePanel();
        peoplePanel.setBorder(new EmptyBorder(COMPONENT_HEIGHT,COMPONENT_WIDTH,COMPONENT_HEIGHT,COMPONENT_WIDTH));

        contentPanel.add(peoplePanel);

        timerPanel.add(sprayTime);
        timerPanel.setBackground(COMPONENT_COLOR);
        contentPanel.add(timerPanel);
        this.add(padding,BorderLayout.SOUTH);

        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.add(contentPanel,BorderLayout.SOUTH);
        contentPanel.setBackground(BACKGROUND_COLOR);
        ((FlowLayout)(contentPanel.getLayout())).setHgap(50);
        centerPanel.setVisible(true);
        contentPanel.setVisible(true);
        this.add(title,BorderLayout.NORTH);
        title.setFont(new Font("Ink Free",Font.BOLD,40));
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer){
            sprayTime.setText("Last spray time: "+(System.currentTimeMillis()-time)/1000 +"s");
            String input=bc.readLine();
            StringTokenizer st=null;
            try {
                st = new StringTokenizer(input);
            }
            catch(NullPointerException E){

            }
            ArrayList<Integer> statusList= new ArrayList<>();
            while(st!=null&&st.hasMoreTokens()){
                try{
                    String str=st.nextToken();
                    statusList.add(Integer.parseInt(str));
                }
                catch(NumberFormatException E){
                    statusList.add(-1);
                }
            }
            try {
                statusPanel.setStatus(statusList.get(0));
                peoplePanel.setNumberOfPeopleInside(statusList.get(1));
                peoplePanel.setSafeNumber(statusList.get(2));
            }
            catch(Exception E){

            }
            if(statusPanel.getStatus()==2){
                time=System.currentTimeMillis();
            }

        }
    }
}
