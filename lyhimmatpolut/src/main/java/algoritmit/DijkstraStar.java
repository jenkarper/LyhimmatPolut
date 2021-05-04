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

    /**
     * Algoritmin tarvitsemat oliomuuttujat.
     * char-taulukko kartan muodostavista merkeistä
     * boolean-taulukko pitämään kirjaa, mitä solmuja on jo tutkittu
     * double-taulukko pitämään kirjaa etäisyyksistä alkusolmuun
     * Solmu-taulukko pitämään kirjaa edeltäjistä polulla
     * boolean-arvo kertomaan, halutaanko käyttää Dijkstraa vai A*:ia
     * int-arvo kertomaan, montako vaapaata ruutua kartassa on
     * int-arvo etäisyystaulukon alustamiseen
     */
    private char[][] kartta;
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private final boolean dijkstra;
    private int vapaitaRuutuja;

    private static final int INF = 999999999;

    /**
     * Alustaa uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     * @param dijkstra halutaanko luoda Dijkstra vai A*
     */
    public DijkstraStar(Kartta valittuKartta, boolean dijkstra) {
        this.dijkstra = dijkstra;
        alusta(valittuKartta);
    }

    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {
        long aikaAlussa = System.nanoTime();

        alku.setVertailuarvo(0);
        etaisyys[alku.getY()][alku.getX()] = 0;

        Keko keko = new Keko();
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();

            if (u.samaSolmu(loppu)) {
                long kesto = System.nanoTime() - aikaAlussa;
                double pituus = this.etaisyys[loppu.getY()][loppu.getX()];
                Lista polku = muodostaPolku(alku, loppu);

                return new Tulos(polku, pituus, kesto, this.vierailtu, this.vapaitaRuutuja);
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

        return new Tulos();
    }

    /**
     * Käy läpi kaikki kahdeksan naapurisolmua ja poimii ehdokkaat listalle. Ei salli oikaisua esteen kulmalla.
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
                    naapurit.lisaa(new Solmu(x + j, y + i, 1));
                } else { // diagonaalisiirtymä
                    if (sallittuSolmu(y, x + j) && sallittuSolmu(y + i, x)) {
                        naapurit.lisaa(new Solmu(x + j, y + i, Math.sqrt(2)));
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
     * @return euklidinen etäisyys s --> loppu, jos suorituksessa on A*, muutoin 0
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

    /**
     * Alustaa muuttujat uuden reitin laskemista varten.
     *
     * @param valittuKartta uusi valittu kartta
     */
    public final void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.vierailtu = new boolean[kartta.length][kartta[0].length];
        this.etaisyys = new double[kartta.length][kartta[0].length];
        this.edeltaja = new Solmu[kartta.length][kartta[0].length];
        this.vapaitaRuutuja = valittuKartta.getVapaitaRuutuja();

        for (int i = 0; i < kartta.length; i++) {
            for (int j = 0; j < kartta[0].length; j++) {
                this.etaisyys[i][j] = INF;
            }
        }
    }
}
