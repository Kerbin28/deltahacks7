
import com.fazecast.jSerialComm.SerialPort;
import java.util.*;
import java.io.*;

public class BluetoothCommunicator {
    private static final SerialPort ports[] = SerialPort.getCommPorts();
    private SerialPort port;

    public BluetoothCommunicator(int port) {
        this.port = ports[port];

    }

    public BluetoothCommunicator() {
        int j=0;
        for(SerialPort i:ports){
            //System.out.println(j++ + ". "+i.getSystemPortName());
        }
        this.port = ports[1];
    }

    public void setPort(int port) {
        this.port = ports[port];
    }

    public String readLine() {
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,0,0);
        Scanner input = new Scanner(port.getInputStream());
        String inputText="";
        port.openPort();
        while(input.hasNextLine()) {

            inputText= input.nextLine();
            input.close();
            port.closePort();
            return inputText;
        }
        input.close();
        port.closePort();
        return null;
    }
    //
    /*
    public void sendStringToComm(String command) throws Exception {
        port.openPort();
        port.writeBytes(command.getBytes(), command.length());
        port.closePort();
    }

     */
}
