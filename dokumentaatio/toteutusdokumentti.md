# Toteutusdokumentti

## Ohjelman yleisrakenne

Ohjelman pakkausrakenne on seuraava:

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/pakkausrakenne.png">

Sovelluslogiikka on jaettu kolmeen pakkaukseen: algoritmit ja tietorakenteet ovat omissa pakkauksissaan, ja muu sovelluslogiikan toteuttava koodi on pakkauksessa domain. Ulkoisen tiedon käsittelyn hoitavat luokat ovat pakkauksessa dao, ja käyttöliittymän rakentava koodi sijaitsee pakkauksessa ui. Pääohjelmaluokka on omassa pakkauksessaan main, ja sen tarkoitus on välttää JavaFX:n käytöstä aiheutuva ongelma jar-tiedoston paketoinnissa. Pääohjelman ainoa tehtävä on kutsua GUI-luokan metodia *main*.

## Saavutetut aika- ja tilavuusvaativuudet

## Suorituskyky- ja O-analyysivertailu

## Työhön jääneet puutteet ja parannusehdotukset

### Lähteet
