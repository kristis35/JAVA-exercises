/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kavaliauskas;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import Lab3Kavaliauskas.TelGamyba;
import Lab3Kavaliauskas.Telefonai;
import laborai.gui.MyException;

/**
 *
 * @author JustPC
 */
public class TelGamyba {
    private static Telefonai[] telefonas;
    private static int pradinisIndeksas = 0, galinisIndeksas = 0;
    private static boolean arPradzia = true;

    public static Telefonai[] generuoti(int kiekis) {
        telefonas = new Telefonai[kiekis];
        for (int i = 0; i < kiekis; i++) {
            telefonas[i] = new Telefonai.Builder().buildRandom();
        }
        return telefonas;
    }

    public static Telefonai[] generuotiIrIsmaisyti(int aibesDydis,
            double isbarstymoKoeficientas) throws MyException {
        return generuotiIrIsmaisyti(aibesDydis, aibesDydis, isbarstymoKoeficientas);
    }

    /**
     *
     * @param aibesDydis
     * @param aibesImtis
     * @param isbarstymoKoeficientas
     * @return Gražinamas aibesImtis ilgio masyvas
     * @throws MyException
     */
    public static Telefonai[] generuotiIrIsmaisyti(int aibesDydis,
            int aibesImtis, double isbarstymoKoeficientas) throws MyException {
        telefonas = generuoti(aibesDydis);
        return ismaisyti(telefonas, aibesImtis, isbarstymoKoeficientas);
    }

    // Galima paduoti masyvą išmaišymui iš išorės
    public static Telefonai[] ismaisyti(Telefonai[] autoBaze,
            int kiekis, double isbarstKoef) throws MyException {
        if (autoBaze == null) {
            throw new IllegalArgumentException("TelBaze yra null");
        }
        if (kiekis <= 0) {
            throw new MyException(String.valueOf(kiekis), 1);
        }
        if (autoBaze.length < kiekis) {
            throw new MyException(autoBaze.length + " >= " + kiekis, 2);
        }
        if ((isbarstKoef < 0) || (isbarstKoef > 1)) {
            throw new MyException(String.valueOf(isbarstKoef), 3);
        }

        int likusiuKiekis = autoBaze.length - kiekis;
        int pradziosIndeksas = (int) (likusiuKiekis * isbarstKoef / 2);

        Telefonai[] pradineTelefonuImtis = Arrays.copyOfRange(autoBaze, pradziosIndeksas, pradziosIndeksas + kiekis);
        Telefonai[] likusiTelefonuImtis = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(autoBaze, 0, pradziosIndeksas)),
                        Arrays.stream(Arrays.copyOfRange(autoBaze, pradziosIndeksas + kiekis, autoBaze.length)))
                .toArray(Telefonai[]::new);

        Collections.shuffle(Arrays.asList(pradineTelefonuImtis)
                .subList(0, (int) (pradineTelefonuImtis.length * isbarstKoef)));
        Collections.shuffle(Arrays.asList(likusiTelefonuImtis)
                .subList(0, (int) (likusiTelefonuImtis.length * isbarstKoef)));

        TelGamyba.pradinisIndeksas = 0;
        galinisIndeksas = likusiTelefonuImtis.length - 1;
        TelGamyba.telefonas = likusiTelefonuImtis;
        return pradineTelefonuImtis;
    }

    public static Telefonai gautiIsBazes() throws MyException {
        if ((galinisIndeksas - pradinisIndeksas) < 0) {
            throw new MyException(String.valueOf(galinisIndeksas - pradinisIndeksas), 4);
        }
        // Vieną kartą Automobilis imamas iš masyvo pradžios, kitą kartą - iš galo.     
        arPradzia = !arPradzia;
        return telefonas[arPradzia ? pradinisIndeksas++ : galinisIndeksas--];
    }
}
