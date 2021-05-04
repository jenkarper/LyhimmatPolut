package dao;

import domain.Kartta;
import java.io.File;
import java.util.Scanner;
import suorituskykytestaus.Reittikuvaus;
import suorituskykytestaus.Skenaario;

/**
 * Karttatiedoston lukemisesta vastaava luokka.
 *
 * @author pertjenn
 */
public class Kartanlukija implements TiedostonlukijaIO {

    /**
     * Kartanlukijan tarvitsemat oliomuuttujat.
     * tiedostopolku, josta kartta/skenaario löytyy
     * Kartta-olio, joka luodaan
     * char-taulukko kartan muodostavista merkeistä
     * kartan korkeus
     * kartan leveys
     * kartan nimi
     * karttaa vastaava skenaario, joka luodaan
     */
    private String tiedostopolku;
    private Kartta kartta;
    private char[][] karttataulu;
    private int korkeus;
    private int leveys;
    private String nimi;
    private Skenaario skenaario;

    /**
     * Lukee parametrina annetun karttatiedoston ja luo siitä Kartta-olion.
     *
     * @param tiedosto tiedostopolku
     */
    @Override
    public boolean lueKartta(final String tiedosto) {
        this.tiedostopolku = tiedosto;

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
     * Palauttaa muodostetun karttaolion.
     * @return karttaolio
     */
    @Override
    public Kartta haeKartta() {
        return this.kartta;
    }

    /**
     * Lukee karttatiedoston alusta tarvittavat tiedot ja siirtää lukijan osoittamaan ensimmäistä varsinaista karttariviä.
     * @param lukija Scanner-olio, joka on lukenut tiedoston
     * @param tiedosto luetun tiedoston polku merkkijonona
     */
    private void haeMetatiedot(Scanner lukija, String tiedosto) {
        this.nimi = tiedosto.substring(7, tiedosto.length() - 4);
        lukija.nextLine();
        this.korkeus = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        this.leveys = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        lukija.nextLine();
    }

    /**
     * Muodostaa luetuista tiedoista uuden kartan.
     * @param lukija Scanner-olio, joka on lukenut tiedoston
     */
    private void muodostaKartta(Scanner lukija) {
        int vapaitaRuutuja = 0;
        int rivi = 0;
        this.karttataulu = new char[korkeus][leveys];

        while (lukija.hasNextLine()) {
            String kartanRivi = lukija.nextLine();
            
            for (int sarake = 0; sarake < leveys; sarake++) {
                karttataulu[rivi][sarake] = kartanRivi.charAt(sarake);
                if (kartanRivi.charAt(sarake) == '.') {
                    vapaitaRuutuja++;
                }
                
            }
            rivi++;
        }
        this.kartta = new Kartta(karttataulu, korkeus, leveys, nimi, vapaitaRuutuja);
    }

    /**
     * Lukee valittua karttaa vastaavan skenaariotiedoston.
     * @param tiedosto valitun kartan tiedostopolku
     * @return true, jos luku onnistui, muutoin false
     */
    @Override
    public boolean lueSkenaario(String tiedosto) {
        String polku = muodostaSkenaarionPolku(tiedosto);
        this.skenaario = new Skenaario();
        
        try (Scanner lukija = new Scanner(new File(polku))) {
            muodostaSkenaario(lukija);
            return true;

        } catch (Exception e) {
            System.out.println("Virhe tiedostonluvussa: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Skenaario haeSkenaario() {
        return this.skenaario;
    }
    
    /**
     * Muodostaa skenaariotiedoston polun karttatiedoston polusta.
     * @param karttatiedosto valitun kartan tiedostopolku
     * @return skenaariotiedoston polku merkkijonona
     */
    private String muodostaSkenaarionPolku(String karttatiedosto) {
        StringBuilder polku = new StringBuilder();
        
        polku.append(karttatiedosto.substring(0, 7))
                .append("skenaariot/")
                .append(karttatiedosto.substring(7, karttatiedosto.length()))
                .append(".scen");
        
        
        return polku.toString();
    }

    /**
     * Muodostaa luetuista tiedoista uuden Skenaario-olion.
     * @param lukija Scanner-olio, joka on lukenut tiedoston
     */
    private void muodostaSkenaario(Scanner lukija) {
        lukija.nextLine(); // skipataan ensimmäinen rivi
                
        while (lukija.hasNextLine()) {
            String tiedostonRivi = lukija.nextLine();
            this.skenaario.lisaaReittikuvaus(lueRivi(tiedostonRivi));
        }
    }

    /**
     * Muodostaa jokaisesta skenaarion reitistä reittikuvauksen.
     * @param tiedostonRivi yhtä reittikuvausta vastaava skenaariotiedoston rivi
     * @return uusi Reittikuvaus-olio
     */
    private Reittikuvaus lueRivi(String tiedostonRivi) {
        String[] sarakkeet = tiedostonRivi.split("\t");
        
        int alkuX = Integer.parseInt(sarakkeet[4]);
        int alkuY = Integer.parseInt(sarakkeet[5]);
        int loppuX = Integer.parseInt(sarakkeet[6]);
        int loppuY = Integer.parseInt(sarakkeet[7]);
        double pituus = Double.parseDouble(sarakkeet[8]);
        
        
        return new Reittikuvaus(alkuX, alkuY, loppuX, loppuY, pituus);
    }
}
