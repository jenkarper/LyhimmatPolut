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
    private final Kartta helppo;
    Suunta[] suunnat;
    
    public JPSTest() {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/testikartat/testikartta_1.map");
        this.helppo = lukija.haeKartta();
        this.suunnat = new Suunta[]{new Suunta(1, 0), new Suunta(-1, 0), new Suunta(0, 1), new Suunta(0, -1)};
    }

    @Test
    public void suorahakuLoytaaHyppypisteetHelpossaKartassa() {
        Solmu alku = new Solmu(5, 9);
        this.algoritmi = new JPS(helppo);
        Solmu[] odotetutHyppypisteet = new Solmu[]{new Solmu(11, 9), new Solmu(5, 14)};
        
        for (Suunta suunta : suunnat) {
            algoritmi.suoraHaku(alku, suunta);
        }
        assertTrue(algoritmi.getHyppypisteet().koko() == 2);
        assertTrue(odotetutHyppypisteet[0].samaSolmu(algoritmi.getHyppypisteet().haeSolmu(0)));
        assertTrue(odotetutHyppypisteet[1].samaSolmu(algoritmi.getHyppypisteet().haeSolmu(1)));
    }
}