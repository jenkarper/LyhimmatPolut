package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Käyttöliittymän päävalikon luova luokka.
 *
 * @author pertjenn
 */
public class PaavalikonRakentaja {

    private final VBox valikko;
    private HBox karttalistanKehys;
    private Button piirraUusiKartta;
    private Label alku;
    private Label loppu;
    private VBox laskennanKaynnistysValikko;
    private Label reitinPituus;
    private Label laskennanKesto;
    private String valittuKartta;

    public PaavalikonRakentaja() {
        this.valikko = new VBox();
    }

    public VBox luoValikko() {

        luoKartanValinta();
        luoPisteidenValinta();
        luoLaskennanKaynnistys();
        luoTulostenNaytto();

        valikko.setSpacing(20);
        valikko.setPadding(new Insets(10, 10, 10, 10));

        return valikko;
    }

    private void luoKartanValinta() {
        Label kartanValinta = new Label("1. Jos et halua käyttää oletuskarttaa, valitse uusi kartta valikosta:");
        muotoileOtsikko(kartanValinta);
        this.karttalistanKehys = new HBox();
        valikko.getChildren().addAll(kartanValinta, karttalistanKehys);
    }

    private void luoPisteidenValinta() {
        Label pisteidenValinta = new Label("2. Valitse päätepisteet klikkaamalla haluamaasi kohtaa kartalla.");
        muotoileOtsikko(pisteidenValinta);
        Label valitutPisteet = new Label("Valitut pisteet: ");
        this.alku = new Label("Alku: ");
        this.alku.setMinWidth(200);
        this.loppu = new Label("Loppu: ");
        this.loppu.setMinWidth(200);
        HBox pisteet = new HBox(10, alku, loppu);
        valikko.getChildren().addAll(pisteidenValinta, valitutPisteet, pisteet);
    }

    private void luoLaskennanKaynnistys() {
        Label laskennanKaynnistys = new Label("3. Käynnistä reitin laskenta: ");
        muotoileOtsikko(laskennanKaynnistys);
        this.laskennanKaynnistysValikko = new VBox(10, laskennanKaynnistys);
        valikko.getChildren().add(laskennanKaynnistysValikko);
    }

    private void luoTulostenNaytto() {
        Label tulokset = new Label("4. Tulokset:");
        muotoileOtsikko(tulokset);
        Label pituus = new Label("Reitin pituus: ");
        Label aika = new Label("Käytetty aika: ");
        valikko.getChildren().addAll(tulokset, pituus, aika);
    }

    private void muotoileOtsikko(Label otsikko) {
        otsikko.setFont(new Font("Arial", 15));
        otsikko.setStyle("-fx-font-weight: bold");
    }
   
    public void asetaAlku(int x, int y) {
        this.alku.setText("Alku: " + x + ", " + y);
    }
    
    public void asetaLoppu(int x, int y) {
        this.loppu.setText("Loppu: " + x + ", " + y);
    }
    
    public HBox getKarttalistanKehys() {
        return this.karttalistanKehys;
    }
    
    public VBox getLaskennanKaynnistysValikko() {
        return this.laskennanKaynnistysValikko;
    }
    
    public void lisaaKarttalista(ChoiceBox lista) {
        this.karttalistanKehys.getChildren().add(lista);
    }
}
