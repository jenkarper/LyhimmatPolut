package suorituskykytestaus;

import java.util.ArrayList;

/**
 * Tarjoaa valitun kartan skenaariotiedot.
 * @author pertjenn
 */
public class Skenaario {
    private final ArrayList<Reittikuvaus> skenaario;
    
    /**
     * Luo uuden skenaario-olion ja alustaa listan.
     */
    public Skenaario() {
        this.skenaario = new ArrayList<>();
    }
    
    /**
     * Lisää parametrina annetun kuvauksen listalle.
     * @param kuvaus kartanlukijan muodostama reittikuvaus
     */
    public void lisaaReittikuvaus(Reittikuvaus kuvaus) {
        this.skenaario.add(kuvaus);
    }
    
    /**
     * Arpoo satunnaisen reittikuvauksen listalta testausta varten.
     * @return reittikuvausolio
     */
    public Reittikuvaus arvoReittikuvaus() {
        int indeksi = (int) (Math.random() * skenaario.size() + 1) - 1;
        return skenaario.get(indeksi);
    }
    
    /**
     * Hakee reittikuvauksen tietystä indeksistä (käytetään yksikkötesteissä).
     * @param i indeksi, josta kuvaus haetaan
     * @return indeksissä oleva reittikuvaus
     */
    public Reittikuvaus haeReittikuvaus(int i) {
        return skenaario.get(i);
    }
    
    /**
     * Hakee luettujen reittien määrän.
     * @return luettujen reittien lukumäärä
     */
    public int reittejaLuettu() {
        return this.skenaario.size();
    }
}
