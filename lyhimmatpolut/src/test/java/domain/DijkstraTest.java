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
        ArrayList<Solmu> loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(!loydettyPolku.isEmpty());
        assertTrue(loydettyPolku.size() == 8);
        
    }
    
    @Test
    public void eiLoydaPolkuaHelpostaKartastaKunSitaEiOle() {
        this.algoritmi = new Dijkstra(helppo);
        ArrayList<Solmu> loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(0, 7));
        for (int i = 0; i < loydettyPolku.size(); i++) {
            int x = loydettyPolku.get(i).getX();
            int y = loydettyPolku.get(i).getY();
            System.out.println(x + ", " + y);
        }
        
        assertTrue(loydettyPolku.isEmpty());
    }
    
    @Test
    public void loytaaOikeanLyhimmanPolunHelpostaKartasta() {
        this.algoritmi = new Dijkstra(helppo);
        ArrayList<Solmu> loydettyPolku = algoritmi.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        ArrayList<Solmu> odotettuPolku = new ArrayList<>() {{
            add(new Solmu(0, 0));
            add(new Solmu(1, 0));
            add(new Solmu(2, 0));
            add(new Solmu(3, 0));
            add(new Solmu(4, 1));
            add(new Solmu(4, 2));
            add(new Solmu(4, 3));
            add(new Solmu(4, 4));
        }};
        
        for (int i = 0; i < loydettyPolku.size(); i++) {
            assertTrue(loydettyPolku.get(i).equals(odotettuPolku.get(i)));
        }
    }
}
