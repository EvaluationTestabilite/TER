package Analysis;

import com.sun.source.tree.Tree;

/**
 * Une implementation de fichier qui voudra utiliser les notifications du JavaVisitor devra etendre cette classe.
 * @author Rexxar
 *
 */
public abstract class AbstractAnalysis {
	
	/**
	 * Cette methode est utilisee par le JavaVisitor pour notifier notre classe que l'ast viens de passer par le noeud "tree"
	 * @param tree Le noeud en question.
	 */
	public abstract void treat(Tree tree);
}
