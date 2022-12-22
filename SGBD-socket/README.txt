NB:Plusieurs client peuvent etre connecter au serveur(MultiThread) 

//creation table
syntaxe : create table nom_table column colonne1,colonne2
exemple : create table personne column nom,prenom

//insertion
syntaxe : insert into nom_table values donne1,donne2
exemple : insert into personne values liantsoa,fehizoro

//selection,projection
syntaxe : select * from nom_table ou select colonne1,colonne2 from nom_table
exemple 1 : select * from personne 
exemple 2 : select nom from personne

//suppression
syntaxe : delete from nom_table where colonne=donnee
exemple : delete from personne where nom=liantsoa

//update
syntaxe : update nom_table set colonne=donnee where colonne=donne
exemple : update personne set prenom=jones where nom=liantsoa

//join
syntaxe : select * from table1 join table2 on colonne1=colonne2
exemple : select * from personne join pers on nom=nom
nb: possible aussi avec projection

