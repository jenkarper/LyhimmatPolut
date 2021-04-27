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

## Suorituskyky- ja O-analyysivertailu

## Työhön jääneet puutteet ja parannusehdotukset

### Lähteet
