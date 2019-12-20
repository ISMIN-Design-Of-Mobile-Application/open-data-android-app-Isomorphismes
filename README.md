
## PokAppli
  
Application codée avec amour par :   
 - Constant Bounoure   
 - Tuan-Anh Bach  

## Description  

URL des données : https://data.opendatasoft.com/explore/dataset/pokedex_clean%40public/table/ 
URL Serveur PokApi: https://pokapi.now.sh/
Code source serveur: https://github.com/Trukibouge/PokApi
  
Réalisation d'un pokédex sur Android. L'application permettra de parcourir la liste des pokémons du pokédex, d'obtenir des informations sur les pokémons en cliquant dessus, d'aller sur la mapmonde et de chercher les pokémons via leur position. 

La base Open Data contient les informations liées à la localisaton des Pokemons. Un serveur supplémentaire est maintenu afin de fournir des informations sur leurs performances de combat et gérer les inventaires des dresseurs (fonctionalité toujours en alpha-test sur la version Test de l'application client). 

A l'avenir (possiblement après la date de rendue), on pourra également tenir un inventaire pour le dresseur, et capturer des pokémons et entretenir leur bien-être.
  
## Librairies externes  
  
- Room
- Retrofrit 2
- MPAndroidChart 3.1.0
  
## Consignes  
  
Lien vers le sujet : https://docs.google.com/presentation/d/1mwu2xx7_qfCZDfsRxseC94n7oBGYfhw-9xIftaTDbzk/edit#slide=id.p97  
  
### But du projet  
  
 - Le but du projet est de réaliser une application permettant de visualiser une série de données OpenData  
 - Les données devront être récupérées sur un serveur distant et affichées dans une liste et sur une carte  
 - Un clique sur un élément de la liste ou sur un marker de la carte permet d’accéder à un écran présentant le détail de l’élément  
 - Un écran présentera des informations générales sur les données récupérées  
  
### Exigences    
- [ ] Format des données OpenData :   
	 - Format Json  
	 - Avec un champ correspondant à l’url d’une image  
	 - Avec un champ correspondant à une position  
 - [ ] Application composée au minimum de 3 Fragment et 2 Activity  
 - [ ] Une Actionbar sera présente et permettra de rafraîchir les données récupérées et affichées
 
 ## Bonus
 
 - Amélioration de l’expérience utilisateur :
    - Clustering des markers sur la carte en fonction du niveau de zoom
    - Mise en place d’un système de recherche/filtre sur la liste affichée
 
 - Enrichissements techniques :
    - Mise en place d’une base de données locale pour afficher la liste d’élément en mode hors connexion
    - Utilisation de LiveData ou d’Observable pour la récupération de données dans la BDD
 
