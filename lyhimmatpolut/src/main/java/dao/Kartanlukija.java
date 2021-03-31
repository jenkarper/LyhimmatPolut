package dao;

import domain.Kartta;
import java.io.File;
import java.util.Scanner;

/**
 * Karttatiedoston lukemisesta vastaava luokka.
 *
 * @author pertjenn
 */
public class Kartanlukija implements TiedostonlukijaIO {

    /**
     * Karttaolio, joka luodaan karttatiedostosta.
     */
    private Kartta kartta;
    private char[][] karttataulu;
    private int korkeus;
    private int leveys;
    private String nimi;

    /**
     * Lukee parametrina annetun karttatiedoston ja luo siitä Kartta-olion.
     *
     * @param tiedosto tiedostopolku
     */
    @Override
    public boolean lue(final String tiedosto) {

        try (Scanner lukija = new Scanner(new File(tiedosto))) {

            haeMetatiedot(lukija, tiedosto);
            muodostaKartta(lukija);
            return true;

        } catch (Exception e) {
            System.out.println("Virhe tiedostonluvussa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Palauttaa tiedostosta muodostetun Kartta-olion.
     *
     * @return
     */
    @Override
    public Kartta haeKartta() {
        return this.kartta;
    }

    private void haeMetatiedot(Scanner lukija, String tiedosto) {
        this.nimi = tiedosto.substring(7, tiedosto.length() - 4);
        lukija.nextLine(); // kartan tyyppi
        this.korkeus = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        this.leveys = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        lukija.nextLine(); // tiedostotyyppi?
    }

    private void muodostaKartta(Scanner lukija) {
        int rivi = 0;
        this.karttataulu = new char[korkeus][leveys];

        while (lukija.hasNextLine()) {
            String kartanRivi = lukija.nextLine();
            karttataulu[rivi] = kartanRivi.toCharArray();
            rivi++;
        }

        this.kartta = new Kartta(karttataulu, korkeus, leveys, nimi);
    }
}
