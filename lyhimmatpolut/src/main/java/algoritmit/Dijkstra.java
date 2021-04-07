package algoritmit;

import domain.Ajanottaja;
import domain.Kartta;
import tietorakenteet.Lista;
import domain.Solmu;
import domain.Tulos;
import static java.lang.Math.sqrt;
import tietorakenteet.Keko;

/**
 * Luokka etsii polun kartalta Dijkstran algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class Dijkstra implements Algoritmi {

    private char[][] kartta;
    private int sarakkeet; // x
    private int rivit; // y
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private Ajanottaja ajanottaja;
    private int vapaitaRuutuja;
    private int tutkittujaRuutuja;

    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public Dijkstra(Kartta valittuKartta) {
        alusta(valittuKartta);
    }
    
    @Override
    public boolean[][] haeTutkitut() {
        return this.vierailtu;
    }

    /**
     * Laskee lyhimmän polun annettujen pisteiden välillä.
     *
     * @param alku lähtösolmu
     * @param loppu maalisolmu
     * @return haun tulokset sisältävä olio
     */
    @Override
    public Tulos laskeReitti(final Solmu alku, final Solmu loppu) {
        ajanottaja.kaynnista();

        alku.setEtaisyys(0);
        etaisyys[alku.getY()][alku.getX()] = 0;

        Keko keko = new Keko();
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();
            int uy = u.getY();
            int ux = u.getX();

            if (u.samaSolmu(loppu)) {
                vierailtu[uy][ux] = true;
                tutkittujaRuutuja++;
                break;
            }

            if (!vierailtu[uy][ux]) {

                vierailtu[uy][ux] = true;
                tutkittujaRuutuja++;
                Lista naapurit = haeNaapurit(u);

                for (int i = naapurit.getViimeinen(); i >= 0; i--) {
                    Solmu n = naapurit.haeSolmu(i);
                    int ny = n.getY();
                    int nx = n.getX();

                    if (etaisyys[ny][nx] > etaisyys[uy][ux] + n.getPaino()) {
                        n.setEtaisyys(etaisyys[uy][ux] + n.getPaino()); // päivitetään uusi, parempi etäisyysarvo
                        etaisyys[ny][nx] = n.getEtaisyys();
                        edeltaja[ny][nx] = u;
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

    private Lista haeNaapurit(Solmu s) {
        Lista naapurit = new Lista();
        int x = s.getX();
        int y = s.getY();

        boolean yla = false;
        boolean ala = false;
        boolean oikea = false;
        boolean vasen = false;

        // suoraan yläpuolella oleva naapuri
        if (kartalla(y - 1, x) && kartta[y - 1][x] == '.') {
            Solmu ylaNaapuri = new Solmu(x, y - 1, 1);
            yla = true;
            naapurit.lisaa(ylaNaapuri);
        }

        // suoraan oikealla oleva naapuri
        if (kartalla(y, x + 1) && kartta[y][x + 1] == '.') {
            Solmu oikeaNaapuri = new Solmu(x + 1, y, 1);
            oikea = true;
            naapurit.lisaa(oikeaNaapuri);
        }

        // suoraan alapuolella oleva naapuri
        if (kartalla(y + 1, x) && kartta[y + 1][x] == '.') {
            Solmu alaNaapuri = new Solmu(x, y + 1, 1);
            ala = true;
            naapurit.lisaa(alaNaapuri);
        }

        // suoraan vasemmlla oleva naapuri
        if (kartalla(y, x - 1) && kartta[y][x - 1] == '.') {
            Solmu vasenNaapuri = new Solmu(x - 1, y, 1);
            vasen = true;
            naapurit.lisaa(vasenNaapuri);
        }

        // vasemmalla ylhäällä oleva naapuri
        if (kartalla(y - 1, x - 1) && kartta[y - 1][x - 1] == '.') {
            Solmu ylaVasenNaapuri = new Solmu(x - 1, y - 1, sqrt(2));
            if (yla && vasen) {
                naapurit.lisaa(ylaVasenNaapuri);
            }
        }

        // oikealla ylhäällä oleva naapuri
        if (kartalla(y - 1, x + 1) && kartta[y - 1][x + 1] == '.') {
            Solmu ylaOikeaNaapuri = new Solmu(x + 1, y - 1, sqrt(2));
            if (yla && oikea) {
                naapurit.lisaa(ylaOikeaNaapuri);
            }
        }

        // vasemmalla alhaalla oleva naapuri
        if (kartalla(y + 1, x - 1) && kartta[y + 1][x - 1] == '.') {
            Solmu alaVasenNaapuri = new Solmu(x - 1, y + 1, sqrt(2));
            if (ala && vasen) {
                naapurit.lisaa(alaVasenNaapuri);
            }
        }

        // oikealla alhaalla oleva naapuri
        if (kartalla(y + 1, x + 1) && kartta[y + 1][x + 1] == '.') {
            Solmu alaOikeaNaapuri = new Solmu(x + 1, y + 1, sqrt(2));
            if (oikea && ala) {
                naapurit.lisaa(alaOikeaNaapuri);
            }
        }

        return naapurit;
    }

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
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

            return new Tulos("Dijkstra", polku, pituus, aika, tutkittuja, true);
        }

        return new Tulos("Dijkstra");
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

    /**
     * Käy läpi kaikki kahdeksan naapuriruutua ja poimii ehdokkaat listalle.
     *
     * @param s solmu, jonka naapureita haetaan
     * @return lista valideista naapurisolmuista
     */
//    private Lista haeNaapurit(Solmu s) {
//        Lista naapurit = new Lista();
//        for (int i = -1; i <= 1; i++) {
//            int rivi = s.getY() + i;
//            for (int j = -1; j <= 1; j++) {
//                int sarake = s.getX() + j;
//
//                if (!kartalla(rivi, sarake)) { // ovatko indeksit valideja
//                    continue;
//                }
//
//                if (i == 0 && j == 0) { // onko kyseessä solmu s
//                    continue;
//                }
//
//                if (kartta[rivi][sarake] == '.') { // tässä kohdassa karttaa on reitti
//                    if (rivi == s.getY() || sarake == s.getX()) { // sama rivi/sarake kuin solmulla s
//                        Solmu naapuri = new Solmu(sarake, rivi, 1);
//                        naapurit.lisaa(naapuri);
//                    } else {
//                        Solmu naapuri = new Solmu(sarake, rivi, sqrt(2)); // diagonaalisiirtymä
//                        naapurit.lisaa(naapuri);
//                    }
//                }
//            }
//        }
//        return naapurit; // naapurisolmuilla on tässä vaiheessa x- ja y-koordinaatit sekä paino, mutta etäisyys on vielä INF
//    }
}
