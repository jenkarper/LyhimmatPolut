package tietorakenteet;

import domain.Solmu;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author pertjenn
 */
public class ListaTest {
    
    public ListaTest() {
    }

    @Test
    public void alustaaUudeListanOikein() {
        Lista lista = new Lista();
        assertTrue(lista.tyhja());
    }
    
    @Test
    public void kasvattaaListaaOikein() {
        Lista lista = new Lista();
        
        for (int i = 0; i < 9; i++) {
            Solmu s = new Solmu(i, i);
            lista.lisaa(s);
        }
        assertTrue(lista.kapasiteetti() == 10);
        
        lista.lisaa(new Solmu(10, 10));
        assertTrue(lista.kapasiteetti() == 20);
    }
    
    @Test
    public void eiHaeSolmuaVaarallaIndeksilla() {
        Lista lista = new Lista();
        lista.lisaa(new Solmu(1, 1));
        
        assertTrue(lista.haeSolmu(-1)==null && lista.haeSolmu(1)==null);
    }
    
    @Test
    public void hakeeOikeanSolmunOikeallaIndeksilla() {
        Solmu odotettu = new Solmu(1, 1);
        Lista lista = new Lista();
        lista.lisaa(odotettu);
        
        assertTrue(lista.haeSolmu(0).samaSolmu(odotettu));
    }
}
