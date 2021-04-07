# Viikko 3

## Työn edistyminen

Lisäsin omat toteutukset listalle ja keolle ja konfiguroin Dijkstran käyttämään niitä. Lisäsin myös Algoritmi-rajapinnan, jonka kautta käyttöliittymä käyttää valittua algoritmia. Voi olla, että rajapinnan sijaan kannattaa tehdä abstrakti luokka, jos kaikilla algoritmeilla on paljon yhteistä koodia. Muokkasin pakkausrakennetta niin, että algoritmit ja tietorakenteet tulevat omiin pakkauksiinsa.

Tein omien tietorakenteiden oikeellisuutta tarkastelevat yksikkötestit automaattisen testauksen piiriin, ja vertasin niiden tehokkuutta Javan valmiisiin rakenteihin hiekkalaatikossa. Teen tehokkuusvertailun vielä vähän systemaattisemmin tuonnempana, jotta saan kirjoitettua siitä raportin. Kirjoitin myös Dijkstran oikeellisuutta valvovan yksikkötestin, joka vertaa algoritmin löytämien polkujen pituuksia haastavan kartta-aineiston skenaarioihin.

Lisäsin uuden Tulos-luokan, joka kokoaa laskennan tulokset käyttöliittymän tarvitsemaan muotoon. Muokkasin algoritmia niin, että se pitää kirjaa myös tutkittujen ruutujen määrästä, jotta Dijkstran ja muiden algoritmien vertailussa näkyisi myös tämä ero. Tulos-luokassa on siten oliomuuttujana tutkittujen ruutujen prosentuaalinen osuus käytetyn kartan kaikista vapaista ruuduista.

### Pulmatilanteita

En ole vielä aloittanut muiden algoritmien kirjoittamista, koska graafiseen käyttöliittymään ja vieruslistan hakuun tuhrautui niin paljon aikaa. Olen pettynyt siihen, että minun oli niin hankala hahmottaa naapurien läpikäyntiä 2d-taulukossa! Tällaiset tehtävät olivat minulle Tirassakin vaikeita,ja vaikka sain ne yleensä kuitenkin ratkaistua, vuoden takaiset opit näyttävät olevan ruosteessa. Toivon, etten ajaudu hankaluuksiin monimutkaisemman JPS-algoritmin kanssa.

Pulmatilanteita aiheuttivat mainitun vieruslistan haun lisäksi koordinaattien yhteensovittaminen toisaalta 2d-taulukossa, toisaalta Canvas-oliossa, sekä uuden kartan piirtäminen, kun käyttäjä valitsee kartan listalta. Sain kaiken onneksi ratkaistua joko kokeilemalla tai ohjaajan neuvojen avulla.

## Ensi viikon suunnitelma

Nyt ohjelma tuntuu olevan siinä tilanteessa, että voin alkaa pohtia uusien algoritmien toteutusta. Tarkoitukseni on toteuttaa vähintään A* ja kirjoittaa ainakin pseudokoodia JPS-algoritmiin. Kun muut algoritmit alkavat muotoutua, pystyn myös paremmin pohtimaan niille parhaiten sopivaa suunnittelumallia. Yritän myös saada ohjelman luokkarakenteen mahdollisimman hyväksi, joten ensi viikon töihin kuuluu varsinaisen koodin kirjoittamisen lisäksi paljon suunnittelua!

## Mitä opin tällä viikolla

Olen pyöritellyt 2d-taulukkoa nyt niin monella eri tavalla, että se ei tunnu enää niin vaikealta kuin ennen. Opin myös uutta JavaFX-elementeistä. Ohjelmistotuotannon teoreettinen anti avautuu uudella tavalla, kun yritän muistaa pohtia koodin sisäistä laatua ja versionhallinnan hyödyntämistä.

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
| -----:|:---:| :-----|
| 30.3. |  5  | Oman listan toteutus ja sille testejä, muun koodin muokkaus listan käytössä; oman keon rakentamisen aloitus |
| 31.3. |  4  | Oman listan ja keon testaamista ja tehokkuusvertailuja Javan rakenteisiin, kartan piirtämisessä olleen virheen metsästäminen ja korjaus |
|  1.4. |  5  | Uuden vieruslistanhakumetodin toteutus, käyttöliittymän täydennys, pakkausrakenteen muokkaus ja uusien luokkien luominen ajanottoa ja tulosten keruuta varten |
|  6.4. | 5.5 | Parannuksia käyttöliittymään, oman keon lisääminen ohjelmaan |
|  7.4. |  2  | Vieruslistanhakumetodin siistiminen, koodin siistiminen |
|       |     | |
|**YHT**|**21.5**| |
