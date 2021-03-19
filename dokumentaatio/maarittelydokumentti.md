# Määrittelydokumentti

## Projektin kuvaus

Toteutettava ohjelma vertailee reitinhakualgoritmeja. Ohjelma ottaa syötteenään merkkigrafiikkakartan, lähtöpisteen ja päätepisteen ja etsii sitten lyhimmän mahdollisen reitin pisteiden välillä. Graafinen käyttöliittymä visualisoi sekä kartan että siihen piirtyvän reitin käyttäjän tarkasteltavaksi. Ohjelma tulostaa käyttäjälle myös tiedon haetun reitin pituudesta sekä hakuun kuluneesta ajasta.

Syötteinä käytettävät kartat haetaan [Moving AI Lab](https://movingai.com/benchmarks/street/index.html) -sivuston tarjoamasta vapaasta aineistosta, ja lähtö- ja päätepisteet pyydetään käyttäjältä.

Projekti toteutetaan Helsingin yliopiston kurssille *Tietorakenteet ja algoritmit: aineopintojen harjoitustyö* osana tietojenkäsittelytieteen kandidaatintutkintoa. Käytetty ohjelmointikieli on Java. Koodin nimennässä, JavaDoc-kommenteissa ja dokumentaatiossa käytetään suomea.

## Toteutettavat algoritmit

Ohjelmaan valitut algoritmit ovat leveyshakua (BFS) käyttävä Dijkstran algoritmi, sen paranneltu versio A* sekä Jump Point Search -algoritmi, joka käyttää A*-algoritmia mutta pyrkii nopeuttamaan hakua karsimalla symmetristen polkujen tutkimista.

Dijkstran algoritmi käy verkon solmut läpi leveyshaun mukaisessa järjestyksessä, mutta ahneena algoritmina se valitsee aina tarkasteltavaksi solmun, jonka etäisyys lähtösolmuun on pienin. Algoritmin avulla voidaan löytää lyhin reitti lähtösolmusta minne tahansa toiseen solmuun, mutta kun halutaan päästä johonkin tiettyyn pisteeseen kartalla, Dijkstra saattaa tutkia monia tarpeettomiksi osoittautuvia reittejä.

A* parantaa Dijkstran suoritusta heuristiikan avulla. Siinä missä Dijkstra asettaa seuraavaksi tutkittavia solmuja prioriteettijonoon sen perusteella, millä etäisyydellä ne ovat lähtösolmusta, A* käyttää järjestysperusteena tutkittavaksi tulevan solmun etäisyyttä sekä lähtö- että maalisolmuun. Näin tutkittavaksi tulevat ensin sellaiset solmut, jotka ovat linnuntietä katsottuna lähtösolmun ja maalisolmun välissä, eikä turhaan lähdetä esimerkiksi maalisolmusta poispäin. Solmun etäisyys maalisolmuun voidaan laskea esimerkiksi Manhattan-etäisyytenä, koska tietoa suoralla reitillä mahdollisesti olevista esteistä ei ole.

Jump Point Search on A*-algoritmin muunnelma, joka hyödyntää tietoa siitä, että kartalla voi olla useita, etäisyysarvoltaan verrannollisia reittejä pisteiden välillä. JPS ikään kuin tiedustelee edeltäkäsin, mihin suuntaan kannattaa edetä, ja valitsee tarkasteluun vain lupaavimmat solmut. Sen hyöty nousee esille etenkin kartoilla, joilla on laajoja, avoimia alueita.  

## Toteutettavat tietorakenteet

Sekä Dijkstra että A* käyttävät prioriteettijonoa, joka toteutetaan ohjelmassa minimikekona. Tutkittavien solmuolioiden säilömiseen tarvitaan mahdollisesti myös listaa, joka tarjoaa taulukkoa joustavamman lisäystoiminnallisuuden.

## Tavoitellut aika- ja tilavaativuudet

Kaikilla toteutettavaksi valituilla algoritmeilla on verkon solmujen lukumäärästä *n* riippuva kiinteä tilavaativuus O(n). Tilavaativuuksissa on kuitenkin eroa: Dijkstran algoritmi käy pahimmassa tapauksessa läpi kaikki verkon solmut *n* ja kaaret *m*, lisää solmuja kekoon ja poistaa ne sieltä. Jos keko on toteutettu hyvin, lisäys ja poisto vievät aikaa O(log n). Kokonaisaikavaativuudeksi tulisi tällöin O(n + m log n). A*-algoritmin aikavaativuus riippuu käytetystä heuristiikasta. Tässä ohjelmassa tavoitteena oleva aikavaativuus on luokkaa O(m). JPS-algoritmi voi parantaa A*-algoritmin suoritusta yli kymmenkertaisesti [1], ja se on tavoitteena myös tässä projektissa.

### Lähteet

[1] D. Harabor. ["Shortest Path - Jump Point Search"](https://harablog.wordpress.com/2011/09/07/jump-point-search/), 2011. Luettu 19.3.2021.

[2] N. Witmer. ["A Visual Explanation of Jump Point Search"](https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html), 2013. Luettu 19.3.2021.

[3] A. Patel. ["Introduction to the A* Algorithm"](https://www.redblobgames.com/pathfinding/a-star/introduction.html), 2014. Luettu 19.3.2021.

[4] A. Laaksonen. Tietorakenteet ja algoritmit, 2020. PDF ladattu tammikuussa 2020.
