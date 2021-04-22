package suorituskykytestaus;

import domain.Solmu;
import java.util.Objects;

/**
 * Suorituskykytesteissä käytetty luokka, johon tallennetaan tarpeelliset tiedot
 * skenaariotiedostosta.
 *
 * @author pertjenn
 */
public class Reittikuvaus {

    private final Solmu alku;
    private final Solmu loppu;
    private final double reitinPituus;

    /**
     * Luo uuden reittikuvauksen skenaariotiedoston tiedoista.
     *
     * @param alkuX alkusolmun x-koordinaatti
     * @param alkuY alkusolmun y-koordinaatti
     * @param loppuX loppusolmun x-koordinaatti
     * @param loppuY loppusolmun y-koordinaatti
     * @param pituus optimaalinen pituus
     */
    public Reittikuvaus(int alkuX, int alkuY, int loppuX, int loppuY, double pituus) {
        this.alku = new Solmu(alkuX, alkuY);
        this.loppu = new Solmu(loppuX, loppuY);
        this.reitinPituus = pituus;
//        double rp = Math.round(pituus * 10000);
//        this.reitinPituus = rp / 10000;
    }

    public Solmu getAlku() {
        return alku;
    }

    public Solmu getLoppu() {
        return loppu;
    }

    public double getReitinPituus() {
        return reitinPituus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alku: ").append(alku.getX()).append(" ").append(alku.getY())
                .append("\nLoppu: ").append(loppu.getX()).append(" ").append(loppu.getY())
                .append("\nPituus: ").append(reitinPituus)
                .append("\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        boolean samaAlku = false;
        boolean samaLoppu = false;

        if (o != null && o instanceof Reittikuvaus) {
            samaAlku = this.alku.samaSolmu(((Reittikuvaus) o).getAlku());
            samaLoppu = this.loppu.samaSolmu(((Reittikuvaus) o).getLoppu());
        }
        
        return (samaAlku && samaLoppu);
    }
}
