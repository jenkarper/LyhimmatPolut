package algoritmit;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Suunta;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author pertjenn
 */
public class JPSTest {
    private JPS algoritmi;
    private final TiedostonlukijaIO lukija;
    private final Kartta helppo1;
    private final Kartta helppo3;
    Suunta[] suunnat;
    
    public JPSTest() {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/testikartat/testikartta_1.map");
        this.helppo1 = lukija.haeKartta();
        lukija.lue("kartat/testikartat/testikartta_3.map");
        this.helppo3 = lukija.haeKartta();
        this.suunnat = new Suunta[]{new Suunta(1, 0), new Suunta(-1, 0), new Suunta(0, 1), new Suunta(0, -1)};
    }

    // Nämä testit testaavat vain toistaiseksi toteutettua algoritmin osaa ja pitää korvata myöhemmin!
    
    @Test
    public void suorahakuLoytaaHyppypisteetHelpossaKartassa() {
        Solmu alku = new Solmu(5, 9);
        this.algoritmi = new JPS(helppo1);
        Solmu[] odotetutHyppypisteet = new Solmu[]{new Solmu(11, 9), new Solmu(5, 14)};
        
        for (Suunta suunta : suunnat) {
            algoritmi.suoraHaku(alku, suunta);
        }
        assertTrue(algoritmi.getHyppypisteet().koko() == 2);
        assertTrue(odotetutHyppypisteet[0].samaSolmu(algoritmi.getHyppypisteet().haeSolmu(0)));
        assertTrue(odotetutHyppypisteet[1].samaSolmu(algoritmi.getHyppypisteet().haeSolmu(1)));
    }
    
    @Test
    public void loytaaHyppypisteetHelpossaKartassa() {
        Solmu alku = new Solmu(0, 0);
        this.algoritmi = new JPS(helppo1);
        Solmu[] odotetutHyppypisteet = new Solmu[]{
            new Solmu(8, 5), new Solmu(5, 14), new Solmu(8, 1),
            new Solmu(11, 9), new Solmu(13, 15), new Solmu(13, 11)};
        
        this.algoritmi.laskeReitti(alku, new Solmu(19, 19)); // Toistaiseksi maalisolmulla ei ole mitään väliä.
        
        assertTrue(this.algoritmi.getHyppypisteet().koko() == odotetutHyppypisteet.length);
        
        for (int i = 0; i < odotetutHyppypisteet.length; i++) {
            Solmu o = odotetutHyppypisteet[i];
            Solmu l = this.algoritmi.getHyppypisteet().haeSolmu(i);
            
            assertTrue(o.samaSolmu(l));
        }
    }
}