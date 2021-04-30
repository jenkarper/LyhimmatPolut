# Toteutusdokumentti

## Ohjelman yleisrakenne

Ohjelman pakkausrakenne on seuraava:

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/pakkausrakenne.png">

Sovelluslogiikka on jaettu kolmeen pakkaukseen: algoritmit ja tietorakenteet ovat omissa pakkauksissaan, ja muu sovelluslogiikan toteuttava koodi on pakkauksessa domain. Ulkoisen tiedon käsittelyn hoitavat luokat ovat pakkauksessa dao, ja käyttöliittymän rakentava koodi sijaitsee pakkauksessa ui. Pääohjelmaluokka on omassa pakkauksessaan main, ja sen tarkoitus on välttää JavaFX:n käytöstä aiheutuva ongelma jar-tiedoston paketoinnissa. Pääohjelman ainoa tehtävä on kutsua GUI-luokan metodia *main*.

## Saavutetut aika- ja tilavuusvaativuudet

Dijkstran aikavaativuus on tunnetusti O(n + m log n), jossa *n* on solmujen lukumäärä ja *m* on kaarien lukumäärä. Pahimmassa tapauksessa algoritmi lisää kaikki verkon solmut kekoon ja poistaa ne sieltä, ennen kuin loppusolmu tulee vastaan. A* voi parantaa Dijkstran suoritusta käyttämällä solmujen järjestysperusteena keossa arvoa, jossa siihen asti kuljettuun etäisyyteen lisätään heuristisesti arvioitu etäisyys loppuun. Tässä työssä heuristiikaksi on valittu ns. [euklidinen etäisyys](https://github.com/jenkarper/LyhimmatPolut/blob/24f10c643bed57811af3a50dbd0c2beeebaabb3a/lyhimmatpolut/src/main/java/algoritmit/DijkstraStar.java#L133). A*:n nopeus Dijkstraan nähden perustuu siis siihen, että Dijkstra voi tehdä turhaa työtä esimerkiksi tutkimalla solmuja, jotka ovat kauempana maalista kuin alkusolmu.

### A*:n pseudokoodi

```java
1    laskeReitti(alku, loppu)
2        etäisyys[alku] = 0
3        insert(keko, alku)
4        while NOT isEmpty(keko)
5            seuraava = deleteMin(keko)
6            if seuraava IS loppu
7                return tulos
8            if NOT vierailtu[seuraava]
9                vierailtu[seuraava] = true
10               naapurit = haeNaapurit(seuraava)
11               for naapuri in naapurit
12                   if etäisyys[naapuri] > etäisyys[seuraava] + naapuri.kaari.paino
13                       etäisyys[naapuri] = etäisyys[seuraava] + naapuri.kaari.paino
14                       vertailuarvo = etäisyys[naapuri] + arvioituEtäisyysLoppuun[naapuri]
15                       insert(keko, naapuri, vertailuarvo)
16
17
18   haeNaapurit(solmu)
19       naapurit = new Lista
20       for i = -1 to 1
21           for j = -1 to j
22               // vakioaikaista laskentaa
23       return naapurit
```
Pseudokoodista nähdään, että while-silmukan ympärillä sekä sen sisällä tehdään vain vakioaikaista laskentaa. Naapurit hakevassa metodissa on kaksi sisäkkäistä for-silmukkaa, mutta niiden suorituskertojen määrä on aina vakio. Koska keosta poimitaan seuraavaksi aina solmu, jonka arvioitu etäisyys loppuun on pieni, polun pää tulee nopeammin vastaan.

JPS on tässä työssä toteutettu niin, että se käyttää A*:n tapaan heuristiikkaa solmujen järjestyksen määrittelyyn keossa. Karkealla tasolla algoritmi etenee samoin kuin Dijkstra ja A*: poimitaan keosta lupaavin solmu, haetaan sen naapurit, valitaan seuraava kekoon pantava solmu, päivitetään sen etäisyysarvo ja edeltäjä ja pannaan se lopuksi kekoon. Tätä silmukkaa suoritetaan, kunnes loppusolmu löytyy tai keossa ei enää ole solmuja. JPS ei kuitenkaan pane kaikkia naapurisolmuja kekoon kuten Dijkstra ja A*, vaan tiedustelee ensin, mihin naapurisolmuista on mahdollista päästä, ja tekee päätöksen vasta sitten. Se hyödyntää tietoa, että verkossa voi olla useita symmetrisiä polkuja, eli polkuja, jotka kulkevat lähtösolmusta maalisolmuun eri solmujen kautta mutta jotka tekevät yhtä monta suoraa ja diagonaalisiirtymää. JPS:n toteutus pohjautuu Haraborin ja Grastienin artikkeleihin vuosilta 2011 ja 2012.[1][2]

### JPS:n pseudokoodi

```java
1    laskeReitti(alku, loppu)
2        etäisyys[alku] = 0
3        insert(keko, alku)
4        while NOT isEmpty(keko)
5            seuraava = deleteMin(keko)
6            if seuraava IS loppu
7                return tulos
8            naapurit = haeNaapurit(seuraava)
9            for naapuri in naapurit
10               hyppypiste = hyppää(seuraava, suunta(seuraava -> naapuri))
11               if NOT isNull(hyppypiste)
12                   if etäisyys[hyppypiste] > etäisyys[seuraava] + hypyn pituus AND NOT vierailtu[hyppypiste]
13                       etäisyys[hyppypiste] = etäisyys[seuraava] + hypyn pituus
14                       vierailtu[hyppypiste] = true
15                       edeltäjä[hyppypiste] = seuraava
16                       vertailuarvo = etäisyys[hyppypiste] + arvioituEtäisyysLoppuun[hyppypiste]
17                       insert(keko, hyppypiste, vertailuarvo)
18
19
20   hyppää(solmu, suunta(mistä, mihin))
21       seuraava = astu(solmu, suunta)
22       if NOT sallittu(seuraava)
23           return null
24       if seuraava IS loppu
25           return seuraava
26       if IS pakotettujaNaapureita(seuraava)
27           return seuraava
28       if suunta IS diagonaalinen
29           vaakahyppy = hyppää(seuraava, suunta(seuraava, naapuri oikealla/vasemmalla))
30           if NOT isNull(vaakahyppy)
31               return seuraava
32           pystyhyppy = hyppää(seuraava, suunta(seuraava, naapuri ylhäällä/alhaalla))
33           if NOT isNull(pystyhyppy)
34               return seuraava
35       return hyppää(seuraava, suunta)
```
Pseudokoodissa nähtävä metodi haeNaapurit sisältää Dijsktran ja A*:n tapaan vakioaikaista laskentaa. Laskennan alussa, kun käsiteltävänä on alkusolmu, haetaan kaikki sen vapaat naapurit kahdella sisäkkäisellä for-silmukalla kuten A*:ssa. Myöhemmin naapurit haetaan sen perusteella, mihin suuntaan ollaan etenemässä, ja tällöin naapurihaussa tehdään vain tarkistuksia suunnan ja koordinaattien perusteella. Hyppymetodin kutsun ulkopuolella JPS:n laskeReitti on siis hyvin samankaltainen kuin A*.

Hyppymetodia kutsutaan kaikille keosta poimituille solmuille. Se suorittaa JPS:n tehokkuuden perustana olevan tiedustelun, jonka tarkoitus on selvittää, kannattaako käsittelyssä olevasta solmusta edetä johonkin tiettyyn suuntaan. Hyppymetodi palauttaa joko seuraavan hyppypisteen solmuoliona tai null, jos uutta hyppypistettä ei löydy. JPS:n tuoma tehokkuusetu riippuu pitkälti kartasta, jossa polkuja etsitään. Jos kartassa on verraten paljon vapaita alueita, siinä on myös paljon symmetrisiä polkuja, jolloin algoritmi voi tiedustelun avulla jättää huomiotta suurempia kartan alueita kerrallaan. Jos taas isoja vapaita alueita ei juuri ole, JPS:n tuoma etu heikkenee.

Tässä JPS:n toteutuksessa hyppymetodi palauttaa siis aina täsmälleen yhden hyppypisteen tai ei yhtäkään. Jos hyppy etenee diagonaalisti, tehdään tiedusteluja aina ensin vaaka- ja pystysuuntaan, koska suora siirtymä on halvempi kuin viisto. Jos vaaka- tai pystyhaku tuottaa mahdollisen hyppypisteen (pseudokoodissa rivit 30 ja 33), palautetaan se solmu, josta vaaka- ja pystyhaku tehtiin. Harabor ja Grastien ovat sittemmin julkaisseet myös algoritmin parannellun version, jossa tiedustelun aikana rekursiota ei päätetä heti uuden hyppypisteen löydyttyä, vaan tällaiset diagonaalihypyn sisällä löytyneet vaaka- ja pystyhypyn tuottamat hyppypisteet kerätään listaan, ja kaikki löytyneet hyppypisteet palautetaan kerralla.

## Suorituskyky- ja O-analyysivertailu

*Tulossa!*

## Työhön jääneet puutteet ja parannusehdotukset

Kaiken kaikkiaan olen melko tyytyväinen ohjelman rakenteeseen. Käyttöliittymän rakentava koodi varmasti hyötyisi refaktoroinnista, mutta se on ollut työssä sivuosassa. Jump Point Search -algoritmista olisi ollut kiinnostavaa toteuttaa alkuperäisen version lisäksi tai sijasta saman rekursion aikana useita hyppypisteitä keräävä versio. Suorituskykyvertailun olisi voinut toteuttaa käyttäjän kannalta mielekkäämmin: käyttäjälle voisi antaa mahdollisuuden valita kartan, jolla algoritmeja testataan, ja testitulokset voisi jollakin lailla visualisoida graafiseen käyttöliittymään.

Halusin pitää käyttöliittymäkoodin ja sovelluslogiikkakoodin toisistaan erillään, ja käyttäjä näkeekin algoritmien toiminnasta vain lopputuloksen. Olisi kuitenkin voinut olla mielenkiintoista toteuttaa myös sellainen vaihtoehto, että käyttäjä voi seurata laskennan etenemistä ja polun muodostumista käyttöliittymässä. Tällaisessa tapauksessa algoritmien toimintaa olisi voinut sopivasti hidastaa.

*Jatkuu!*

### Lähteet

[1] D. Harabor & A. Grastien. ["Online Graph Pruning for Pathfinding on Grid Maps"](http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf), 2011. Luettu 27.4.2021.

[2] D. Harabor & A. Grastien. ["The JPS Pathinding System"](https://harabor.net/data/papers/harabor-grastien-socs12.pdf), 2012. Luettu 27.4.2021.

[3] N. Witmer. ["A Visual Explanation of Jump Point Search"](https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html), 2013. Luettu 27.4.2021.
