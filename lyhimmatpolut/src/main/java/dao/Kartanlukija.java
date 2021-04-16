package dao;

import domain.Kartta;
import java.io.File;
import java.util.ArrayList;
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
     * Karttaolio, joka luodaan karttatiedostosta.
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

    private void haeMetatiedot(Scanner lukija, String tiedosto) {
        this.nimi = tiedosto.substring(7, tiedosto.length() - 4);
        lukija.nextLine(); // kartan tyyppi
        this.korkeus = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        this.leveys = Integer.valueOf(lukija.nextLine().split(" ")[1]);
        lukija.nextLine(); // tiedostotyyppi?
    }

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
//            String kartanRivi = lukija.nextLine();
//            karttataulu[rivi] = kartanRivi.toCharArray();
//            rivi++;
        }

        this.kartta = new Kartta(karttataulu, korkeus, leveys, nimi, vapaitaRuutuja);
    }

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
    
    private String muodostaSkenaarionPolku(String karttatiedosto) {
        StringBuilder polku = new StringBuilder();
        
        polku.append(karttatiedosto.substring(0, 7))
                .append("skenaariot/")
                .append(karttatiedosto.substring(7, karttatiedosto.length()))
                .append(".scen");
        
        
        return polku.toString();
    }

    private void muodostaSkenaario(Scanner lukija) {
        lukija.nextLine(); // skipataan ensimmäinen rivi
                
        while (lukija.hasNextLine()) {
            String tiedostonRivi = lukija.nextLine();
            this.skenaario.lisaaReittikuvaus(lueRivi(tiedostonRivi));
        }        
    }

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
