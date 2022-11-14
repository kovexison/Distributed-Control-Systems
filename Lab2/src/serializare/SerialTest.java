package serializare;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialTest extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1977);
            Socket s = ss.accept();
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Pers p = (Pers) ois.readObject();
            System.out.println(p);
            s.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //trimite obiect prin socket
        (new SerialTest()).start();

        Socket s = new Socket(InetAddress.getByName("localhost"), 1977);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        Pers p = new Pers("Alin", 14);
        oos.writeObject(p);
        s.close();
    }

    //serverul si clientul sa fie pe calculatoare separate si sa se trimita intre ele un fir de executie
    //(suprascriere metoda run(), extends Thread). Atat serverul cat si clientul pot sa aiba ambele fluxuri: inputString si outputString
    //clientul sa trimita firul la server, iar serverl s-o lanseze in executie acel fir (care poate sa contina un algoritm)

}
