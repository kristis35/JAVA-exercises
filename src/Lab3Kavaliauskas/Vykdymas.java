/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kavaliauskas;

import java.util.Locale;
import Lab3Kavaliauskas.TelTestai;
import laborai.gui.swing.Lab3Window;

/**
 *
 * @author JustPC
 */
public class Vykdymas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        TelTestai.aibėsTestas();
        Lab3Window.createAndShowGUI();
    }
    
}
