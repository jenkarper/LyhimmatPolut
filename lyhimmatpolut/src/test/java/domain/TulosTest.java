package domain;

import algoritmit.DijkstraStar;
import dao.Kartanlukija;
import org.junit.Test;
import static org.junit.Assert.*;
import algoritmit.Algoritmi;
import tietorakenteet.Lista;

/**
 *
 * @author pertjenn
 */
public class TulosTest {
    private final Algoritmi a;
    private final Kartanlukija lukija;
    private Kartta kartta;
    
    public TulosTest() {
        this.lukija = new Kartanlukija();
        lukija.lueKartta("kartat/testikartat/testikartta_2.map");
        this.kartta = lukija.haeKartta();
        this.a = new DijkstraStar(kartta, true);
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaOnnistuu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(tulos.getAika() != -1);
        assertTrue(tulos.getPituus() > 0);
        assertTrue(!tulos.getPolku().tyhja());
        assertTrue(tulos.onnistui());
    }
    
    @Test
    public void luoTuloksenOikeinKunLaskentaEiOnnistu() {
        Tulos tulos = a.laskeReitti(new Solmu(0, 0), new Solmu(4, 0));
        
        assertTrue(tulos.getAika() == 0.0);
        assertTrue(tulos.getPituus() == -1);
        assertTrue(tulos.getPolku().tyhja());
        assertTrue(!tulos.onnistui());
    }
    
    @Test
    public void laskeePyoristetynPituudenOikein() {
        Tulos tulos = new Tulos(new Lista(), 1.23456789, System.nanoTime(), new boolean[1][1], 23456);
        double odotettuPituus = 1.23;
        
        assertTrue(odotettuPituus == tulos.laskePyoristettyPituus());
    }
    
    @Test
    public void palauttaaTutkitutTaulukonOikein() {
        Algoritmi d = new DijkstraStar(this.kartta, false);
        Tulos tulos = d.laskeReitti(new Solmu(0, 0), new Solmu(4, 4));
        
        assertTrue(tulos.getTutkitut().length > 1);
    }
    
    @Test
    public void laskeeTutkittujenOsuudenOikein() {
        boolean[][] tutkitut = new boolean[][]{
            {false, true, false, false, false},
            {false, true, false, false, false},
            {false, true, true, false, false},
            {false, false, true, true, false}
        };
        
        Tulos tulos = new Tulos(new Lista(), 1, 1, tutkitut, 20);
        
        double odotettuTutkittujenOsuus = (6.0 / 20.0) * 100.0;
        double saatuTutkittujenOsuus = tulos.laskeTutkittujenOsuus();
        
        assertTrue(odotettuTutkittujenOsuus == saatuTutkittujenOsuus);
        
    }
}
