package domain;

/**
 * JPS-algoritmin hyödyntämä luokka, joka kertoo, mihin suuntaan ollaan menossa akseleilla.
 * @author pertjenn
 */
public class Suunta {
    public int x;
    public int y;
    
    /**
     * Luo uuden suuntaolion.
     * @param x 1 jos ollaan menossa oikealle, -1 jos ollaan menossa vasemmalle
     * @param y 1 jos ollaan menossa alaspäin, -1 jos ollaan menossa ylöspäin
     */
    public Suunta(int x, int y) {
        this.x = x;
        this.y = y;
    }
}