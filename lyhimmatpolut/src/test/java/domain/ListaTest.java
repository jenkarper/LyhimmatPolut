package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void kasvattaaListaaYhdeksannenAlkionLisaamisenJalkeen() {
        Lista lista = new Lista();
        
        for (int i = 0; i < 10; i++) {
            Solmu s = new Solmu(i, i);
            lista.lisaa(s);
        }
        assertTrue(lista.getViimeinen() == 9);
        
        lista.lisaa(new Solmu(10, 10));
        assertTrue(lista.haeTaulukonKoko() == 20);
    }
}
