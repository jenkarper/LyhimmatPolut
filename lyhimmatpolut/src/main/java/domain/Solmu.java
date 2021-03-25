package domain;

/**
 * Kartan yksittäistä solmua vastaava luokka.
 * @author pertjenn
 */
public class Solmu {
    private final int x;
    private final int y;
    
    /**
     * Luo uuden solmuolion annetuilla koordinaateilla.
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     */
    public Solmu(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
