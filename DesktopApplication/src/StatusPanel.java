import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel implements ActionListener {
    public static final JLabel header = new JLabel("Status",JLabel.CENTER);

    private int status=0;
    private JLabel statusLabel;
    //private JButton changeStatus;
    public StatusPanel(){
        header.setFont(MainWindow.defaultFont);
        this.setSize(WIDTH,HEIGHT);
        statusLabel=new JLabel("Powered Down",JLabel.CENTER);
        statusLabel.setFont(MainWindow.defaultFont);
        status=0;

        //changeStatus=new JButton("Change Status");
       // changeStatus.setFont(MainWindow.defaultFont);
        this.setBackground(MainWindow.COMPONENT_COLOR);
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        this.add(header);
        statusLabel.setVisible(true);
        this.add(statusLabel);
        //this.add(changeStatus);
        //changeStatus.addActionListener(this);
    }
    public void setStatus(int status){
        this.status=status;
        switch(status){
            case 0:
                statusLabel.setText("Powered Down");
                break;
            case 1:
                statusLabel.setText("Ready               ");
                break;
            case 2:
                statusLabel.setText("Disinfecting");
                break;
            case 3:
                statusLabel.setText("Refill Required");
                break;
            case 4:
                statusLabel.setText("Overflow\n");
                break;
            default:
                statusLabel.setText("Error      ");
                status=-1;
                break;
        }
    }
    public int getStatus(){
        return status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
