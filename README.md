TER

Comment installer :

Une fois le projet dans votre workspace eclipse,

Il faut retirer les JRE System Library que votre Eclipse ajoute par defaut pour ne laisser qu'une jdk (testé sur la jdk1.8.0_05).

Pour retirer une JRE :
	CliqueDroit -> Build Path -> Remove from Build Path
	
Pour ajouter une JRE :
	TER.CliqueDroit -> Build Path -> Add Libraries -> JRE System Libraries -> choisissez votre jdk

Ensuite, pour que le parser de sun soit utilisable, il faut ajouter tools.jar au Build Path :
	TER.CliqueDroit -> Build Path -> Add External Archives -> tools.jar (Vous le trouverez dans votre jdk\lib)
	
Enfin on ajoute bcel-5.2.jar de la meme façon.
	