import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener{
    public static final int WIDTH = 1020;
    public static final int HEIGHT = 620;
    public static final int DELAY = 100;
    public static final Font defaultFont = new Font("Font",Font.LAYOUT_LEFT_TO_RIGHT,14);
    public static final int COMPONENT_WIDTH=MainWindow.WIDTH/500;
    public static final int COMPONENT_HEIGHT=MainWindow.HEIGHT/20;
    public static final Color BACKGROUND_COLOR = Color.CYAN;
    public static final Color COMPONENT_COLOR = Color.CYAN;
    public static final BluetoothCommunicator bc = new BluetoothCommunicator();
    public static final JLabel title = new JLabel("Doorhandle Cleaner",JLabel.CENTER);
    private long time;
    private JPanel centerPanel;
    private JPanel contentPanel;
    private JPanel padding;
    private JPanel timerPanel;
    private PeoplePanel peoplePanel;
    private StatusPanel statusPanel;
    private Timer timer =new Timer(DELAY,this);
    private long lastSprayTime;
    private StartWindow start;
    private JLabel overflowTime;
    private JLabel room1;
    private ArrayList<JLabel> labels = new ArrayList<>();
    public MainWindow(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        this.setSize(WIDTH,HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        start = new StartWindow(this);
        this.add(start);
        this.setVisible(true);

    }
    public void setPlaceholder(){
        for(int i=0;i<6;i++){
            labels.add(new JLabel("N/A"));

            labels.get(i).setFont(defaultFont);
        }
    }
    public void setMainUI(){
        setPlaceholder();
        remove(start);
        room1 = new JLabel("First Floor of Meeting Room");
        room1.setFont(defaultFont);
        time=System.currentTimeMillis();
        overflowTime =new JLabel("Last time overcrowded: "+0+" minutes",JLabel.CENTER);
        overflowTime.setFont(defaultFont);
        timer.start();

        padding=new JPanel();
        timerPanel=new JPanel();
        timerPanel.setBorder(new EmptyBorder((int) (COMPONENT_HEIGHT*1.83),COMPONENT_WIDTH, (int) (COMPONENT_HEIGHT*1.83),COMPONENT_WIDTH));
        timerPanel.setVisible(true);
        padding.setBackground(BACKGROUND_COLOR);
        padding.setBorder(new EmptyBorder(60,50,50,50));

        contentPanel = new JPanel();
        centerPanel=new JPanel();
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setLayout(new GridLayout());
        contentPanel.add(room1);
        statusPanel=new StatusPanel();
        statusPanel.setBorder(new EmptyBorder(COMPONENT_HEIGHT,COMPONENT_WIDTH,COMPONENT_HEIGHT,COMPONENT_WIDTH));

        contentPanel.add(statusPanel);

        peoplePanel=new PeoplePanel();
        peoplePanel.setBorder(new EmptyBorder(COMPONENT_HEIGHT,COMPONENT_WIDTH,COMPONENT_HEIGHT,COMPONENT_WIDTH));

        contentPanel.add(peoplePanel);

        timerPanel.add(overflowTime);
        timerPanel.setBackground(COMPONENT_COLOR);
        contentPanel.add(timerPanel);
        this.add(padding,BorderLayout.SOUTH);
        JLabel room2 = new JLabel("Server Room");
        room2.setFont(defaultFont);
        JLabel room3 = new JLabel("HR Office");
        room3.setFont(defaultFont);

        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.add(contentPanel,BorderLayout.SOUTH);
        JPanel row2=new JPanel();
        JPanel row3=new JPanel();
        row2.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
        row3.setLayout(new FlowLayout());
        ((FlowLayout)row2.getLayout()).setHgap(150);
        ((FlowLayout)row2.getLayout()).setVgap(50);
        ((FlowLayout)row3.getLayout()).setHgap(150);
        ((FlowLayout)row3.getLayout()).setVgap(50);
        row2.add(room2);
        row3.add(room3);
        row2.setBackground(BACKGROUND_COLOR);
        row3.setBackground(BACKGROUND_COLOR);
        for(int i=0;i<3;i++){
            row2.add(labels.get(i));
            row3.add(labels.get(i+3));
        }
        centerPanel.add(row2);
        centerPanel.add(row3);

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
                peoplePanel.setSafeNumber(statusList.get(2));
                peoplePanel.setNumberOfPeopleInside(statusList.get(1));

            }
            catch(Exception E){

            }
            if(statusPanel.getStatus()==4){
                overflowTime.setText("Last time overcrowded: "+((System.currentTimeMillis()-time)/1000)%60 +"minutes");
                time=System.currentTimeMillis();

            }

        }
    }
}
