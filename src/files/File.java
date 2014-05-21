package files;

import java.util.ArrayList;

import parser.JavaParser;
import parser.JavaVisitor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class File extends AbstractFile {
	private static final JavaVisitor<Object, File> visitor = new JavaVisitor<Object, File>();
	private ArrayList<MethodTree> returnMethods = new ArrayList<MethodTree>();
	private ClassTree mainClass;
	private ArrayList<ClassTree> innerClasses = new ArrayList<ClassTree>();
	private ArrayList<VariableTree> parameters = new ArrayList<VariableTree>();
	private ArrayList<MethodTree> setters = new ArrayList<MethodTree>();
	private ArrayList<MethodTree> getters = new ArrayList<MethodTree>();
	
	public File(java.io.File file) {
		if(file != null) {
			try {
				JavaParser.parse(file, null).iterator().next().accept(visitor, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
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