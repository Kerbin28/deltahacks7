import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PeoplePanel extends JPanel implements ActionListener {
    private JLabel numberPeopleLabel;
    private int safeNumberOfPeople;
    private int numberOfPeopleInside;
    private JLabel safeNumberLabel;
    private Font font = new  Font("Font",Font.LAYOUT_LEFT_TO_RIGHT,18);
    private JButton changeNumber;
    public PeoplePanel(){
        numberOfPeopleInside=0;
        safeNumberOfPeople=5;
        numberPeopleLabel = new JLabel("Number of people inside: "+numberOfPeopleInside,JLabel.CENTER);
        safeNumberLabel =new JLabel("Safe number of people: "+safeNumberOfPeople,JLabel.CENTER);
        changeNumber =new JButton("Change safe number of people");
        numberPeopleLabel.setFont(font);
        safeNumberLabel.setFont(font);
        changeNumber.setFont(MainWindow.defaultFont);

        this.setBackground(MainWindow.COMPONENT_COLOR);
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        this.add(numberPeopleLabel);
        safeNumberLabel.setVisible(true);
        this.add(safeNumberLabel);
        //this.add(changeNumber);
        //changeNumber.addActionListener(this);
    }
    public void setSafeNumber(int safeNumber){
        if(safeNumber>=0) {
            safeNumberOfPeople = safeNumber;
        }
        safeNumberLabel.setText("Safe number of people: "+safeNumberOfPeople);
    }
    public void setNumberOfPeopleInside(int number){
        if(number>=0) {
            numberOfPeopleInside = number;
        }
        numberPeopleLabel.setText("Number of people inside: "+numberOfPeopleInside);
        if(numberOfPeopleInside>safeNumberOfPeople){
            numberPeopleLabel.setForeground(Color.RED);
        }
        else{
            numberPeopleLabel.setForeground(Color.BLACK);

        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
