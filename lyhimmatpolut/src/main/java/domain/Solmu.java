package domain;

/**
 * Kartan yksittäistä solmua vastaava luokka.
 * @author pertjenn
 */
public class Solmu implements Comparable<Solmu> {
    private final int x;
    private final int y;
    private double paino;
    private double etaisyys;
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla ja alustaa painon ja etäisyyden.
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     */
    public Solmu(final int x, final int y) {
        this(x, y, -1, 999999999);
    }
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla ja painolla ja alustaa etäisyyden.
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     * @param paino solmun etäisyys polun edelliseen solmuun
     */
    public Solmu(final int x, final int y, final double paino) {
        this(x, y, paino, 999999999);
    }
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla ja painolla.
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     * @param paino solmun etäisyys polun edelliseen solmuun
     * @param etaisyys solmun etäisyys alkusolmuun
     */
    public Solmu(final int x, final int y, final double paino, final double etaisyys) {
        this.x = x;
        this.y = y;
        this.paino = paino;
        this.etaisyys = etaisyys;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public double getPaino() {
        return this.paino;
    }
    
    public double getEtaisyys() {
        return this.etaisyys;
    }
    
    public void setEtaisyys(double etaisyys) {
        this.etaisyys = etaisyys;
    }
    
    /**
     * Antaa solmujen järjestyksen minimikeon toiminnallisuutta varten.
     * @param s verrattava solmu
     * @return lukuarvo, joka osoittaa solmujen järjestyksen
     */
    @Override
    public int compareTo(Solmu s) {
        return Double.compare(this.etaisyys, s.getEtaisyys());
    }
    
    /**
     * Vertaa solmujen koordinaatteja.
     * @param s verrattava solmu
     * @return palauttaa true, jos koordinaatit ovat samat, ja false muutoin
     */
    public boolean equals(Solmu s) {
        return this.x == s.getX() && this.y == s.getY();
    }
}
