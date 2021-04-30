# Viikko 6

## Työn edistyminen

Sain lopulta JPS:n valmiiksi, jei! Hioin jonkin verran käyttöliittymää ja täydensin suorituskykytestausta varten tekemääni ohjelman osaa. Se toimii nyt niin, että testausta varten valitaan kartta, polun pituushaarukka sekä arvottavien reittien lukumäärä. Sitten testaaja arpoo parametrien mukaiset reitit, etsii polut kolmella eri algoritmilla ja muodostaa lopulta yhteenvedon tuloksista, eli laskee kullekin algoritmille keskiarvon laskentaan käytetystä ajasta sekä Dijkstralle ja A*:lle vielä keskiarvon tutkituista ruuduista.

### Pulmatilanteita

A*:n ja JPS:n yksikkötestauksessa käytetään samaa Testaaja-luokkaa kuin suorituskykytesteissä, mutta siinä polun pituutta ei rajoiteta, vaan reittejä arvotaan koko skenaariosta. Se tarkistus kuitenkin tehdään, ettei sama reitti tule listalle kahteen kertaan. Suorituskykytestissä tällaista tarkistusta ei voi tehdä. Jotta saisi jokseenkin luotettavan keskiarvon suoritusajasta, pitää reittejä kuitenkin laskea monta, vaikka tuhat. Lisäksi laskettujen reittien pituuksien pitäisi olla samaa luokkaa keskenään, koska suoritusaikaan tietenkin vaikuttaa reitin pituus. Yhdessä skenaariossa on 3500-4000 reittiä, joten samoja reittejä tulee pakosti laskettua useampaan kertaan. Ihannetapauksessa laskettava reitti olisi varmana eri joka kerran, mutta en keksinyt, miten sen voisi toteuttaa.

## Ensi viikon suunnitelma

Ensi viikon tavoitteena on saada suorituskykytestaus tehtyä ja raportoitua. Yritän saada koko projektin viimeistelyä vaille valmiiksi ensi viikon aikana.

## Mitä opin tällä viikolla

Opin taas uutta liukuluvuilla laskemisesta ja koordinaatistossa navigoinnista. Jump Point Searchiin kuuluvat lukuisat tarkistukset vaativat suurta tarkkuutta, ja kävin niitä läpi aika moneen otteeseen. Opin myös, että jos metodissa on useampia if-else-rakenteita, niitä kannattaa rakentaa ikään kuin tasoittain, jotta jokin if-else-tarkistus ei vahingossa tule väärään tasoon. Checkstyle-määrittelyissä tasojen määrä onkin oletusarvoisesti vain 2, mutta nostin arvon omassa työssäni if-else-tapauksessa kolmeen, koska mielestäni vain kahden tason käyttäminen olisi tehnyt koodista hankalammin luettavaa. Tämä voi olla makukysymys.

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
|----:|:------:|:---------|
| 27.4.|  6  | JPS:n kokoaminen (jollakin tavoin) toimivaksi kokonaisuudeksi, toteutusdokumentin täydennys  |
| 28.4.|  4  | JPS:n parantelu, toteutusdokumentin täydennys |
| 29.4.|  6  | Lopullinen versio JPS:stä, koodin siistiminen, testien viimeistely |
| 30.4.| 2.5 | Suorituskykytestauksen täydentäminen, dokumentaation täydentäminen |
|      |     | |
|**YHT**|**18.5**| |
