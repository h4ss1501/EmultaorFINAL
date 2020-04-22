package sample;


import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

//UDPConnector class implements the Runnable interface for multithreading!
public class UDPConnector implements Runnable {

    //fields
    private DatagramSocket mySocket;
    private int udpPort = 7000;
    private int udpPortEcho = 7007;
    private Controller messageHandler;
    private boolean receivedMessage = true;

    //constructor
    public UDPConnector (Controller messageHandler){
        this.messageHandler = messageHandler;
        setupSocket();
    }

    //methods

    //setting up the socket
    public void setupSocket(){

        try{
            mySocket = new DatagramSocket(udpPort);

        } catch (SocketException e) {
            System.out.println("IOException: Tried to create new DatagramSocket on " + udpPort);
            System.out.println(e.getMessage());

        }
    }

    //closing the socket
    public void closeSocket(){
        this.mySocket.close();
    }

    //message sending methods
    public void sendMessage(byte [] bytes, InetAddress adress){
        DatagramPacket myPacket = new DatagramPacket(bytes, bytes.length,adress,udpPortEcho);

        try {
            //mySocket = new DatagramSocket (udpPort);
            mySocket.send(myPacket);
        }catch (IOException e){
            System.out.println("IOException: Tried to send packet!");
        } finally {
            //socket.close();
        }
    }

    //overloaded message sending method, taking a string instead.
    public void sendMessage(String message, InetAddress address){
        sendMessage(message.getBytes(),address);
    }


    public UDPmessage receiveMessage() {
        byte[] buffer = new byte[256];
        DatagramPacket myPacket = new DatagramPacket(buffer, buffer.length);
        try{
                //socket = new DatagramSocket(udpPort);
                System.out.println("waiting for a udp packet on port: " + udpPort);
                mySocket.receive(myPacket);
                UDPmessage message = new UDPmessage(myPacket.getData(), myPacket.getLength(), myPacket.getAddress(), myPacket.getPort());
                System.out.println("received: " + message);

                if (receivedMessage) messageHandler.receiveMessage(message);
                return message;
            } catch(
            IOException e)

            {
                System.out.println("IOEXCEPTION: Tried to receive packet");
                return null;
            } finally

            {
                //socket.close();
            }
        }

    public void echoServer()
    {
        UDPmessage myMessage = receiveMessage();
        try {
            if (receivedMessage) sendMessage("Echoserver receiver: "+myMessage.getMessage(), InetAddress.getByName(myMessage.getIp()));
        } catch (UnknownHostException e) {
            e.printStackTrace();

        }
    }


    //connection loop
    public void connectionLoop() {
        System.out.println("Started UdpConnector Thread");
        do
        {
            echoServer();
            //receiveMessage();
        }
        while(isReceiveMessages());
        // socket.close();
    }

    //overridden run method associated with the implemented Runnable interface
    @Override
    public void run() {
        connectionLoop();

    }

    //boolean method returning status of whether message is received or not
    public boolean isReceiveMessages() {
        return receivedMessage;
    }

    //setter for receivedMessage boolean method
    public void setReceiveMessages(boolean receiveMessages) {
        this.receivedMessage = receiveMessages;
    }

    }





