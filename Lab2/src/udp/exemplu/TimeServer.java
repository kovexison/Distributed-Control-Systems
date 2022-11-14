package udp.exemplu;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServer extends Thread {
    //server care trimite data curenta catre client care se logheaza
    //cu protocolul UDP
    //pentru realizarea conexiunii, se utilizeaza clasa DatagramSocket din pachetul java.net
    //se da portul ca si atribut
    //DatagramPacket contine un sir de biti si cu metoda send se trimit datele, iar clientul le primeste cu metoda receive().

    boolean running = true;

    public TimeServer() {
        start();
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(1977);
            while (running) {
                //asteapta client
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                //citeste adresa si portul clientului
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                //trimite un reply catre client
                buf = ((new Date()).toString()).getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TimeServer timeServer1 = new TimeServer();
    }

}
