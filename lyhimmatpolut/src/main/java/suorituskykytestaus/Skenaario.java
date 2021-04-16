package suorituskykytestaus;

import java.util.ArrayList;

/**
 * Tarjoaa valitun kartan skenaariotiedot.
 * @author pertjenn
 */
public class Skenaario {
    private final ArrayList<Reittikuvaus> skenaario;
    
    public Skenaario() {
        this.skenaario = new ArrayList<>();
    }
    
    public void lisaaReittikuvaus(Reittikuvaus kuvaus) {
        this.skenaario.add(kuvaus);
    }
    
    public Reittikuvaus arvoReittikuvaus() {
        int indeksi = (int) (Math.random() * skenaario.size() + 1) - 1;
        return skenaario.get(indeksi);
    }
}
