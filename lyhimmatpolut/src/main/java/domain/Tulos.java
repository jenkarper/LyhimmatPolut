package domain;

import tietorakenteet.Lista;

/**
 * Laskennan tulokset kokoava luokka.
 * Tarjoaa kolme konstruktoria: tyhjälle tulosoliolle, Dijkstran/A*:n tulokselle (ei hyppypisteitä)
 * ja JPS:n tulokselle (hyppypisteet).
 * @author pertjenn
 */
public class Tulos {
    private final String algoritmi;
    private final Lista polku;
    private final Lista hyppypisteet;
    private final double pituus;
    private final double aika;
    
    /**
     * Luo tyhjän tulosolion, jos polkua ei löytynyt.
     * @param algoritmi valitun algoritmin nimi
     */
    public Tulos(String algoritmi) {
        this(algoritmi, new Lista(), new Lista(), -1, -1);
    }
    
    /**
     * Luo uuden Dijkstra/A*-tulosolion annetuilla parametreilla.
     * @param algoritmi valitun algoritmin nimi
     * @param polku algoritmin löytämä polku solmulistana
     * @param pituus löydetyn polun pituus
     * @param nanoaika laskentaan käytetty aika nanosekunteina
     */
    public Tulos(String algoritmi, Lista polku, double pituus, long nanoaika) {
        this(algoritmi, polku, new Lista(), pituus, nanoaika);
    }
    
    /**
     * Luo uuden JPS-tulosolion annetuilla parametreilla.
     * @param algoritmi valitun algoritmin nimi
     * @param polku algoritmin löytämä polku solmulistana
     * @param hyppypisteet algoritmin tunnistamat hyppypisteet
     * @param pituus löydetyn polun pituus
     * @param nanoaika laskentaan käytetty aika nanosekunteina
     */
    public Tulos(String algoritmi, Lista polku, Lista hyppypisteet, double pituus, long nanoaika) {
        this.algoritmi = algoritmi;
        this.polku = polku;
        this.hyppypisteet = hyppypisteet;
        this.pituus = pituus;
        this.aika = muunna(nanoaika);
    }

    public String getAlgoritmi() {
        return algoritmi;
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
    
    public Lista getHyppypisteet() {
        return hyppypisteet;
    }
    
    /**
     * Palauttaa tiedon laskennan onnistumisesta.
     * @return true, jos onnistui, muutoin false
     */
    public boolean onnistui() {
        // väliaikainen ratkaisu, koska toistaiseksi JPS ei muodosta polkua, mutta haluan hyppypisteet näkyviin
        if (this.algoritmi.equals("JPS")) {
            return true;
        }
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
