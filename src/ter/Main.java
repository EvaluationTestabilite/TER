package ter;

import java.util.ArrayList;

import files.File;
import utils.Utils;

public class Main {
	
	public static void main(String [] args) {
		ArrayList<java.io.File> javaIOFiles = Utils.readWorkingDirectory("D:\\Documents\\Cours\\Java Workspace\\");
		ArrayList<File> files = new ArrayList<File>();
		for(int i = 0; i < javaIOFiles.size(); ++i) {
			files.add(new File(javaIOFiles.get(i)));
		}
		if(files.size() > 0) {
			File current = files.get(0);
			System.out.println("ClassName : " + current.getName());
			System.out.println("\nListe des classes internes : ");
			for(int i = 0; i < current.getInnerClasses().size(); ++i) {
				System.out.println(current.getInnerClasses().get(i).getSimpleName());
			}
			System.out.println("\nListe des methodes avec return : ");
			for(int i = 0; i < current.getReturnMethods().size(); ++i) {
				System.out.println(current.getReturnMethods().get(i).getName().toString());
			}
			System.out.println("\nListe des getters et setters : ");
			for(int i = 0; i < current.getGetters().size(); ++i) {
				System.out.println(current.getGetters().get(i).getName().toString());
			}
			for(int i = 0; i < current.getSetters().size(); ++i) {
				System.out.println(current.getSetters().get(i).getName().toString());
			}
		}
	}
}
