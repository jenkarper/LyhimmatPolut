package ui;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Dijkstra;
import domain.Kartta;
import domain.Lista;
import domain.Solmu;
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
        alustaKartta("kartat/Milan_1_1024.map");
        muodostaPaavalikko();
        muodostaAsetteluJaNakyma();
        muodostaKarttalista("kartat");
        valitsePaatepisteet();
        ikkuna.setScene(this.nakyma);
        ikkuna.show();
    }

    private void valitsePaatepisteet() {
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

    private void alustaKartta(String karttatiedosto) {
        this.lukija = new Kartanlukija();
        if (lukija.lue(karttatiedosto)) {
            this.kartta = lukija.haeKartta();
            this.piirtaja = new Kartanpiirtaja(kartta);
            piirtaja.piirraKartta();
            this.karttataulu = piirtaja.getAlusta();
        }
    }

    private void muodostaPaavalikko() {
        this.valikonRakentaja = new PaavalikonRakentaja();
        this.tiedotJaTulokset = valikonRakentaja.luoValikko();
        muodostaLaskennanKaynnistysNappi();
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

    private void muodostaKarttalista(String hakemisto) {
        KarttalistanRakentaja rakentaja = new KarttalistanRakentaja(hakemisto);
        this.karttalista = rakentaja.luoKarttalista();
        this.valikonRakentaja.lisaaKarttalista(karttalista);
    }

    private void muodostaLaskennanKaynnistysNappi() {
        Button laske = new Button("Laske!");

        laske.setOnAction((event) -> {
            if (alku == null || loppu == null) {
                naytaPaatepisteidenValintaVaroitus(false);
            } else {
                Dijkstra d = new Dijkstra(kartta);
//                alku = new Solmu(38, 28);
//                loppu = new Solmu(1012, 941);
                Lista polku = d.laskeReitti(alku, loppu);
                double pituus = d.getPolunPituus(loppu);
                if (polku.tyhja()) {
                    System.out.println("Polkua ei voitu muodostaa!");
                }
                this.piirtaja.piirraPolku(polku);
                this.valikonRakentaja.asetaLoydetynPolunPituus(pituus);
            }
        });

        this.valikonRakentaja.getLaskennanKaynnistysValikko().getChildren().add(laske);
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

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
