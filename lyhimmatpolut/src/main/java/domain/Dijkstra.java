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
    private final ArrayList<Solmu> polku;
    
    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public Dijkstra(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.sarakkeet = valittuKartta.getLeveys();
        this.rivit = valittuKartta.getKorkeus();
        this.polku = new ArrayList<>();

        alustaTaulukot();
    }

    /**
     * Laskee lyhimman polun annettujen pisteiden välillä.
     * @param alku lähtösolmu
     * @param loppu maalisolmu
     * @return löydetty polku listana
     */
    //CHECKSTYLE:OFF
    public ArrayList<Solmu> laskeReitti(final Solmu alku, final Solmu loppu) {
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
                ArrayList<Solmu> naapurit = haeNaapurit(u);

                for (Solmu n : naapurit) {
                    int nx = n.getX();
                    int ny = n.getY();

                    if (etaisyys[nx][ny] > etaisyys[ux][uy] + n.getPaino()) {
                        etaisyys[nx][ny] = etaisyys[ux][uy] + n.getPaino();
                        n.setEtaisyys(etaisyys[nx][ny]);
                        edeltaja[nx][ny] = u;
                        keko.add(n);
                    }
                }
            }
        }
        if (!vierailtu[loppu.getX()][loppu.getY()]) {
            return new ArrayList<>();
        }
        muodostaPolku(alku, loppu);

        return this.polku;
    }
    //CHECKSTYLE:ON

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

    //CHECKSTYLE:OFF
    private ArrayList<Solmu> haeNaapurit(Solmu s) {
        ArrayList<Solmu> naapurit = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            int rivi = s.getX() + i;
            for (int j = -1; j <= 1; j++) {
                int sarake = s.getY() + j;

                if (!kartalla(rivi, sarake)) {
                    continue;
                }

                if (i == 0 && j == 0) {
                    continue;
                }

                if (kartta[rivi][sarake] == '.') { // tässä kohdassa karttaa on reitti
                    if (rivi == s.getX() || sarake == s.getY()) {
                        Solmu naapuri = new Solmu(rivi, sarake, 1);
                        naapurit.add(naapuri);
                    } else {
                        Solmu naapuri = new Solmu(rivi, sarake, sqrt(2));
                        naapurit.add(naapuri);
                    }
                }
            }
        }

        return naapurit;
    }
    //CHECKSTYLE:ON

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
    }

    private void muodostaPolku(final Solmu alku, final Solmu loppu) {
        Solmu s = loppu;
        this.polku.add(loppu);

        while (!s.equals(alku)) {
            s = edeltaja[s.getX()][s.getY()];
            polku.add(s);
        }

        Collections.reverse(polku);
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
