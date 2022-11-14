package laborator.inversor;

import java.util.EnumMap;
import java.util.Map;

public class InversorExample {
    public static TwoXTwoTable createInversor() {

//construim tabela1 FLRS pentru inversor, prima iesire

        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1 = new EnumMap<>(FuzzyValue.class);
        Map<FuzzyValue, FuzzyValue> nlLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.NL, nlLine);
        nlLine.put(FuzzyValue.NL, FuzzyValue.PL);
        nlLine.put(FuzzyValue.NM, FuzzyValue.PL);
        nlLine.put(FuzzyValue.ZR, FuzzyValue.PL);
        nlLine.put(FuzzyValue.PM, FuzzyValue.PL);
        nlLine.put(FuzzyValue.PL, FuzzyValue.PL);

        Map<FuzzyValue, FuzzyValue> nmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.NM, nmLine);
        nmLine.put(FuzzyValue.NL, FuzzyValue.PM);
        nmLine.put(FuzzyValue.NM, FuzzyValue.PM);
        nmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
        nmLine.put(FuzzyValue.PM, FuzzyValue.PM);
        nmLine.put(FuzzyValue.PL, FuzzyValue.PM);

        Map<FuzzyValue, FuzzyValue> zrLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.ZR, zrLine);
        zrLine.put(FuzzyValue.NL, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.NM, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PM, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PL, FuzzyValue.ZR);

        Map<FuzzyValue, FuzzyValue> pmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.PM, pmLine);
        pmLine.put(FuzzyValue.NL, FuzzyValue.NM);
        pmLine.put(FuzzyValue.NM, FuzzyValue.NM);
        pmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
        pmLine.put(FuzzyValue.PM, FuzzyValue.NM);
        pmLine.put(FuzzyValue.PL, FuzzyValue.NM);

        Map<FuzzyValue, FuzzyValue> plLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.PL, plLine);
        plLine.put(FuzzyValue.NL, FuzzyValue.NL);
        plLine.put(FuzzyValue.NM, FuzzyValue.NL);
        plLine.put(FuzzyValue.ZR, FuzzyValue.NL);
        plLine.put(FuzzyValue.PM, FuzzyValue.NL);
        plLine.put(FuzzyValue.PL, FuzzyValue.NL);

//construim tabela2 FLRS pentru inversor, a doua iesire

        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2 = new EnumMap<>(FuzzyValue.class);
        Map<FuzzyValue, FuzzyValue> generalLine = new EnumMap<>(FuzzyValue.class);
        generalLine.put(FuzzyValue.NL, FuzzyValue.PL);
        generalLine.put(FuzzyValue.NM, FuzzyValue.PM);
        generalLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        generalLine.put(FuzzyValue.PM, FuzzyValue.NM);
        generalLine.put(FuzzyValue.PL, FuzzyValue.NL);
        ruleTable2.put(FuzzyValue.PL, generalLine);
        ruleTable2.put(FuzzyValue.PM, generalLine);
        ruleTable2.put(FuzzyValue.ZR, generalLine);
        ruleTable2.put(FuzzyValue.NM, generalLine);
        ruleTable2.put(FuzzyValue.NL, generalLine);

//se returneaza tabela FLRS cu doua intrari si doua iesiri
        return new TwoXTwoTable(ruleTable1, ruleTable2);
    }

    public static void main(String[] args) {
        double w1 = 0.33;
        double w2 = 1.5;

//se specifica limitele pentru fuzzyficare, defuzzyficare
        FuzzyDefuzzy fuzDefuz = new FuzzyDefuzzy(-1.0, -0.5, 0.0, 0.5, 1.0);

//se creează tabela FLRS pentru doua intrari si doua iesiri
        TwoXTwoTable inversor = createInversor();

//se dau cele 2 intari
        double x1 = -0.33;
        double x2 = 0.12;

//se inmultesc intrarile cu factorul de amplificare si se fuzzyfica
        FuzzyToken inpToken1 = fuzDefuz.fuzzyfie(x1 * w1);
        FuzzyToken inpToken2 = fuzDefuz.fuzzyfie(x2 * w2);

//se executa tabela FLRS

        FuzzyToken[] out = inversor.execute(inpToken1, inpToken2);

//se afiseaza rezultatele defuzificate

        System.out.println("x3 :: " + fuzDefuz.defuzzify(out[0]));
        System.out.println("x4 :: " + fuzDefuz.defuzzify(out[1]));

    }

}
