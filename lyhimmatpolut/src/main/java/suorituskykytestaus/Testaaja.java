package suorituskykytestaus;

import algoritmit.Algoritmi;
import algoritmit.DijkstraStar;
import algoritmit.JPS;
import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;

/**
 * Algoritmien suorituskykyä testaava luokka.
 *
 * @author pertjenn
 */
public class Testaaja {

    private Algoritmi dijkstra;
    private Algoritmi aStar;
    private Algoritmi jps;
    private Kartta kartta;
    private Skenaario skenaario;
    private TiedostonlukijaIO lukija;
    private ArrayList<Reittikuvaus> testireitit;
    private ArrayList<Testitulos> tulokset;
    
    /**
     * Suorittaa suorituskykytestit valitulla kartalla ja annetuilla spekseillä.
     * 
     * @param valittuKartta karttatiedoston polku
     * @param polunPituusMin arvottavien reittien polun pituuden alaraja
     * @param polunPituusMax arvottavien reittien polun pituuden yläraja
     * @param arvottaviaReitteja kuinka monta reittiä arvotaan
     */
    public void suoritaSuorituskykytestit(String valittuKartta, int polunPituusMin, int polunPituusMax, int arvottaviaReitteja) {
        
        lueTiedostot(valittuKartta);
        arvoReititSuorituskykytestiin(polunPituusMin, polunPituusMax, arvottaviaReitteja);
        this.tulokset = new ArrayList<>();
        
        for (Reittikuvaus rk : testireitit) {
            Solmu alku = rk.getAlku();
            Solmu loppu = rk.getLoppu();

            alustaAlgoritmit();

            Tulos d = this.dijkstra.laskeReitti(alku, loppu);
            Tulos a = this.aStar.laskeReitti(alku, loppu);
            Tulos j = this.jps.laskeReitti(alku, loppu);

            this.tulokset.add(muodostaTestitulos(rk, d, a, j));
        }
        naytaYksittaisetTulokset();
        double[] keskiarvoajat = laskeYhteenvetoSuorituskykytestista();
        String yhteenveto = yhteenvetoMerkkijonona(polunPituusMin, polunPituusMax, keskiarvoajat);
        System.out.println(yhteenveto);
    }

    /**
     * Suorittaa oikeellisuustestit valitulla kartalla n reitillä.
     *
     * @param valittuKartta karttatiedoston polku
     * @param arvottaviaReitteja kuinka monta reittiä arvotaan
     */
    public void suoritaOikeellisuustestit(String valittuKartta, int arvottaviaReitteja) {

        lueTiedostot(valittuKartta);
        arvoReititOikeellisuustestiin(arvottaviaReitteja);
        this.tulokset = new ArrayList<>();

        for (Reittikuvaus rk : testireitit) {
            Solmu alku = rk.getAlku();
            Solmu loppu = rk.getLoppu();

            alustaAlgoritmit();

            Tulos d = this.dijkstra.laskeReitti(alku, loppu);
            Tulos a = this.aStar.laskeReitti(alku, loppu);
            Tulos j = this.jps.laskeReitti(alku, loppu);

            this.tulokset.add(muodostaTestitulos(rk, d, a, j));
        }
    }

    /**
     * Lukee karttatiedoston ja vastaavan skenaarion reittien arpomista varten.
     *
     * @param valittuKartta karttatiedoston polku
     */
    private void lueTiedostot(String valittuKartta) {
        this.lukija = new Kartanlukija();
        lukija.lueKartta(valittuKartta);
        this.kartta = lukija.haeKartta();
        lukija.lueSkenaario(valittuKartta);
        this.skenaario = lukija.haeSkenaario();
    }

    /**
     * Arpoo parametrin mukaisen määrän reittejä skenaariosta.
     *
     * @param reitteja kuinka monta reittiä arvotaan
     */
    private void arvoReititOikeellisuustestiin(int reitteja) {
        this.testireitit = new ArrayList<>();

        while (testireitit.size() < reitteja) {
            Reittikuvaus rk = this.skenaario.arvoReittikuvaus();
            if (!testireitit.contains(rk) && rk.getReitinPituus() > 150.0) { // kaikkein lyhimmät reitit karsitaan
                testireitit.add(rk);
            }
        }
    }
    
