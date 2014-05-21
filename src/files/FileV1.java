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
public class FileV1 extends AbstractFile {
	private static final JavaVisitor<Object, FileV1> visitor = new JavaVisitor<Object, FileV1>();

	private ArrayList<VariableTree> parameters;
	
	private ArrayList<MethodTree> returnMethods;
	private ClassTree mainClass;
	private ArrayList<ClassTree> innerClasses;
	private ArrayList<MethodTree> setters;
	private ArrayList<MethodTree> getters;
	
	/**
	 * Unique constructeur de cette classe. Permet d'analyser une fichier Java.
	 * @param file Le fichier Java en question. Si file est null, il ne se passe rien. Si file n'est pas un fichier Java correct, il y aura des erreurs.
	 */
	public FileV1(java.io.File file) {
		if(file != null) {
			try {
				JavaParser.parse(file).accept(visitor, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			parameters = new ArrayList<VariableTree>();
			returnMethods = new ArrayList<MethodTree>();
			innerClasses = new ArrayList<ClassTree>();
			setters = new ArrayList<MethodTree>();
			getters = new ArrayList<MethodTree>();
		}
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

	@Override
	public void treat(Tree tree) {
		if(tree !=null) {
			switch(tree.getClass().getSimpleName().toString().substring(2))
			{
			case "MethodDecl" :
				MethodTree methodTree = (MethodTree) tree;
				if(methodTree.getReturnType() != null) {
					if(!methodTree.getReturnType().toString().equals("void")) {
						returnMethods.add(methodTree);
					}
				}
				for(int i = 0; i < parameters.size(); ++i) {
					if(("set"+parameters.get(i).getName().toString()).compareToIgnoreCase(methodTree.getName().toString()) == 0) {
						setters.add(methodTree);
					}
					else if(("get"+parameters.get(i).getName().toString()).compareToIgnoreCase(methodTree.getName().toString()) == 0) {
						getters.add(methodTree);
					}
				}
				break;
			case "ClassDecl" :
				ClassTree classTree = (ClassTree) tree;
				if(mainClass == null) {
					mainClass = classTree;
					for(int i = 0; i < mainClass.getMembers().size(); ++i) {
						if(mainClass.getMembers().get(i).getClass().getSimpleName().substring(2).equals("VariableDecl")) {
							parameters.add((VariableTree) mainClass.getMembers().get(i));
						}
					}
				}
				else {
					innerClasses.add(classTree);
				}
				break;
			}
		}
	}
}
