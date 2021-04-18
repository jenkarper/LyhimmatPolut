package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import tietorakenteet.Lista;
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
    private DijkstraStar algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta helppo;
    private final Kartta vaikeaBerliini;
    private final Kartta vaikeaLontoo;
    private final Kartta vaikeaMilano;
    private final Lista testiPolku;
    
    public DijkstraTest() {
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
        this.algoritmi = new DijkstraStar(helppo, true);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        Lista loydettyPolku = tulos.getPolku();
        
        assertTrue(!loydettyPolku.tyhja());
        assertTrue(loydettyPolku.getViimeinen() == 8); // polulla on enemmän solmuja, jos esteen nurkalla ei saa edetä viistoon
    }
    
    @Test
    public void eiLoydaPolkuaHelpostaKartastaKunSitaEiOle() {
        this.algoritmi = new DijkstraStar(helppo, true);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(0, 7));
        Lista loydettyPolku = tulos.getPolku();
        
        assertTrue(loydettyPolku.tyhja());
    }
    
    @Test
    public void loytaaOikeanLyhimmanPolunHelpostaKartasta() {
        this.algoritmi = new DijkstraStar(helppo, true);
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
        this.algoritmi = new DijkstraStar(helppo, true);
        Solmu lahto = new Solmu(0, 0);
        Solmu maali = new Solmu(0, 4);
        Tulos tulos = algoritmi.laskeReitti(lahto, maali);
        Lista loydettyPolku = tulos.getPolku();
        double odotettuPituus = 4.0;
        double loydettyPituus = tulos.getPituus();
        
        assertTrue(odotettuPituus == loydettyPituus);
    }
    
    @Test
    public void eiLoydaPolkuaVaikeastaKartastaKunSitaEiOle() {
        this.algoritmi = new DijkstraStar(vaikeaBerliini, true);
        Solmu lahto = new Solmu(19, 3);
        Solmu maali = new Solmu(652, 486);
        Tulos tulos = algoritmi.laskeReitti(lahto, maali);
        
        assertTrue(!tulos.onnistui());
    }
    
    @Test
    public void laskeePolunPituudenOikeinVaikeassaKartassa() {
        this.algoritmi = new DijkstraStar(vaikeaBerliini, true);
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
