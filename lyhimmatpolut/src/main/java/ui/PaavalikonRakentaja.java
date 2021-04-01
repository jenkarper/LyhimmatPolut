package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
    private ToggleGroup algoritmiNapit;

    /**
     * Luo uuden päävalikonrakentajan ja alustaa valikkoelementin.
     */
    public PaavalikonRakentaja() {
        this.valikko = new VBox();
    }

    /**
     * Täyttää valikkoelementin ja palauttaa sen GUIn käyttöön.
     *
     * @return valikko VBox-oliona
     */
    public VBox luoValikko() {

        luoAlgoritminValinta();
        luoKartanValinta();
        luoPisteidenValinta();
        luoLaskennanKaynnistys();
        luoTulostenNaytto();

        valikko.setSpacing(20);
        valikko.setPadding(new Insets(10, 10, 10, 10));

        return valikko;
    }

    private void luoAlgoritminValinta() {
        Label algoritminValinta = new Label("1. Valitse laskennassa käytettävä algoritmi:");
        muotoileOtsikko(algoritminValinta);

        RadioButton dijkstra = new RadioButton("Dijkstra");
        RadioButton aStar = new RadioButton("A*");
        RadioButton jps = new RadioButton("Jump Point Search");

        luoToggleRyhma(dijkstra, aStar, jps);

        HBox algoritminValintaNapit = new HBox(10, dijkstra, aStar, jps);
        valikko.getChildren().addAll(algoritminValinta, algoritminValintaNapit);
    }

    private void luoKartanValinta() {
        Label kartanValinta = new Label("2. Jos et halua käyttää oletuskarttaa, valitse uusi kartta valikosta:");
        muotoileOtsikko(kartanValinta);
        this.karttalistanKehys = new HBox();
        valikko.getChildren().addAll(kartanValinta, karttalistanKehys);
    }

    private void luoPisteidenValinta() {
        Label pisteidenValinta = new Label("3. Valitse päätepisteet klikkaamalla haluamaasi kohtaa kartalla.");
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
        Label laskennanKaynnistys = new Label("4. Käynnistä reitin laskenta: ");
        muotoileOtsikko(laskennanKaynnistys);
        this.laskennanKaynnistysValikko = new VBox(10, laskennanKaynnistys);
        valikko.getChildren().add(laskennanKaynnistysValikko);
    }

    private void luoTulostenNaytto() {
        Label tulokset = new Label("5. Tulokset:");
        muotoileOtsikko(tulokset);
        this.reitinPituus = new Label("Reitin pituus: ");
        this.laskennanKesto = new Label("Käytetty aika (s): ");
        valikko.getChildren().addAll(tulokset, reitinPituus, laskennanKesto);
    }

    private void muotoileOtsikko(Label otsikko) {
        otsikko.setFont(new Font("Arial", 15));
        otsikko.setStyle("-fx-font-weight: bold");
    }

    private void luoToggleRyhma(RadioButton d, RadioButton a, RadioButton j) {
        this.algoritmiNapit = new ToggleGroup();
        d.setToggleGroup(algoritmiNapit);
        d.setSelected(true);
        a.setToggleGroup(algoritmiNapit);
        j.setToggleGroup(algoritmiNapit);
    }

    /**
     * Päivittää valitun alkupisteen koordinaatit valikkoon.
     *
     * @param x valitun pisteen x-koordinaatti
     * @param y valitun pisteen y-koordinaatti
     */
    public void asetaAlku(int x, int y) {
        this.alku.setText("Alku: (" + x + ", " + y + ")");
    }

    /**
     * Päivittää valitun loppupisteen koordinaatit valikkoon.
     *
     * @param x valitun pisteen x-koordinaatti
     * @param y valitun pisteen y-koordinaatti
     */
    public void asetaLoppu(int x, int y) {
        this.loppu.setText("Loppu: (" + x + ", " + y + ")");
    }

    /**
     * Päivittää polun pituuden oikeaan Label-olioon.
     *
     * @param pituus löydetyn polun pituus
     * @param aika laskentaan käytetty aika sekunteina
     */
    public void asetaTulokset(double pituus, double aika) {
        this.reitinPituus.setText("Reitin pituus: " + pituus);
        this.laskennanKesto.setText("Käytetty aika (s): " + aika);
    }

    public HBox getKarttalistanKehys() {
        return this.karttalistanKehys;
    }

    public VBox getLaskennanKaynnistysValikko() {
        return this.laskennanKaynnistysValikko;
    }

    /**
     * Palauttaa sen RadioButtonin arvon, joka on valittuna.
     * @return algoritmin nimi
     */
    public String haeValittuAlgoritmi() {
        RadioButton valittu = (RadioButton) algoritmiNapit.getSelectedToggle();

        if (valittu == null) {
            return "Et ole valinnut algoritmia!";
        } else {
            return valittu.getText();
        }
    }

    /**
     * Lisää GUIssa muodostetun ChoiceBox-listan kartta-aineistosta.
     *
     * @param lista ChoiceBox-olio
     */
    public void lisaaKarttalista(ChoiceBox lista) {
        this.karttalistanKehys.getChildren().add(lista);
    }
}
