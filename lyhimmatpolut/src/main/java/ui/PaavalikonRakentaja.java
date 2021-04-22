package ui;

import domain.Tulos;
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
    private VBox tulokset;
    private Label pituus;
    private Label kesto;
    private VBox suorituskykytestienKaynnistysValikko;
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
        luoSuorituskykytestienKaynnistys();

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
        Label tulosOtsikko = new Label("5. Tulokset:");
        muotoileOtsikko(tulosOtsikko);
        this.pituus = new Label("");
        this.kesto = new Label("");
        this.tulokset = new VBox(10, pituus, kesto);
        valikko.getChildren().addAll(tulosOtsikko, tulokset);
    }
    
    private void luoSuorituskykytestienKaynnistys() {
        Label testausOtsikko = new Label("Käynnistä suorituskykytestit");
        muotoileOtsikko(testausOtsikko);
        Label selitys = new Label("(Tulokset tulostuvat toistaiseksi vain komentoriville/NetBeansin Output-ikkunaan!)");
        this.suorituskykytestienKaynnistysValikko = new VBox(10, testausOtsikko, selitys);
        suorituskykytestienKaynnistysValikko.setMargin(testausOtsikko, new Insets(200, 0, 0, 0));
        valikko.getChildren().add(suorituskykytestienKaynnistysValikko);
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
        
        // Asetetaan toteuttamattomat algoritmit ei-valittaviksi
        //j.setDisable(true);
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
     * @param tulos laskennan tulokset
     */
    public void asetaTulokset(Tulos tulos) {

        if (tulos.onnistui()) {
            this.pituus.setText("Reitin pituus: " + tulos.getPituus());
            this.kesto.setText("Käytetty aika (ms): " + tulos.getAika());
        } else {
            this.pituus.setText("Antamiesi pisteiden välille ei voitu muodostaa polkua!");
        }
    }
    
    /**
     * Nollaa valikon näyttämät tulokset uutta laskentaa varten.
     */
    public void nollaaTulokset() {
        this.pituus.setText("");
        this.kesto.setText("");
    }

    public HBox getKarttalistanKehys() {
        return this.karttalistanKehys;
    }

    public VBox getLaskennanKaynnistysValikko() {
        return this.laskennanKaynnistysValikko;
    }
    
    public VBox getSuorituskykytestienKaynnistysValikko() {
        return this.suorituskykytestienKaynnistysValikko;
    }

    /**
     * Palauttaa sen RadioButtonin arvon, joka on valittuna.
     *
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
