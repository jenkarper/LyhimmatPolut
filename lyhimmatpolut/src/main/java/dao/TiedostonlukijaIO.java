package dao;

import domain.Kartta;
import suorituskykytestaus.Skenaario;

/**
 * Rajapinta kartanlukijalle.
 * @author pertjenn
 */
public interface TiedostonlukijaIO {

    /**
     * Lukee parametrina annetussa polussa olevan tiedoston sisällön.
     * @param tiedosto tiedostopolku
     * @return onnistuiko luku
     */
    boolean lueKartta(String tiedosto);
    
    /**
     * Lukee karttaa vastaavan skenaariotiedoston sisällön.
     * @param tiedosto tiedostopolku
     * @return onnistuiko luku
     */
    boolean lueSkenaario(String tiedosto);

    /**
     * Palauttaa muodostetun karttaolion.
     * @return karttaolio
     */
    Kartta haeKartta();
    
    /**
     * Palauttaa karttaoliota vastaavan skenaarion.
     * @return skenaario-olio
     */
    Skenaario haeSkenaario();
}
