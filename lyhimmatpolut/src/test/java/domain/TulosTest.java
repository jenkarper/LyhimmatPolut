package domain;

import algoritmit.Algoritmi;
import algoritmit.DijkstraStar;
import dao.Kartanlukija;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class TulosTest {
    private final Algoritmi a;
    private final Kartanlukija lukija;
    
    public TulosTest() {
        this.lukija = new Kartanlukija();
        lukija.lueKartta("kartat/testikartat/testikartta_2.map");
        this.a = new DijkstraStar(lukija.haeKartta(), true);
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaOnnistuu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(tulos.getAlgoritmi().equals("Dijkstra"));
        assertTrue(tulos.getAika() != -1);
        assertTrue(tulos.getPituus() > 0);
        assertTrue(!tulos.getPolku().tyhja());
        assertTrue(tulos.onnistui());
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaEiOnnistu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 0));
        
        assertTrue(tulos.getAlgoritmi().equals("Dijkstra"));
        assertTrue(tulos.getAika() == 0.0);
        assertTrue(tulos.getPituus() == -1);
        assertTrue(tulos.getPolku().tyhja());
        assertTrue(!tulos.onnistui());
    }
}
