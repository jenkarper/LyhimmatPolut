package domain;

import static java.lang.Math.sqrt;
import java.util.PriorityQueue;

/**
 * Luokka etsii polun kartalta Dijkstran algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class Dijkstra {

    private char[][] kartta;
    private int sarakkeet; // x
    private int rivit; // y
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private Lista polku;

    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public Dijkstra(Kartta valittuKartta) {
        alusta(valittuKartta);
    }

    /**
     * Laskee lyhimman polun annettujen pisteiden välillä.
     *
     * @param alku lähtösolmu
     * @param loppu maalisolmu
     * @return löydetty polku listana
     */
    public Lista laskeReitti(final Solmu alku, final Solmu loppu) {
        alku.setEtaisyys(0);
        etaisyys[alku.getY()][alku.getX()] = 0;

        PriorityQueue<Solmu> keko = new PriorityQueue<>();
        keko.add(alku);

        while (!keko.isEmpty()) {
            Solmu u = keko.poll();
            int uy = u.getY();
            int ux = u.getX();

            if (u.samaSolmu(loppu)) {
                vierailtu[uy][ux] = true;
                break;
            }

            if (!vierailtu[uy][ux]) {
                
                vierailtu[uy][ux] = true;
                Lista naapurit = haeNaapurit(u);
                
                for (int i = naapurit.getViimeinen(); i >= 0; i--) {
                    Solmu n = naapurit.haeSolmu(i);
                    int ny = n.getY();
                    int nx = n.getX();

                    if (etaisyys[ny][nx] > etaisyys[uy][ux] + n.getPaino()) {
                        n.setEtaisyys(etaisyys[uy][ux] + n.getPaino()); // päivitetään uusi, parempi etäisyysarvo
                        etaisyys[ny][nx] = n.getEtaisyys();
                        edeltaja[ny][nx] = u;
                        keko.add(n);
                    }
                }
            }
        }
        if (!vierailtu[loppu.getY()][loppu.getX()]) { // varmistetaan vielä, onko loppuun päästy
            return new Lista();
        }
        muodostaPolku(alku, loppu);

        return this.polku;
    }
  
    /**
     * Käy läpi kaikki kahdeksan naapuriruutua ja poimii ehdokkaat listalle.
     * @param s solmu, jonka naapureita haetaan
     * @return lista valideista naapurisolmuista
     */
    private Lista haeNaapurit(Solmu s) {
        Lista naapurit = new Lista();
        for (int i = -1; i <= 1; i++) {
            int rivi = s.getY() + i;
            for (int j = -1; j <= 1; j++) {
                int sarake = s.getX() + j;

                if (!kartalla(rivi, sarake)) { // ovatko indeksit valideja
                    continue;
                }

                if (i == 0 && j == 0) { // onko kyseessä solmu s
                    continue;
                }

                if (kartta[rivi][sarake] == '.') { // tässä kohdassa karttaa on reitti
                    if (rivi == s.getY() || sarake == s.getX()) { // sama rivi/sarake kuin solmulla s
                        Solmu naapuri = new Solmu(sarake, rivi, 1);
                        naapurit.lisaa(naapuri);
                    } else {
                        Solmu naapuri = new Solmu(sarake, rivi, sqrt(2)); // diagonaalisiirtymä
                        naapurit.lisaa(naapuri);
                    }
                }
            }
        }
        return naapurit; // naapurisolmuilla on tässä vaiheessa x- ja y-koordinaatit sekä paino, mutta etäisyys on vielä INF
    }

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
    }

    private void muodostaPolku(final Solmu alku, final Solmu loppu) {
        Solmu s = loppu;
        this.polku.lisaa(loppu);

        while (!s.samaSolmu(alku)) {
            s = edeltaja[s.getY()][s.getX()];
            polku.lisaa(s);
        }
    }

    /**
     * Hakee löydetyn polun pituuden etäisyystaulukosta.
     *
     * @param loppu maalisolmu
     * @return maalisolmun etäisyys lähtösolmusta
     */
    public double getPolunPituus(Solmu loppu) {
        return this.etaisyys[loppu.getY()][loppu.getX()];
    }

    /**
     * Alustaa muuttujat uuden reitin laskemista varten.
     * @param valittuKartta uusi valittu kartta
     */
    public final void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.sarakkeet = valittuKartta.getLeveys();
        this.rivit = valittuKartta.getKorkeus();
        this.polku = new Lista();

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
}
