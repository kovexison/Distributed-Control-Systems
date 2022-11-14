package serializare.exercitiu;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    @Override
    public void run() {
        try {
            Socket s = new Socket(InetAddress.getByName("localhost"), 1977);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            System.out.println("Nume fir: ");
            Scanner keyboard = new Scanner(System.in);
            String name = keyboard.nextLine();
            FirEx fir = new FirEx(name);
            oos.writeObject(fir);
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
