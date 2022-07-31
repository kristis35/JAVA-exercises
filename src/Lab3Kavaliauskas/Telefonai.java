/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kavaliauskas;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import Lab3Kavaliauskas.Telefonai;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;

/**
 *
 * @author JustPC
 */
public final class Telefonai implements KTUable<Telefonai> {
    // bendri duomenys visiems automobiliams (visai klasei)
    private static final int priimtinųMetųRiba = 2012;
    private static final int esamiMetai = LocalDate.now().getYear();
    private static final double minKaina = 100.0;
    private static final double maxKaina = 3000.0;
    private static final String idCode = "OG";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja
    private final String leidimoNr;
    private String gamintojas = "";
    private String modelis = "";
    private int gamMetai = -1;
    private int svoris = -1;
    private double kaina = -1.0;

    public Telefonai() {
        leidimoNr= idCode + (serNr++);    // suteikiamas originalus autoRegNr
    }

    public Telefonai(String markė, String modelis,
            int gamMetai, int rida, double kaina) {
        leidimoNr = idCode + (serNr++);    // suteikiamas originalus autoRegNr
        this.gamintojas = gamintojas;
        this.modelis = modelis;
        this.gamMetai = gamMetai;
        this.svoris = svoris;
        this.kaina = kaina;
        validate();
    }

    public Telefonai(String dataString) {
        leidimoNr = idCode + (serNr++);    // suteikiamas originalus autoRegNr
        this.parse(dataString);
    }

    public Telefonai(Builder builder) {
        leidimoNr = idCode + (serNr++);    // suteikiamas originalus autoRegNr
        this.gamintojas = builder.gamintojas;
        this.modelis = builder.modelis;
        this.gamMetai = builder.gamMetai;
        this.svoris = builder.svoris;
        this.kaina = builder.kaina;
        validate();
    }

    @Override
    public Telefonai create(String dataString) {
        return new Telefonai(dataString);
    }

    @Override
    public String validate() {
        String klaidosTipas = "";
        if (gamMetai < priimtinųMetųRiba || gamMetai > esamiMetai) {
            klaidosTipas = "Netinkami gamybos metai, turi būti ["
                    + priimtinųMetųRiba + ":" + esamiMetai + "]";
        }
        if (kaina < minKaina || kaina > maxKaina) {
            klaidosTipas += " Kaina už leistinų ribų [" + minKaina
                    + ":" + maxKaina + "]";
        }
        return klaidosTipas;
    }

    @Override
    public void parse(String dataString) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(dataString);
            gamintojas = ed.next();
            modelis = ed.next();
            gamMetai = ed.nextInt();
            setSvoris(ed.nextInt());
            setKaina(ed.nextDouble());
            validate();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie telefonu -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie telefonu -> " + dataString);
        }
    }

    @Override
    public String toString() {  // papildyta su autoRegNr
        return getLeidimoNr() + "=" + gamintojas + "_" + modelis + ":" + gamMetai + " " + getSvoris() + " " + String.format("%4.1f", kaina);
    }

    public String getGamintojas() {
        return gamintojas;
    }

    public String getModelis() {
        return modelis;
    }

    public int getGamMetai() {
        return gamMetai;
    }

    public int getSvoris() {
        return svoris;
    }

    public void setSvoris(int rida) {
        this.svoris = svoris;
    }

    public double getKaina() {
        return kaina;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public String getLeidimoNr() {  //** nauja.
        return leidimoNr;
    }

    @Override
    public int compareTo(Telefonai a) {
        return getLeidimoNr().compareTo(a.getLeidimoNr());
    }

    public static Comparator<Telefonai> pagalMarke = (Telefonai a1, Telefonai a2) -> a1.gamintojas.compareTo(a2.gamintojas); // pradžioje pagal markes, o po to pagal modelius

    public static Comparator<Telefonai> pagalKaina = (Telefonai a1, Telefonai a2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Telefonai> pagalMetusKainą = (Telefonai a1, Telefonai a2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (a1.gamMetai > a2.gamMetai) {
            return +1;
        }
        if (a1.gamMetai < a2.gamMetai) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        return 0;
    };

    // Automobilis klases objektų gamintojas
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MODELIAI = { // galimų automobilių markių ir jų modelių masyvas
            {"Apple", "11", "12", "13", "14"},
            {"Galaxy", "S17", "S18", "S19", "S21", "S22"},
            {"Nokia", "Lumia", "Lumia5"},
            {"Huawei", "Go", "Slow", "Stop"},
            {"Xiomi", "Lite", "Pro", "Super"},
            {"Blackberry", "red", "blue", "black"}
        };

        private String gamintojas = "";
        private String modelis = "";
        private int gamMetai = -1;
        private int svoris = -1;
        private double kaina = -1.0;

        public Telefonai build() {
            return new Telefonai(this);
        }

        public Telefonai buildRandom() {
            int ma = RANDOM.nextInt(MODELIAI.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(MODELIAI[ma].length - 1) + 1;// modelio indeksas 1..              
            return new Telefonai(MODELIAI[ma][0],
                    MODELIAI[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    6000 + RANDOM.nextInt(222000),// rida tarp 6000 ir 228000
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder gamMetai(int gamMetai) {
            this.gamMetai = gamMetai;
            return this;
        }

        public Builder markė(String gamintojas) {
            this.gamintojas = gamintojas;
            return this;
        }

        public Builder modelis(String modelis) {
            this.modelis = modelis;
            return this;
        }

        public Builder svoris(int svoris) {
            this.svoris = svoris;
            return this;
        }

        public Builder kaina(double kaina) {
            this.kaina = kaina;
            return this;
        }
    }
}
