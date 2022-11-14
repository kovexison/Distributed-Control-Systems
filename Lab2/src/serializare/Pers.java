package serializare;

import java.io.Serializable;

public class Pers implements Serializable {
    String nume;
    int varsta;

    Pers(String n, int v) {
        nume = n;
        varsta = v;
    }

    public String toString() {
        return "Persoana: " + nume + " varsta: " + varsta;
    }

}
