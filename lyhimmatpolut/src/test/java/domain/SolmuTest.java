package domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class SolmuTest {
    
    public SolmuTest() {
    }
    
    @Test
    public void tunnistaaSamanSolmunKoordinaattienPerusteella() {
        Solmu solmu = new Solmu(8, 6);
        Solmu solmuSama = new Solmu(8, 6);
        
        assertTrue(solmu.samaSolmu(solmuSama));
    }
    
    @Test
    public void tunnistaaEriSolmunKoordinaattienPerusteella() {
        Solmu solmu = new Solmu(8, 6);
        Solmu eriSolmu = new Solmu(8, 7);
        
        assertTrue(!solmu.samaSolmu(eriSolmu));
    }
}
