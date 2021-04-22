package algoritmit;

import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import tietorakenteet.Keko;
import tietorakenteet.Lista;

/**
 * Luokka etsii lyhimmän polun Dijkstran tai A*-algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class DijkstraStar implements Algoritmi {

    private char[][] kartta;
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private final boolean dijkstra;
    private final String nimi;

    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     * @param dijkstra halutaanko luoda Dijkstra vai A*
     */
    public DijkstraStar(Kartta valittuKartta, boolean dijkstra) {
        this.dijkstra = dijkstra;
        if (dijkstra) {
            this.nimi = "Dijkstra";
        } else {
            this.nimi = "A*";
        }
        alusta(valittuKartta);

    }

    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {
        long aikaAlkaa = System.nanoTime();

        alku.setVertailuarvo(0);
        etaisyys[alku.getY()][alku.getX()] = 0;

        Keko keko = new Keko();
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();

            if (u.samaSolmu(loppu)) {
                long kesto = System.nanoTime() - aikaAlkaa;
                double pituus = this.etaisyys[loppu.getY()][loppu.getX()];
                Lista polku = muodostaPolku(alku, loppu);

                return new Tulos(this.nimi, polku, pituus, kesto);
            }

            if (!this.vierailtu[u.getY()][u.getX()]) {
                this.vierailtu[u.getY()][u.getX()] = true;
                Lista naapurit = haeNaapurit(u);

                for (int i = naapurit.getViimeinen(); i >= 0; i--) {
                    Solmu n = naapurit.haeSolmu(i);
                    double vanhaEtaisyys = this.etaisyys[n.getY()][n.getX()];
                    double uusiEtaisyys = this.etaisyys[u.getY()][u.getX()] + n.getPaino();

                    if (vanhaEtaisyys > uusiEtaisyys) {
                        double vertailuarvo = uusiEtaisyys + arvioituEtaisyysLoppuun(n, loppu);
                        n.setVertailuarvo(vertailuarvo);
                        this.etaisyys[n.getY()][n.getX()] = uusiEtaisyys;
                        this.edeltaja[n.getY()][n.getX()] = u;
                        keko.lisaa(n);
                    }
                }
            }
        }

        return new Tulos(this.nimi);
    }

    /**
     * Käy läpi kaikki kahdeksan naapuriruutua ja poimii ehdokkaat listalle.
     *
     * @param s solmu, jonka naapureita haetaan
     * @return lista valideista naapurisolmuista
     */
    private Lista haeNaapurit(Solmu s) {
        Lista naapurit = new Lista();

        int y = s.getY();
        int x = s.getX();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (!sallittuSolmu(y + i, x + j) || (i == 0 && j == 0)) {
                    continue;
                }

                if ((y + i == y) || (x + j == x)) { // samalla rivillä/samassa sarakkeessa kuin s
                    Solmu naapuri = new Solmu(x + j, y + i, 1);
                    naapurit.lisaa(naapuri);
                } else { // diagonaalisiirtymä
                    if (sallittuSolmu(y, x + j) && sallittuSolmu(y + i, x)) {
                        Solmu naapuri = new Solmu(x + j, y + i, Math.sqrt(2));
                        naapurit.lisaa(naapuri);
                    }
                }
            }
        }
        return naapurit;
    }

    /**
     * Tarkistaa, ovatko koordinaatit kartan rajoissa ja onko niiden kohdalla vapaata maastoa.
     * @param rivi käsillä olevan solmun y-koordinaatti
     * @param sarake käsillä olevan solmun x-koordiaatti
     * @return true, jos solmuun voidaan siirtyä
     */
    private boolean sallittuSolmu(int rivi, int sarake) {

        if ((rivi >= 0 && rivi < this.kartta.length) && (sarake >= 0 && sarake < this.kartta[0].length)) { // ollaan kartan rajojen sisällä
            return this.kartta[rivi][sarake] == '.'; // solmu on vapaata maastoa
        }

        return false;
    }

    /**
     * Laskee arvioidun etäisyyden loppuun sen perusteella, onko suorituksessa Dijkstra vai A*.
     * @param s käsillä oleva solmu
     * @param loppu etsittävän polun loppusolmu
     * @return euklidinen etäisyys s-loppu, jos suorituksessa on A*, muutoin 0
     */
    private double arvioituEtaisyysLoppuun(Solmu s, Solmu loppu) {
        if (this.dijkstra) {
            return 0.0;
        }
        double xErotus = s.getX() - loppu.getX();
        double yErotus = s.getY() - loppu.getY();

        return Math.sqrt((xErotus * xErotus) + (yErotus * yErotus));
    }

    /**
     * Muodostaa löydetyn polun rekursiivisesti käymällä läpi edeltäjätaulukkoa.
     * @param alku haetun polun alku
     * @param loppu haetun polun loppu
     * @return polun solmut Lista-oliona (loppusolmu indeksissä 0)
     */
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

    @Override
    public boolean[][] haeTutkitut() {
        return this.vierailtu;
    }

    /**
     * Alustaa muuttujat uuden reitin laskemista varten.
     *
     * @param valittuKartta uusi valittu kartta
     */
    public final void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        int rivit = this.kartta.length;
        int sarakkeet = this.kartta[0].length;
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
