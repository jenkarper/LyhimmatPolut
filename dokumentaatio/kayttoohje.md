# Käyttöohje

Kloonaa repositorio koneellesi. Sen jälkeen voit suorittaa sen komentoriviltä navigoimalla projektin juurihakemistoon (samaan, jossa pom.xml-tiedosto sijaitsee) ja ajamalla komennon `mvn compile exec:java -Dexec.mainClass=main.Main`.

Sovellus aukeaa ikkunaan, jossa näkyy oletusvalintana Berliinin historiallista keskustaa esittävä kartta sekä toimintavalikko.

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/kayttoliittyma.png" width="1000">

**1. Valitse laskennassa käytettävä algoritmi**

Oletusarvoisesti valittuna on Dijkstra. Voit vaihtaa algoritmia klikkaamalla vaihtoehtoa. Huom! Jump Point Search ei tällä hetkellä etsi lainkaan polkua, koska koko algoritmia ei vielä ole toteutettu! Jos valitset Jump Point Searchin laskentaan, karttaan piirtyy polun sijasta valitusta alkupisteestä lähtevän haun löytämät hyppypisteet ([kts. kommentti](https://github.com/jenkarper/LyhimmatPolut/blob/cf9f119b70eb1adabefd04b0f22e1be8d7124383/lyhimmatpolut/src/main/java/algoritmit/JPS.java#L41)).

**2. Jos et halua käyttää oletuskartaa, valitse uusi kartta valikosta:**

Valittavana on muutamia erilaisia karttoja.

**3. Valitse päätepisteet klikkaamalla haluamaasi kohtaa kartalla.**

Voit valita mitkä tahansa pisteet, jotka sijaitsevat kartan valkoisella alueella. Ohjelma ilmoittaa, jos yrität valita ei-kelvollista pistettä. Varoitusviesti ilmestyy myös, jos yrität käynnistää laskennan ennen kuin molemmat päätepisteet on valittu, tai jos yrität valita enemmän kuin kaksi pistettä. Valitsemiesi pisteiden koordinaatit ilmestyvät näkyviin otsikon 3 alle. 

**4. Käynnistä reitin laskenta:**

Klikkaa Laske!-nappia käynnistääksesi reitin laskennan. Ohjelma piirtää löytyneen reitin kartalle fuksianvärisenä viivana, ja laskennassa tutkitut ruudut väritetään haalealla ruusunpunaisella. Kun Jump Point Search on toteutettu, ohjelma piirtää näkyviin myös haun aikana tunnistetut hyppypisteet.

**5. Tulokset:**

Jos annettujen pisteiden välille löytyy reitti, ohjelma tulostaa laskennan tiedot otsikon 5 alle: reitin pituuden, laskentaan käytetyn ajan millisekunteina sekä tutkittujen ruutujen osuuden kaikista vapaista ruuduista. Jos reittiä ei voida muodostaa, ohjelma ilmoittaa sen samassa kohdassa.

**Käynnistä suorituskykytestit**

Numeroidun valikon alapuolella on vielä suorituskykytestaukset käynnistävä nappi. Toistaiseksi tehtävät suorituskykytestit on kovakoodattu ohjelmakoodiin, ja testien tulokset tulostuvat ajoympäristöstä riippuen komentoriville tai NetBeansin Output-ikkunaan. Myös Jump Point Search on mukana suorituskykytestissä, mutta se ei toistaiseksi löydä mitään polkua.
