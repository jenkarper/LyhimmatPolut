package algoritmit;

import domain.Kartta;
import domain.Solmu;
import domain.Suunta;
import domain.Tulos;
import tietorakenteet.Keko;
import tietorakenteet.Lista;

/**
 * Luokka etsii lyhimmän polun JPS-algoritmia käyttäen (kesken!).
 *
 * @author pertjenn
 */
public class JPS implements Algoritmi {

    private char[][] kartta;
    private double[][] etaisyys;
    private Solmu[][] edeltaja;
    private boolean[][] vierailtu;
    private Solmu alku;
    private Solmu loppu;

    private static final int INF = 999999999;

    /**
     * Luo uuden algoritmiolion valitulle kartalle (ei toimi vielä täysin oikein!).
     *
     * @param valittuKartta käyttäjän valitsema kartta
     */
    public JPS(Kartta valittuKartta) {
        alusta(valittuKartta);
    }

    // Palauttaa väärän polun pituuden; virhe liittyy mahdollisesti etenemiseen esteen kulmalla.
    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {
        long aikaAlkaa = System.nanoTime();

        this.alku = alku;
        this.loppu = loppu;

        Keko keko = new Keko();
        this.etaisyys[alku.getY()][alku.getX()] = 0;
        this.vierailtu[alku.getY()][alku.getX()] = true;

        alku.setVertailuarvo(0);
        keko.lisaa(alku);

        while (!keko.tyhja()) {
            Solmu u = keko.poistaPienin();

            if (u.samaSolmu(loppu)) {
                long kesto = System.nanoTime() - aikaAlkaa;
                double pituus = this.etaisyys[loppu.getY()][loppu.getX()];
                Lista polku = muodostaPolku(alku, loppu);
                return new Tulos(polku, pituus, kesto);
            }

            Lista naapurit = haeNaapurit(u);

            for (int i = 0; i < naapurit.koko(); i++) {
                Solmu naapuri = naapurit.haeSolmu(i);

                // lasketaan hypyn suunta ja hypätään
                Suunta suunta = laskeSuunta(u, naapuri);
                Solmu hyppypiste = hyppaa(u, suunta);

                if (hyppypiste == null) {
                    continue;
                }

                // lasketaan hypyssä löytynyt uusi etäisyysarvo vertailua varten
                double vanhaEtaisyys = this.etaisyys[hyppypiste.getY()][hyppypiste.getX()];
                double hypynPituus = laskeHypynPituus(u, hyppypiste, suunta);
                double uusiEtaisyys = this.etaisyys[u.getY()][u.getX()] + hypynPituus;

                if (uusiEtaisyys < vanhaEtaisyys && !this.vierailtu[hyppypiste.getY()][hyppypiste.getX()]) {

                    this.etaisyys[hyppypiste.getY()][hyppypiste.getX()] = uusiEtaisyys;
                    this.vierailtu[hyppypiste.getY()][hyppypiste.getX()] = true;
                    this.edeltaja[hyppypiste.getY()][hyppypiste.getX()] = u;

                    double vertailuarvo = uusiEtaisyys + arvioituEtaisyysLoppuun(hyppypiste, loppu);
                    hyppypiste.setVertailuarvo(vertailuarvo);
                    keko.lisaa(hyppypiste);
                }
            }
        }
        return new Tulos();
    }

    /**
     * Etsii seuraavia mahdollisia hyppypisteitä solmusta s käsin.
     * @param s solmu, joka viimeksi on poimittu keosta
     * @param suunta suunta, johon ollaan liikkumassa
     * @return hyppypiste solmuna, jos sellainen löytyy
     */
    private Solmu hyppaa(Solmu s, Suunta suunta) {

        // otetaan ensimmäinen askel
        Solmu seuraava = new Solmu(s.getX() + suunta.x, s.getY() + suunta.y);

        // ollaanko ulkona kartalta tai esteessä?
        if (!sallittuSolmu(seuraava.getY(), seuraava.getX())) {
            return null;
        }

        // ollaanko lopussa?
        if (seuraava.samaSolmu(loppu)) {
            return seuraava;
        }

        // onko pakotettuja naapureita?
        int x = seuraava.getX();
        int y = seuraava.getY();

        if (suunta.y == 0) { // ollaan hyppäämässä vaakasuoraan
            if ((!sallittuSolmu(y + 1, x) && sallittuSolmu(y + 1, x + suunta.x))
                    || (!sallittuSolmu(y - 1, x) && sallittuSolmu(y - 1, x + suunta.x))) {
                return seuraava;
            }
        } else if (suunta.x == 0) { // ollaan hyppäämässä pystysuoraan
            if ((!sallittuSolmu(y, x + 1) && sallittuSolmu(y + suunta.y, x + 1))
                    || (!sallittuSolmu(y, x - 1) && sallittuSolmu(y + suunta.y, x - 1))) {
                return seuraava;
            }
        } else { // ollaan hyppäämässä viistoon
            if ((!sallittuSolmu(y, x - suunta.x) && sallittuSolmu(y + suunta.y, x - suunta.x))
                    || (!sallittuSolmu(y - suunta.y, x) && sallittuSolmu(y - suunta.y, x + suunta.x))) {
                return seuraava;
            }
        }

        // onko suunta diagonaalinen?
        if (suunta.x != 0 && suunta.y != 0) {
            // jos pystyhyppy solmusta seuraava löytää uusia hyppypisteitä, solmu seuraava on myös hyppypiste
            Solmu pystyhyppy = hyppaa(seuraava, new Suunta(0, suunta.y));
            if (pystyhyppy != null) {
                return seuraava;
            }
            // jos vaakahyppy löytää uusia hyppypisteitä, solmu seuraava on myös hyppypiste
            Solmu vaakahyppy = hyppaa(seuraava, new Suunta(suunta.x, 0));
            if (vaakahyppy != null) {
                return seuraava;
            }
        }
        // jos mikään ylläolevista ehdoista ei ole toteutunut, jatketaan hyppypisteen etsimistä samasta suunnasta
        return hyppaa(seuraava, suunta);
    }

