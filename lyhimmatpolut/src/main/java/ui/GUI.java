package ui;

import algoritmit.DijkstraStar;
import algoritmit.Algoritmi;
import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import domain.Solmu;
import domain.Tulos;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private PaavalikonRakentaja valikonRakentaja;
    private ChoiceBox karttalista;

    @Override
    public void start(Stage ikkuna) throws Exception {
        ikkuna.setTitle("Lyhimmät polut - reitinhakualgoritmien vertailu");
        muodostaPaavalikko();
        muodostaKarttavalikko("kartat");
        muodostaAsetteluJaNakyma();

        ikkuna.setScene(this.nakyma);
        ikkuna.show();
    }

    private void muodostaPaavalikko() {
        this.valikonRakentaja = new PaavalikonRakentaja();
        this.tiedotJaTulokset = valikonRakentaja.luoValikko();
        muodostaLaskennanKaynnistysNappi();
    }

    private void muodostaKarttavalikko(String hakemisto) {
        KarttalistanRakentaja rakentaja = new KarttalistanRakentaja(hakemisto);
        this.karttalista = rakentaja.luoKarttalista();
        this.valikonRakentaja.lisaaKarttalista(karttalista);

        this.karttalista.getSelectionModel().selectFirst();
        lueKartta((String) karttalista.getSelectionModel().getSelectedItem());
        piirraKartta();
        alustaKartanValinta();
        alustaPaatepisteidenValinta();
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

    private void muodostaLaskennanKaynnistysNappi() {
        Button laske = new Button("Laske!");

        laske.setOnAction((event) -> {
            if (alku == null || loppu == null) {
                naytaPaatepisteidenValintaVaroitus(false);
            } else {
                Algoritmi algoritmi = asetaAlgoritmi(kartta);
                Tulos laskennanTulos = algoritmi.laskeReitti(alku, loppu);
                
                if (laskennanTulos.onnistui()) {
                    this.piirtaja.piirraPolku(laskennanTulos.getPolku(), algoritmi.haeTutkitut());
                }
                this.valikonRakentaja.asetaTulokset(laskennanTulos);
            }
        });

        this.valikonRakentaja.getLaskennanKaynnistysValikko().getChildren().add(laske);
    }

    private void lueKartta(String karttatiedosto) {
        this.lukija = new Kartanlukija();
        lukija.lue("kartat/" + karttatiedosto);
        this.kartta = lukija.haeKartta();
    }

    private void piirraKartta() {
        this.piirtaja = new Kartanpiirtaja(this.kartta);
        this.piirtaja.piirraKartta();
        this.karttataulu = this.piirtaja.getAlusta();
    }

    private void alustaKartanValinta() {
        this.karttalista.setOnAction(e -> {
            nollaaPisteidenValinta();
            lueKartta((String) karttalista.getSelectionModel().getSelectedItem());
            this.kartta = lukija.haeKartta();
            piirraKartta();
            this.asettelu.setCenter(karttataulu);
            alustaPaatepisteidenValinta();
            this.valikonRakentaja.nollaaTulokset();
        });
    }

    private void alustaPaatepisteidenValinta() {
        this.karttataulu.setOnMouseClicked((event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (this.alku == null) {
                if (this.piirtaja.valitsePaatepiste(x, y)) {
                    this.alku = new Solmu(x, y);
                    this.valikonRakentaja.asetaAlku(x, y);
                } else {
                    naytaPaatepisteidenValintaVaroitus(false);
                }
            } else if (this.loppu == null) {
                if (this.piirtaja.valitsePaatepiste(x, y)) {
                    this.loppu = new Solmu(x, y);
                    this.valikonRakentaja.asetaLoppu(x, y);
                } else {
                    naytaPaatepisteidenValintaVaroitus(false);
                }
            } else {
                naytaPaatepisteidenValintaVaroitus(true);
            }
        }));

    }

    private Algoritmi asetaAlgoritmi(Kartta kartta) {
        String valittuAlgoritmi = this.valikonRakentaja.haeValittuAlgoritmi();

        switch (valittuAlgoritmi) {
            case "Dijkstra":
                return new DijkstraStar(kartta, true);
            case "A*":
                return new DijkstraStar(kartta, false);
            case "Jump Point Search":
                System.out.println("JPS valittu!");
            default:
                // Käytetään oletusarvoisesti Dijkstraa.
                return new DijkstraStar(kartta, true);
        }
    }

    private void naytaPaatepisteidenValintaVaroitus(boolean valittu) {
        Alert huomautus = new Alert(AlertType.WARNING);
        huomautus.setHeaderText(null);
        huomautus.setTitle(null);

        if (valittu) {
            huomautus.setContentText("Olet jo valinnut kaksi pistettä!");
        } else {
            huomautus.setContentText("Valitse päätepisteet kartan vapaalta alueelta!");
        }
        huomautus.show();
    }

    private void nollaaPisteidenValinta() {
        this.alku = null;
        this.loppu = null;
    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
