# lottery

Solution to the Pyxis Lottery test in Clojure.

Problem description (in French):

Une fois par mois a lieu la loterie Pyxis.  Lors de chaque tirage, François sort son vieux boulier contenant 50 boules numérotées de 1 à 50.  Après avoir bien mélangé le boulier, il sort au hasard trois boules gagnantes pour des prix correspondant respectivement à 75%, 15% et 10% du total des prix disponibles.  Ce total des prix disponibles correspond quant à lui à 50% du montant total contenu dans la caisse de la loterie au moment du tirage.

Pour vous donner un exemple, la caisse comprend actuellement 200$.  S'il y avait un tirage maintenant, les prix seraient donc répartis comme suit:

-  75$ pour la première boule :

-  15$ pour la deuxième boule :

-  10$ pour la troisième boule : 

Pour participer au prochain tirage on peut se procurer un ticket en tout temps au coût de 10$.  A chaque tirage on utilise une nouvelle liasse de 50 tickets numérotés de la même manière que les boules.

François désire passer à l'ère moderne et vous demande de faire un programme pour remplacer son vieux boulier et ses tickets. Il vous liste ce que doit permettre le programme:

-  Je veux pouvoir acheter un ticket pour le tirage en fournissant un prénom.  Lors de l'achat, le numéro de la boule est affiché à l'écran.

-  Je veux pouvoir déclencher un tirage

-  Je veux pouvoir afficher les tickets gagnants comme ceci:

1ère boule	 2ème boule	 3ème boule
André : 75$	 Sylvie : 15$	 Dominic : 10$

François se satisfera d'un programme à la console.  Il demande toutefois que les trois fonctionnalités mentionné plus haut puissent être lancées avec les commandes "achat", "tirage" et "gagnants".

Lors du lancement du programme, vous pouvez initialiser l'encaisse à 200$.

Le programme n'a pas à persister d'information sur disque ou en base de données.

Vous pouvez arrondir ou tronquer les montants au dollars près.

## Usage

### Starting the program

      $ lein repl
      "REPL started; server listening on localhost:25948."
      lottery.core=> 

### The "buy-ticket" command

      lottery.core=> (buy-ticket "John")
      24

### The "draw-winners" command

      lottery.core=> (draw-winners)
      {1 {:ticket [15 "Frank"], :amount 105.0}, 2 {:ticket [24 "John"], :amount 21.0}, 3 {:ticket [43 "Mike"], :amount 14.0}}
## Installation

      $ lein deps

## License

Copyright (C) 2011 Enrique Lopez de Lara

Distributed under the Eclipse Public License, the same as Clojure.
