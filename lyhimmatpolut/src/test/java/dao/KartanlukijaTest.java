package dao;

import domain.Kartta;
import org.junit.Test;
import static org.junit.Assert.*;
import suorituskykytestaus.Reittikuvaus;
import suorituskykytestaus.Skenaario;

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
        this.lukija.lueKartta("kartat/testikartat/testikartta_1.map");
        Kartta testikartta = this.lukija.haeKartta();
        String testikartanRivi = new String(testikartta.getKarttataulu()[0]);
        String odotettuRivi = "....@@..@..@@@@.....";
        
        assertEquals(odotettuRivi, testikartanRivi);
    }
    
    @Test
    public void lukijaPoimiiKartanKoonOikein() {
        this.lukija.lueKartta("kartat/testikartat/testikartta_1.map");
        Kartta testikartta = this.lukija.haeKartta();
        
        assertEquals(20, testikartta.getKorkeus());
        assertEquals(20, testikartta.getLeveys());
    }
    
    @Test
    public void lukijaTunnistaaVirheellisenTiedoston() {
        assertTrue(!this.lukija.lueKartta("kartat/olematonkartta.map"));
    }
    
    @Test
    public void lukijaMuodostaaSkenaarionOikein() {
        this.lukija.lueSkenaario("kartat/London_0_1024.map");
        Skenaario skenaario = this.lukija.haeSkenaario();
        Reittikuvaus odotettu = new Reittikuvaus(415, 51, 147, 842, 1603.24090728);
        Reittikuvaus loydetty = skenaario.haeReittikuvaus(skenaario.reittejaLuettu() - 1);
        
        assertEquals(odotettu.toString(), loydetty.toString());
    }
}
