package ui;

import domain.Kartta;
import tietorakenteet.Lista;
import domain.Solmu;
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
    private static final Color TUTKITTU = Color.MISTYROSE;

    public Kartanpiirtaja(Kartta kartta) {
        this.kartta = kartta;
        this.alusta = new Canvas(kartta.getKorkeus(), kartta.getLeveys());
        this.piirturi = alusta.getGraphicsContext2D();
    }

    public void piirraKartta() {
        for (int x = 0; x < kartta.getKorkeus(); x++) {
            for (int y = 0; y < kartta.getLeveys(); y++) {
                if (kartta.getKarttataulu()[y][x] == '.') {
                    taytaRuutu(x, y, VAPAA);
                } else {
                    taytaRuutu(x, y, ESTE);
                }
            }
        }
    }
    
    public void piirraPolku(Lista polku, boolean[][] tutkitut) {
        for (int x = 0; x < kartta.getKorkeus(); x++) {
            for (int y = 0; y < kartta.getLeveys(); y++) {
                if (tutkitut[y][x]) {
                    taytaRuutu(x, y, TUTKITTU);
                }
            }
        }
        
        piirturi.setFill(POLKU);
        for (int i = polku.getViimeinen(); i >= 0; i--) {
            Solmu s = polku.haeSolmu(i);
            piirturi.fillRect(s.getX(), s.getY(), 2, 2);
        }
        valitsePaatepiste(polku.haeSolmu(0).getX(), polku.haeSolmu(0).getY());
        valitsePaatepiste(polku.haeSolmu(polku.getViimeinen()).getX(), polku.haeSolmu(polku.getViimeinen()).getY());
    }

    private void taytaRuutu(int x, int y, Color vari) {
        piirturi.setFill(vari);
        piirturi.fillRect(x, y, 1, 1);
    }

    protected boolean valitsePaatepiste(int x, int y) {
        if (kartta.getKarttataulu()[y][x] == '.') {
            piirturi.setFill(POLKU);
            piirturi.fillOval(x-5, y-5, 10, 10);
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
