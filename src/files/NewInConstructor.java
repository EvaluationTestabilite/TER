package files;

import parser.JavaVisitor;

import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class NewInConstructor extends AbstractFile {
	private static final JavaVisitor<Object, NewInConstructor> visitor = new JavaVisitor<Object, NewInConstructor>();
	
	private int nbNew;

	public NewInConstructor(MethodTree tree) {
		tree.accept(visitor, this);
	}
	
	public int getNbNew() {
		return nbNew;
	}
	
	@Override
	public void treat(Tree tree) {
		if(tree != null) {
			if(NewArrayTree.class.isInstance(tree) || NewClassTree.class.isInstance(tree)) {
				++nbNew;
			}
		}
	}

}
