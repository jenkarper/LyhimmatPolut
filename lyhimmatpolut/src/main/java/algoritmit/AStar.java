package algoritmit;

import domain.Ajanottaja;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import static java.lang.Math.sqrt;
import tietorakenteet.Keko;
import tietorakenteet.Lista;

/**
 * Luokka etsii lyhimmän polun A*-algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class AStar implements Algoritmi {

    private char[][] kartta;
    private int sarakkeet; // x
    private int rivit; // y
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private Ajanottaja ajanottaja;
    private int vapaitaRuutuja;
    private int tutkittujaRuutuja;
    private Heuristiikka heuristiikka;

    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public AStar(Kartta valittuKartta) {
        alusta(valittuKartta);
    }

    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {
        ajanottaja.kaynnista();

        alku.setVertailuarvo(0);
        etaisyys[alku.getY()][alku.getX()] = 0;

        Keko keko = new Keko();
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();

            if (u.samaSolmu(loppu)) {
                vieraile(u);
                break;
            }

            if (!this.vierailtu[u.getY()][u.getX()]) {
                vieraile(u);
                Lista naapurit = haeNaapurit(u);

                for (int i = naapurit.getViimeinen(); i >= 0; i--) {
                    Solmu n = naapurit.haeSolmu(i);
                    if (kasitteleNaapuri(u, n, loppu)) {
                        keko.lisaa(n);
                    }
                }
            }
        }
        if (!vierailtu[loppu.getY()][loppu.getX()]) { // varmistetaan vielä, onko loppuun päästy
            return muodostaTulos(alku, loppu, false);
        }

        ajanottaja.pysayta();

        return muodostaTulos(alku, loppu, true);
    }

    @Override
    public boolean[][] haeTutkitut() {
        return this.vierailtu;
    }

    /**
     * Käy läpi kaikki kahdeksan naapuriruutua ja poimii ehdokkaat listalle.
     *
     * @param s solmu, jonka naapureita haetaan
     * @return lista valideista naapurisolmuista
     */
    private Lista haeNaapurit(Solmu s) {
        Lista naapurit = new Lista();
        int sy = s.getY();
        int sx = s.getX();

        for (int i = -1; i <= 1; i++) {
            int y = sy + i;
            for (int j = -1; j <= 1; j++) {
                int x = sx + j;

                if (!sallittuSolmu(y, x) || (i == 0 && j == 0)) {
                    continue;
                }

                if ((y == sy) || (x == sx)) { // samalla rivilla/samassa sarakkeessa kuin s
                    Solmu naapuri = new Solmu(x, y, 1);
                    naapurit.lisaa(naapuri);
                } else { // diagonaalisiirtymä
                    if (sallittuSolmu(sy, sx + j) && sallittuSolmu(sy + i, sx)) {
                        Solmu naapuri = new Solmu(x, y, sqrt(2));
                        naapurit.lisaa(naapuri);
                    }
                }
            }
        }
        return naapurit;
    }

    /**
     * Päivittää tarvittaessa naapurisolmun vertailuarvon.
     *
     * @param s solmu jossa ollaan nyt
     * @param naapuri solmun käsittelyssä oleva naapuri
     * @param loppu maalisolmu, jota tarvitaan manhattanetäisyyden laskemisessa
     * @return true, jos löytyi pienempi vertailuarvo, false muuten
     */
    private boolean kasitteleNaapuri(Solmu s, Solmu naapuri, Solmu loppu) {
        double vanhaEtaisyys = this.etaisyys[naapuri.getY()][naapuri.getX()];
        double uusiEtaisyys = this.etaisyys[s.getY()][s.getX()] + naapuri.getPaino();

        if (vanhaEtaisyys > uusiEtaisyys) {
            double vertailuarvo = uusiEtaisyys + euklidinenEtaisyys(naapuri, loppu);
            naapuri.setVertailuarvo(vertailuarvo);
            this.etaisyys[naapuri.getY()][naapuri.getX()] = uusiEtaisyys;
            this.edeltaja[naapuri.getY()][naapuri.getX()] = s;

            return true;
        }
        return false;
    }

    private boolean sallittuSolmu(int rivi, int sarake) {

        if (kartalla(rivi, sarake)) {
            return this.kartta[rivi][sarake] == '.';
        }

        return false;
    }

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
    }

    private void vieraile(Solmu s) {
        this.vierailtu[s.getY()][s.getX()] = true;
        this.tutkittujaRuutuja++;
    }

    private Lista muodostaPolku(final Solmu alku, final Solmu loppu) {
        Solmu s = loppu;
        Lista polku = new Lista();
        polku.lisaa(loppu);

        while (!s.samaSolmu(alku)) {
            s = edeltaja[s.getY()][s.getX()];
            polku.lisaa(s);
        }
        return polku;
    }

    private Tulos muodostaTulos(final Solmu alku, final Solmu loppu, boolean onnistui) {

        if (onnistui) {
            Lista polku = muodostaPolku(alku, loppu);
            double pituus = this.etaisyys[loppu.getY()][loppu.getX()];
            double aika = ajanottaja.getAika();
            double tutkittuja = haeTutkittujenOsuus();

            return new Tulos("A*", polku, pituus, aika, tutkittuja, true);
        }

        return new Tulos("A*");
    }

    private double haeTutkittujenOsuus() {
        return (tutkittujaRuutuja * 100) / vapaitaRuutuja;
    }

    /**
     * Alustaa muuttujat uuden reitin laskemista varten.
     *
     * @param valittuKartta uusi valittu kartta
     */
    public final void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.sarakkeet = valittuKartta.getLeveys();
        this.rivit = valittuKartta.getKorkeus();
        this.ajanottaja = new Ajanottaja();
        this.vapaitaRuutuja = valittuKartta.getVapaitaRuutuja();
        this.tutkittujaRuutuja = 0;
        this.heuristiikka = new Heuristiikka();

        alustaTaulukot();
    }

    private void alustaTaulukot() {
        this.vierailtu = new boolean[rivit][sarakkeet];
        this.etaisyys = new double[rivit][sarakkeet];
        this.edeltaja = new Solmu[rivit][sarakkeet];

        for (int i = 0; i < rivit; i++) {
            for (int j = 0; j < sarakkeet; j++) {
                this.etaisyys[i][j] = INF;
            }
        }
    }

    private double manhattanEtaisyys(Solmu s, Solmu loppu) {
        return this.heuristiikka.manhattanEtaisyys(s, loppu);
    }
    
    private double euklidinenEtaisyys(Solmu s, Solmu loppu) {
        return this.heuristiikka.euklidinenEtaisyys(s, loppu);
    }

}
