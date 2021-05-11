# Testaus

## Yksikkö- ja integraatiotestaus

Automatisoidut testit on rakennettu [JUnit](https://junit.org/junit4/)-testauskehyksen avulla (versio 4.12). Yksikkötestaus on kasvanut ohjelmakoodin mukana, ja pyrin pitämään sen jatkuvasti yli 90 prosentissa. Konfiguroin GitHub Actionsin suorittamaan yksikkötestit automaattisesti aina, kun tein etärepositorioon muutoksia joko suoraan päähaaraan puskemalla tai pull requestin kautta. Regressiotestauksen avulla pyrin varmistamaan, etteivät muutokset riko mitään vanhaa toiminnallisuutta.

Yksikkö- ja integraatiotestaus kattaa pakkaukset [algoritmit](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/algoritmit), [domain](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/domain), [dao](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/dao) ja [tietorakenteet](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/tietorakenteet). Näiden pakkausten luokat ja metodit testataan joko omilla yksikkötesteillä tai osana integraatiotestausta.

### Algoritmien oikeellisuustestaus

Algoritmeista Dijkstran oikeellisuutta valvoo testi, joka vertaa algoritmin löytämien polkujen pituuksia kartta-aineiston tarjoamiin skenaarioihin. Toisten algoritmien oikeellisuutta puolestaan testataan vertaamalla niitä Dijkstraan.

A*:n ja Jump Point Searchin automaattiset testit käyttävät samaa Testaaja-luokkaa, jolla suorituskykytestit rakennetaan. Testaaja arpoo luetusta skenaariotiedostosta rajallisen määrän reittikuvauksia (alku- ja loppupisteen koordinaatit sekä optimaalisen polunpituuden) ja etsii sitten reitin ensin Dijkstran algoritmia käyttäen, sitten A*- tai JPS-algoritmia käyttäen. Testaaja muodostaa testituloksen ja tarkistaa samassa yhteydessä, täsmäävätkö löydettyjen polkujen pituudet keskenään. Koska Dijkstra ja A* ovat yhden metodikutsun palauttamaa arvoa lukuunottamatta identtiset, tämä testi on mielenkiintoinen lähinnä siinä tapauksessa, että A*:lla olisi käytössään useampia heuristiikkoja. Enemmän merkitystä on JPS:n oikeellisuutta testaavalla testillä, koska JPS poikkeaa selvästi Dijkstrasta. Liukuluvuilla laskemisesta aiheutuvien pienten tarkkuuserojen vuoksi testeissä ei vaadita täydellistä vastaavuutta, vaan vaadittu tarkkuus on määritelty erikseen.

### Testikattavuus

Ajantasaisen testikattavuusraportin voi generoida Javan testikattavuuskirjaston [JaCoCon](https://www.eclemma.org/jacoco/) avulla, ja projektin README-tiedostossa olevat CI-palvelimen ja Codecov-palvelun badget kertovat, läpäiseekö repositoriossa oleva koodi automatisoidut testit ja mikä on senhetkinen testikattavuus.

Testikattavuuden ulkopuolelle on rajattu pakkaukset ui ja suorituskykytestaus. Niiden oikeellisuutta ei siis testata automaattisesti, vaan pelkästään järjestelmätestauksessa. Käyttöliittymän rakentavassa koodissa käytetään lähinnä JavaFX-kirjaston komponentteja, joiden oikeellisuutta sinänsä ei ole tarvetta testata. Suorituskykytestauksen koodi ei ole toiminnaltaan kovin monimutkaista, ja algoritmien oikeellisuustesteissä osa näiden luokkien metodeista tulee myös integraatiotestauksen piiriin.

Testikattavuus koko projektin tasolla (4.5.2021):

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/testikattavuus_0405.png" width="1000">

## Järjestelmätestaus

Järjestelmätestausta on toteutettu manuaalisesti kehitystyön edetessä. Olen käyttänyt kehitystyössä NetBeans-ympäristöä ja ajanut ohjelmaa enimmäkseen siellä, mutta olen pyrkinyt aina isojen muutosten jälkeen generoimaan myös jar-tiedoston ja suorittamaan sen. Olen myös testannut ohjelmaa laitoksen koneella etätyöpöydän avulla.

## Suorituskykyvertailu

Suorituskykytestaus toteutettiin kolmella kartalla:

Berliini: <img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/berliini.png" width="250"> Boston: <img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/boston.png" width="250"> Pariisi: <img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/pariisi.png" width="250">

Testaus käynnistetään käyttöliittymästä, ja testien tulokset tulostuvat suoritusympäristöstä riippuen konsoliin tai NetBeansin Output-ikkunaan. Käyttöliittymän testien käynnistysnapin painaminen kutsuu Testaaja-luokan [metodia](https://github.com/jenkarper/LyhimmatPolut/blob/940301348ff49741c5d55e050238aceb418587aa/lyhimmatpolut/src/main/java/suorituskykytestaus/Testaaja.java#L37), joka arpoo laskettavat reiti, laskee jokaista reittiä kohden lyhimmäin polun kullakin kolmesta algoritmista ja laskee lopulta algoritmien suoritusten keskiarvoja. Testausta varten valitaan käytettävä kartta, arvottavien reittein enimmäis- ja vähimmäispituus sekä laskettavien reittien lukumäärä.

### Huomioita testausaineistosta

Pyrin valitsemaan keskenään mahdollisimman erilaisia karttoja, mutta käyttämässäni aineistossa ei ole järin suurta variaatiota. Solmujen ja kaarien lukumäärä on kaikissa kartoissa samaa luokkaa, ja kaarten paino on joko 1 (suora siirtymä) tai sqrt(2) (vino siirtymä). Testeissä käytettävien alku- ja loppusolmujen koordinaattien lukeminen karttaa vastaavasta skenaariotiedostosta varmistaa, että testeissä haetaan vain reittejä, jotka todella ovat olemassa. Jaoin testin jokaisella kartalla kolmeen osaan haettavan polun pituuden mukaan. Arvoin skenaariotiedostosta lyhyitä (pituus 450-550), keskipitkiä (pituus 950-1050) ja pitkiä (pituus 1450-1550) reittejä, aina tuhat reittiä testiajoa kohti. Koska haluttuun pituushaarukkaan ei kuuluu tuhatta reittiä missään kartassa, osa reiteistä tuli laskettua useampaan kertaan.

Vertasin kaikkia kolmea algoritmia laskenta-ajan perusteella ja Dijkstraa ja A*:ia lisäksi tutkittujen solmujen perusteella. Eri kartoista saatavat tutkittujen solmujen prosenttiosuudet eivät ole keskenään verrannollisia, koska osuus on laskettu kaikista niistä kartan ruuduista, joissa ei ole estettä. Berliini-kartassa kuitenkin osa tällaisista vapaaksi tulkituista ruuduista on esteen ympätöimiä, joten niihin ei ole edes mahdollista edetä, jos päätepisteet sijaitsevat niiden ulkopuolella. Yhden kartan reittejä laskettaessa saadut prosenttiosuudet antavat kuitenkin kuvaa Dijkstran ja A*:in tilan käytön eroista.

### Suorituskykyvertailun tulokset

Keskiarvot algoritmien käyttämästä laskenta-ajasta ja tutkituista solmuista on koottu pylväskaavioihin. Kaaviossa "Lyhyt" vastaa reitin pituutta välillä 450-550, "Keskipitkä" välillä 950-1050 ja "Pitkä" välillä 1450-1550.

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/kaavio_laskenta-aika.png">

Algoritmien keskinäinen järjestys laskentanopeudessa oli odotettu: Dijkstra oli hitain, sitten A*, ja Jump Point Search oli selvästi nopein. Eri karttojen välillä ei ollut dramaattista eroa. Dijkstran laskenta-ajassa lyhyen ja keskipitkän reitin välillä oli paljon selvempi ero kuin keskipitkän ja pitkän, kun taas kahdessa muussa algoritmissa selvempi ero oli keskipitkän ja pitkän reitin välillä. Etenkin Dijkstran ja A*:in ero siis tasoittui hieman pidempiin reitteihin mennessä. Jump Point Searchin erot Berliinin kartalla eri pituisten reittien laskennassa olivat paljon pienemmät kuin kahdella muulla kartalla. Odotin ennakkoon, että Jump Point Searchin ero kahteen muuhun algoritmiin olisi suhteessa suurempi Bostonin kartalla, jolla on suhteessa enemmän tyhjiä alueita. Tällaisella kartalla Jump Point Searchin symmetristen polkujen hyödyntäminen tuottaa suurimman edun. Ero onkin jonkin verran suurempi kuin muissa kartoissa, mutta luultavasti selvemmän eron esiintuominen vaatisi selvemmän eron karttaprofiilissa.

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/kaavio_tutkitut-solmut.png">

A*:in etu Dijkstraan nähden visualisoituu hyvin siinä, kuinka suuren osuuden kartan kaikista solmuista algoritmi laskennan aikana tutkii. Lyhyissä ja keskipitkissä poluissa Dijkstra tutkii moninkertaisen määrän solmuja A*:iin verrattuna, ja pisimmissäkin poluissa ero on kaksinkertainen. Bostonin kartalla, jossa vapaata yhtenäistä maastoa on eniten, Dijkstra tutkii pitkissä poluissa lähes kaikki vapaat solmut.
