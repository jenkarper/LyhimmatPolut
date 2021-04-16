package suorituskykytestaus;

/**
 * Suorituskykytestauksen tulokset kokoava luokka.
 * @author pertjenn
 */
public class Testitulos {
    private final Reittikuvaus reitti;
    private final double dijkstra;
    private final double aStar;
    private final boolean pituusTasmaa;
    
    public Testitulos(Reittikuvaus reitti, double dijkstra, double aStar, boolean pituusTasmaa) {
        this.reitti = reitti;
        this.dijkstra = dijkstra;
        this.aStar = aStar;
        this.pituusTasmaa = pituusTasmaa;
    }
    
    public String haeTulos() {
        StringBuilder tulos = new StringBuilder();
        tulos.append("Dijkstran suoritusaika: ").append(dijkstra)
                .append("\nA* suoriutui");
        
        return tulos.toString();
    }
    
//    private final String algoritmi;
//    private final int polkuLoytyi;
//    private final double suoritusaika;
//    
//    public Testitulos(String algoritmi, int polkuLoytyi, double kokonaisaika, int toistoja) {
//        this.algoritmi = algoritmi;
//        this.polkuLoytyi = polkuLoytyi;
//        this.suoritusaika = kokonaisaika / toistoja;
//    }
//
//    public String getAlgoritmi() {
//        return algoritmi;
//    }
//
//    public int getPolkuLoytyi() {
//        return polkuLoytyi;
//    }
//
//    public double getSuoritusaika() {
//        return suoritusaika;
//    }
//    
//    public String haeTulos() {
//        StringBuilder tulos = new StringBuilder();
//        tulos.append(this.algoritmi).append("\n")
//                .append("Polku löytyi ").append(polkuLoytyi).append(" haussa")
//                .append("\nKeskimääräinen suoritusaika: ").append(suoritusaika);
//        
//        return tulos.toString();
//    }
}
