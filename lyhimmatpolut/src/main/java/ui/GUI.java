package ui;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Graafisen käyttöliittymän rakentava luokka.
 *
 * @author pertjenn
 */
public class GUI extends Application {

    private TiedostonlukijaIO lukija;
    private Kartanpiirtaja piirtaja;
    private Kartta kartta;
    private Solmu alku;
    private Solmu loppu;

    private Canvas karttataulu;
    private BorderPane asettelu;
    private VBox tiedotJaTulokset;
    private Scene nakyma;

    @Override
    public void start(Stage ikkuna) throws Exception {
        alustaKartta("kartat/Berlin_0_1024.map");
        alustaTiedotJaTulokset();
        muodostaAsetteluJaNakyma();
        valitsePaatepisteet();

        ikkuna.setScene(this.nakyma);
        ikkuna.show();
    }

    private void valitsePaatepisteet() {
        this.karttataulu.setOnMouseClicked((event -> {
            int rivi = (int) event.getX();
            int sarake = (int) event.getY();

            if (this.alku == null) {
                this.piirtaja.valitsePaatepiste(rivi, sarake);
                this.alku = new Solmu(rivi, sarake);
            } else if (this.loppu == null) {
                this.piirtaja.valitsePaatepiste(rivi, sarake);
                this.loppu = new Solmu(rivi, sarake);
            } else {
                Alert huomautus = new Alert(AlertType.WARNING);
                huomautus.setHeaderText(null);
                huomautus.setTitle(null);
                huomautus.setContentText("Olet jo valinnut kaksi pistettä!");
                huomautus.show();
            }

        }));

    }

    private void alustaKartta(String karttatiedosto) {
        this.lukija = new Kartanlukija();
        if (lukija.lue(karttatiedosto)) {
            this.kartta = lukija.haeKartta();
            this.piirtaja = new Kartanpiirtaja(kartta);
            this.karttataulu = piirtaja.piirraKartta();
        }
    }

    private void alustaTiedotJaTulokset() {
        Label kartanNimi = new Label("Valittu kartta: " + kartta.getNimi());
        Button laske = new Button("Laske reitti!");
        Label reitinPituus = new Label("Reitin pituus: x");
        Label kaytettyAika = new Label("Käytetty aika: x");

        this.tiedotJaTulokset = new VBox(20, kartanNimi, laske, reitinPituus, kaytettyAika);
    }

    private void muodostaAsetteluJaNakyma() {
        Insets ulkoreunat = new Insets(20, 20, 20, 20);
        this.asettelu = new BorderPane();
        this.asettelu.setPadding(ulkoreunat);
        this.asettelu.setCenter(karttataulu);
        BorderPane.setMargin(karttataulu, ulkoreunat);
        this.asettelu.setRight(tiedotJaTulokset);
        this.nakyma = new Scene(asettelu);
    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