    /**
     * Hakee käsillä olevan solmun naapurit karsien ne suunnan perusteella (jos s on alkusolmu, haetaan kaikki vapaat naapurit).
     * @param s viimeksi keosta poimittu solmu
     * @return listan naapurisolmuista, joihin on mahdollista edetä
     */
    private Lista haeNaapurit(Solmu s) {

        Lista naapurit = new Lista();
        int sx = s.getX();
        int sy = s.getY();
        Solmu edellinen = this.edeltaja[sy][sx];

        // jos kyseessä on alkusolmu, haetaan kaikki vapaat naapurit
        if (edellinen == null) {
            return haeKaikkiNaapurit(s);
        }

        Suunta suunta = laskeSuunta(edellinen, s);

        if (suunta.y == 0 || suunta.x == 0) { // ollaan siirtymässä suoraan
            if (sallittuSolmu(sy + suunta.y, sx + suunta.x)) { // luonnollinen naapuri
                naapurit.lisaa(new Solmu(sx + suunta.x, sy + suunta.y, 1));
            }

            if (suunta.y == 0) { // vaakasiirtymän pakotetut naapurit
                if (!sallittuSolmu(sy + 1, sx) && sallittuSolmu(sy + 1, sx + suunta.x)) {
                    naapurit.lisaa(new Solmu(sx + suunta.x, sy + 1, Math.sqrt(2)));
                }
                if (!sallittuSolmu(sy - 1, sx) && sallittuSolmu(sy - 1, sx + suunta.x)) {
                    naapurit.lisaa(new Solmu(sx + suunta.x, sy - 1, Math.sqrt(2)));
                }
            } else { // pystysiirtymän pakotetut naapurit
                if (!sallittuSolmu(sy, sx + 1) && sallittuSolmu(sy + suunta.y, sx + 1)) {
                    naapurit.lisaa(new Solmu(sx + 1, sy + suunta.y, Math.sqrt(2)));
                }
                if (!sallittuSolmu(sy, sx - 1) && sallittuSolmu(sy + suunta.y, sx - 1)) {
                    naapurit.lisaa(new Solmu(sx - 1, sy + suunta.y, Math.sqrt(2)));
                }
            }
        } else { // ollaan siirtymässä viistoon
            
            if (sallittuSolmu(sy, sx + suunta.x)) { // luonnollinen naapuri suoraan sivulla
                naapurit.lisaa(new Solmu(sx + suunta.x, sy, 1));
            }
            if (sallittuSolmu(sy + suunta.y, sx)) { // luonnollinen naapuri suoraan yllä/alla
                naapurit.lisaa(new Solmu(sx, sy + suunta.y, 1));
            }
            if (sallittuSolmu(sy + suunta.y, sx + suunta.x)) { // luonnollinen naapuri viistossa + ei olla siirtymässä viistoon esteen nurkalla
                naapurit.lisaa(new Solmu(sx + suunta.x, sy + suunta.y, Math.sqrt(2)));
            }

            // diagonaalisiirtymän pakotetut naapurit
            if (!sallittuSolmu(sy, sx - suunta.x) && sallittuSolmu(sy + suunta.y, sx - suunta.x)) {
                naapurit.lisaa(new Solmu(sx - suunta.x, sy + suunta.y, Math.sqrt(2)));
            }
            if (!sallittuSolmu(sy - suunta.y, sx) && sallittuSolmu(sy - suunta.y, sx + suunta.x)) {
                naapurit.lisaa(new Solmu(sx + suunta.x, sy - suunta.y, Math.sqrt(2)));
            }
        }

        return naapurit;
    }

    /**
     * Hakee kaikki solmun s vapaat naapurit (eli naapurit, jotka ovat kartalla ja joissa ei ole estettä).
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
     * Laskee naapurihaussa ja hyppäämisessä tarvittavan suhteellisen suunnan kahden solmun välillä.
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
     * @return euklidinen etäisyys s-loppu, jos suorituksessa on A*, muutoin 0
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

    @Override
    public boolean[][] haeTutkitut() {
        return this.vierailtu;
    }

    /**
     * Laskee kahden solmun välisen etäisyyden koordinaattien perusteella (oletus on, että kahden solmun välillä on kuljettu koko ajan samaan suuntaan).
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

        if (suunta.x == 0 || suunta.y == 0) { // ollaan hypätty suoraan
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

        for (int i = 0; i < this.etaisyys.length; i++) {
            for (int j = 0; j < this.etaisyys[0].length; j++) {
                this.etaisyys[i][j] = INF;
            }
        }
    }
}
