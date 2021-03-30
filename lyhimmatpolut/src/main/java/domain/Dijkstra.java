package domain;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Luokka etsii polun kartalta Dijkstran algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class Dijkstra {

    private final char[][] kartta;
    private final int sarakkeet;
    private final int rivit;
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private final Lista polku;
    //private final ArrayList<Solmu> polku;
    
    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public Dijkstra(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.sarakkeet = valittuKartta.getLeveys();
        this.rivit = valittuKartta.getKorkeus();
        this.polku = new Lista();
        //this.polku = new ArrayList<>();

        alustaTaulukot();
    }

    /**
     * Laskee lyhimman polun annettujen pisteiden välillä.
     * @param alku lähtösolmu
     * @param loppu maalisolmu
     * @return löydetty polku listana
     */
    public Lista laskeReitti(final Solmu alku, final Solmu loppu) {
        alku.setEtaisyys(0);
        etaisyys[alku.getX()][alku.getY()] = 0;

        PriorityQueue<Solmu> keko = new PriorityQueue<>();
        keko.add(alku);

        while (!keko.isEmpty()) {
            Solmu u = keko.poll();
            int ux = u.getX();
            int uy = u.getY();

            if (maali(u, loppu)) {
                vierailtu[ux][uy] = true;
                break;
            }

            if (!vierailtu[ux][uy]) {
                vierailtu[ux][uy] = true;
                //ArrayList<Solmu> naapurit = haeNaapurit(u);
                Lista naapurit = haeNaapurit(u);
                
                for (int i = naapurit.getViimeinen(); i >= 0; i--) {
                    Solmu n = naapurit.haeSolmu(i);
                    int nx = n.getX();
                    int ny = n.getY();

                    if (etaisyys[nx][ny] > etaisyys[ux][uy] + n.getPaino()) {
                        etaisyys[nx][ny] = etaisyys[ux][uy] + n.getPaino();
                        n.setEtaisyys(etaisyys[nx][ny]);
                        edeltaja[nx][ny] = u;
                        keko.add(n);
                    }
                }

//                for (Solmu n : naapurit) {
//                    int nx = n.getX();
//                    int ny = n.getY();
//
//                    if (etaisyys[nx][ny] > etaisyys[ux][uy] + n.getPaino()) {
//                        etaisyys[nx][ny] = etaisyys[ux][uy] + n.getPaino();
//                        n.setEtaisyys(etaisyys[nx][ny]);
//                        edeltaja[nx][ny] = u;
//                        keko.add(n);
//                    }
//                }
            }
        }
        if (!vierailtu[loppu.getX()][loppu.getY()]) {
            return new Lista();
            //return new ArrayList<>();
        }
        muodostaPolku(alku, loppu);

        return this.polku;
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

    private boolean maali(Solmu s, Solmu maali) {
        return s.equals(maali);
    }

//    private ArrayList<Solmu> haeNaapurit(Solmu s) {
//        ArrayList<Solmu> naapurit = new ArrayList<>();
//        for (int i = -1; i <= 1; i++) {
//            int rivi = s.getX() + i;
//            for (int j = -1; j <= 1; j++) {
//                int sarake = s.getY() + j;
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
//                    if (rivi == s.getX() || sarake == s.getY()) { // sama rivi/sarake kuin solmulla s
//                        Solmu naapuri = new Solmu(rivi, sarake, 1); // tässä etäisyys on vielä INF
//                        naapurit.add(naapuri);
//                    } else {
//                        Solmu naapuri = new Solmu(rivi, sarake, sqrt(2)); // diagonaalisiirtymä
//                        naapurit.add(naapuri);
//                    }
//                }
//            }
//        }
//
//        return naapurit;
//    }
    
    private Lista haeNaapurit(Solmu s) {
        Lista naapurit = new Lista();
        for (int i = -1; i <= 1; i++) {
            int rivi = s.getX() + i;
            for (int j = -1; j <= 1; j++) {
                int sarake = s.getY() + j;

                if (!kartalla(rivi, sarake)) { // ovatko indeksit valideja
                    continue;
                }

                if (i == 0 && j == 0) { // onko kyseessä solmu s
                    continue;
                }

                if (kartta[rivi][sarake] == '.') { // tässä kohdassa karttaa on reitti
                    if (rivi == s.getX() || sarake == s.getY()) { // sama rivi/sarake kuin solmulla s
                        Solmu naapuri = new Solmu(rivi, sarake, 1); // tässä etäisyys on vielä INF
                        naapurit.lisaa(naapuri);
                    } else {
                        Solmu naapuri = new Solmu(rivi, sarake, sqrt(2)); // diagonaalisiirtymä
                        naapurit.lisaa(naapuri);
                    }
                }
            }
        }
        return naapurit;
    }

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
    }

    private void muodostaPolku(final Solmu alku, final Solmu loppu) {
        Solmu s = loppu;
        this.polku.lisaa(loppu);

        while (!s.equals(alku)) {
            s = edeltaja[s.getX()][s.getY()];
            polku.lisaa(s);
        }

        //Collections.reverse(polku);
    }
    
    /**
     * Hakee löydetyn polun pituuden etäisyystaulukosta.
     * @param loppu maalisolmu
     * @return maalisolmun etäisyys lähtösolmusta
     */
    public double getPolunPituus(Solmu loppu) {
        return this.etaisyys[loppu.getX()][loppu.getY()];
    }
}
