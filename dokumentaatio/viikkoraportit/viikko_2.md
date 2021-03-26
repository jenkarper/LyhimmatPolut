# Viikko 2

## Työn edistyminen

Toteutin karttatiedoston lukemisen ja kartan piirtämisen sekä käyttöliittymän pohjan. Olen jättänyt käyttöliittymän rakentavan koodin testikattavuuden ulkopuolelle, mutta karttatiedoston lukemisesta vastaavalle luokalle tein muutaman yksikkötestin.

Kirjoitin myös ensimmäisen version Dijkstran algoritmista ja tein siihen muutaman testin helpoilla testikartoilla. Lisäsin käyttöliittymään algoritmin löytämän polun piirtämisen. Lisäsin myös kartta-aineistoa ja loin käytettävissä olevista kartoista valikon käyttöliittymään, mutta vielä karttaa ei voi valita, vaan tiedoston nimi on kovakoodattuna käyttöliittymään.

### Pulmatilanteita ja kysymyksiä ohjaajalle

Kirjoittamani Dijkstran algoritmin löytämät polut näyttävät silmämääräisesti hyviltä, mutta kun vertasin kaikkein pisimpien polkujen pituuksia Moving AI Lab -sivuston tarjoamiin skenaarioihin, lukemat eivät täsmänneet. Oman algoritmini palauttama polun pituus on pienempi kuin skenaariossa ilmoitettu optimaalinen pituus, joten etäisyyden laskennassa on jokin virhe. Tähän kaipaisin apua! Tuntuu, ettei ole järkevää edetä toisiin algoritmeihin, ennen kuin tämä yksinkertaisin toimii varmasti oikein.

Toinen päänvaivaa aiheuttanut seikka oli testien kirjoittaminen algoritmille. Pienillä, yksinkertaisilla testikartoilla se oli ok, mutta en ole varma, millaisia testejä kannattaisi kirjoittaa monimutkaisemmille kartoille.  Ehkä voisi verrata löydetyn polun pituutta tiedettyyn optimaaliseen pituuteen?

## Ensi viikon suunnitelma

Toivon ensi viikolla ainakin saavani Dijkstran algoritmin toimimaan oikein ja rakentamaan sen avulla A*-algoritmin. Yritän myös toteuttaa ainakin minimikeon, mahdollisesti myös listan, jotta minulla olisi valmis pohja JPS-algoritmin aloittamiseen.

## Mitä opin tällä viikolla

Tämän viikon oppianti on keskittynyt enemmän graafisen käyttöliittymän rakentamiseen kuin algoritmin kirjoittamiseen. Olen kuitenkin yrittänyt soveltaa ohjelmoinnin kursseilla opetettuja hyviä koodauskäytänteitä olio- ja komponenttisuunnittelussa. Treenasin myös debuggaamista ja testien kirjoittamista. Otin käyttöön GitHub Actions -työkalun, ja sitä varten jouduin jonkin verran hakemaan apua netistä - se oli hyvä oppimiskokemus!

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
| -----:|:---:| :-----|
| 23.3. |  6  | Graafisen käyttöliittymän täydennys, dao-pakkauksen luokat ja testit, jar-paketointi |
| 24.3. |  2  | GitHub Actionsin konfigurointi |
| 25.3. |  3  | Käyttöliittymän täydennys, Codecov-seurannan konfigurointi, JavaDoc-kommenttien täydennys |
| 26.2. |  5  | Alustava Dijkstra ja sille testejä, viikkoraportin kirjoittaminen |
|       |     | |
|**YHT**|**16**| |
