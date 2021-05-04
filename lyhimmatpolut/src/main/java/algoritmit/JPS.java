package algoritmit;

import domain.Kartta;
import domain.Solmu;
import domain.Suunta;
import domain.Tulos;
import tietorakenteet.Keko;
import tietorakenteet.Lista;

/**
 * Luokka etsii lyhimmän polun JPS-algoritmia käyttäen.
 *
 * @author pertjenn
 */
public class JPS implements Algoritmi {

    /**
     * Algoritmin tarvitsemat oliomuuttujat.
     * char-taulukko kartan muodostavista merkeistä
     * boolean-taulukko pitämään kirjaa, mitä solmuja on jo tutkittu
     * double-taulukko pitämään kirjaa etäisyyksistä alkusolmuun
     * Solmu-taulukko pitämään kirjaa edeltäjistä polulla
     * int-arvo kertomaan, montako vapaata ruutua kartassa on
     * int-arvo etäisyystaulukon alustamiseen
     */
    private char[][] kartta;
    private boolean[][] vierailtu;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private int vapaitaRuutuja;

    private static final int INF = 999999999;

    /**
     * Luo uuden algoritmiolion valitulle kartalle.
     *
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public JPS(Kartta valittuKartta) {
        alusta(valittuKartta);
    }

    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {
        long aikaAlkaa = System.nanoTime();

        Keko keko = new Keko();
        this.etaisyys[alku.getY()][alku.getX()] = 0;

        alku.setVertailuarvo(0);
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();

            if (u.samaSolmu(loppu)) {
                long kesto = System.nanoTime() - aikaAlkaa;
                double pituus = this.etaisyys[loppu.getY()][loppu.getX()];
                Lista polku = muodostaPolku(alku, loppu);
                
                return new Tulos(polku, pituus, kesto, this.vierailtu, this.vapaitaRuutuja);
            }

            if (!this.vierailtu[u.getY()][u.getX()]) {
                
                this.vierailtu[u.getY()][u.getX()] = true;
                Lista naapurit = haeNaapurit(u);

                for (int i = 0; i < naapurit.koko(); i++) {
                    Solmu naapuri = naapurit.haeSolmu(i);
                    Suunta suunta = laskeSuunta(u, naapuri);
                    Solmu hyppypiste = hyppaa(u, suunta, loppu);

                    if (hyppypiste == null) {
                        continue;
                    }

                    double vanhaEtaisyys = this.etaisyys[hyppypiste.getY()][hyppypiste.getX()];
                    double hypynPituus = laskeHypynPituus(u, hyppypiste, suunta);
                    double uusiEtaisyys = this.etaisyys[u.getY()][u.getX()] + hypynPituus;

                    if (uusiEtaisyys < vanhaEtaisyys) {

                        this.etaisyys[hyppypiste.getY()][hyppypiste.getX()] = uusiEtaisyys;
                        this.edeltaja[hyppypiste.getY()][hyppypiste.getX()] = u;
                        double vertailuarvo = uusiEtaisyys + arvioituEtaisyysLoppuun(hyppypiste, loppu);
                        hyppypiste.setVertailuarvo(vertailuarvo);
                        keko.lisaa(hyppypiste);
                    }
                }
            }

        }
        return new Tulos();
    }

    /**
     * Etsii seuraavia mahdollisia hyppypisteitä solmusta s käsin.
     *
     * @param s solmu, joka viimeksi on poimittu keosta
     * @param suunta suunta, johon ollaan liikkumassa
     * @param loppu etsittävänä olevan polun loppusolmu
     * @return hyppypiste solmuna, jos sellainen löytyy
     */
    private Solmu hyppaa(Solmu s, Suunta suunta, Solmu loppu) {

        Solmu seuraava = new Solmu(s.getX() + suunta.x, s.getY() + suunta.y);

        if (!sallittuSolmu(seuraava.getY(), seuraava.getX())) {
            return null;
        }

        if (seuraava.samaSolmu(loppu)) {
            return seuraava;
        }

        // onko pakotettuja naapureita?
        int x = seuraava.getX();
        int y = seuraava.getY();
        
        if (suunta.y == 0) {
            if ((!sallittuSolmu(y + 1, s.getX()) && sallittuSolmu(y + 1, x))
                    || (!sallittuSolmu(y - 1 , s.getX()) && sallittuSolmu(y - 1, x))) {
                return seuraava;
            }
        } else if (suunta.x == 0) {
            if ((!sallittuSolmu(s.getY(), x + 1)) && sallittuSolmu(y, x + 1)
                    || (!sallittuSolmu(s.getY(), x - 1) && sallittuSolmu(y, x - 1))) {
                return seuraava;
            }
        }

        if (suunta.x != 0 && suunta.y != 0) {
            
            Solmu pystyhyppy = hyppaa(seuraava, new Suunta(0, suunta.y), loppu);
            if (pystyhyppy != null) {
                return seuraava;
            }

            Solmu vaakahyppy = hyppaa(seuraava, new Suunta(suunta.x, 0), loppu);
            if (vaakahyppy != null) {
                return seuraava;
            }
        }

        return hyppaa(seuraava, suunta, loppu);
    }

