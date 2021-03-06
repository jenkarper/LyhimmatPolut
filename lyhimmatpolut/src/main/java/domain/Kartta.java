package domain;

/**
 * Kartta-olio, jota algoritmit ja gui käyttävät.
 * @author pertjenn
 */
public class Kartta {
    
    /**
     * Kartan tarvitsemat oliomuuttujat.
     * char-taulukko kartan muodostavista merkeistä
     * kartan leveys
     * kartan korkeus
     * kartan nimi
     * kuinka monessa kartan ruudussa ei ole estettä
     */
    private final char[][] karttataulu;
    private final int leveys;
    private final int korkeus;
    private final String nimi;
    private final int vapaitaRuutuja;

    /**
     * Luo uuden karttaolion.
     * @param taulu karttatiedostoa vastaavaa 2d-taulukko
     * @param k taulukon korkeus
     * @param l taulukon leveys
     * @param nimi kartan nimi
     * @param vapaitaRuutuja kartalla olevien vapaiden ruutujen lkm
     */
    public Kartta(final char[][] taulu, final int k, final int l, final String nimi, final int vapaitaRuutuja) {
        this.karttataulu = taulu;
        this.korkeus = k;
        this.leveys = l;
        this.nimi = nimi;
        this.vapaitaRuutuja = vapaitaRuutuja;
    }

    /**
     * Palauttaa karttaa vastaavan char-taulun.
     * @return karttataulu
     */
    public char[][] getKarttataulu() {
        return karttataulu;
    }

    public int getLeveys() {
        return leveys;
    }

    public int getKorkeus() {
        return korkeus;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public int getVapaitaRuutuja() {
        return vapaitaRuutuja;
    }
}
