# Viikko 4

## Työn edistyminen

Toteutin A*-algoritmin ja tein uuden luokan, joka vastaa sekä Dijkstran että A*-algoritmin luomisesta (konstruktori saa parametrina boolean-arvon, joka määrittelee, kumpi algoritmi on kyseessä). Tällä viikolla aloitin tarkemman perehtymisen JPS-algoritmiin muutamien lähteiden avulla. Toteutin ensimmäisen algoritmin palana x- ja y-akselien suuntaisen skannauksen valitusta pisteestä, jonka on tarkoitus tunnistaa mahdolliset hyppypisteet.

### Pulmatilanteita

En ole ihan varma, toimiiko JPS-algoritmille kirjoittamani akselien suuntainen skannaus oikein. JPS-algoritmin voi nyt valita ohjelmassa polun laskentaan, mutta tällä hetkellä se lähtee liikkeelle alkupisteestä, suorittaa akselien suuntaisen haun siitä, siirtyy sitten yhden solmun oikealle ja yhden alas ja suorittaa taas akselien suuntaisen haun. Se jatkaa näin, kunnes siirtyminen yksi oikealle, yksi alas johtaa esteeseen tai kartan ulkopuolelle. Hauissa löydetyt hyppypisteet piirretään sen jälkeen kartalle, ja ne näyttävät piirtyvän sellaisiin kohtiin kuin odotankin (esteiden kulmille). Minulla ei kuitenkaan ole mitään oikeaksi tiedettyä, pelkästään hyppypisteitä etsivää algoritmia vertailuun, joten on vaikea sanoa, toimiiko oma toteutukseni oikein!

Luulen ymmärtäväni periaatteet, joilla hyppypisteitä luodaan, mutta minun on vielä vähän hankala hahmottaa, mistä pitäisi lähteä liikkeelle. Mitä kekoon lisätään ensimmäisenä? Pitääkö lisätä alkupisteen kaikki kahdeksan naapuria ja aloittaa skannaus jokaisesta vuorollaan? Pohdiskelen myös suuntien hallintaa. Tällä hetkellä minulla on oma Suunta-luokka, joka kertoo, mihin suuntaan ollaan menossa x- ja y-akseleilla, mutta en vielä ole varma, miten pystyn kuljettamaan suuntaa mukana, jos se ei liity kiinteästi mihinkään tiettyyn solmuun.

Selvitettävää siis edelleen riittää, mutten vielä osaa muotoilla mitään kovin täsmällistä kysymystä.

## Ensi viikon suunnitelma

## Mitä opin tällä viikolla

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
| -----:|:---:| :-----|
| 7.4. |  2  | Ensimmäinen versio A*-algoritmista |
| 8.4. | 2.5 | A*-algoritmin ensimmäiset testit, käyttöliittymän jalostamista, tietorakenteiden suorituskykyvertailua |
| 13.4.|  3  | Tarkempi perehtyminen Jump Point Start -algoritmiin |
| 14.4.|  3  | Perehtyminen JPS-algoritmiin jatkuu, AStar- ja Dijkstra-luokkien yhdistäminen (koska koodi on 99-prosenttisesti sama)
| 15.4.| 1.5 | Ensimmäisen JPS-algoritmin osan lisääminen ohjelmaan ja sille pari yksinkertaista testiä, JavaDocin päivittäminen |
|       |     | |
|**YHT**|**xx**| |
