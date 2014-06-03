package files;

import java.util.ArrayList;

import parser.JavaVisitor;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class RelativeMeth extends AbstractFile {
	private static final JavaVisitor<Object, RelativeMeth> visitor = new JavaVisitor<Object, RelativeMeth>();
	
	private ArrayList<String> calledMethods;
	private int nbAssert;

	public RelativeMeth(MethodTree tree) {
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
