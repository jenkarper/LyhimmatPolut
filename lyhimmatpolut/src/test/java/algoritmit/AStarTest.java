package algoritmit;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import suorituskykytestaus.Testaaja;
import suorituskykytestaus.Testitulos;

/**
 *
 * @author pertjenn
 */
public class AStarTest {
    private final Testaaja testaaja;
    private final String testikartta;
    
    public AStarTest() {
        this.testaaja = new Testaaja();
        this.testikartta = "kartat/London_0_1024.map";
    }
    
    @Test
    public void loytaaSamatPolutKuinDijkstra() {
        this.testaaja.suoritaTestit(this.testikartta, 10);
        ArrayList<Testitulos> tulokset = this.testaaja.getTulokset();
        
        tulokset.forEach((tulos) -> {
            assertTrue(tulos.haeOikeellisuustulosAStarille());
        });
    }
}
