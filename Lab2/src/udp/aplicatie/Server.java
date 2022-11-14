package udp.aplicatie;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Server extends Thread {
    boolean running = true;

    public Server(){
        this.start();
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(1977);
            //asteapta client
            while (running) {
                byte[] buffer = new byte[256];
                DatagramPacket dgPacket = new DatagramPacket(buffer,0, buffer.length);
                socket.receive(dgPacket);
                System.out.print("Received: ");
                String received = new String(dgPacket.getData());
                for (int i = 0; i < received.length(); i++) {
                    if(received.charAt(i) == ' ') {

                    }
                }
                System.out.println(received);
                //read the client's address and port
                InetAddress address = dgPacket.getAddress();
                int port = dgPacket.getPort();
                //send a reply to client
                //buffer = new byte[256];
                byte [] buffer2 = "Uite, stau.".getBytes();
                System.out.println("Responding: Uite, stau.");
                dgPacket = new DatagramPacket(buffer2, buffer2.length, address, port);
                socket.send(dgPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
