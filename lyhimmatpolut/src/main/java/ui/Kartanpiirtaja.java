package ui;

import domain.Kartta;
import domain.Lista;
import domain.Solmu;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Kartan visualisoinnista vastaava luokka.
 *
 * @author pertjenn
 */
public class Kartanpiirtaja {

    private Kartta kartta;
    private final Canvas alusta;
    private final GraphicsContext piirturi;

    private static final Color VAPAA = Color.SNOW;
    private static final Color ESTE = Color.DARKGREEN;
    private static final Color POLKU = Color.FUCHSIA;

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
            //System.out.println("");
        }
    }
    
    public void piirraPolku(Lista polku) {
        piirturi.setFill(POLKU);
        for (int i = polku.getViimeinen(); i >= 0; i--) {
            Solmu s = polku.haeSolmu(i);
            piirturi.fillRect(s.getY(), s.getX(), 2, 2);
        }
    }

    private void taytaRuutu(int rivi, int sarake) {
        if (kartta.getKarttataulu()[rivi][sarake] == '.') {
            //System.out.print(kartta.getKarttataulu()[rivi][sarake]);
            piirturi.setFill(VAPAA);
        } else {
            //System.out.print(kartta.getKarttataulu()[rivi][sarake]);
            piirturi.setFill(ESTE);
        }
        
        piirturi.fillRect(sarake, rivi, 1, 1);
    }

    protected boolean valitsePaatepiste(int rivi, int sarake) {
        if (kartta.getKarttataulu()[rivi][sarake] == '.') {
            piirturi.setFill(POLKU);
            piirturi.fillOval(sarake-7, rivi-5, 10, 10);
            return true;
        }
        return false;
    }

    public Canvas getAlusta() {
        return alusta;
    }

    public void setKartta(Kartta kartta) {
        this.kartta = kartta;
    }
}
