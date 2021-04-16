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
    private Reittikuvaus[] testireitit;
        
    public void suoritaTestit(String valittuKartta, int toistoja) {
        
        lueTiedostot(valittuKartta);
        arvoReitit(toistoja);
        this.tulokset = new ArrayList<>();
        
        for (Reittikuvaus rk : this.testireitit) {
            Solmu alku = rk.getAlku();
            Solmu loppu = rk.getLoppu();
            
            alustaAlgoritmit();
            
            Tulos d = this.dijkstra.laskeReitti(alku, loppu);
            Tulos a = this.aStar.laskeReitti(alku, loppu);
            
            muodostaTestitulos(rk, d, a);
        }
    }

    private void lueTiedostot(String valittuKartta) {
        this.lukija = new Kartanlukija();
        lukija.lueKartta(valittuKartta);
        this.kartta = lukija.haeKartta();
        lukija.lueSkenaario(valittuKartta);
        this.skenaario = lukija.haeSkenaario();
    }

    private void arvoReitit(int toistoja) {
        this.testireitit = new Reittikuvaus[toistoja];
        for (int i = 0; i < toistoja; i++) {
            Reittikuvaus rk = this.skenaario.arvoReittikuvaus();
            this.testireitit[i] = rk;
        }
    }

    private void alustaAlgoritmit() {
        this.dijkstra = new DijkstraStar(this.kartta, true);
        this.aStar = new DijkstraStar(this.kartta, false);
        this.jps = new JPS(this.kartta);
    }

    private void muodostaTestitulos(Reittikuvaus reitti, Tulos dijkstra, Tulos aStar) {
        String kartanNimi = this.kartta.getNimi();
        double dijkstraAika = dijkstra.getAika();
        double aStarEro = dijkstraAika - aStar.getAika();
        boolean aStarLoysiPolun = (dijkstra.getPituus() == aStar.getPituus());
        
        Testitulos tulos = new Testitulos(kartanNimi, reitti, dijkstraAika, aStarEro, 0.0, aStarLoysiPolun, false);
        this.tulokset.add(tulos);
    }
    
    public void naytaTulokset() {
        for (Testitulos tulos : tulokset) {
            System.out.println("");
            System.out.println(tulos.haeSuorituskykytulos());
            System.out.println("");
        }
    }
}
