package ui;

import domain.Kartta;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Kartan visualisoinnista vastaava luokka.
 * @author pertjenn
 */
public class Kartanpiirtaja {
    private final Kartta kartta;
    private final Canvas alusta;
    private final GraphicsContext piirturi;
    
    private final Color VAPAA = Color.WHITE;
    private final Color ESTE = Color.DARKGREEN;
    
    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.alusta = new Canvas(kartta.getKorkeus(), kartta.getLeveys());
        this.piirturi = alusta.getGraphicsContext2D();
    }
    
    public Canvas piirraKartta() {
        for (int rivi = 0; rivi < kartta.getKorkeus(); rivi++) {
            for (int sarake = 0; sarake < kartta.getLeveys(); sarake++) {
                taytaRuutu(rivi, sarake);
            }
        }
        return alusta;
    }
    
    private void taytaRuutu(int rivi, int sarake) {
        if (kartta.getKarttataulu()[rivi][sarake] == '.') {
            piirturi.setFill(VAPAA);
        } else {
            piirturi.setFill(ESTE);
        }
        piirturi.fillRect(rivi, sarake, 1, 1);
    }
}
