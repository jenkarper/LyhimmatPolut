package algoritmit;

import domain.Solmu;
import domain.Tulos;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import suorituskykytestaus.Testaaja;
import suorituskykytestaus.Testitulos;

/**
 * Testaa algoritmin oikeellisuutta vertaamalla sen tulosta Dijkstran tulokseen.
 * @author pertjenn
 */
public class AStarTest {
    private final Testaaja testaaja;
    private final String testikartta;

    public AStarTest() {
        this.testaaja = new Testaaja();
        this.testikartta = "kartat/London_0_1024.map";
    }

    /**
     * Arpoo satunnaisia reittejä, laskee niiden pituuden ari algoritmeilla ja vertaa pituuksia toisiinsa.
     */
    @Test
    public void loytaaSamatPolutKuinDijkstra() {
        this.testaaja.suoritaTestit(this.testikartta, 10);
        ArrayList<Testitulos> tulokset = this.testaaja.getTulokset();

        for (Testitulos tulos : tulokset) {
            
            assertTrue(tulos.haeOikeellisuustulosAStarille());
        }
    }
    
    @Test
    public void eiLoydaPolkuaKunSitaEiOle() {
        this.testaaja.suoritaTestit(testikartta, 1);
        Algoritmi aStar = new DijkstraStar(this.testaaja.getKartta(), false);
        Tulos tulos = aStar.laskeReitti(new Solmu(50, 858), new Solmu(921, 142));
        
        assertFalse(tulos.onnistui());
    }
}
