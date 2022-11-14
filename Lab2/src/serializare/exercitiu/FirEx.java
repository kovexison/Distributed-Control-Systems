package serializare.exercitiu;

import java.io.Serializable;

public class FirEx extends Thread implements Serializable {
    private String mes;

    public FirEx(String message) {
        this.mes = message;
    }

    @Override
    public void run() {
        System.out.println(this.mes + " sunt");
    }
}
