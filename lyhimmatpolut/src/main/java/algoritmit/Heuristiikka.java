package algoritmit;

import domain.Solmu;
import static java.lang.Math.sqrt;

/**
 * Luokka tarjoaa heuristiikkoja A*-algoritmin käyttöön.
 * @author pertjenn
 */
public class Heuristiikka {
    
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
     * Laskee annettujen solmujen Manhattan-etäisyyden.
     * @param s käsittelyssä oleva solmu
     * @param maali maalisolmu
     * @return Manhattan-etäisyys liukulukuna
     */
    public double manhattanEtaisyys(Solmu s, Solmu maali) {
        double xErotus = s.getX() - maali.getX();
        double yErotus = s.getY() - maali.getY();
        
        return itseisarvo(xErotus) + itseisarvo(yErotus);
    }
    
    private double itseisarvo(double luku) {
        if (luku < 0) {
            return -luku;
        }
        return luku;
    }
}
