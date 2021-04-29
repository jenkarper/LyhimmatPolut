package suorituskykytestaus;

/**
 * Suorituskykytestauksen tulokset kokoava luokka.
 *
 * @author pertjenn
 */
public class Testitulos {

    private final String kartta;
    private final Reittikuvaus reitti;
    private final double dijkstraAika;
    private final double aStarAika;
    private final double jpsAika;
    private final boolean aStarLoysiPolun;
    private final boolean jpsLoysiPolun;

    /**
     * Muodostaa uuden suorituskykytestituloksen.
     *
     * @param kartta kartta, jossa polkuja etsittiin
     * @param reitti kartalta arvottu reitti
     * @param dijkstraAika Dijkstran käyttämä aika
     * @param aStarAika A*:n käyttämä aika
     * @param jpsAika JPS:n käyttämä aika
     * @param aStarLoysiPolun toimiko A* oikein
     * @param jpsLoysiPolun toimiko JPS oikein
     */
    public Testitulos(String kartta, Reittikuvaus reitti, double dijkstraAika, double aStarAika, double jpsAika, boolean aStarLoysiPolun, boolean jpsLoysiPolun) {
        this.kartta = kartta;
        this.reitti = reitti;
        this.dijkstraAika = dijkstraAika;
        this.aStarAika = aStarAika;
        this.jpsAika = jpsAika;
        this.aStarLoysiPolun = aStarLoysiPolun;
        this.jpsLoysiPolun = jpsLoysiPolun;
    }

    public String getKartta() {
        return kartta;
    }

    public Reittikuvaus getReitti() {
        return reitti;
    }

    public double getDijkstraAika() {
        return dijkstraAika;
    }

    public double getAStarAika() {
        return aStarAika;
    }

    public double getJpsAika() {
        return jpsAika;
    }

    /**
     * Laskee aikaeron Dijkstran ja A*:n välillä.
     *
     * @return merkkijono, joka ilmaisee eron
     */
    private String laskeAStarEro() {
        double ero = dijkstraAika - aStarAika;
        StringBuilder sb = new StringBuilder();
        sb.append("\nA* oli ").append(ero).append(" ms ");

        if (ero > 0) {
            sb.append("nopeampi kuin Dijkstra.");
        } else {
            sb.append("hitaampi kuin Dijkstra.");
        }

        return sb.toString();
    }

    /**
     * Laskee aikaeron Dijkstran ja JPS:n välillä.
     * @return merkkijono, joka ilmaisee eron
     */
    private String laskeJPSEro() {
        double ero = dijkstraAika - jpsAika;
        StringBuilder sb = new StringBuilder();
        sb.append("\nJump Point Search oli ").append(ero).append(" ms ");

        if (ero > 0) {
            sb.append("nopeampi kuin Dijkstra.");
        } else {
            sb.append("hitaampi kuin Dijkstra.");
        }

        return sb.toString();
    }

    /**
     * Näyttää testin tuloksen merkkijonomuodossa.
     * @return testi tulos merkkijonona
     */
    public String haeSuorituskykytulos() {
        StringBuilder tulos = new StringBuilder();
        tulos.append("Käytetty kartta: ").append(kartta)
                .append("\nHaetun polun pituus: ").append(reitti.getReitinPituus())
                .append("\nDijkstran käyttämä aika: ").append(dijkstraAika);

        if (aStarLoysiPolun) {
            tulos.append("\nA*-algoritmin käyttämä aika: ").append(aStarAika)
                    .append(laskeAStarEro());
        } else {
            tulos.append("\nA* ei löytänyt oikeaa polkua");
        }

        if (jpsLoysiPolun) {
            tulos.append("\nJump Point Search -algoritmin käyttämä aika: ").append(jpsAika)
                    .append(laskeJPSEro());
        } else {
            tulos.append("\nJump Point Search -algoritmi ei löytänyt oikeaa polkua");
        }

        return tulos.toString();
    }

    /**
     * Palauttaa tiedon, toimiko A* oikein verrattuna Dijkstraan (käytetään yksikkötesteissä).
     * @return true, jos A* ja Dijkstra löysivät saman polun
     */
    public boolean haeOikeellisuustulosAStarille() {
        return this.aStarLoysiPolun;
    }

    /**
     * Palauttaa tiedon, toimiko JPS oikein verrattuna Dijkstraan (käytetään yksikkötesteissä).
     * @return true, jos JPS ja Dijkstra löysivät saman polun
     */
    public boolean haeOikeellisuustulosJPSlle() {
        return this.jpsLoysiPolun;
    }
}
