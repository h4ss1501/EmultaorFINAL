package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;


public class Controller {

    @FXML
    TableView <UDPmessage> table;
    @FXML
    ToggleButton toggleButtonEcho;
    @FXML
    ToggleButton toggleButtonBroadcast;



    //fields
    private UDPConnector udpConnector;
    private UDPbroadcastServer broadcastServer;

    //buttons
    public void toggleButtonEcho() {

        System.out.println("toggleButtonEcho Clicked!");
        if (udpConnector.isReceiveMessages())
        {
            udpConnector.setReceiveMessages(false);
            toggleButtonEcho.setText("OFF");
        }
        else{

        startUdpConnection();
        toggleButtonEcho.setText("ON");

    }
    }

    public void ToggleButtonBroadcast()
    {
        System.out.println("togglebtnBROADCAST clicked");
        if (broadcastServer.isBroadcast())
        {
            broadcastServer.setBroadcast(false);
            toggleButtonBroadcast.setText("OFF");
        }
        else
        {
            startBroadcasting();
            toggleButtonBroadcast.setText(("ON"));
        }
    }

    public void initialize()
    {
        System.out.println("initialize");

        startUdpConnection();
        startBroadcasting();
    }

    private void startBroadcasting() {
        broadcastServer = new UDPbroadcastServer();
        new Thread(broadcastServer).start();
    }

    private void startUdpConnection() {
        if (udpConnector != null) udpConnector.closeSocket();
        udpConnector = new UDPConnector(this);
        new Thread(udpConnector).start();
    }

    public void receiveMessage(UDPmessage udpMessage)
    {
        table.getItems().add(0, udpMessage);
    }
}
