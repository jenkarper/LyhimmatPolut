# Reitinhakualgoritmien vertailu

![GitHub Actions](https://github.com/jenkarper/LyhimmatPolut/workflows/Java%20CI%20with%20Maven/badge.svg)

Aineopintojen harjoitustyö: Tietorakenteet ja algoritmit, kevät 2021

Tämä projekti on toteutettu Helsingin yliopiston tietojenkäsittelytieteen oppiaineen kurssille Aineopintojen harjoitustyö: Tietorakenteet ja algoritmit.

## Dokumentaatio

[Määrittelydokumentti](https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/maarittelydokumentti.md)

## Viikkoraportit

- [Viikko 1](https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/viikkoraportit/viikko_1.md)

## Komentorivikomennot

Sovelluksen käynnistys:

`mvn compile exec:java -Dexec.mainClass=lyhimmatpolut.Main`

Testien ajaminen ja testikattavuusraportin luominen:

`mvn test jacoco:report`

Testikattavuusraportin avaaminen:

`chromium-browser target/site/jacoco/index.html`

Checkstyle-tarkistuksen ajaminen ja checkstyle-raportin luominen:

`mvn jxr:jxr checkstyle:checkstyle`

Checkstyle-raportin avaaminen:

`chromium-browser target/site/checkstyle.html`

Suoritettavan jar-tiedoston paketointi:

`mvn package`

Jar-tiedosto muodostuu projektin juuressa olevaan target-hakemistoon nimellä _LyhimmatPolut-1.0-SNAPSHOT.jar_. Jar-tiedoston voi tallentaa haluamaansa sijaintiin ja ajaa komennolla `java -jar LyhimmatPolut-1.0-SNAPSHOT.jar`. Tämä kuitenkin edellyttää, että aineistona käytettävät kartat sijaitsevat samassa hakemistossa jar-tiedoston kanssa kansiossa _kartat_. Myös komento tulee ajaa samassa hakemistossa.