    /**
     * Hakee käsillä olevan solmun naapurit karsien ne suunnan perusteella (jos
     * s on alkusolmu, haetaan kaikki vapaat naapurit). Ei salli oikaisua esteen kulmalla.
     *
     * @param s viimeksi keosta poimittu solmu
     * @return listan naapurisolmuista, joihin on mahdollista edetä
     */
    public Lista haeNaapurit(Solmu s) {

        Lista naapurit = new Lista();
        int x = s.getX();
        int y = s.getY();
        Solmu edellinen = this.edeltaja[y][x];

        if (edellinen == null) {
            return haeKaikkiNaapurit(s);
        }

        Suunta suunta = laskeSuunta(edellinen, s);

        if (suunta.x == 0) { // ollaan etenemässä pystysuuntaan
            if (sallittuSolmu(y + suunta.y, x)) {
                naapurit.lisaa(new Solmu(x, y + suunta.y)); // luonnollinen naapuri
            }
            if (!sallittuSolmu(y - suunta.y, x - 1)) { // pakotetut naapurit sarakkeessa x - 1
                if (sallittuSolmu(y, x - 1)) {
                    naapurit.lisaa(new Solmu(x - 1, y));
                    if (sallittuSolmu(y + suunta.y, x) && sallittuSolmu(y + suunta.y, x - 1)) {
                        naapurit.lisaa(new Solmu(x - 1, y + suunta.y));
                    }
                }
            }
            if (!sallittuSolmu(y - suunta.y, x + 1)) { // pakotetut naapurit sarakkeessa x + 1
                if (sallittuSolmu(y, x + 1)) {
                    naapurit.lisaa(new Solmu(x + 1, y));
                    if (sallittuSolmu(y + suunta.y, x) && sallittuSolmu(y + suunta.y, x + 1)) {
                        naapurit.lisaa(new Solmu(x + 1, y + suunta.y));
                    }
                }
            }

        } else if (suunta.y == 0) { // ollaan etenemässä vaakasuuntaan
            if (sallittuSolmu(y, x + suunta.x)) {
                naapurit.lisaa(new Solmu(x + suunta.x, y)); // luonnollinen naapuri
            }
            if (!sallittuSolmu(y - 1, x - suunta.x)) { // pakotetut naapurit rivillä y - 1
                if (sallittuSolmu(y - 1, x)) {
                    naapurit.lisaa(new Solmu(x, y - 1));
                    if (sallittuSolmu(y, x + suunta.x) && sallittuSolmu(y - 1, x + suunta.x)) {
                        naapurit.lisaa(new Solmu(x + suunta.x, y - 1));
                    }
                }
            }
            if (!sallittuSolmu(y + 1, x - suunta.x)) { // pakotetut naapurit rivillä y + 1
                if (sallittuSolmu(y + 1, x)) {
                    naapurit.lisaa(new Solmu(x, y + 1));
                    if (sallittuSolmu(y, x + suunta.x) && sallittuSolmu(y + 1, x + suunta.x)) {
                        naapurit.lisaa(new Solmu(x + suunta.x, y + 1));
                    }
                }
            }

        } else { // ollaan etenemässä viistoon
            if (sallittuSolmu(y + suunta.y, x)) {
                naapurit.lisaa(new Solmu(x, y + suunta.y));
            }
            if (sallittuSolmu(y, x + suunta.x)) {
                naapurit.lisaa(new Solmu(x + suunta.x, y));
            }
            if (sallittuSolmu(y + suunta.y, x) && sallittuSolmu(y, x + suunta.x)) {
                if (sallittuSolmu(y + suunta.y, x + suunta.x)) {
                    naapurit.lisaa(new Solmu(x + suunta.x, y + suunta.y));
                }
            }
        }

        return naapurit;
    }

