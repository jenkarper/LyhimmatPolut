package suorituskykytestaus;

import domain.Tulos;

/**
 * Suorituskykytestauksen tulokset kokoava luokka.
 *
 * @author pertjenn
 */
public class Testitulos {

    private final String kartta;
    private final Reittikuvaus reitti;
    private final Tulos dijkstra;
    private final Tulos aStar;
    private final Tulos jps;

    /**
     * Muodostaa uuden suorituskykytestituloksen.
     *
     * @param kartta kartta, jossa polkuja etsittiin
     * @param reitti kartalta arvottu reitti
     * @param dijkstra Dijkstran laskema tulos
     * @param aStar A*:n laskema tulos
     * @param jps JPS:n laskema tulos
     */
    public Testitulos(String kartta, Reittikuvaus reitti, Tulos dijkstra, Tulos aStar, Tulos jps) {
        this.kartta = kartta;
        this.reitti = reitti;
        this.dijkstra = dijkstra;
        this.aStar = aStar;
        this.jps = jps;
    }

    public String getKartta() {
        return kartta;
    }

    public Reittikuvaus getReitti() {
        return reitti;
    }

    public double getDijkstraAika() {
        return dijkstra.getAika();
    }

    public double getAStarAika() {
        return aStar.getAika();
    }

    public double getJpsAika() {
        return jps.getAika();
    }
    
    public double getDijkstranTutkitut() {
        return dijkstra.laskeTutkittujenOsuus();
    }
    public double getAStarinTutkitut() {
        return aStar.laskeTutkittujenOsuus();
    }

    /**
     * Laskee aikaeron Dijkstran ja A*:n välillä.
     *
     * @return merkkijono, joka ilmaisee eron
     */
    private String laskeEroAStarDijkstra() {
        double ero = dijkstra.getAika() - aStar.getAika();
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
     *
     * @return merkkijono, joka ilmaisee eron
     */
    private String laskeEroJpsDijkstra() {
        double ero = dijkstra.getAika() - jps.getAika();
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
     * Hakee Dijkstran ja A*:n tutkittujen solmujen osuudet.
     */
    private String haeTutkittujenSolmujenOsuudet() {
        double dijkstraTutki = dijkstra.laskeTutkittujenOsuus();
        double aStarTutki = aStar.laskeTutkittujenOsuus();
        StringBuilder sb = new StringBuilder();
        sb.append("\nDijkstra käsitteli laskennan aikana ").append(dijkstraTutki).append(" % kartan vapaasta alueesta.");
        sb.append("\nA* käsitteli laskennan aikana ").append(aStarTutki).append(" % kartan vapaasta alueesta.");

        return sb.toString();
    }

    /**
     * Näyttää testin tuloksen merkkijonomuodossa.
     *
     * @return testi tulos merkkijonona
     */
    public String haeSuorituskykytulosMerkkijonona() {
        StringBuilder tulos = new StringBuilder();
        tulos.append("Käytetty kartta: ").append(kartta)
                .append("\nHaetun polun pituus: ").append(reitti.getReitinPituus())
                .append("\nDijkstran käyttämä aika: ").append(dijkstra.getAika());

        if (haeOikeellisuustulosAStarille()) {
            tulos.append("\nA*-algoritmin käyttämä aika: ").append(aStar.getAika())
                    .append(laskeEroAStarDijkstra());
        } else {
            tulos.append("\nA* ei löytänyt oikeaa polkua");
        }

        if (haeOikeellisuustulosJPSlle()) {
            tulos.append("\nJump Point Search -algoritmin käyttämä aika: ").append(jps.getAika())
                    .append(laskeEroJpsDijkstra());
        } else {
            tulos.append("\nJump Point Search -algoritmi ei löytänyt oikeaa polkua");
        }

        return tulos.toString();
    }

    /**
     * Palauttaa tiedon, toimiko A* oikein verrattuna Dijkstraan (käytetään
     * yksikkötesteissä).
     *
     * @return true, jos A* ja Dijkstra löysivät saman polun
     */
    public boolean haeOikeellisuustulosAStarille() {
        return dijkstra.getPituus() == aStar.getPituus();
    }

    /**
     * Palauttaa tiedon, toimiko JPS oikein verrattuna Dijkstraan (käytetään
     * yksikkötesteissä).
     *
     * @return true, jos JPS ja Dijkstra löysivät saman polun
     */
    public boolean haeOikeellisuustulosJPSlle() {
        double dijkstranPituus = dijkstra.getPituus();
        double jpsPituus = jps.getPituus();
        double erotus = Math.abs(dijkstranPituus - jpsPituus);
        
        return (erotus < 0.0000001);
    }
}
