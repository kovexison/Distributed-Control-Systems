package comparator;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.TableParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ComparatorExample {

    //tabelul FLRS pentru T0 care implementeaza P0-P1
    String differentiator = "" + //
            "{[<ZR><NM><NL><NL><NL>]" + //
            " [<PM><ZR><NM><NL><NL>]" + //
            " [<PL><PM><ZR><NM><NL>]" + //
            " [<PL><PL><PM><ZR><NM>]" + //
            " [<PL><PL><PL><PM><ZR>]}";

    //tabelul FLRS pentru T1 care face selectia in functie de rezultatul P0-P1 (pozitiv sau negativ)
    String separator = "{[<FF,NL><FF,NL,><FF,FF><PL,FF><PL,FF>]}";

    public ComparatorExample() {

// se construieste reteaua Petri
        TableParser parser = new TableParser();
        FuzzyPetriNet petriNet = new FuzzyPetriNet();

// se adauga locatiile de intrare
        int p0Inp = petriNet.addInputPlace();
        int p1Inp = petriNet.addInputPlace();

// se ataseaza tranzitiei t0 tabela FLRS corespunzatoare
        TwoXOneTable diffTable = parser.parseTwoXOneTable(differentiator);
        int t0 = petriNet.addTransition(0, diffTable);

// se adauga arcele si ponderile corespunzatoare retelei Petri
        petriNet.addArcFromPlaceToTransition(p0Inp, t0, 1.0);
        petriNet.addArcFromPlaceToTransition(p1Inp, t0, 1.0);

// se adauga locatiile si arcele corespunzatoare retelei Petri
        int p2 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t0, p2);

        int t1 = petriNet.addTransition(0, parser.parseOneXTwoTable(separator));
        petriNet.addArcFromPlaceToTransition(p2, t1, 1.0);

        int p3 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p3);
        int p4 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p4);

        int t2Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p3, t2Out, 1.0);

// asociem o actiune tranzitiei de iesire t2
        petriNet.addActionForOuputTransition(t2Out, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println("Output From Transition 2: " + t.shortString());
            }
        });

        int t3Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p4, t3Out, 1.0);

// asociem o actiune tranzitiei de iesire t3
        petriNet.addActionForOuputTransition(t3Out, new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println("Output From Transition  3: " + t.shortString());
            }
        });
// se creeaza executorul pentru reteaua Petri data si se specifica perioada in milisecunde
        AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);

//  se creeaza un obiect pentru vizualizarea comportamentului retelei Petri
        FullRecorder recorder = new FullRecorder();
        executor.setRecorder(recorder);
        FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

// se lanseaza in executie firul ce contine executorul
        (new Thread(executor)).start();

        for (int i = 0; i < 100; i++) {

// construim colectia de tip dictionar (map) pentru intrari
            Map<Integer, FuzzyToken> inps = new HashMap<>();
            if (i % 10 < 5) {
// se pune jetonul fuzzyficat
                inps.put(p0Inp, driver.fuzzifie(i / 100.0));
                inps.put(p1Inp, driver.fuzzifie(i / -100.0));
            } else {
                inps.put(p1Inp, driver.fuzzifie(i / 100.0));
                inps.put(p0Inp, driver.fuzzifie(i / -100.0));
            }

// se pun jetoanele de intrare pentru executor
            executor.putTokenInInputPlace(inps);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.stop();

// se vizualizeaza reteaua Petri  si comportamentul ei.
        FuzzyPVizualzer.visualize(petriNet, recorder);
    }

    public static void main(String[] main) {
        new ComparatorExample();
    }
}
