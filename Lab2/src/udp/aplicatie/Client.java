package udp.aplicatie;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        while (true) {
            try {
                DatagramSocket socket = new DatagramSocket();
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf,0, buf.length, InetAddress.getByName("localhost"), 1977);
                System.out.println("Message: ");
                Scanner scanner = new Scanner(System.in);
                String in = scanner.nextLine();
                buf = in.getBytes();
                packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 1977);
                socket.send(packet);
                buf = new byte[11];
                packet = new DatagramPacket(buf, 0,buf.length);
                socket.receive(packet);
                System.out.print("Response: ");
                System.out.println(new String(packet.getData()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
