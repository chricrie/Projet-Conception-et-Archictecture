# Projet-Conception-et-Archictecture
Projet de session en groupe : 420-A13-BB, Hiver 2022

Membres : Mélanie Allard
          Gabrielle Gallagher 
          Jean-Claude Lahaie 
          Christian Sayegh
          
Sujet:
Vous devez déterminer l’architecture et faire un prototype fonctionnel pour un logiciel de gestion de billets de service (un peu comme Bugzilla, Mantis ou Jira).
Concentrez-vous sur la logique d’affaires, l’interface usager et le support de persistance seront déterminés dans une phase ultérieure du projet.
Le travail est à faire en équipes de quatre personnes (maximum).

Fonctions principales
•	Créer un compte usager technique  
•	Créer un projet
•	Assigner les usagers techniques aux projets
•	Définir les catégories
•	Créer un billet (e-mail du demandeur, date, notes, projet, gravité faible/moyenne/élevée)
•	Assigner un billet (date, personne en charge, description du problème)
          [link](Diagrammes de conception/Diagrammes de communication/Assigner_Billet_UC.drawio)
•	Changer l’état d’un billet (Ouvert, Travail en cours, Bloqué, En attente de déploiement, Fermé). On note le changement d’état à l’historique du billet : date, usager, commentaire).
•	Consulter la liste des billets (Filtres par date d’ouverture, demandeur, état, personne en charge, projet, catégorie, gravité)
•	Consulter le détail d’un billet (par id)

Implantation du composant application
•	Implantation en Java, incluant tout ce qui est nécessaire pour assembler et faire fonctionner le logiciel.
•	Vous devez démontrer le fonctionnement du composant. Il est conseillé de simuler les appels à ce composant à l’aide de tests automatisés (tests d’API)
•	Il est conseillé de simuler les composants utilisés par le composant application à l’aide d’objets retournant des valeurs prédéterminées (vous pouvez employer des mock objects à l’aide d’une bibliothèque comme Mockito si vous le désirez)
