package sample;

import java.io.IOException;
import java.net.*;

//Broadcast server class implements the Runnable interface for multithreading!
public class UDPbroadcastServer implements Runnable {

    //fields
    private boolean broadcast = true;
    private DatagramSocket mySocket;
    private int broadcastPort;
    public String message = "Echoserver is ready on port 7000";
    public boolean isBroadcast() {
        return broadcast;
    }

    //methods

    private void broadcastMessage(String message) {
        try {
            //instantiating the DatagramSocket
            mySocket = new DatagramSocket();
            mySocket.setBroadcast(true);

            //buffer byte array containing the message
            byte[] buffer = message.getBytes();

            //instantiating DataGramPacket
            DatagramPacket myPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 7007);
            //the packet containing a message gets sent.
            mySocket.send(myPacket);

            //closing the socket after sending.
            mySocket.close();
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void broadcastLoop()
    {
        do{
            try{
                Thread.sleep((3000));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            if(broadcast) broadcastMessage(message);
        }
        while (broadcast);
    }

    //run method from the runnable class overridden.
    //runs the broadcastLoop
    public void run(){
        broadcastLoop();
    }

    //setter
    public void setBroadcast(boolean broadcast){
        this.broadcast = broadcast;
    }


}
