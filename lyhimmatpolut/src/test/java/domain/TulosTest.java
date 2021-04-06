package domain;

import algoritmit.Algoritmi;
import algoritmit.Dijkstra;
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
        lukija.lue("kartat/testikartat/testikartta_2.map");
        this.a = new Dijkstra(lukija.haeKartta());
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaOnnistuu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(tulos.getAlgoritmi().equals("Dijkstra"));
        assertTrue(tulos.getAika() > 0);
        assertTrue(tulos.getPituus() > 0);
        assertTrue(!tulos.getPolku().tyhja());
        assertTrue(tulos.onnistui());
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaEiOnnistu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 0));
        
        assertTrue(tulos.getAlgoritmi().equals("Dijkstra"));
        assertTrue(tulos.getAika() == -1);
        assertTrue(tulos.getPituus() == -1);
        assertTrue(tulos.getPolku().tyhja());
        assertTrue(!tulos.onnistui());
    }
    
    @Test
    public void pyoristaaLiukuluvutOikein() {
        double pituus = 12.345678;
        double aika = 0.87654321;
        Tulos tulos = new Tulos("testi", new Lista(), pituus, aika, true);
        
        double odotettuPyoristettyPituus = 12.35;
        double odotettuPyoristettyAika = 0.8765;
        
        assertTrue(odotettuPyoristettyPituus == tulos.getPituus());
        assertTrue(odotettuPyoristettyAika == tulos.getAika());
    }
}
