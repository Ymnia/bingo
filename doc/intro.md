# Introduction to Clojure-webapp

Spelverloop en onderdelen
Getrokken getal		Box met random nummer, niet herhalend tussen 0 en 100*
Kaart				Grid met 25 getallen random tussen 0 en 100* op volgorde (L-R, B-N)
	Spel begint met alle getallen ingevuld
		Als getal op bord staat moet de speler die aanklikken
		Als niet, klikt de speler op het getrokken getal en nieuw getal word getrokken
	Als er een rij, kolom of diagonaal vol is heeft de speler gewonnen
	Als een rij, kolom of diagonaal gevuld zou zijn maar speler is vergeten aan te clicken heeft speler verloren.

 _ _ _ _ _ 
|_ _ _ _ _ |

 _ _ _ _ _ 
|_|_|_|_|_|
|_|_|_|_|_|
|_|_|_|_|_|
|_|_|_|_|_|
|_|_|_|_|_|

Testbaarheid
	Test voor controle of volle rijen, kolommen en diagonalen gevonden worden
	De kaart geen dubbele getallen bevat
	Test of na 99 gegeven getallen geen dubbel is getrokken
	Test of als een speler niet speelt, de speler verliest
