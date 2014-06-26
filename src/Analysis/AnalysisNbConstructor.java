package Analysis;

import parser.JavaVisitor;

import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class AnalysisNbConstructor extends AbstractAnalysis {
	private static final JavaVisitor<Object, AnalysisNbConstructor> visitor = new JavaVisitor<Object, AnalysisNbConstructor>();
	
	private int nbNew;

	public AnalysisNbConstructor(MethodTree tree) {
		tree.accept(visitor, this);
	}
	
	public int getNbNew() {
		return nbNew;
	}
	
	@Override
	public void treat(Tree tree) {
		if(tree != null) {
			if(NewClassTree.class.isInstance(tree)) {
				++nbNew;
			}
		}
	}

}
