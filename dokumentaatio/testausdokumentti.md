# Testaus

## Yksikkö- ja integraatiotestaus

Automatisoidut testit on rakennettu [JUnit](https://junit.org/junit4/)-testauskehyksen avulla (versio 4.12). Yksikkötestaus on kasvanut ohjelmakoodin mukana, ja pyrin pitämään sen jatkuvasti yli 90 prosentissa. Konfiguroin GitHub Actionsin suorittamaan yksikkötestit automaattisesti aina, kun tein etärepositorioon muutoksia joko suoraan päähaaraan puskemalla tai pull requestin kautta. Regressiotestauksen avulla pyrin varmistamaan, etteivät muutokset riko mitään vanhaa toiminnallisuutta.

Yksikkö- ja integraatiotestaus kattaa pakkaukset [algoritmit](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/algoritmit), [domain](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/domain), [dao](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/dao) ja [tietorakenteet](https://github.com/jenkarper/LyhimmatPolut/tree/main/lyhimmatpolut/src/test/java/tietorakenteet). Näiden pakkausten luokat ja metodit testataan joko omilla yksikkötesteillä tai osana integraatiotestausta.

### Algoritmien oikeellisuustestaus

Algoritmeista Dijkstran oikeellisuutta valvoo testi, joka vertaa algoritmin löytämien polkujen pituuksia kartta-aineiston tarjoamiin skenaarioihin. Toisten algoritmien oikeellisuutta puolestaan testataan vertaamalla niitä Dijkstraan. Suorituskykytestaus on toteutettu ohjelmallisesti, ja siitä kerrotaan tarkemmin [toteutusdokumentissa](toteutusdokumentti.md).

A*:n ja Jump Point Searchin automaattiset testit käyttävät samaa Testaaja-luokkaa, jolla suorituskykytestit rakennetaan. Testaaja arpoo luetusta skenaariotiedostosta rajallisen määrän reittikuvauksia (alku- ja loppupisteen koordinaatit sekä optimaalisen polunpituuden) ja etsii sitten reitin ensin Dijkstran algoritmia käyttäen, sitten A*- tai JPS-algoritmia käyttäen. Testaaja muodostaa testituloksen ja tarkistaa samassa yhteydessä, täsmäävätkö löydettyjen polkujen pituudet keskenään. Koska Dijkstra ja A* ovat yhden metodikutsun palauttamaa arvoa lukuunottamatta identtiset, tämä testi on mielenkiintoinen lähinnä siinä tapauksessa, että A*:lla olisi käytössään useampia heuristiikkoja. Enemmän merkitystä on JPS:n oikeellisuutta testaavalla testillä, koska JPS poikkeaa selvästi Dijkstrasta. Liukuluvuilla laskemisesta aiheutuvien pienten tarkkuuserojen vuoksi testeissä ei vaadita täydellistä vastaavuutta, vaan vaadittu tarkkuus on määritelty erikseen.

### Testikattavuus

Ajantasaisen testikattavuusraportin voi generoida Javan testikattavuuskirjaston [JaCoCon](https://www.eclemma.org/jacoco/) avulla, ja projektin README-tiedostossa olevat CI-palvelimen ja Codecov-palvelun badget kertovat, läpäiseekö repositoriossa oleva koodi automatisoidut testit ja mikä on senhetkinen testikattavuus.

Testikattavuuden ulkopuolelle on rajattu pakkaukset ui ja suorituskykytestaus. Niiden oikeellisuutta ei siis testata automaattisesti, vaan pelkästään järjestelmätestauksessa. Käyttöliittymän rakentavassa koodissa käytetään lähinnä JavaFX-kirjaston komponentteja, joiden oikeellisuutta sinänsä ei ole tarvetta testata. Suorituskykytestauksen koodi ei ole toiminnaltaan kovin monimutkaista, ja algoritmien oikeellisuustesteissä osa näiden luokkien metodeista tulee myös integraatiotestauksen piiriin.

Testikattavuus koko projektin tasolla (29.4.2021):

<img src="https://github.com/jenkarper/LyhimmatPolut/blob/main/dokumentaatio/kuvat/testikattavuus_2904.png" width="1000">

## Järjestelmätestaus

Järjestelmätestausta on toteutettu manuaalisesti kehitystyön edetessä. Olen käyttänyt kehitystyössä NetBeans-ympäristöä ja ajanut ohjelmaa enimmäkseen siellä, mutta olen pyrkinyt aina isojen muutosten jälkeen generoimaan myös jar-tiedoston ja suorittamaan sen. Olen myös testannut ohjelmaa laitoksen koneella etätyöpöydän avulla.
