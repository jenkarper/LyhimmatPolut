package dao;

import domain.Kartta;
import java.io.File;
import java.util.Scanner;

/**
 * Karttatiedoston lukemisesta vastaava luokka.
 * @author pertjenn
 */
public class Kartanlukija implements TiedostonlukijaIO {
    /**
     * Karttaolio, joka luodaan karttatiedostosta.
     */
    private Kartta kartta;

    /**
     * Lukee parametrina annetun karttatiedoston ja luo siitä Kartta-olion.
     * @param tiedosto tiedostopolku
     */
    @Override
    public boolean lue(final String tiedosto) {

        try (Scanner lukija = new Scanner(new File(tiedosto))) {

            lukija.nextLine(); // kartan tyyppi
            int korkeus = Integer.valueOf(lukija.nextLine().split(" ")[1]);
            int leveys = Integer.valueOf(lukija.nextLine().split(" ")[1]);
            lukija.nextLine(); // tiedostotyyppi?

            int rivi = 0;
            char[][] karttataulu = new char[korkeus][leveys];

            while (lukija.hasNextLine()) {
                String kartanRivi = lukija.nextLine();
                karttataulu[rivi] = kartanRivi.toCharArray();
                rivi++;
            }
            this.kartta = new Kartta(karttataulu, korkeus, leveys);
            return true;

        } catch (Exception e) {
            System.out.println("Virhe tiedostonluvussa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Palauttaa tiedostosta muodostetun Kartta-olion.
     * @return
     */
    @Override
    public Kartta haeKartta() {
        return this.kartta;
    }
}
