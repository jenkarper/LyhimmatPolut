package domain;

import static java.lang.Math.sqrt;

/**
 * Tarjoaa toiminnallisuuden etäisyyksien laskemiseen.
 * @author pertjenn
 */
public class Laskin {
    
    /**
     * Laskee annettujen solmujen euklidisen etäisyyden.
     * @param s käsittelyssä oleva solmu
     * @param maali maalisolmu
     * @return euklidinen etäisyys liukulukuna
     */
    public double euklidinenEtaisyys(Solmu s, Solmu maali) {
        double xErotus = s.getX() - maali.getX();
        double yErotus = s.getY() - maali.getY();
        
        return sqrt((xErotus * xErotus) + (yErotus * yErotus));
    }
    
    /**
     * Laskee annetun luvun neliöjuuren.
     * @param luku luku, josta neliöjuuri lasketaan (käytännössä aina 2)
     * @return neliöjuuri
     */
    public double neliojuuri(int luku) {
        return sqrt(luku);
    }
}
