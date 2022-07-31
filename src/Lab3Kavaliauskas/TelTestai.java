/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kavaliauskas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import Lab3Kavaliauskas.TelApskaita;
import Lab3Kavaliauskas.Telefonai;;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.BstSetKTUx;
import laborai.studijosktu.Ks;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.SortedSetADTx;

/**
 *
 * @author JustPC
 */
public class TelTestai {

    static Telefonai[] telBaze;
    static SortedSetADTx<Telefonai> aSerija = new BstSetKTUx(new Telefonai(), Telefonai.pagalKaina);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        aibėsTestas();
    }

    static SortedSetADTx generuotiAibe(int kiekis, int generN) {
        telBaze = new Telefonai[generN];
        for (int i = 0; i < generN; i++) {
            telBaze[i] = new Telefonai.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(telBaze));
        aSerija.clear();
        for (int i = 0; i < kiekis; i++) {
            aSerija.add(telBaze[i]);
        }
        return aSerija;
    }

    public static void aibėsTestas() throws CloneNotSupportedException {
        Telefonai a1 = new Telefonai("Apple", "13", 2018, 450, 1100);
        Telefonai a2 = new Telefonai.Builder()
                .markė("Apple")
                .modelis("11")
                .gamMetai(2019)
                .svoris(330)
                .kaina(800)
                .build();
        Telefonai a3 = new Telefonai.Builder().buildRandom();
        Telefonai a4 = new Telefonai("Apple 13 2018 480 700");
        Telefonai a5 = new Telefonai("Apple 11 2019 900 950");
        Telefonai a6 = new Telefonai("Samsung   S20  2020  700 100");
        Telefonai a7 = new Telefonai("Apple 13 2018 115 750");
        Telefonai a8 = new Telefonai("Apple 11 2019 365 950");
        Telefonai a9 = new Telefonai("Samsung   S20  2020  364 850");

        Telefonai[] telMasyvas = {a1, a2, a3, a4, a5, a6};

        Ks.oun("Telefonu Aibė:");
        SortedSetADTx<Telefonai> telAibe = new BstSetKTUx(new Telefonai());

        for (Telefonai a : telMasyvas) {
            telAibe.add(a);
            Ks.oun("Aibė papildoma: " + a + ". Jos dydis: " + telAibe.size());
        }
        Ks.oun("");
        Ks.oun(telAibe.toVisualizedString(""));

        SortedSetADTx<Telefonai> telAibeKopija
                = (SortedSetADTx<Telefonai>) telAibe.clone();
        SortedSetADTx<Telefonai> telAibeKopija5
                = (SortedSetADTx<Telefonai>) telAibe.clone();

        telAibeKopija.add(a2);
        telAibeKopija.add(a3);
        telAibeKopija.add(a4);
        Ks.oun("Papildyta autoaibės kopija:");
        Ks.oun(telAibeKopija.toVisualizedString(""));

        a9.setSvoris(150);

        Ks.oun("Originalas:");
        Ks.ounn(telAibe.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Telefonai a : telMasyvas) {
            Ks.oun(a + ": " + telAibe.contains(a));
        }
        Ks.oun(a2 + ": " + telAibe.contains(a2));
        Ks.oun(a3 + ": " + telAibe.contains(a3));
        Ks.oun(a4 + ": " + telAibe.contains(a4));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Telefonai a : telMasyvas) {
            Ks.oun(a + ": " + telAibeKopija.contains(a));
        }
        Ks.oun(a2 + ": " + telAibeKopija.contains(a2));
        Ks.oun(a3 + ": " + telAibeKopija.contains(a3));
        Ks.oun(a4 + ": " + telAibeKopija.contains(a4));
        Ks.oun("");

        Ks.oun("Elementų šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " + telAibeKopija.size());
        for (Telefonai a : new Telefonai[]{a2, a1, a9, a8, a5, a3, a4, a2, a7, a6, a7, a9}) {
            telAibeKopija.remove(a);
            Ks.oun("Iš autoaibės kopijos pašalinama: " + a + ". Jos dydis: " + telAibeKopija.size());
        }
        Ks.oun("");

        Ks.oun("");
        Ks.oun("Automobilių aibė DP-medyje:");
        Ks.ounn(telAibeKopija5.toVisualizedString(""));
        Ks.oun("Išsiaiškinkite, kodėl medis augo tik į vieną pusę.");
        

        telAibeKopija5 = (SortedSetADTx)telAibeKopija5.subSet(a3, a6);
        
        Ks.oun(telAibeKopija5.toVisualizedString("")); 
        Ks.oun("Pabaiga");
        Ks.oun("");
        
        
        telAibe.remove(a5);
        Ks.oun("Naikinamas obejktas " + a5);
        Ks.oun(telAibe);
        
        Ks.oun("");
        int n = telAibe.MedzioDydis();
        Ks.oun(n);
        
        
      
        
        Ks.oun("");
        Ks.oun("Telefonu aibė AVL-medyje:");
        SortedSetADTx<Telefonai> telAibe3 = new AvlSetKTUx(new Telefonai());
        for (Telefonai a : telMasyvas) {
            telAibe3.add(a);
        }
        Ks.ounn(telAibe3.toVisualizedString(""));

        

        
        
        

        // Išvalome ir suformuojame aibes skaitydami iš failo
        telAibe.clear();
        telAibe3.clear();

        

        

        SetADT<String> autoAibe4 = TelApskaita.telefonuModeliai(telMasyvas);
        Ks.oun("Pasikartojančios automobilių markės:\n" + autoAibe4.toString());
        
        
        
        
        
    }
    
}
