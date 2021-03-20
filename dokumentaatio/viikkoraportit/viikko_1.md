# Viikko 1

## Työn edistyminen

### Lähdemateriaalin hankinta ja taustatyö

Ennen kurssin alkua pyysin ohjaajalta apua aihevalintaan. Ensimmäinen ajatukseni jossakin tekstimassassa esiintyvien sanojen frekvenssien tarkastelusta osoittautui algoritmisesti liian yksinkertaiseksi, joten päädyin lopulta valitsemaan reitinhakualgoritmien vertailun projektin aiheeksi. Ohjauksessa sain myös apua työssä tarvittavan kartta-aineiston hankintaan ja vinkkejä Dijkstran algoritmin soveltamisessa 2d-taulukkoon.

Perustin projektille etärepositorion GitHubiin, loin alustavan projektirakenteen ja tein myös alustavat Checkstyle-konfiguraatiot. Kertasin muutamista lähteistä Dijkstran algoritmin toiminnan ja tutustuin sen paranneltuun versioon A*-algoritmiin. Vertailuun valitsin kurssimateriaalissakin mainitun Jump Point Search -algoritmin, johon tutustuin muutaman lähteen avulla.

### Koodaus

En kirjoittanut itse työhön varsinaista koodia vielä tällä viikolla, mutta tein joitakin hiekkalaatikkokokeiluja esimerkiksi karttatiedoston lukemisesta ja kartan piirtmäisestä graafiseen käyttöliittymään. Olin alun perin ajatellut tekeväni työhön tekstikäyttöliittymän, mutta tulin siihen tulokseen, että algoritmien hakutulosten visualisointi olisi parempi tehdä graafisessa käyttöliittymässä.

### Pulmatilanteita

En juurikaan ole käyttänyt JavaFX-kirjaston Canvas-olioita Ohjelmoinnin jatkokurssin jälkeen, ja niiden ominaisuuksien hyödyntäminen vaatii opettelua. Kokeiluissani piirsin kartan siten, että 2d-taulukoksi muunnetun kartan yhtä solua vastaa yksi pikseli Canvas-oliossa, jolloin kartta-aineiston pienimmät kartat ovat ruudulla melko pieniä. En ole vielä keksinyt, miten saisin visualisoitua erikokoiset kartat ruudulle samankokoisina.

## Ensi viikon suunnitelma

Kartan visualisointi toimii nyt riittävän hyvin, jotta voin aloittaa ensimmäisen algoritmin kirjoittamisen. Ajattelin edetä kurssimateriaalin vinkin mukaan niin, että käytän alussa Javan valmiita tietorakenteita (PriorityQueue ja ArrayList) ja yritän ensin saada algoritmit toimimaan niiden avulla. Aloitan Tira-kurssilta tutulla Dijkstralla. Seuraavaa palautusta varten tarkoitukseni on siis saada aikaan toimiva Dijkstran algoritmi sekä sen löytämän polun piirtäminen kartalle. Tarkoitukseni on myös aloittaa testaaminen sitä mukaa kuin koodia syntyy. Testien kirjoittaminen tulee luultavasti viemään jonkin verran aikaa, koska otaksun, että yksikkötekstitkään eivät ole niin suoraviivaisesti luotavissa kuin viime syksynä tekemässäni Ohjelmistotekniikan harjoitustyössä. Pyrin kuitenkin noudattamaan kurseilla vahvasti suositeltua käytäntöä, että testaus olisi koko ajan ajantasalla.

## Mitä opin tällä viikolla

En vielä ole varsinaisesti tehnyt mitään uutta, vaan kaikki on ollut vanhan kertausta. Olen kuitenkin oppinut Jump Point Search -agoritmin toiminnan teoriaa, ja minulla on jonkinlainen käsitys siitä, miten sitä voisi lähteä toteuttamaan.

## Tuntikirjanpito

| PVM | Tunnit | Tehtävät |
| -----:|:---:| :-----|
| 15.3. |  1  | Aiheen valinta ohjaajaan avustuksella |
| 18.3. |  3  | Hiekkalaatikkokokeiluja (tiedostonluku, kartan piirtäminen) |
| 19.3. |  3  | Lähdemateriaalin etsiminen, määrittelydokumentin kirjoittaminen |
| 20.2. |  1  | Viikkoraportti ja pakettien JavaDoc-kuvaukset |
|       |     | |
|**YHT**|**8**| |
