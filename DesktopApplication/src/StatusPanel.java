import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel implements ActionListener {
    public static final JLabel header = new JLabel("Status");
    private JLabel statusLabel;
    private JButton changeStatus;
    public StatusPanel(){
        statusLabel=new JLabel("Off");
        changeStatus=new JButton("Change Status");
        this.setBackground(MainWindow.BACKGROUND_COLOR);
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        this.add(header);
        statusLabel.setVisible(true);
        this.add(statusLabel);
        this.add(changeStatus);
        changeStatus.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
