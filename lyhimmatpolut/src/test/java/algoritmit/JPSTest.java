package algoritmit;

import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import suorituskykytestaus.Testaaja;
import suorituskykytestaus.Testitulos;

/**
 * Testaa algoritmin oikeellisuutta vertaamalla sen tulosta Dijkstran tulokseen.
 * @author pertjenn
 */
public class JPSTest {

    private final Testaaja testaaja;
    private final String testikartta;

    public JPSTest() {
        this.testaaja = new Testaaja();
        this.testikartta = "kartat/London_0_1024.map";
    }

    /**
     * Arpoo satunnaisia reittejä, laskee niiden pituuden ari algoritmeilla ja vertaa pituuksia toisiinsa.
     */
    @Test
    public void loytaaSamatPolunpituudetKuinDijkstra() {
        this.testaaja.suoritaTestit(testikartta, 10);

        ArrayList<Testitulos> tulokset = this.testaaja.getTulokset();

        for (Testitulos tulos : tulokset) {
            assertTrue(tulos.haeOikeellisuustulosJPSlle());
        }
    }
    
    @Test
    public void eiLoydaPolkuaKunSitaEiOle() {
        this.testaaja.suoritaTestit(testikartta, 1);
        Algoritmi jps = new JPS(this.testaaja.getKartta());
        Tulos tulos = jps.laskeReitti(new Solmu(50, 858), new Solmu(921, 142));
        
        assertFalse(tulos.onnistui());
    }
}
