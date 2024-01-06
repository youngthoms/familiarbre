# familiarbre

J'aime pas les arbres

## Fonctionalités
### Annuaire des Utilisateurs
Accès ouvert à tous, avec une authentification requise pour d'autres fonctionnalités.
A faire :
- Front
  - Faire la page d'annuaire (liste des utilisateurs)
- Backend
  - Mettre en place l'API pour les utilisateurs

### Inscription et Authentification
Demande d'adhésion en ligne.
- Front
  - Formulaire d'inscription
  - Formulaire pour renseigner plus d'informations
    -  Champs [Obligatoire(Numéro de sécu, nom, prénom, date de naissance), Optionnel(Numéro de sécu des parents)]
- Backend
  - Renforcer les vérifications sur les champs

Fait :
- Fonction d'inscription avec Token

### Manipulation Intra-arbre Généalogique
Ajout de personnes, consultation, formulation de requêtes, suppression et modification de nœuds.
L'ajout se fait par recherche de nom, numéro de sécu, par rapport à un noeud qui doit accepter ou non le nouveau parent si le noeud est un utilisateur de la plateforme sinon ajouter.

A faire :
- Front
  - Utiliser familly tree js
- Backend
  - Faire attention au format utiliser par familly  tree js   
  - Fonction d'ajout de père, mère, fils et fille et époux
  - Ajouter au modèle des époux/épouseses

### Manipulation Inter-arbres Généalogiques
Niveaux de visibilité pour les arbres, visualisation et interrogation d'arbres d'autres utilisateurs, recherche de membres communs.

A faire :
- Front
- Backend
  - Accès à la ressource, vérification de l'utilisateur
    - Privé : vérification utilisateur = arbre.utilisateur
    - Publique : OK
    - Protégé : OK si est dans l'arbre
  - Construction (relative à l'utilisateur qui consulte et l'utilisateur) :
      - Privé : Rien si cond est faux sinon arbre
      - Publique : seulement les noeuds publiques et ceux protégés dont l'utilisateur fait parti de l'arbre
      - Protégé : tous les noeuds publiques et ceux protégés dont l'utilisateur fait parti de l'arbre

  La famille : tous les liens directs descendants et tous les liens ascendants (direct ou pas)

### Partage de Souvenirs ou Nouvelles de Famille (moyen)
Partage de ressources avec les membres de la famille uniquement.
### Statistiques des Consultations
Suivi des consultations de l'arbre par d'autres utilisateurs.

## TODO FOR NICO
  - avoir le nom d'utilisateur sur le side menu --> supprimer les tous les elements inutiles du menu + afficher en haut du menu le user connecté (PersonalTreeComponent)
  - fix le css sur le menu (bord blanc autour qui faudrait enlever)
  - page d'accueil à pimp : stylisé les boutons "s'inscrire" et "se connecter" le mettre au milieu de la page + centrer les deux fields pour la connexion (LoginForm)
