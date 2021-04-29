package suorituskykytestaus;

import algoritmit.DijkstraStar;
import algoritmit.JPS;
import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;
import algoritmit.Algoritmi;

/**
 * Algoritmien suorituskykyä testaava luokka.
 * @author pertjenn
 */
public class Testaaja {
    private Algoritmi dijkstra;
    private Algoritmi aStar;
    private Algoritmi jps;
    private Kartta kartta;
    private Skenaario skenaario;
    private TiedostonlukijaIO lukija;
    private ArrayList<Testitulos> tulokset;
    private ArrayList<Reittikuvaus> testireitit;
        
    /**
     * Suorittaa suorituskykytestit valitulla kartalla n reitillä.
     * @param valittuKartta karttatiedoston polku
     * @param n kuinka monta reittiä arvotaan
     */
    public void suoritaTestit(String valittuKartta, int n) {
        
        lueTiedostot(valittuKartta);
        arvoReitit(n);
        this.tulokset = new ArrayList<>();
        
        for (Reittikuvaus rk : this.testireitit) {
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
     * @param toistoja kuinka monta reittiä arvotaan
     */
    private void arvoReitit(int toistoja) {
        this.testireitit = new ArrayList<>();
        
        while (this.testireitit.size() < toistoja) {
            Reittikuvaus rk = this.skenaario.arvoReittikuvaus();
            if (!this.testireitit.contains(rk) && rk.getReitinPituus() > 150.0) { // kaikkein lyhimmät reitit karsitaan
                this.testireitit.add(rk);
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
     * @param reitti skenaariotiedostosta arvottu reitti
     * @param dijkstra Dijkstran laskema tulos
     * @param aStar A*:n laskema tulos
     * @return uusi testitulosolio
     */
    private Testitulos muodostaTestitulos(Reittikuvaus reitti, Tulos dijkstra, Tulos aStar, Tulos jps) {
        String kartanNimi = this.kartta.getNimi();
        double dijkstraAika = dijkstra.getAika();
        double aStarEro = dijkstraAika - aStar.getAika();
        double jpsEro = dijkstraAika - jps.getAika();
        boolean aStarLoysiPolun = (dijkstra.getPituus() == aStar.getPituus());
        boolean jpsLoysiPolun = laskeJpsOikeellisuus(dijkstra, jps);
        
        return new Testitulos(kartanNimi, reitti, dijkstraAika, aStarEro, 0.0, aStarLoysiPolun, jpsLoysiPolun);
    }
    
    /**
     * Tulostaa saadut tulokset käyttäjän nähtäville.
     */
    public void naytaTulokset() {
        for (Testitulos tulos : tulokset) {
            System.out.println("");
            System.out.println(tulos.haeSuorituskykytulos());
            System.out.println("");
        }
    }

    public ArrayList<Testitulos> getTulokset() {
        return tulokset;
    }

    private boolean laskeJpsOikeellisuus(Tulos dijkstra, Tulos jps) {
        double dijkstranPituus = dijkstra.getPituus();
        double jpsPituus = jps.getPituus();
        double erotus = Math.abs(dijkstranPituus - jpsPituus);
        
        return (erotus < 0.0000001);
    }
    
    public Kartta getKartta() {
        return kartta;
    }
}
