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
public class AnalysisSrc extends AbstractAnalysis {
	private static final JavaVisitor<Object, AnalysisSrc> visitor = new JavaVisitor<Object, AnalysisSrc>();

	private ArrayList<VariableTree> attributs;
	
	private ArrayList<MethodTree> returnMethods;
	private ArrayList<MethodTree> methods;
	private ClassTree mainClass;
	private ArrayList<ClassTree> innerClasses;
	private ArrayList<MethodTree> setters;
	private ArrayList<MethodTree> getters;
	private ArrayList<MethodTree> constructors;
	private java.io.File file;
	
	/**
	 * Unique constructeur de cette classe. Permet d'analyser une fichier Java.
	 * @param file Le fichier Java en question. Si file est null, il ne se passe rien. Si file n'est pas un fichier Java correct, il y aura des erreurs.
	 */
	public AnalysisSrc(java.io.File file) {
		if(file != null) {
			this.file = file;
			attributs = new ArrayList<VariableTree>();
			returnMethods = new ArrayList<MethodTree>();
			methods = new ArrayList<MethodTree>();
			constructors = new ArrayList<MethodTree>();
			innerClasses = new ArrayList<ClassTree>();
			setters = new ArrayList<MethodTree>();
			getters = new ArrayList<MethodTree>();
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
	
	public ArrayList<VariableTree> getAttributs() {
		return attributs;
	}
	
	public ClassTree getMainClass() {
		return mainClass;
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
	
	public ArrayList<MethodTree> getReturnMethods() {
		return returnMethods;
	}
	
	public ArrayList<MethodTree> getSetters() {
		return setters;
	}
	
	public ArrayList<MethodTree> getGetters() {
		return getters;
	}
	
	public ArrayList<ClassTree> getInnerClasses() {
		return innerClasses;
	}
	
	public ArrayList<MethodTree> getConstructors() {
		return constructors;
	}

	@Override
	public void treat(Tree tree) {
		if(tree !=null) {
			if(MethodTree.class.isInstance(tree)) {
				MethodTree node = (MethodTree) tree;
				if(node.getReturnType() != null) {
					if(!node.getReturnType().toString().equals("void")) {
						returnMethods.add(node);
					}
				}
				else {
					constructors.add(node);
				}
				for(int i = 0; i < attributs.size(); ++i) {
					if(("set"+attributs.get(i).getName().toString()).compareToIgnoreCase(node.getName().toString()) == 0) {
						setters.add(node);
					}
					else if(("get"+attributs.get(i).getName().toString()).compareToIgnoreCase(node.getName().toString()) == 0) {
						getters.add(node);
					}
				}
				methods.add(node);
			}
			else if(ClassTree.class.isInstance(tree)) {
				ClassTree node = (ClassTree) tree;
				if(mainClass == null) {
					mainClass = node;
					for(int i = 0; i < mainClass.getMembers().size(); ++i) {
						if(VariableTree.class.isInstance(mainClass.getMembers().get(i))) {
							attributs.add((VariableTree) mainClass.getMembers().get(i));
						}
					}
				}
				else {
					innerClasses.add(node);
				}
			}
		}
	}
}
