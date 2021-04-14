package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class AStarTest {
    private DijkstraStar algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta vaikeaBerliini;
    private final Kartta vaikeaLontoo;
    private final Kartta vaikeaMilano;
    
    public AStarTest() {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/Berlin_0_1024.map");
        this.vaikeaBerliini = lukija.haeKartta();
        lukija.lue("kartat/London_0_1024.map");
        this.vaikeaLontoo = lukija.haeKartta();
        lukija.lue("kartat/Milan_1_1024.map");
        this.vaikeaMilano = lukija.haeKartta();
    }
    
    @Test
    public void eiLoydaPolkuaVaikeastaKartastaKunSitaEiOle() {
        this.algoritmi = new DijkstraStar(vaikeaBerliini, false);
        Solmu lahto = new Solmu(19, 3);
        Solmu maali = new Solmu(652, 486);
        Tulos tulos = algoritmi.laskeReitti(lahto, maali);
        
        assertTrue(!tulos.onnistui());
    }
    
    @Test
    public void laskeePolunPituudenOikeinVaikeassaKartassa() {
        this.algoritmi = new DijkstraStar(vaikeaBerliini, false);
        Solmu lahto = new Solmu(19, 3);
        Solmu maali = new Solmu(1005, 1002);
        Tulos saatuTulos = algoritmi.laskeReitti(lahto, maali);
        double odotettuPituus = 1539.80;
        double loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
        
        algoritmi.alusta(vaikeaLontoo);
        lahto = new Solmu(415, 51);
        maali = new Solmu(147, 842);
        saatuTulos = algoritmi.laskeReitti(lahto, maali);
        odotettuPituus = 1603.24;
        loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
        
        algoritmi.alusta(vaikeaMilano);
        lahto = new Solmu(38, 28);
        maali = new Solmu(1012, 941);
        saatuTulos = algoritmi.laskeReitti(lahto, maali);
        odotettuPituus = 1468.61;
        loydettyPituus = saatuTulos.getPituus();
        
        assertTrue(loydettyPituus == odotettuPituus);
    }
}
