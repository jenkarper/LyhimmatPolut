package algoritmit;

import domain.Solmu;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author pertjenn
 */
public class HeuristiikkaTest {
    private final Heuristiikka heuristiikka;
    
    public HeuristiikkaTest() {
        this.heuristiikka = new Heuristiikka();
    }
    
    @Test
    public void laskeeManhattanEtaisyydenOikein() {
        Solmu alku = new Solmu(13, 5);
        Solmu loppu = new Solmu(11, 27);
        double odotettuEtaisyys = 24;
        
        assertTrue(odotettuEtaisyys == this.heuristiikka.manhattanEtaisyys(alku, loppu));
    }
    
    @Test
    public void laskeeEuklidisenEtaisyydenOikein() {
        Solmu alku = new Solmu(13, 5);
        Solmu loppu = new Solmu(11, 27);
        double odotettuEtaisyys = 22.090722;
        double saatuEtaisyys = this.heuristiikka.euklidinenEtaisyys(alku, loppu);
        
        assertTrue(odotettuEtaisyys == pyoristaKuudenDesimaalinTarkkuuteen(saatuEtaisyys));
    }
    
    private double pyoristaKuudenDesimaalinTarkkuuteen(double liukuluku) {
        double palautettava = Math.round(liukuluku * 1000000);
        return palautettava / 1000000;
    }
}
