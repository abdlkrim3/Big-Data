<h1> Spark SQL </h1>


<h2> Application 1 : </h2>
On souhaite développer pour une entreprise industrielle une application Spark qui traite les
incidents de chaque service. Les incidents sont stockés dans un fichier csv.
Le format de données dans les fichiers csv et la suivante :
Id, titre, description, service, date

<img src="captures/Capture4.png"></img>

Travail à faire :

1. Afficher le nombre d’incidents par service.

<img src="captures/Capture5.png"></img>

2. Afficher les deux années où il a y avait plus d’incidents.

<img src="captures/Capture6.png"></img>

<h2> Application 2 : </h2>

L’hôpital national souhaite traiter ces données au moyen d’une application Spark d’une
manière parallèle est distribuée. L’hôpital possède des données stockées dans une base de
données relationnel et des fichiers csv. L’objectif est de traiter ces données en utilisant Spark
SQL à travers les APIs DataFrame et Dataset pour extraire des informations utiles afin de
prendre des décisions.

I. Traitement de données stockées dans Mysql
L’hôpital possède une application web pour gérer les consultations de ces patients, les
données sont stockées dans une base de données MYSQL nommée DB_HOPITAL, qui
contient trois tables PATIENTS, MEDECINS et CONSULTATIONS (Voir les figures 1, 2 et 3).

<img src="captures/Capture1.png"></img>
<h3>Figure 1: structure de la table PATIENTS</h3>

<img src="captures/Capture2.png"></img>
<h3>Figure 2: structure de la table MEDECINS</h3>

<img src="captures/Capture3.png"></img>
<h3>Figure 3: La table CONSULTATIONS</h3>

Travail à faire :

Vous créez la base de données et les tables et vous répondez aux questions suivantes :
- Afficher le nombre de consultations par jour.

<img src="captures/Capture7.png"></img>

- Afficher le nombre de consultation par médecin. Le format d’affichage est le suivant :
NOM | PRENOM | NOMBRE DE CONSULTATION

<img src="captures/Capture8.png"></img>

- Afficher pour chaque médecin, le nombre de patients qu’il a assisté.

<img src="captures/Capture9.png"></img>

Afficher les deux tables consultations et médecins:

<img src="captures/Capture9.png"></img>
