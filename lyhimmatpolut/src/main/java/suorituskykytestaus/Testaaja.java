package suorituskykytestaus;

import algoritmit.Algoritmi;
import algoritmit.DijkstraStar;
import algoritmit.JPS;
import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import java.util.HashMap;

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
    private HashMap<String, Testitulos> tulokset;
    private Reittikuvaus[] testireitit;
    
    public Testaaja(String valittuKartta, int toistoja) {
        lueTiedostot(valittuKartta);
        arvoReitit(toistoja);
        this.tulokset = new HashMap<>();
    }
    
    public void suoritaTestit() {
        
        for (int i = 0; i < this.testireitit.length; i++) {
            Reittikuvaus rk = this.testireitit[i];
            Solmu alku = rk.getAlku();
            Solmu loppu = rk.getLoppu();
            
            alustaAlgoritmit();
            
            // testaa algoritmit
            
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
}
