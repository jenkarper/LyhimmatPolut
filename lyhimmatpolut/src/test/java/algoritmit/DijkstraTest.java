package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Lista;
import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author pertjenn
 */
public class DijkstraTest {
    private Dijkstra algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta helppo;
    private final Kartta vaikeaBerliini;
    private final Kartta vaikeaLontoo;
    private final Kartta vaikeaMilano;
    private final Lista testiPolku;
    
    public DijkstraTest() {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/testikartat/testikartta_2.map");
        this.helppo = lukija.haeKartta();
        lukija.lue("kartat/Berlin_0_1024.map");
        this.vaikeaBerliini = lukija.haeKartta();
        lukija.lue("kartat/London_0_1024.map");
        this.vaikeaLontoo = lukija.haeKartta();
        lukija.lue("kartat/Milan_1_1024.map");
        this.vaikeaMilano = lukija.haeKartta();
        this.testiPolku = new Lista();
    }
    
    @Before
    public void alusta() {
        testiPolku.lisaa(new Solmu(4, 4));
        testiPolku.lisaa(new Solmu(3, 4));
        testiPolku.lisaa(new Solmu(2, 4));
        testiPolku.lisaa(new Solmu(1, 4));
        testiPolku.lisaa(new Solmu(0, 3));
        testiPolku.lisaa(new Solmu(0, 2));
        testiPolku.lisaa(new Solmu(0, 1));
        testiPolku.lisaa(new Solmu(0, 0));
    }
    
    @Test
    public void loytaaPolunHelpostaKartasta() {
        this.algoritmi = new Dijkstra(helppo);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        Lista loydettyPolku = tulos.getPolku();
        
        assertTrue(!loydettyPolku.tyhja());
        assertTrue(loydettyPolku.getViimeinen() == 8); // polulla on enemmän solmuja, jos esteen nurkalla ei saa edetä viistoon
    }
    
    @Test
    public void eiLoydaPolkuaHelpostaKartastaKunSitaEiOle() {
        this.algoritmi = new Dijkstra(helppo);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(0, 7));
        Lista loydettyPolku = tulos.getPolku();
        
        assertTrue(loydettyPolku.tyhja());
    }
    
    @Test
    public void loytaaOikeanLyhimmanPolunHelpostaKartasta() {
        this.algoritmi = new Dijkstra(helppo);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        Lista loydettyPolku = tulos.getPolku();
        ArrayList<Solmu> odotettuPolku = new ArrayList<>() {{
            add(new Solmu(4, 4));
            add(new Solmu(3, 4));
            add(new Solmu(2, 4));
            add(new Solmu(1, 4));
            add(new Solmu(0, 4)); // tämä solmu tulee reitille, jos esteen nurkalla ei saa edetä viistoon
            add(new Solmu(0, 3));
            add(new Solmu(0, 2));
            add(new Solmu(0, 1));
            add(new Solmu(0, 0));
        }};
        
        for (int i = 0; i <= loydettyPolku.getViimeinen(); i++) {
            Solmu loydetty = loydettyPolku.haeSolmu(i);
            Solmu odotettu = odotettuPolku.get(i);
            
            assertTrue(loydetty.samaSolmu(odotettu));
        }
    }
    
    @Test
    public void laskeePolunPituudenOikeinHelpossaKartassa() {
        this.algoritmi = new Dijkstra(helppo);
        Solmu maali = new Solmu(0, 4);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), maali);
        Lista loydettyPolku = tulos.getPolku();
        double odotettuPituus = 4.0;
        double loydettyPituus = algoritmi.getPolunPituus(maali);
        
        assertTrue(odotettuPituus == loydettyPituus);
    }
    
//    @Test
//    public void laskeePolunPituudenOikeinVaikeassaKartassa() {
//        
//        // Berliini 1
//        this.algoritmi = new Dijkstra(vaikeaBerliini);
//        Solmu maali = new Solmu(1005, 1002);
//        Lista loydettyPolku = algoritmi.laskeReitti(new Solmu(19, 3), maali);
//        double odotettuPituus = 1539.80230712;
//        double loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Berliini 2
//        algoritmi.alusta(vaikeaBerliini);
//        maali = new Solmu(8, 68);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(1013, 1012), maali);
//        odotettuPituus = 1537.19213409;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Berliini 3
//        algoritmi.alusta(vaikeaBerliini);
//        maali = new Solmu(1012, 964);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 1), maali);
//        odotettuPituus = 1524.53022918;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Lontoo 1
//        algoritmi.alusta(vaikeaLontoo);
//        maali = new Solmu(147, 842);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(415, 51), maali);
//        odotettuPituus = 1603.24090728;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Lontoo 2
//        algoritmi.alusta(vaikeaLontoo);
//        maali = new Solmu(45, 304);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(471, 82), maali);
//        odotettuPituus = 1600.52813720;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Lontoo 3
//        algoritmi.alusta(vaikeaLontoo);
//        maali = new Solmu(118, 402);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(458, 58), maali);
//        odotettuPituus = 1591.70685271;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//        // Milano 1
//        algoritmi.alusta(vaikeaMilano);
//        maali = new Solmu(1012, 941);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(38, 28), maali);
//        odotettuPituus = 1468.61349029;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//         // Milano 2
//        algoritmi.alusta(vaikeaMilano);
//        maali = new Solmu(1014, 738);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(27, 57), maali);
//        odotettuPituus = 1468.41753081;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//        
//         // Milano 3
//        algoritmi.alusta(vaikeaMilano);
//        maali = new Solmu(2, 64);
//        loydettyPolku = algoritmi.laskeReitti(new Solmu(1017, 830), maali);
//        odotettuPituus = 1458.04494781;
//        loydettyPituus = algoritmi.getPolunPituus(maali);
//        
//        System.out.println(odotettuPituus + " " + loydettyPituus);
//    }
}
