package domain;

import tietorakenteet.Lista;

/**
 * Laskennan tulokset kokoava luokka.
 * @author pertjenn
 */
public class Tulos {
    private final String algoritmi;
    private final Lista polku;
    private final double pituus;
    private final double aika;
    private final double tutkittujaRuutuja;
    private boolean onnistui;
    
    /**
     * Luo tyhjän tulosolion, jos polkua ei löytynyt.
     * @param algoritmi valitun algoritmin nimi
     */
    public Tulos(String algoritmi) {
        this(algoritmi, new Lista(), -1, -1, -1, false);
    }
    
    /**
     * Luo uuden tulosolion annetuilla parametreilla.
     * @param algoritmi valitun algoritmin nimi
     * @param polku algoritmin löytämä polku solmulistana
     * @param pituus löydetyn polun pituus
     * @param aika laskentaan käytetty aika
     * @param tutkittuja tutkittujen ruutujen osuus vapaista
     * @param onnistui onnistuiko laskenta
     */
    public Tulos(String algoritmi, Lista polku, double pituus, double aika, double tutkittuja, boolean onnistui) {
        this.algoritmi = algoritmi;
        this.polku = polku;
        this.pituus = pyorista(pituus, 100);
        this.aika = pyorista(aika, 10000);
        this.tutkittujaRuutuja = pyorista(tutkittuja, 100);
        this.onnistui = onnistui;
    }

    public String getAlgoritmi() {
        return algoritmi;
    }

    public Lista getPolku() {
        return polku;
    }
    
    public double getPituus() {
        return this.pituus;
    }

    public double getAika() {
        return this.aika;
    }
    
    public double getTutkittujaRuutuja() {
        return this.tutkittujaRuutuja;
    }
    
    /**
     * Palauttaa tiedon laskennan onnistumisesta.
     * @return true, jos onnistui, muutoin false
     */
    public boolean onnistui() {
        return onnistui;
    }
    
    private double pyorista(double liukuluku, int kerroin) {
        double palautettava = Math.round(liukuluku * kerroin);
        return palautettava / kerroin;
    }
}
