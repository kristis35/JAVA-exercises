/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kavaliauskas;

import Lab3Kavaliauskas.Telefonai;;
import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.SetADT;

/**
 *
 * @author JustPC
 */
public class TelApskaita {
    public static SetADT<String> telefonuModeliai(Telefonai[] tel) {
        SetADT<Telefonai> uni = new BstSetKTU<>(Telefonai.pagalMarke);
        SetADT<String> kart = new BstSetKTU<>();
        for (Telefonai a : tel) {
            int sizeBefore = uni.size();
            uni.add(a);

            if (sizeBefore == uni.size()) {
                kart.add(a.getGamintojas());
            }
        }
        return kart;
    }
}
