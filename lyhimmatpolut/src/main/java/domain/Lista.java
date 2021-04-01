package domain;

/**
 * Tarjoaa listarakenteen solmujen tallennusta varten.
 * @author pertjenn
 */
public class Lista {
    private Solmu[] lista;
    private int viimeinen;
    
    /**
     * Luo uuden listaolion oletuskoolla.
     */
    public Lista() {
        this.lista = new Solmu[10];
        this.viimeinen = -1;
    }
    
    /**
     * Lisää parametrina saatavan solmun listalle ja kasvattaa tarvittaessa kokoa.
     * @param s lisättävä solmu
     */
    public void lisaa(Solmu s) {
        lista[viimeinen + 1] = s;
        viimeinen++;
        
        if (viimeinen == lista.length - 1) {
            kasvata();
        }
    }
    
    private void kasvata() {
        Solmu[] uusiLista = new Solmu[lista.length * 2];
        for (int i = 0; i <= viimeinen; i++) {
            uusiLista[i] = lista[i];
        }
        lista = uusiLista;
    }
    
    public int getViimeinen() {
        return viimeinen;
    }
    
    /**
     * Palauttaa annetussa indeksissä olevan solmun tai null, jos solmua ei ole/indeksi on listan ulkopuolella.
     * @param indeksi indeksi, josta solmua etsitään
     * @return haettu solmu tai null
     */
    public Solmu haeSolmu(int indeksi) {
        if (indeksi > viimeinen || indeksi < 0) {
            return null;
        }
        return lista[indeksi];
    }
    
    /**
     * Palauttaa true, jos lista on tyhjä, muutoin false.
     * @return boolean-arvo
     */
    public boolean tyhja() {
        return this.viimeinen == -1;
    }
    
    /**
     * Palauttaa listaolion käyttämän taulukon koon (testausta varten).
     * @return taulukon integer-koko
     */
    public int kapasiteetti() {
        return this.lista.length;
    }
    
    /**
     * Palauttaa listalla olevien solmujen lukumäärän.
     * @return listan taulukon koko
     */
    public int koko() {
        return this.viimeinen + 1;
    }
    
    /**
     * Resetoi listan uuden polun laskemista varten.
     */
    public void tyhjenna() {
        this.lista = new Solmu[10];
        this.viimeinen = -1;
    }
}
