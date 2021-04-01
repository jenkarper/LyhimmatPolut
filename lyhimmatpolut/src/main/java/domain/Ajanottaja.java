package domain;

/**
 * Laskennan ajanmittauksesta huolehtiva luokka.
 * @author pertjenn
 */
public class Ajanottaja {
    private long alku;
    private long loppu;
    
    /**
     * Käynnistää ajastimen.
     */
    public void kaynnista() {
        this.alku = System.nanoTime();
    }
    
    /**
     * Pysäyttää ajastimen.
     */
    public void pysayta() {
        this.loppu = System.nanoTime();
    }
    
    /**
     * Palauttaa laskennassa käytetyn ajan sekunteina.
     * @return kulunut aika
     */
    public double getAika() {
        return (loppu - alku) / 1e9;
    }
}
