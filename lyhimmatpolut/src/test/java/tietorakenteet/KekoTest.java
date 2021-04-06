package tietorakenteet;

import domain.Solmu;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pertjenn
 */
public class KekoTest {
    private final ArrayList<Solmu> testilista;
    
    public KekoTest() {
        this.testilista = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            Solmu s = new Solmu(i, i, 1, i+i);
            testilista.add(s);
        }
        Collections.shuffle(testilista);
    }
    
    @Test
    public void alustaaUudenKeonOikein() {
        Keko keko = new Keko();
        
        assertTrue(keko.tyhja());
    }
    
    @Test
    public void kasvattaaKekoaOikein() {
        Keko keko = new Keko();
        
        for (int i = 0; i < 9; i++) {
            Solmu s = new Solmu(i, i, 1, i+i);
            keko.lisaa(s);
        }
        
        assertTrue(keko.kapasiteetti() == 9);
        
        keko.lisaa(new Solmu(10, 10, 1, 20));
        
        assertTrue(keko.kapasiteetti() == 19);
    }
    
    @Test
    public void yllapitaaKekojarjestystaOikein() {
        Keko keko = new Keko();
        Solmu odotettuPienin = new Solmu(0, 0);
        Solmu odotettuSuurin = new Solmu(999, 999);
        
        for (int i = 0; i < testilista.size(); i++) {
            keko.lisaa(testilista.get(i));
        }
        
        assertTrue(keko.koko() == testilista.size());
        
        Solmu edellinen = keko.poistaPienin();
        
        assertTrue(edellinen.samaSolmu(odotettuPienin));
        
        while (keko.koko() > 1) {
            Solmu seuraava = keko.poistaPienin();
            assertTrue(edellinen.lahempanaKuin(seuraava));
            edellinen = seuraava;
        }
        
        Solmu suurin = keko.poistaPienin();
        
        assertTrue(suurin.samaSolmu(odotettuSuurin));
    }
}
