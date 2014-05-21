package ter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import files.FileV1;
import utils.Tuple;
import utils.Utils;

public class Main {
	
	public static void main(String [] args) {
		//test("D:\\Documents\\Cours\\Java Workspace\\");
		//tri("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
	}
	
	/**
	 * Cette methode permet de trier tous les .java dans 2 fichiers, les tests dans le fichier tests et les classes correspondantes dans le fichier src.
	 * Construit egalement l'arbre ast pour chacune d'entre elle afin se savoir si leur syntaxe est correcte.
	 * @param workspace Le chemin vers le dossier ou se trouve toutes les classes a trier.
	 */
	public static void tri(String workspace) {
		ArrayList<java.io.File> javaIOFiles = Utils.readWorkingDirectory(workspace);
		ArrayList<Tuple<FileV1, FileV1>> files = new ArrayList<Tuple<FileV1, FileV1>>();
		for(int i = 0; i < javaIOFiles.size(); ++i) {
			for(int j = 0; j < javaIOFiles.size(); ++j) {
				if(javaIOFiles.get(j).getName().equalsIgnoreCase("test"+javaIOFiles.get(i).getName()) ||
						javaIOFiles.get(j).getName().equalsIgnoreCase(javaIOFiles.get(i).getName()+"test")) {
					files.add(new Tuple<FileV1, FileV1>(new FileV1(javaIOFiles.get(i)), new FileV1(javaIOFiles.get(j))));
				}
			}
		}
		
		ArrayList<String> liste = new ArrayList<String>();
		for(int i = 0; i < files.size(); ++i) {
			for(int j = 0; j < files.size(); ++j) {
				if(files.get(i).getX().getName().equalsIgnoreCase(files.get(j).getX().getName()) && !liste.contains(files.get(i).getX().getName())) {
					liste.add(files.get(i).getX().getName());
				}
			}
		}
		for(int i = files.size() - 1; i >= 0; --i) {
			if(liste.contains(files.get(i).getX())) {
				files.remove(i);
			}
		}
		for(int i = 0; i < files.size(); ++i) {System.out.println(i);
			java.io.File src = new java.io.File(workspace+"\\src\\"+files.get(i).getX().getName()+".java");
			java.io.File test = new java.io.File(workspace+"\\test\\"+files.get(i).getY().getName()+".java");
			try {
				src.createNewFile();
				test.createNewFile();
				copyFile(files.get(i).getX().getFile(), src);
				copyFile(files.get(i).getY().getFile(), test);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Exemple de programme qui affiche a l'ecran les caractéristiques qui nous interessent.
	 * @param args Non utilisés.
	 */
	public static void test(String workspace) {
		ArrayList<java.io.File> javaIOFiles = Utils.readWorkingDirectory(workspace);
		ArrayList<FileV1> files = new ArrayList<FileV1>();
		for(int i = 0; i < javaIOFiles.size(); ++i) {
			files.add(new FileV1(javaIOFiles.get(i)));
		}
		if(files.size() > 0) {
			FileV1 current = files.get(0);
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
	
	private static void copyFile(java.io.File source, java.io.File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
