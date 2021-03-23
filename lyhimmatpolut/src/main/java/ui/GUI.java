package ui;

import dao.Kartanlukija;
import dao.TiedostonlukijaIO;
import domain.Kartta;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author pertjenn
 */
public class GUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        System.out.println("Hello Dijkstra!");
        TiedostonlukijaIO lukija = new Kartanlukija();
        lukija.lue("kartat/testikartta.map");
        Kartta kartta = lukija.haeKartta();
        System.out.println(kartta.getKorkeus());
        System.out.println(kartta.getLeveys());
        String rivi = new String(kartta.getKarttataulu()[0]);
        System.out.println(rivi);
    }
}