    /**
     * Hakee kaikki solmun s vapaat naapurit (eli naapurit, jotka ovat kartalla
     * ja joissa ei ole estettä).
     *
     * @param s käsillä oleva solmu (käytännössä aina alkusolmu)
     * @return lista naapurisolmuista, joihin on mahdollista edetä
     */
    private Lista haeKaikkiNaapurit(Solmu s) {
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
     * Laskee naapurihaussa ja hyppäämisessä tarvittavan suhteellisen suunnan
     * kahden solmun välillä.
     *
     * @param mista solmu josta ollaan tultu
     * @param mihin solmu johon ollaan tultu
     * @return uusi suuntaolio
     */
    private Suunta laskeSuunta(Solmu mista, Solmu mihin) {
        int x = 0;
        int y = 0;

        if (mista != null) {
            if ((mihin.getX() - mista.getX()) > 0) {
                x = 1;
            } else if ((mihin.getX() - mista.getX() < 0)) {
                x = -1;
            }

            if ((mihin.getY() - mista.getY()) > 0) {
                y = 1;
            } else if ((mihin.getY() - mista.getY()) < 0) {
                y = -1;
            }
        }

        return new Suunta(x, y);
    }

    /**
     * Tarkistaa, ovatko koordinaatit kartan rajojen sisällä ja onko niiden kohdalla vapaata.
     * @param rivi y-koordinaatti
     * @param sarake x-koordinaatti
     * @return true, jos solmuun voi edetä, muutoin false
     */
    private boolean sallittuSolmu(int rivi, int sarake) {

        if ((rivi >= 0 && rivi < this.kartta.length) && (sarake >= 0 && sarake < this.kartta[0].length)) { // ollaan kartan rajojen sisällä
            return this.kartta[rivi][sarake] == '.'; // solmu on vapaata maastoa
        }

        return false;
    }

    /**
     * Laskee arvioidun etäisyyden loppuun.
     *
     * @param s käsillä oleva solmu
     * @param loppu etsittävän polun loppusolmu
     * @return euklidinen etäisyys s --> loppu
     */
    private double arvioituEtaisyysLoppuun(Solmu s, Solmu loppu) {
        double xErotus = s.getX() - loppu.getX();
        double yErotus = s.getY() - loppu.getY();

        return Math.sqrt((xErotus * xErotus) + (yErotus * yErotus));
    }

    /**
     * Muodostaa löydetyn polun rekursiivisesti käymällä läpi edeltäjätaulukkoa.
     *
     * @param alku haetun polun alku
     * @param loppu haetun polun loppu
     * @return polun solmut Lista-oliona (loppusolmu indeksissä 0)
     */
    private Lista muodostaPolku(final Solmu alku, final Solmu loppu) {
        Solmu s = loppu;
        Lista polku = new Lista();
        polku.lisaa(loppu);

        while (!s.samaSolmu(alku)) {
            Solmu edellinenHyppypiste = this.edeltaja[s.getY()][s.getX()];
            Suunta suunta = laskeSuunta(s, edellinenHyppypiste);

            // sisempi while-silmukka poimii polkuun ne solmut, jotka jäävät kahden hyppypisteen väliin
            while (!s.samaSolmu(edellinenHyppypiste)) {
                s = new Solmu(s.getX() + suunta.x, s.getY() + suunta.y);
                polku.lisaa(s);
            }
        }
        return polku;
    }

    /**
     * Laskee kahden solmun välisen etäisyyden koordinaattien perusteella.
     *
     * @param mista solmu josta on lähdetty
     * @param mihin solmu johon on tultu
     * @param suunta suunta johon on kuljettu
     * @return laskettu etäisyys doublena
     */
    private double laskeHypynPituus(Solmu mista, Solmu mihin, Suunta suunta) {
        int xErotus = mista.getX() - mihin.getX();
        if (xErotus < 0) {
            xErotus = -xErotus;
        }

        int yErotus = mista.getY() - mihin.getY();
        if (yErotus < 0) {
            yErotus = -yErotus;
        }

        if (suunta.x == 0 || suunta.y == 0) {
            return xErotus + yErotus;
        } else {
            return xErotus * Math.sqrt(2);
        }
    }

    private void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.etaisyys = new double[kartta.length][kartta[0].length];
        this.vierailtu = new boolean[kartta.length][kartta[0].length];
        this.edeltaja = new Solmu[kartta.length][kartta[0].length];
        this.vapaitaRuutuja = valittuKartta.getVapaitaRuutuja();

        for (int i = 0; i < this.etaisyys.length; i++) {
            for (int j = 0; j < this.etaisyys[0].length; j++) {
                this.etaisyys[i][j] = INF;
            }
        }
    }
}
