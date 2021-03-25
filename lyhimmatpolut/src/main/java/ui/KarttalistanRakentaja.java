package ui;

import java.io.File;
import javafx.scene.control.ChoiceBox;

/**
 * Luokka muodostaa listan ohjelman käytettävissä olevasta kartta-aineistosta.
 * @author pertjenn
 */
public class KarttalistanRakentaja {

    private final ChoiceBox lista;
    private final String karttahakemisto;

    /**
     * Luo uuden karttalistan rakentajan.
     * @param hakemisto karttahakemisto, josta lista luodaan
     */
    public KarttalistanRakentaja(String hakemisto) {
        this.lista = new ChoiceBox();
        this.karttahakemisto = hakemisto;
    }

    /**
     * Lukee karttatiedostojen nimet ja luo niistä listavalikon.
     * @return karttalista ChoiceBox-oliona
     */
    public ChoiceBox luoKarttalista() {
        File[] karttatiedostot = new File(this.karttahakemisto).listFiles();

        for (File karttatiedosto : karttatiedostot) {
            if (karttatiedosto.isFile()) {
                lista.getItems().add(karttatiedosto.getName());
            }
        }
        return this.lista;
    }
}
