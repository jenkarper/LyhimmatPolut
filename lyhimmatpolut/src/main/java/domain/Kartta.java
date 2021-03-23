package domain;

/**
 * Kartta-olio, jota algoritmit ja gui käyttävät.
 * @author pertjenn
 */
public class Kartta {
    private final char[][] karttataulu;
    private final int leveys;
    private final int korkeus;

    /**
     * Luo uuden karttaolion.
     * @param taulu karttatiedostoa vastaavaa 2d-taulukko
     * @param k taulukon korkeus
     * @param l taulukon leveys
     */
    public Kartta(final char[][] taulu, final int k, final int l) {
        this.karttataulu = taulu;
        this.korkeus = k;
        this.leveys = l;
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
}
