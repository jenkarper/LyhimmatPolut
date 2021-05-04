# Käyttöohje

Kloonaa repositorio koneellesi. Sen jälkeen voit suorittaa sen komentoriviltä navigoimalla projektin juurihakemistoon (samaan, jossa pom.xml-tiedosto sijaitsee) ja ajamalla komennon `mvn compile exec:java -Dexec.mainClass=main.Main`. Voit myös avata ohjelman NetBeansissa.

Vaihtoehtoisesti voit ladata suoritettavan jar-tiedoston [täältä](https://github.com/jenkarper/LyhimmatPolut/releases/tag/v1.0). Ohjelman voi sen jälkeen käynnistää komentoriviltä navigoimalla siihen hakemistoon, jossa jar sijaitsee, ja ajamalla komennon `java -jar LyhimmatPolut.jar`.

Sovellus aukeaa ikkunaan, jossa näkyy oletusvalintana Berliinin historiallista keskustaa esittävä kartta sekä toimintavalikko.

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/kayttoliittyma.png" width="1000">

**1. Valitse laskennassa käytettävä algoritmi**

Oletusarvoisesti valittuna on Dijkstra. Voit vaihtaa algoritmia klikkaamalla vaihtoehtoa.

**2. Jos et halua käyttää oletuskartaa, valitse uusi kartta valikosta:**

Valittavana on muutamia erilaisia karttoja.

**3. Valitse päätepisteet klikkaamalla haluamaasi kohtaa kartalla.**

Voit valita mitkä tahansa pisteet, jotka sijaitsevat kartan valkoisella alueella. Ohjelma ilmoittaa, jos yrität valita ei-kelvollista pistettä. Varoitusviesti ilmestyy myös, jos yrität käynnistää laskennan ennen kuin molemmat päätepisteet on valittu, tai jos yrität valita enemmän kuin kaksi pistettä. Valitsemiesi pisteiden koordinaatit ilmestyvät näkyviin otsikon 3 alle. 

**4. Käynnistä reitin laskenta:**

Klikkaa Laske!-nappia käynnistääksesi reitin laskennan. Ohjelma piirtää löytyneen reitin kartalle fuksianvärisenä viivana, ja laskennassa tutkitut ruudut väritetään haalealla ruusunpunaisella. Kun Jump Point Search on toteutettu, ohjelma piirtää näkyviin myös haun aikana tunnistetut hyppypisteet.

**5. Tulokset:**

Jos annettujen pisteiden välille löytyy reitti, ohjelma tulostaa laskennan tiedot otsikon 5 alle: reitin pituuden, laskentaan käytetyn ajan millisekunteina sekä tutkittujen ruutujen osuuden kaikista vapaista ruuduista. Jos reittiä ei voida muodostaa, ohjelma ilmoittaa sen samassa kohdassa.

**Käynnistä suorituskykytestit**

Numeroidun valikon alapuolella on vielä suorituskykytestaukset käynnistävä nappi. Tehtävät suorituskykytestit on kovakoodattu ohjelmakoodiin, ja testien tulokset tulostuvat ajoympäristöstä riippuen komentoriville tai NetBeansin Output-ikkunaan. Voit säätää testejä muuttamalla valittua karttaa, arvottavien reittien määrää ja laskettavan polun pituushaarukkaa luokassa [GUI](https://github.com/jenkarper/LyhimmatPolut/blob/15adfee4f1bf974194a3219d78ba56fdbe79a971/lyhimmatpolut/src/main/java/ui/GUI.java#L189).
