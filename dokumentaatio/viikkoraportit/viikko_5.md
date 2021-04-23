# Viikko 5

## Työn edistyminen

Edellisen viikkopalautuksen jälkeen huomasin sattumalta algoritmissani virheen, kun A* palautti osalla poluista eri arvon kuin Dijkstra. Tämän virheen metsästykseen meni kauan, enkä ole tällä viikolla edennyt aikomallani tavalla. Virhe kuitenkin onneksi löytyi, ja olen saanut myös JPS:ää edistettyä. Muokkasin jonkin verran ohjelman rakennetta poistamalla tarpeettomia metodeita ja luokkia. Olen pyrkinyt noudattamaan single responsibility -periaatetta, ja sen vuoksi luokkien ja metodien määrä on ollut aika suuri. Tiivistin siis DijkstraStar-luokan sisältöä.

### Pulmatilanteita

Olen kirjoittanut osia JPS:n koodista, mutta minulle on vielä vähän epäselvää, miten miten saan osat sidottua yhteen. On hankalaa kirjoittaa koodia pieniä paloja kerrallaan ja testata aina välillä, että kaikki toimii, eikä minulla olekaan toistaiseksi kuin hyvin triviaali testi JPS:lle. Algoritmissa on aika monta osasta, ja minua pohdituttaa, miten saan solmuihin liittyvät suunnat ja etäisyydet pysymään ajan tasalla osasta toiseen kuljettaessa. Luulen että työ vaatii nyt vähän etäisyyttä ja alitajuista työstöä.

## Ensi viikon suunnitelma

Ensi viikolla JPS pitäisi saada toimimaan, jotta voin siirtyä suorituskykytestaukseen ja dokumentaation täydentämiseen ja viimeistelyyn. Haluan joka tapauksessa täydentää dokumentaatiota niin pitkälle kuin pystyn, vaikkei JPS vielä toimisikaan, jotta kaikki ei jää viimeiselle kahdelle viikolle.

## Mitä opin tällä viikolla

Virheen etsintä oli tuskaisuudestaan huolimatta opettavaista! Lyhyet muuttujanimet tekevät koodista tiiviimpää, mutta ne myös sekaantuvat helpommin toisiinsa.

Olen tutkaillut erilaisia sanallisia ja pseudokoodiesityksiä JPS-algoritmista, ja pseudokoodin lukutaitoni on kyllä saanut harjoitusta. Erilaiset tarkistukset 2d-taulukossa ovat melko olennainen osa ainakin omaa JPS-toteutustani, ja niiden parissa askartelu on myös ollut opettavaista.

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
| -----:|:---:| :-----|
| 17.4.|  2  | Virheen metsästys DijkstraStarin koodista |
| 20.4.|  5  | Virheen metsästys + korjaus |
| 21.4.| 6.5 | Koodin refaktorointi, virheen metsästyksessä käytettyjen väliaikaistestien yms. koodin poistaminen, JPS-opiskelu |
| 22.4.|  6  | JPS-opiskelu, pseudokoodipätkien kirjoittelu, muutamien metodien kirjoittelu |
| 23.4.|  5  | JPS-kokeilut |
|      |     | |
|**YHT**|**23.5**| |
