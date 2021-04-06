package tietorakenteet;

import domain.Solmu;

/**
 * Tarjoaa minimikekorakenteen algoritmien käyttöön.
 * @author pertjenn
 */
public class Keko {
    private Solmu[] keko;
    private int viimeinen;
    
    public Keko() {
        this.keko = new Solmu[10];
        this.viimeinen = 0;
        this.keko[0] = new Solmu(0, 0, 0, -1);
    }
    
    public void lisaa(Solmu s) {
        this.viimeinen++;
        
        if (viimeinen == keko.length) {
            kasvata();
        }
        
        this.keko[viimeinen] = s; // lisätään ensimmäiseen vapaaseen kohtaan
        int p = viimeinen;
        
        while (p > 1 && keko[p].lahempanaKuin(keko[vanhemmanIndeksi(p)])) { // ei olla indeksissä 1 eli juuressa, ja lisättävä solmu on prioriteettijärjestyksessä ennen sen tämänhetkistä vanhempaa
            vaihdaPaikkaa(p, vanhemmanIndeksi(p));
            p = vanhemmanIndeksi(p);
        }
    }
    
    public Solmu poistaPienin() {
        Solmu pienin = this.keko[1];
        this.keko[1] = this.keko[viimeinen];
        viimeinen--;
        korjaaKekoehto(1);
        
        return pienin;
    }

    private void kasvata() {
        Solmu[] uusiKeko = new Solmu[keko.length * 2];
        for (int i = 0; i < viimeinen; i++) {
            uusiKeko[i] = keko[i];
        }
        keko = uusiKeko;
    }
    
    public Solmu[] haeKeko() {
        return this.keko;
    }

    private void korjaaKekoehto(int indeksi) {
        int pienempiLapsi = 0;
        
        if (vasemmanLapsenIndeksi(indeksi) == 0) {
            return; // ei lapsia
        } else if (vasemmanLapsenIndeksi(indeksi) == viimeinen) { // vain vasen lapsi, joka on keon viimeinen alkio
            pienempiLapsi = vasemmanLapsenIndeksi(indeksi);
        } else {
            if (this.keko[vasemmanLapsenIndeksi(indeksi)].lahempanaKuin(this.keko[oikeanLapsenIndeksi(indeksi)])) {
                pienempiLapsi = vasemmanLapsenIndeksi(indeksi);
            } else {
                pienempiLapsi = oikeanLapsenIndeksi(indeksi);
            }
        }
        
        if (this.keko[pienempiLapsi].lahempanaKuin(this.keko[indeksi])) {
            vaihdaPaikkaa(indeksi, pienempiLapsi);
            korjaaKekoehto(pienempiLapsi);
        }
    }
    
    private int vanhemmanIndeksi(int paikka) {
        if (paikka == 1) {
            return 0;
        }
        return paikka/2;
    }
    
    private int vasemmanLapsenIndeksi(int paikka) {
        if ((2 * paikka) > this.viimeinen) {
            return 0;
        } else {
            return 2 * paikka;
        }
    }
    
    private int oikeanLapsenIndeksi(int paikka) {
        if ((2 * paikka + 1) > this.viimeinen) {
            return 0;
        } else {
            return 2 * paikka + 1;
        }
    }

    private void vaihdaPaikkaa(int a, int b) {
        Solmu vanhempi = keko[a];
        keko[a] = keko[b];
        keko[b] = vanhempi;
    }
    
    public int koko() {
        return this.viimeinen;
    }
    
    public int kapasiteetti() {
        return this.keko.length - 1;
    }
    
    public boolean tyhja() {
        return this.viimeinen == 0;
    }
}
