package domain;

/**
 * Kartan yksittäistä solmua vastaava luokka.
 * @author pertjenn
 */
public class Solmu implements Comparable<Solmu> {
    private final int x;
    private final int y;
    private double paino;
    private double vertailuarvo;
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla ja alustaa painon ja vertailuarvon.
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     */
    public Solmu(final int x, final int y) {
        this(x, y, -1, 999999999);
    }
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla ja painolla ja alustaa vertailuarvon.
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
     * @param vertailuarvo solmun prioriteetti keossa
     */
    public Solmu(final int x, final int y, final double paino, final double vertailuarvo) {
        this.x = x;
        this.y = y;
        this.paino = paino;
        this.vertailuarvo = vertailuarvo;
    }
    
    /**
     * Vertaa solmujen koordinaatteja.
     * @param s verrattava solmu
     * @return palauttaa true, jos koordinaatit ovat samat, ja false muutoin
     */
    public boolean samaSolmu(Solmu s) {
        return this.x == s.getX() && this.y == s.getY();
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
    
    public void setPaino(double paino) {
        this.paino = paino;
    }
    
    public double getVertailuarvo() {
        return this.vertailuarvo;
    }
    
    public void setVertailuarvo(double vertailuarvo) {
        this.vertailuarvo = vertailuarvo;
    }
    
    /**
     * Keko-olion tarvitsema vertailumetodi.
     * @param s kutsuvaan solmuun verrattava solmu
     * @return true, jos kutsuvan solmun vertailuarvo on pienempi kuin verrattavan
     */
    public boolean pienempiKuin(Solmu s) {
        return this.vertailuarvo < s.getVertailuarvo();
    }

    @Override
    public int compareTo(Solmu t) {
        return Double.compare(this.vertailuarvo, t.getVertailuarvo());
    }
    
    
}
