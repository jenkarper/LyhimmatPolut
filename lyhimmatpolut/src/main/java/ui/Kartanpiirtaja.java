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
    private Kartta kartta;
    private final Canvas alusta;
    private final GraphicsContext piirturi;
    
    private final Color VAPAA = Color.SNOW;
    private final Color ESTE = Color.DARKGREEN;
    private final Color PAATEPISTE = Color.FUCHSIA;
    
    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.alusta = new Canvas(kartta.getKorkeus(), kartta.getLeveys());
        this.piirturi = alusta.getGraphicsContext2D();
    }
    
    public void piirraKartta() {
        for (int rivi = 0; rivi < kartta.getKorkeus(); rivi++) {
            for (int sarake = 0; sarake < kartta.getLeveys(); sarake++) {
                taytaRuutu(rivi, sarake);
            }
        }
    }
    
    private void taytaRuutu(int rivi, int sarake) {
        if (kartta.getKarttataulu()[rivi][sarake] == '.') {
            piirturi.setFill(VAPAA);
        } else {
            piirturi.setFill(ESTE);
        }
        piirturi.fillRect(rivi, sarake, 1, 1);
    }
    
    protected void valitsePaatepiste(int rivi, int sarake) {
        piirturi.setFill(PAATEPISTE);
        piirturi.fillOval(rivi, sarake, 5, 5);
    }
    
    public Canvas getAlusta() {
        return alusta;
    }
    
    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
    }
}
