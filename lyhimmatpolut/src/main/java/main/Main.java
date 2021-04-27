package main;

import algoritmit.JPS;
import dao.Kartanlukija;
import domain.Kartta;
import domain.Solmu;
import domain.Suunta;
import java.util.ArrayList;
import tietorakenteet.Lista;
import ui.GUI;

/**
 * Pääohjelmaluokka.
 *
 * @author pertjenn
 */
public class Main {

    /**
     * Kutsuu varsinaista main-metodia GUI-luokassa.
     *
     * @param args main-metodille passattava parametri
     */
    public static void main(String[] args) {
//        Kartanlukija lukija = new Kartanlukija();
//        lukija.lueKartta("kartat/Berlin_0_256.map");
//        Kartta kartta = lukija.haeKartta();
//        JPS jps = new JPS(kartta);
//        jps.laskeReitti(new Solmu(135, 166), new Solmu(142, 230));
//        Lista hyppypisteet = jps.getHyppypisteet();
//        System.out.println("Hyppypisteitä " + hyppypisteet.koko());
//        for (int i = 0; i < hyppypisteet.koko(); i++) {
//            Solmu hyppypiste = hyppypisteet.haeSolmu(i);
//            System.out.println("Hyppypiste: " + hyppypiste.getX() + " " + hyppypiste.getY());
//        }

        

        GUI.main(args);
    }
}
