package domain;

import tietorakenteet.Lista;

/**
 * Laskennan tulokset kokoava luokka.
 * @author pertjenn
 */
public class Tulos {
    private final Lista polku;
    private final double pituus;
    private final double aika;
    private final boolean[][] tutkitut;
    private final int tutkittujaSolmuja;
    private final int vapaitaRuutuja;
    
    /**
     * Luo tyhjän tulosolion, jos polkua ei löytynyt.
     */
    public Tulos() {
        this(new Lista(), -1, -1, null, -1, -1);
    }
    
    /**
     * Luo uuden tulosolion annetuilla parametreilla.
     * @param polku algoritmin löytämä polku solmulistana
     * @param pituus löydetyn polun pituus
     * @param nanoaika laskentaan käytetty aika nanosekunteina
     * @param tutkitut boolean-taulukko laskennan aikana tutkituista solmuista
     * @param tutkittujaSolmuja kuinka monta solmua laskennan aikana tutkittiin
     * @param vapaitaRuutuja kuinka monta vapaata ruutua kartassa on
     */
    public Tulos(Lista polku, double pituus, long nanoaika, boolean[][] tutkitut, int tutkittujaSolmuja, int vapaitaRuutuja) {
        this.polku = polku;
        this.pituus = pituus;
        this.aika = muunna(nanoaika);
        this.tutkitut = tutkitut;
        this.tutkittujaSolmuja = tutkittujaSolmuja;
        this.vapaitaRuutuja = vapaitaRuutuja;
    }

    public Lista getPolku() {
        return polku;
    }
    
    public double getPituus() {
        return pituus;
    }
    
    public boolean[][] getTutkitut() {
        return tutkitut;
    }

    public double getAika() {
        return aika;
    }
    
    /**
     * Laskee laskennassa tutkittujen solmujen osuuden kaikista vapaista.
     * @return osuus prosentteina kahden desimaalin tarkkuudella
     */
    public double laskeTutkittujenOsuus() {
        double osuus = (double) this.tutkittujaSolmuja / (double) this.vapaitaRuutuja;
        double pyoristettava = Math.round(osuus * 10000);
        return pyoristettava / 100;
    }
    
    /**
     * Pyöristää lasketun pituuden graafista käyttöliittymää varten.
     * @return pituus kahden desimaalin tarkkuudella
     */
    public double laskePyoristettyPituus() {
        double pyoristettava = Math.round(this.pituus * 100);
        return pyoristettava / 100;
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
