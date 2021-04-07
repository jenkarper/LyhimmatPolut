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
     * @param alku lähtösolmu
     * @param loppu maalisolmu
     * @return laskennan tulos
     */
    Tulos laskeReitti(Solmu alku, Solmu loppu);
    
    /**
     * Hakee algoritmin rakentaman taulukon vierailluista solmuista.
     * @return boolean-taulukko
     */
    boolean[][] haeTutkitut();
}
