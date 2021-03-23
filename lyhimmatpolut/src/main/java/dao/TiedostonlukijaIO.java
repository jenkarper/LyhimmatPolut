package dao;

import domain.Kartta;

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
    boolean lue(String tiedosto);

    /**
     * Palauttaa muodostetun karttaolion.
     * @return karttaolio
     */
    Kartta haeKartta();
}
