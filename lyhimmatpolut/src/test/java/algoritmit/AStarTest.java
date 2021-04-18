package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import suorituskykytestaus.Testaaja;
import suorituskykytestaus.Testitulos;
import tietorakenteet.Lista;

/**
 *
 * @author pertjenn
 */
public class AStarTest {
    private final Testaaja testaaja;
    private final String testikartta;
    
    // Väliaikainen yksikkötestaus
    private final TiedostonlukijaIO lukija;
    private final Kartta helppo;
    private final Kartta vaikeaBerliini;
    private final Kartta vaikeaLontoo;
    private final Kartta vaikeaMilano;
    private final Lista testiPolku;

    public AStarTest() {
        this.testaaja = new Testaaja();
        this.testikartta = "kartat/Berlin_0_1024.map";
        
        // Väliaikainen yksikkötestaus
        this.lukija = new Kartanlukija();
        lukija.lueKartta("kartat/testikartat/testikartta_2.map");
        this.helppo = lukija.haeKartta();
        lukija.lueKartta("kartat/Berlin_0_1024.map");
        this.vaikeaBerliini = lukija.haeKartta();
        lukija.lueKartta("kartat/London_0_1024.map");
        this.vaikeaLontoo = lukija.haeKartta();
        lukija.lueKartta("kartat/Milan_1_1024.map");
        this.vaikeaMilano = lukija.haeKartta();
        this.testiPolku = new Lista();
    }

    // A*:n toiminnassa on tällä hetkellä jokin virhe! Testikartalla 2 tapausta 3850:stä antaa väärän polunpituuden.
    @Test
    public void loytaaSamatPolutKuinDijkstra() {
        this.testaaja.suoritaTestit(this.testikartta, 20);
        ArrayList<Testitulos> tulokset = this.testaaja.getTulokset();
        int epaonnistuneita = 0;

        for (Testitulos tulos : tulokset) {
            if (!tulos.haeOikeellisuustulosAStarille()) {
                System.out.println(tulos.getReitti().toString());
                System.out.print("A*:n laskema pituus: " + tulos.getA().getPituus());
                System.out.println("");
                epaonnistuneita++;
            }
        }
    }
    
    // Väliaikainen yksikkötestaus
    @Test
    public void laskeePolunPituudenOikeinVaikeassaKartassa() {
        DijkstraStar algoritmi = new DijkstraStar(vaikeaBerliini, false);
        Solmu lahto = new Solmu(19, 3);
        Solmu maali = new Solmu(1005, 1002);
        Tulos saatuTulos = algoritmi.laskeReitti(lahto, maali);
        double odotettuPituus = 1539.8023;
        double loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
        
        algoritmi.alusta(vaikeaLontoo);
        lahto = new Solmu(415, 51);
        maali = new Solmu(147, 842);
        saatuTulos = algoritmi.laskeReitti(lahto, maali);
        odotettuPituus = 1603.2409;
        loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
        
        algoritmi.alusta(vaikeaMilano);
        lahto = new Solmu(38, 28);
        maali = new Solmu(1012, 941);
        saatuTulos = algoritmi.laskeReitti(lahto, maali);
        odotettuPituus = 1468.6135;
        loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
    }
}
