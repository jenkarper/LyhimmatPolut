package algoritmit;

import domain.Solmu;
import domain.Tulos;

/**
 * Rajapinta algoritmeille.
 * @author pertjenn
 */
public interface Algoritmi {
    
    /**
     * Käynnistää reitin laskennan annettujen solmujen välillä.
     * @param lahto lähtösolmu
     * @param maali maalisolmu
     * @return laskennan tulos
     */
    Tulos laskeReitti(Solmu lahto, Solmu maali);
    
    /**
     * Hakee algoritmin rakentaman taulukon vierailluista solmuista.
     * @return boolean-taulukko
     */
    boolean[][] haeTutkitut();
}
