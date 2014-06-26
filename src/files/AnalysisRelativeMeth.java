package files;

import java.util.ArrayList;

import parser.JavaVisitor;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

/**
 * Cette analyse prend une methode de l'arbre AST en parametre et permet grace à getCalledMethods de connaitre les methodes appeles par notre parametre.
 * @author Rexxar
 *
 */
public class AnalysisRelativeMeth extends AbstractAnalysis {
	private static final JavaVisitor<Object, AnalysisRelativeMeth> visitor = new JavaVisitor<Object, AnalysisRelativeMeth>();
	
	private ArrayList<String> calledMethods;
	private int nbAssert;

	public AnalysisRelativeMeth(MethodTree tree) {
		calledMethods = new ArrayList<String>();
		tree.accept(visitor, this);
	}
	
	public ArrayList<String> getCalledMethods() {
		return calledMethods;
	}
	
	public int getNbAssert() {
		return nbAssert;
	}
	
	@Override
	public void treat(Tree tree) {
		if(tree != null) {
			if(MethodInvocationTree.class.isInstance(tree)) {
				MethodInvocationTree node = (MethodInvocationTree) tree;
				calledMethods.add(node.getMethodSelect().toString());
				if(node.getMethodSelect().toString().contains("assert")) {
					++nbAssert;
				}
			}
		}
	}

}
