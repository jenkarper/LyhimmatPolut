package algoritmit;

import domain.Kartta;
import domain.Solmu;
import domain.Suunta;
import domain.Tulos;
import tietorakenteet.Lista;

/**
 * Luokka etsii lyhimmän polun JPS-algoritmia käyttäen (kesken!).
 *
 * @author pertjenn
 */
public class JPS implements Algoritmi {

    private char[][] kartta;
    private int rivit;
    private int sarakkeet;
    private Lista hyppypisteet;
    private Suunta[] suunnat;

    /**
     * Luo uuden algoritmiolion valitulle kartalle.
     * @param valittuKartta 
     */
    public JPS(Kartta valittuKartta) {
        alusta(valittuKartta);
    }

    /**
     * Palauttaa haussa löydetyt hyppypisteet listana (käytetään hyppypisteiden visualisointiin).
     * @return hyppypisteet listana
     */
    public Lista getHyppypisteet() {
        return hyppypisteet;
    }

    @Override
    public Tulos laskeReitti(Solmu alku, Solmu loppu) {

        // Tällä hetkellä algoritmi vain tekee suorahaun neljään suuntaan
        // alkupisteestä alkaen alas ja oikealle, kunnes törmätään esteeseen.
        Solmu s = alku;

        while (sallittuSolmu(s)) {
            
            for (Suunta suunta : this.suunnat) {
                suoraHaku(s, suunta);
            }
            s = new Solmu(s.getX() + 1, s.getY() + 1);
        }
        if (this.hyppypisteet.koko() == 0) {
            System.out.println("Hyppypisteitä ei löytynyt.");
        }
        return new Tulos("JPS", new Lista(), hyppypisteet, -1, -1, -1, true);
    }

    /**
     * Algoritmin osa, joka suorittaa akselien mukaisen haun annettuun suuntaan.
     * @param s tarkasteltavana oleva solmu
     * @param suunta suunta, johon haku on etenemässä
     */
    public void suoraHaku(Solmu s, Suunta suunta) {

        int x = s.getX();
        int y = s.getY();
        Solmu nykyinen = s;

        while (true) {
            // tarkastellaan ensin solmun s vieressä olevaa solmua
            nykyinen = new Solmu(x + suunta.x, y + suunta.y);
            if (!sallittuSolmu(nykyinen)) {
                break;
            }

            // jos solmun s vieressä oleva solmu on sallittu, siirrytään tarkastelemaan seuraavaa solmua
            Solmu seuraava = new Solmu(nykyinen.getX() + suunta.x, nykyinen.getY() + suunta.y);
            if (!sallittuSolmu(seuraava)) {
                break;
            }

            // jos seuraavakin solmu on sallittu, tarkistetaan, löytyykö pakotettuja naapureita
            if (etsiPakotetutNaapurit(nykyinen, seuraava, suunta)) {
                hyppypisteet.lisaa(seuraava);
                break;
            } else {
                x += suunta.x;
                y += suunta.y;
            }
        }
    }

    /**
     * Tutkii, onko nykyisellä solmulla pakotettuja naapureita.
     * @param nykyinen tarkasteluvuorossa oleva solmu
     * @param seuraava tarkasteluvuorossa olevan solmun lähin naapuri parametrin mukaisessa suunnassa
     * @param suunta suunta, johon haku on etenemässä
     * @return true, jos pakotettuja naapureita löytyi
     */
    private boolean etsiPakotetutNaapurit(Solmu nykyinen, Solmu seuraava, Suunta suunta) {

        int nx = nykyinen.getX();
        int ny = nykyinen.getY();
        int sx = seuraava.getX();
        int sy = seuraava.getY();

        // ollaan tekemässä vaakahakua
        if (suunta.y == 0) {
            if (kartalla(ny + 1, nx) && este(ny + 1, nx)
                    && kartalla(sy + 1, sx) && !este(sy + 1, sx)) {
                return true;
            }
            if (kartalla(ny - 1, nx) && este(ny - 1, nx)
                    && kartalla(sy - 1, sx) && !este(sy - 1, sx)) {
                return true;
            }
        }

        // ollaan tekemässä pystyhakua
        if (suunta.x == 0) {
            if (kartalla(ny, nx + 1) && este(ny, nx + 1)
                    && kartalla(sy, sx + 1) && !este(sy, sx + 1)) {
                return true;
            }
            if (kartalla(ny, nx - 1) && este(ny, nx - 1)
                    && kartalla(sy, sx - 1) && !este(sy, sx - 1)) {
                return true;
            }
        }

        return false;
    }

    private boolean sallittuSolmu(Solmu s) {
        return kartalla(s.getY(), s.getX()) && !este(s.getY(), s.getX());
    }

    private boolean kartalla(int rivi, int sarake) {
        return (rivi >= 0 && rivi < this.rivit) && (sarake >= 0 && sarake < this.sarakkeet);
    }

    private boolean este(int rivi, int sarake) {
        return this.kartta[rivi][sarake] == '@';
    }

    private void alusta(Kartta valittuKartta) {
        this.kartta = valittuKartta.getKarttataulu();
        this.sarakkeet = valittuKartta.getLeveys();
        this.rivit = valittuKartta.getKorkeus();
        this.hyppypisteet = new Lista();
        this.suunnat = new Suunta[]{new Suunta(1, 0), new Suunta(-1, 0), new Suunta(0, 1), new Suunta(0, -1)};
    }

    @Override
    public boolean[][] haeTutkitut() {
        return new boolean[1][1];
    }

//    private void tulostaHyppypisteet() {
//        for (int i = 0; i < this.hyppypisteet.koko(); i++) {
//            Solmu hp = hyppypisteet.haeSolmu(i);
//            System.out.println("Hyppypiste: " + hp.getX() + " " + hp.getY());
//        }
//    }
}
