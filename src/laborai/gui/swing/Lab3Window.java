package laborai.gui.swing;

import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.BstSetKTUx;
import Lab3Kavaliauskas.TelGamyba;
import Lab3Kavaliauskas.Telefonai;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import Lab3Kavaliauskas.Greitaveika;
import laborai.studijosktu.Ks;

/**
 * Lab3 langas su Swing'u
 * <pre>
 *                  Lab3Panel (BorderLayout)
 *  |-----------------------Center-------------------------|
 *  |  |-----------------scrollOutput-------------------|  |
 *  |  |  |------------------------------------------|  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                 taOutput                 |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |                                          |  |  |
 *  |  |  |------------------------------------------|  |  |                                                              |  |
 *  |  |------------------------------------------------|  |                                          |
 *  |------------------------South-------------------------|
 *  |  |~~~~~~~~~~~~~~~~~~~scrollSouth~~~~~~~~~~~~~~~~~~|  |
 *  |  |                                                |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |    | panButtons || panParam1 || panParam2 |    |  |
 *  |  |    |            ||           ||           |    |  |
 *  |  |    |------------||-----------||-----------|    |  |
 *  |  |                                                |  |
 *  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
 *  |------------------------------------------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class Lab3Window extends JFrame implements ActionListener {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");
    private static final int TF_WIDTH = 8;

    private final JTextArea taOutput = new JTextArea();
    private final JScrollPane scrollOutput = new JScrollPane(taOutput);
    private final JTextField tfDelimiter = new JTextField();
    private final JTextField tfInput = new JTextField();
    private final JComboBox cmbTreeType = new JComboBox();
    private final JPanel panSouth = new JPanel();
    private final JScrollPane scrollSouth = new JScrollPane(panSouth);
    private final JPanel panParam2 = new JPanel();
    private Menu menus;
    private Panels panParam1, panButtons;

    private SortedSetADTx<Telefonai> telSet;
    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet;
    private double coef;
    private String delimiter;
    private final String[] errors;

    public Lab3Window() {
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
        initComponents();
    }

    private void initComponents() {
        // Kad prijungiant tekst?? prie JTextArea vaizdas visada nu??okt?? ?? apa??i??
        scrollOutput.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            taOutput.select(taOutput.getCaretPosition()
                    * taOutput.getFont().getSize(), 0);
        });
        //======================================================================
        // Formuojamas mygtuk?? tinklelis (m??lynas). Naudojame klas?? Panels.
        //======================================================================
        // 4 eilutes, 2 stulpeliai
        panButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4"),
                    MESSAGES.getString("button5"),
                    MESSAGES.getString("button6"),
                    MESSAGES.getString("button7")}, 2, 4);
        panButtons.getButtons().forEach((btn) -> {
            btn.addActionListener(this);
        });
        enableButtons(false);
        //======================================================================
        // Formuojama pirmoji parametr?? lentel?? (??alia). Naudojame klas?? Panels.
        //======================================================================
        panParam1 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam14"),
                    MESSAGES.getString("lblParam15")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam14"),
                    MESSAGES.getString("tfParam15")}, TF_WIDTH);
        //======================================================================
        // Formuojama antroji parametr?? lentel?? (gelsva).
        //======================================================================
        panParam2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 6, 3, 4);
        // I??lygiavimas ?? kair??
        c.anchor = GridBagConstraints.WEST;
        // Komponent?? i??pl??timas iki maksimalaus cel??s dyd??io
        c.fill = GridBagConstraints.BOTH;
        // Pirmas stulpelis
        c.gridx = 0;
        panParam2.add(new JLabel(MESSAGES.getString("lblParam21")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam22")), c);
        panParam2.add(new JLabel(MESSAGES.getString("lblParam23")), c);
        // Antras stulpelis
        c.gridx = 1;
        for (String s : new String[]{
            MESSAGES.getString("cmbTreeType1"),
            MESSAGES.getString("cmbTreeType2"),
            MESSAGES.getString("cmbTreeType3")
        }) {
            cmbTreeType.addItem(s);
        }
        cmbTreeType.addActionListener(this);
        panParam2.add(cmbTreeType, c);
        tfDelimiter.setHorizontalAlignment(JTextField.CENTER);
        panParam2.add(tfDelimiter, c);
        // V??l pirmas stulpelis, ta??iau plotis - 2 cel??s
        c.gridx = 0;
        c.gridwidth = 2;
        tfInput.setEditable(false);
        tfInput.setBackground(Color.lightGray);
        panParam2.add(tfInput, c);
        //======================================================================
        // Formuojamas bendras parametr?? panelis (tamsiai pilkas).
        //======================================================================
        panSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panSouth.add(panButtons);
        panSouth.add(panParam1);
        panSouth.add(panParam2);

        menus = new Menu(this);
        // Meniu juosta patalpinama ??iame freime
        setJMenuBar(menus);
        // Formuojamas Lab3 panelis        
        JPanel lab3 = new JPanel();
        lab3.setLayout(new BorderLayout());
        // ..centre ir pietuose talpiname objektus..
        lab3.add(scrollOutput, BorderLayout.CENTER);
        lab3.add(scrollSouth, BorderLayout.SOUTH);

        // ??io freimo "viduje" talpinamas lab3 panelis
        getContentPane().add(lab3);
        appearance();
    }

    private void appearance() {
        // Grafini?? objekt?? r??meliai
        TitledBorder outputBorder = new TitledBorder(MESSAGES.getString("border1"));
        outputBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollOutput.setBorder(outputBorder);
        TitledBorder southBorder = new TitledBorder(MESSAGES.getString("border2"));
        southBorder.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        scrollSouth.setBorder(southBorder);

        panParam2.setBackground(new Color(255, 255, 153));// Gelsva
        panParam1.setBackground(new Color(204, 255, 204));// ??viesiai ??alia
        panParam1.getTfOfTable().get(2).setEditable(false);
        panParam1.getTfOfTable().get(2).setForeground(Color.red);
        panParam1.getTfOfTable().get(4).setEditable(false);
        panParam1.getTfOfTable().get(4).setBackground(Color.lightGray);
        panButtons.setBackground(new Color(112, 162, 255)); // Bly??kiai m??lyna
        panSouth.setBackground(Color.GRAY);
        taOutput.setFont(Font.decode("courier new-12"));
        taOutput.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            taOutput.setBackground(Color.white);
            Object source = ae.getSource();

            if (source.equals(panButtons.getButtons().get(0))) {
                treeGeneration(null);
            } else if (source.equals(panButtons.getButtons().get(1))) {
                treeIteration();
            } else if (source.equals(panButtons.getButtons().get(2))) {
                treeAdd();
            } else if (source.equals(panButtons.getButtons().get(3))) {
                treeEfficiency();
            } else if (source.equals(panButtons.getButtons().get(4))) {
                treeRemove();
            } else if (source.equals(panButtons.getButtons().get(5))){
                MedzioIlgumas();
            
            
            
            
            
                KsSwing.setFormatStartOfLine(true);
                KsSwing.ounerr(taOutput, MESSAGES.getString("msg1"));
                KsSwing.setFormatStartOfLine(false);
            } else if (source.equals(cmbTreeType)) {
                enableButtons(false);
            }
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsSwing.ounerr(taOutput, errors[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 2) {
                    panParam1.getTfOfTable().get(0).setBackground(Color.red);
                    panParam1.getTfOfTable().get(1).setBackground(Color.red);
                }
            } else if (e.getCode() == 4) {
                KsSwing.ounerr(taOutput, MESSAGES.getString("msg3"));
            } else {
                KsSwing.ounerr(taOutput, e.getMessage());
            }
        } catch (java.lang.UnsupportedOperationException e) {
            KsSwing.ounerr(taOutput, e.getLocalizedMessage());
        } catch (Exception e) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("error5"));
            e.printStackTrace(System.out);
        }
    }

    public void treeGeneration(String filePath) throws MyException {
        // Nuskaitomi u??davinio parametrai
        readTreeParameters();
        // Sukuriamas aib??s objektas, priklausomai nuo med??io pasirinkimo
        // cmbTreeType objekte
        createTree();

        Telefonai[] autoArray;
        // Jei failas nenurodytas - generuojama
        if (filePath == null) {
            autoArray = TelGamyba.generuotiIrIsmaisyti(sizeOfGenSet, sizeOfInitialSubSet, coef);
            panParam1.getTfOfTable().get(2).setText(String.valueOf(sizeOfLeftSubSet));
        } else { // Skaitoma is failo
            telSet.load(filePath);
            autoArray = new Telefonai[telSet.size()];
            int i = 0;
            for (Object o : telSet.toArray()) {
                autoArray[i++] = (Telefonai) o;
            }
            // Skaitant i?? failo i??mai??oma standartiniu Collections.shuffle metodu.
            Collections.shuffle(Arrays.asList(autoArray), new Random());
        }

        // I??mai??yto masyvo elementai sura??omi i aib??
        telSet.clear();
        for (Telefonai a : autoArray) {
            telSet.add(a);
        }
        // I??vedami rezultatai
        // Nustatoma, kad eilut??s prad??ioje neskai??iuot?? i??vedam?? eilu??i?? skai??iaus
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, telSet.toVisualizedString(delimiter),
                MESSAGES.getString("msg5"));
        // Nustatoma, kad eilut??s prad??ioje skai??iuot?? i??vedam?? eilu??i?? skai??i??
        KsSwing.setFormatStartOfLine(false);
        enableButtons(true);
    }

    private void treeAdd() throws MyException {
        Telefonai tel = TelGamyba.gautiIsBazes();
        telSet.add(tel);
        panParam1.getTfOfTable().get(2).setText(String.valueOf(--sizeOfLeftSubSet));
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, tel, MESSAGES.getString("msg7"));
        KsSwing.oun(taOutput, telSet.toVisualizedString(delimiter));
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeRemove() {
        KsSwing.setFormatStartOfLine(true);
        if (telSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
            KsSwing.oun(taOutput, telSet.toVisualizedString(delimiter));
        } else {
            int nr = new Random().nextInt(telSet.size());
            Telefonai tel = (Telefonai) telSet.toArray()[nr];
            telSet.remove(tel );
            KsSwing.oun(taOutput, telSet , MESSAGES.getString("msg6"));
            KsSwing.oun(taOutput, telSet.toVisualizedString(delimiter));
        }
        KsSwing.setFormatStartOfLine(false);
    }

    private void treeIteration() {
        KsSwing.setFormatStartOfLine(true);
        if (telSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
        } else {
            KsSwing.oun(taOutput, telSet, MESSAGES.getString("msg8"));
        }
        KsSwing.setFormatStartOfLine(false);
    }
    
    private void MedzioIlgumas(){
        KsSwing.setFormatStartOfLine(true);
        if (telSet.isEmpty()) {
            KsSwing.ounerr(taOutput, MESSAGES.getString("msg4"));
            KsSwing.oun(taOutput, telSet.toVisualizedString(delimiter));
        } else {
            int n = telSet.MedzioDydis();
            KsSwing.oun(taOutput,n);
        }
        KsSwing.setFormatStartOfLine(false);
        
    }
    
    
    

    private void treeEfficiency() throws MyException {
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, "", MESSAGES.getString("msg2"));
        KsSwing.setFormatStartOfLine(false);
        boolean[] statesOfButtons = new boolean[panButtons.getButtons().size()];
        for (int i = 0; i < panButtons.getButtons().size(); i++) {
            statesOfButtons[i] = panButtons.getButtons().get(i).isEnabled();
            panButtons.getButtons().get(i).setEnabled(false);
        }
        cmbTreeType.setEnabled(false);
        for (Component component : menus.getComponents()) {
            component.setEnabled(false);
        }

        Greitaveika gt = new Greitaveika();

        // Sukuriamos dvi tuscios gijos. Panaudojamas Java Executor servisas.
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Si gija paima rezultatus is greitaveikos tyrimo gijos ir isveda 
        // juos i taOutput. Gija baigia darb?? kai gaunama FINISH_COMMAND
        executorService.submit(() -> {
            KsSwing.setFormatStartOfLine(false);
            try {
                String result;
                while (!(result = gt.getResultsLogger().take())
                        .equals(Greitaveika.FINISH_COMMAND)) {
                    KsSwing.ou(taOutput, result);
                    gt.getSemaphore().release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            gt.getSemaphore().release();

            for (int i = 0; i < panButtons.getButtons().size(); i++) {
                panButtons.getButtons().get(i).setEnabled(statesOfButtons[i]);
            }
            cmbTreeType.setEnabled(true);
            for (Component component : menus.getComponents()) {
                component.setEnabled(true);
            }
        });

        //Sioje gijoje atliekamas greitaveikos tyrimas
        executorService.submit(() -> gt.pradetiTyrima());
        executorService.shutdown();
    }

    private void readTreeParameters() throws MyException {
        // Truput??lis kosmetikos..
        for (int i = 0; i < 3; i++) {
            panParam1.getTfOfTable().get(i).setBackground(Color.WHITE);
        }
        // Nuskaitomos parametr?? reiksm??s. Jei konvertuojant is String
        // ??vyksta klaida, sugeneruojama NumberFormatException situacija. Tam, kad
        // atskirti kuriame JTextfield'e ivyko klaida, panaudojama nuosava
        // situacija MyException
        int i = 0;
        try {
            // Pakeitimas (replace) tam, kad sukelti situacij?? esant
            // neigiamam skai??iui            
            sizeOfGenSet = Integer.valueOf(panParam1.getParametersOfTable().get(i).replace("-", "x"));
            sizeOfInitialSubSet = Integer.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            coef = Double.valueOf(panParam1.getParametersOfTable().get(++i).replace("-", "x"));
        } catch (NumberFormatException e) {
            // Galima ir taip: pagauti exception'?? ir v??l mesti
            throw new MyException(panParam1.getParametersOfTable().get(i), i);
        }
        delimiter = tfDelimiter.getText();
    }

    private void createTree() throws MyException {
        switch (cmbTreeType.getSelectedIndex()) {
            case 0:
                telSet = new BstSetKTUx(new Telefonai());
                break;
            case 1:
                telSet = new AvlSetKTUx(new Telefonai());
                break;
            default:
                enableButtons(false);
                throw new MyException(MESSAGES.getString("msg1"));
        }
    }

    private void enableButtons(boolean enable) {
        for (int i : new int[]{1, 2, 4, 5, 6}) {
            panButtons.getButtons().get(i).setEnabled(enable);
        }
    }

    public JTextArea getTaOutput() {
        return taOutput;
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()
            // Arba sitaip, tada swing komponentu isvaizda priklausys
            // nuo naudojamos OS:
            //  UIManager.getSystemLookAndFeelClassName()
            // Arba taip:
            //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
            // Linux'e dar taip:
            //  "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
            );
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Ks.ou(ex.getMessage());
        }
        Lab3Window window = new Lab3Window();
        window.setLocation(50, 50);
        window.setIconImage(new ImageIcon(MESSAGES.getString("icon")).getImage());
        window.setTitle(MESSAGES.getString("title"));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1100, 650));
        window.pack();
        window.setVisible(true);
    }
}
