package files;

import java.util.ArrayList;

import parser.JavaParser;
import parser.JavaVisitor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

/**
 * Premier exemple de classe ayant pour pere AbstractFile.</br>
 * Cette classe utilise le parser Java de sun puis visite l'ast avec le JavaVisitor et analyse les infos dans sa methode treat.
 * @author Rexxar
 *
 */
public class AnalysisTest extends AbstractAnalysis {
	private static final JavaVisitor<Object, AnalysisTest> visitor = new JavaVisitor<Object, AnalysisTest>();

	private ArrayList<MethodTree> methods;
	private ClassTree mainClass;
	private java.io.File file;
	private int nbAssert;
	
	/**
	 * Unique constructeur de cette classe. Permet d'analyser une fichier Java.
	 * @param file Le fichier Java en question. Si file est null, il ne se passe rien. Si file n'est pas un fichier Java correct, il y aura des erreurs.
	 */
	public AnalysisTest(java.io.File file) {
		if(file != null) {
			nbAssert = 0;
			this.file = file;
			methods = new ArrayList<MethodTree>();
			try {
				JavaParser.parse(file).accept(visitor, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getNbAssert() {
		return nbAssert;
	}
	
	public ArrayList<MethodTree> getMethods() {
		return methods;
	}
	
	public java.io.File getFile() {
		return file;
	}
	
	public String getName() {
		if(mainClass == null) {
			return "Unknown";
		}
		return mainClass.getSimpleName().toString();
	}

	@Override
	public void treat(Tree tree) {
		if(tree !=null) {
			if(MethodTree.class.isInstance(tree)) {
				MethodTree node = (MethodTree) tree;
				methods.add(node);
			}
			else if(ClassTree.class.isInstance(tree)) {
				ClassTree node = (ClassTree) tree;
				if(mainClass == null) {
					mainClass = node;
				}
			}
			else if(MethodInvocationTree.class.isInstance(tree)) {
				MethodInvocationTree node = (MethodInvocationTree) tree;
				if(node.getMethodSelect().toString().contains("assert")) {
					++nbAssert;
				}
			}
		}
	}
}