    private void arvoReititSuorituskykytestiin(int polunPituusMin, int polunPituusMax, int reitteja) {
        this.testireitit = new ArrayList<>();
        
        while (testireitit.size() < reitteja) {
            Reittikuvaus rk = this.skenaario.arvoReittikuvaus();
            if (rk.getReitinPituus() > polunPituusMin && rk.getReitinPituus() < polunPituusMax) {
                testireitit.add(rk);
            }
        }
    }

    /**
     * Luo uudet algoritmioliot testausta varten.
     */
    private void alustaAlgoritmit() {
        this.dijkstra = new DijkstraStar(this.kartta, true);
        this.aStar = new DijkstraStar(this.kartta, false);
        this.jps = new JPS(this.kartta);
    }

    /**
     * Muodostaa uuden testituloksen alogritmien palauttamista tuloksista.
     *
     * @param reitti skenaariotiedostosta arvottu reitti
     * @param dijkstra Dijkstran laskema tulos
     * @param aStar A*:n laskema tulos
     * @param jps JPS:n laskema tulos
     * @return uusi testitulosolio
     */
    private Testitulos muodostaTestitulos(Reittikuvaus reitti, Tulos dijkstra, Tulos aStar, Tulos jps) {
        String kartanNimi = this.kartta.getNimi();

        return new Testitulos(kartanNimi, reitti, dijkstra, aStar, jps);
    }

    /**
     * Tulostaa saadut tulokset käyttäjän nähtäville.
     */
    public void naytaYksittaisetTulokset() {
        for (Testitulos tulos : tulokset) {
            System.out.println("");
            System.out.println(tulos.haeSuorituskykytulosMerkkijonona());
            System.out.println("");
        }
    }
    
    public String yhteenvetoMerkkijonona(int polunPituusMin, int polunPituusMax, double[] keskiarvot) {
        StringBuilder sb = new StringBuilder();
        sb.append("Käytetty kartta: ").append(this.kartta.getNimi());
        sb.append("\nPolun pituus välillä ").append(polunPituusMin).append("-").append(polunPituusMax);
        sb.append("\nReittejä laskettu ").append(this.testireitit.size());
        sb.append("\n\nKESKIMÄÄRÄISET SUORITUSAJAT");
        sb.append("\nDijkstra:\t").append(keskiarvot[0]);
        sb.append("\nA*:\t\t").append(keskiarvot[1]);
        sb.append("\nJPS:\t\t").append(keskiarvot[2]);
        sb.append("\n\nKESKIMÄÄRÄISET TUTKITTUJEN SOLMUJEN OSUUDET");
        sb.append("\nDijkstra:\t").append(keskiarvot[3]);
        sb.append("\nA*:\t\t").append(keskiarvot[4]);
        
        return sb.toString();
    }
    
    private double[] laskeYhteenvetoSuorituskykytestista() {
        double dijkstranAika = 0.0;
        double aStarinAika = 0.0;
        double jpsAika = 0.0;
        double dijkstranTutkitut = 0.0;
        double aStarinTutkitut = 0.0;
        int toistoja = this.tulokset.size();
        
        for (Testitulos tulos : this.tulokset) {
            dijkstranAika += tulos.getDijkstraAika();
            aStarinAika += tulos.getAStarAika();
            jpsAika += tulos.getJpsAika();
            dijkstranTutkitut += tulos.getDijkstranTutkitut();
            aStarinTutkitut += tulos.getAStarinTutkitut();
        }
        
        double[] keskiarvot = new double[5];
        System.out.println(Math.round(dijkstranTutkitut / toistoja));
        
        keskiarvot[0] = dijkstranAika / toistoja;
        keskiarvot[1] = aStarinAika / toistoja;
        keskiarvot[2] = jpsAika / toistoja;
        keskiarvot[3] = (Math.round((dijkstranTutkitut / toistoja) * 10)) / 10;
        keskiarvot[4] = (Math.round((aStarinTutkitut / toistoja) * 10)) / 10;
        
        return keskiarvot;
    }

    public ArrayList<Testitulos> getTulokset() {
        return tulokset;
    }

    public Kartta getKartta() {
        return kartta;
    }
}
