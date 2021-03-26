package dao;

import domain.Kartta;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class KartanlukijaTest {
    private final Kartanlukija lukija;
    
    public KartanlukijaTest() {
        this.lukija = new Kartanlukija();
    }

    @Test
    public void lukijaMuodostaaKartanOikein() {
        this.lukija.lue("kartat/testikartat/testikartta_1.map");
        Kartta testikartta = this.lukija.haeKartta();
        String testikartanSarake = new String(testikartta.getKarttataulu()[0]);
        String odotettuSarake = ".....@@@@@@@@@@@@@@.";
        
        assertEquals(odotettuSarake, testikartanSarake);
    }
    
    @Test
    public void lukijaPoimiiKartanKoonOikein() {
        this.lukija.lue("kartat/testikartat/testikartta_1.map");
        Kartta testikartta = this.lukija.haeKartta();
        
        assertEquals(20, testikartta.getKorkeus());
        assertEquals(20, testikartta.getLeveys());
    }
    
    @Test
    public void lukijaTunnistaaVirheellisenTiedoston() {
        assertTrue(!this.lukija.lue("kartat/olematonkartta.map"));
    }
}
