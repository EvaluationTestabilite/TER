package files;

import java.util.ArrayList;

import parser.JavaParser;
import parser.JavaVisitor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

/**
 * Premier exemple de classe ayant pour pere AbstractFile.</br>
 * Cette classe utilise le parser Java de sun puis visite l'ast avec le JavaVisitor et analyse les infos dans sa methode treat.
 * @author Rexxar
 *
 */
public class FileTest extends AbstractFile {
	private static final JavaVisitor<Object, FileTest> visitor = new JavaVisitor<Object, FileTest>();

	private ArrayList<MethodTree> methods;
	private ClassTree mainClass;
	private java.io.File file;
	
	/**
	 * Unique constructeur de cette classe. Permet d'analyser une fichier Java.
	 * @param file Le fichier Java en question. Si file est null, il ne se passe rien. Si file n'est pas un fichier Java correct, il y aura des erreurs.
	 */
	public FileTest(java.io.File file) {
		if(file != null) {
			this.file = file;
			methods = new ArrayList<MethodTree>();
			try {
				JavaParser.parse(file).accept(visitor, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		}
	}
}
