<p align="center">
  <a href="https://goldstack.party">
    <img src="https://github.com/tandina/P6_MDD/blob/main/front/src/assets/logop6.png" height="128">
    <h1 align="center">MDD Le réseau social des devs pour les devs</h1>
  </a>
</p>


ORION souhaite créer le prochain réseau social dédié aux développeurs : MDD (Monde de Dév). Le but du réseau social MDD est d’aider les développeurs qui cherchent un travail, grâce à la mise en relation, en encourageant les liens et la collaboration entre pairs qui ont des intérêts communs. MDD pourrait devenir un vivier pour le recrutement des profils manquant des entreprises.

Avant de lancer MDD auprès d’un large public, l’entreprise veut le tester avec une version minimale déployée en interne (aussi nommé MVP : Minimum Viable Product). 

Le MVP permettra aux utilisateurs de s’abonner à des sujets liés à la programmation (comme JavaScript, Java, Python, Web3, etc). Son fil d’actualité affichera chronologiquement les articles correspondants. L’utilisateur pourra également écrire des articles et poster des commentaires. 



# Pré-requis

Pour le backend j'utilise Mysql 8.0.34 community edtion, le fichier .sql ainsi que les paramètres de la base de donnée sont disponibles dans le dossier ressources>application.properties. Le backend est à initialiser en premier avant de lancer la partie frontend.
Pour le frontend, j'utilise angular 16.2.0 toutes les dépendances sont disponibles dans le package.json.

BACKEND
une fois les dépendances Maven installer, vous pouvez exécuter la classe P6MvpApiApplication afin que l'api tourne sur le port :8080

FRONTEND
à partir du dossier front end > npm install
ng serve

# Cas d'utilisation
une fois l'application lancer depuis le backend et frontend, sur http://localhost:4200 inscrivez-vous en fournissant un username, email et password.
Connectez-vous sur la page login, dans la page thèmes, vous allez pouvoir vous abonner aux thèmes existants, sur la page articles, écrivez les articles de votre choix selon les thèmes auxquelles vous êtes abonnés, tout user abonnés aux mêmes thèmes pourra consulter les articles et écrire un commentaire.
Sur la page user, vous allez pouvoir vous désabonner, changer l'username ou/et l'email.
