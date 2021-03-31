package domain;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class DijkstraTest {
    private Dijkstra algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta helppo;
    
    public DijkstraTest() {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/testikartat/testikartta_2.map");
        this.helppo = lukija.haeKartta();
    }
    
    @Test
    public void loytaaPolunHelpostaKartasta() {
        this.algoritmi = new Dijkstra(helppo);
        Lista loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(!loydettyPolku.tyhja());
        assertTrue(loydettyPolku.getViimeinen() == 7);
    }
    
    @Test
    public void eiLoydaPolkuaHelpostaKartastaKunSitaEiOle() {
        this.algoritmi = new Dijkstra(helppo);
        Lista loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(0, 7));
        
        assertTrue(loydettyPolku.tyhja());
    }
    
    @Test
    public void loytaaOikeanLyhimmanPolunHelpostaKartasta() {
        this.algoritmi = new Dijkstra(helppo);
        Lista loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        ArrayList<Solmu> odotettuPolku = new ArrayList<>() {{
            add(new Solmu(4, 4));
            add(new Solmu(4, 3));
            add(new Solmu(4, 2));
            add(new Solmu(4, 1));
            add(new Solmu(3, 0));
            add(new Solmu(2, 0));
            add(new Solmu(1, 0));
            add(new Solmu(0, 0));
        }};
        
        for (int i = 0; i <= loydettyPolku.getViimeinen(); i++) {
            Solmu loydetty = loydettyPolku.haeSolmu(i);
            Solmu odotettu = odotettuPolku.get(i);
            
            assertTrue(loydetty.equals(odotettu));
        }
    }
    
    @Test
    public void laskeePolunPituudenOikeinHelpossaKartassa() {
        this.algoritmi = new Dijkstra(helppo);
        Solmu maali = new Solmu(4, 0);
        Lista loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), maali);
        double odotettuPituus = 4.0;
        double loydettyPituus = algoritmi.getPolunPituus(maali);
        
        assertTrue(odotettuPituus == loydettyPituus);
    }
}
