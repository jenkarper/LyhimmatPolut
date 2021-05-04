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
    private final int vapaitaRuutuja;
    
    /**
     * Luo tyhjän tulosolion, jos polkua ei löytynyt.
     */
    public Tulos() {
        this(new Lista(), -1, -1, null, -1);
    }
    
    /**
     * Luo uuden tulosolion annetuilla parametreilla.
     * @param polku algoritmin löytämä polku solmulistana
     * @param pituus löydetyn polun pituus
     * @param nanoaika laskentaan käytetty aika nanosekunteina
     * @param tutkitut boolean-taulukko laskennan aikana tutkituista solmuista
     * @param vapaitaRuutuja kuinka monta vapaata ruutua kartassa on
     */
    public Tulos(Lista polku, double pituus, long nanoaika, boolean[][] tutkitut, int vapaitaRuutuja) {
        this.polku = polku;
        this.pituus = pituus;
        this.aika = muunna(nanoaika);
        this.tutkitut = tutkitut;
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
     * @return osuus prosentteina
     */
    public double laskeTutkittujenOsuus() {
        int tutkittuja = 0;
        for (int i = 0; i < this.tutkitut.length; i++) {
            for (int j = 0; j < this.tutkitut[0].length; j++) {
                if (this.tutkitut[i][j]) {
                    tutkittuja++;
                }
            }
        }

        return ((double) tutkittuja / (double) this.vapaitaRuutuja) * 100.0;
    }
    
    /**
     * Pyöristää lasketun pituuden graafista käyttöliittymää varten.
     * @return pituus kahden desimaalin tarkkuudella
     */
    public double laskePyoristettyPituus() {
        double pyoristettava = Math.round(this.pituus * 100.0);
        return pyoristettava / 100.0;
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
