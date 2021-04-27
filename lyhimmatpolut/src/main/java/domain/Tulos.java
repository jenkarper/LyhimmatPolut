package domain;

import tietorakenteet.Lista;

/**
 * Laskennan tulokset kokoava luokka.
 * Tarjoaa kolme konstruktoria: tyhjälle tulosoliolle, Dijkstran/A*:n tulokselle (ei hyppypisteitä)
 * ja JPS:n tulokselle (hyppypisteet).
 * @author pertjenn
 */
public class Tulos {
    private final Lista polku;
    private final double pituus;
    private final double aika;
    
    /**
     * Luo tyhjän tulosolion, jos polkua ei löytynyt.
     */
    public Tulos() {
        this(new Lista(), -1, -1);
    }
    
    /**
     * Luo uuden tulosolion annetuilla parametreilla.
     * @param polku algoritmin löytämä polku solmulistana
     * @param pituus löydetyn polun pituus
     * @param nanoaika laskentaan käytetty aika nanosekunteina
     */
    public Tulos(Lista polku, double pituus, long nanoaika) {
        this.polku = polku;
        this.pituus = pituus;
        this.aika = muunna(nanoaika);
    }

    public Lista getPolku() {
        return polku;
    }
    
    public double getPituus() {
        return pituus;
    }

    public double getAika() {
        return aika;
    }
    
    /**
     * Palauttaa tiedon laskennan onnistumisesta.
     * @return true, jos onnistui, muutoin false
     */
    public boolean onnistui() {
        return !this.polku.tyhja();
    }
    
    /**
     * Muuntaa saadun keston millisekunneiksi ja pyöristää sen neljään desimaaliin.
     * @param nanoaika algoritmin laskema aika longina
     * @return millisekuntiaika pyöristettynä
     */
    private double muunna(long nanoaika) {
        double milliaika = nanoaika / 1000000;
        double pyoristettava = Math.round(milliaika * 10000);
        return pyoristettava / 10000;
    }
}
