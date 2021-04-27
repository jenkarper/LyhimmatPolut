package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JPS ei toimi vielä kunnolla, joten testi ei testaa vielä kunnolla.
 * @author pertjenn
 */
public class JPSTest {
    private JPS algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta berliini;
    
    public JPSTest() {
        this.lukija = new Kartanlukija();
        lukija.lueKartta("kartat/Berlin_0_1024.map");
        this.berliini = lukija.haeKartta();
    }

    @Test
    public void loytaaPolunVaikeassaKartassa() {
        this.algoritmi = new JPS(this.berliini);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(19, 3), new Solmu(1005, 1002));
        double odotettuPituus = 1539.80230712;
        double loydettyPituus = tulos.getPituus();
        
        assertEquals(odotettuPituus, loydettyPituus, 1);
        
    }
    
    @Test
    public void eiLoydaPolkuaKunSitaEiOle() {
        this.algoritmi = new JPS(this.berliini);
        Tulos tulos = algoritmi.laskeReitti(new Solmu(847, 137), new Solmu(651, 486));
        
        assertTrue(!tulos.onnistui());
    }
}