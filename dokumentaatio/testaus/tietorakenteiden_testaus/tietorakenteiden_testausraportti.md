# Toteutettujen tietorakenteiden suorituskykytestaus

Reitinhakualgoritmit tarvitsevat solmujen käsittelyjärjestyksen määrittelevää prioriteettijonoa. Lisäksi polun muodostavien solmujen ja tutkittavan solmun naapureiden säilömiseen tarvitaan yksinkertaista listarakennetta.

Prioriteettijonon toteuttaa ohjelmassa luokka Keko, joka rakentaa minimikeon. Listan toteuttaa luokka Lista, joka tarjoaa lisäystoiminnallisuuden ohella alkion haun tietystä indeksistä sekä listan kokoon liittyviä toiminnallisuuksia. Alkion poistoa ei ole toteutettu, koska se on ohjelmassa tarpeeton.

## Keko-luokan testaus

Keko-luokan suorituskykyä testattiin vertaamalla sitä Javan valmiiseen PriorityQueue-rakenteeseen.

## Lista-luokan testaus

Lista-luokan suorituskykyä testattiin vertaamalla sitä Javan valmiiseen ArrayList-rakenteeseen.
