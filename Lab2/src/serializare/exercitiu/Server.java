package serializare.exercitiu;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1977);
            Socket s = ss.accept();
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            FirEx f = (FirEx) ois.readObject();
            f.start();
            s.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
