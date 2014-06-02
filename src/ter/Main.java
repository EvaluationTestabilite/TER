package ter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sun.source.tree.MethodTree;

import files.FileSrc;
import files.FileTest;
import utils.AST;
import utils.Tuple;
import utils.Utils;

public class Main {
	
	public static void main(String [] args) {
		//test("D:\\Documents\\Cours\\Java Workspace\\");
		//tri("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
		//test2("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
		//couplage("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
		//test3("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
		test4("D:\\Documents\\Cours\\M1 S2\\TER\\Base de donnees");
	}

	/**
	 * Cette methode ecrit un fichier au format exel avec des caracteristique des methodes testes.
	 * @param workspace Le chemin vers le fichier ou se trouvent les fichiers "src" et "test".
	 */
	public static void test4(String workspace) {
		ArrayList<Tuple<FileSrc, FileTest>> files = couplage(workspace);
		FileWriter file;
		BufferedWriter buffW;
		FileSrc src;
		FileTest test;
		ArrayList<MethodTree> testingMeth;
		try {
			file = new FileWriter(workspace + "\\data_meth.csv");
			buffW = new BufferedWriter(file);
			buffW.write("Classe;Method;Testing_Methods;\n");
			for(int i = 0; i < files.size(); ++i) {
				src = files.get(i).getX();
				test = files.get(i).getY();
				buffW.write(src.getName() + "\n");
				for(int j = 0; j < src.getMethods().size(); ++j) {
					buffW.write(";" + src.getMethods().get(j).getName().toString() + "\n");
					testingMeth = AST.TestingMethods(src.getMethods().get(j), test.getMethods());
					for(int k = 0; k < testingMeth.size(); ++k) {
						buffW.write(";;" + testingMeth.get(k).getName().toString() + "\n");
					}
				}
			}
		} catch (IOException e) {}
	}

	/**
	 * Cette methode ecrit un fichier au format exel avec des caracteristique des classes testes.
	 * @param workspace Le chemin vers le fichier ou se trouvent les fichiers "src" et "test".
	 */
	public static void test3(String workspace) {
		ArrayList<Tuple<FileSrc, FileTest>> files = couplage(workspace);
		FileWriter file;
		BufferedWriter buffW;
		FileSrc src;
		FileTest test;
		int nbStatementSrc;
		int nbStatementTest;
		try {
			file = new FileWriter(workspace + "\\data_class.csv");
			buffW = new BufferedWriter(file);
			buffW.write("Nom;;#Attributs;#Constructors;#Getters & Setters;#InnerClasses;#Statement;#ReturnMeth\\#Meth;;#Statement;#Assert\n");
			for(int i = 0; i < files.size(); ++i) {
				src = files.get(i).getX();
				test = files.get(i).getY();
				nbStatementTest = 0;
				for(int j = 0; j < test.getMethods().size(); ++j) {
					nbStatementTest += AST.getStatement(test.getMethods().get(j)).size();
				}
				nbStatementSrc = 0;
				for(int j = 0; j < src.getMethods().size(); ++j) {
					nbStatementSrc += AST.getStatement(src.getMethods().get(j)).size();
				}
				if(nbStatementSrc > 0) {
					buffW.write(src.getName()+";;"+src.getAttributs().size()+";"+src.getConstructors().size()+";"+(src.getGetters().size()+src.getSetters().size())
							+";"+src.getInnerClasses().size()+";"+nbStatementSrc+";"+src.getReturnMethods().size()+"\\"+src.getMethods().size()+";;"+nbStatementTest+";"+test.getNbAssert()+"\n");
				}
			}
			buffW.close();
		} catch (IOException e) {}
	}
	
	/**
	 * Cette methode tri les fichier source avec leur fichier test associe.
	 * @param workspace Le chemin vers le fichier ou se trouvent les fichiers "src" et "test".
	 * @return Une liste de tuples de FileSrc, FileTest.
	 */
	public static ArrayList<Tuple<FileSrc, FileTest>> couplage(String workspace) {
		ArrayList<Tuple<FileSrc, FileTest>> result = new ArrayList<Tuple<FileSrc, FileTest>>();
		ArrayList<java.io.File> javaIOFilesSrc = Utils.readWorkingDirectory(workspace+"\\src\\");
		ArrayList<java.io.File> javaIOFilesTest = Utils.readWorkingDirectory(workspace+"\\test\\");
		java.io.File src;
		java.io.File test;
		for(int i = 0; i < javaIOFilesSrc.size(); ++i) {
			src = javaIOFilesSrc.get(i);
			test = null;
			for(int j = 0; j < javaIOFilesTest.size(); ++j) {
				if(javaIOFilesTest.get(j).getName().equalsIgnoreCase("test"+src.getName()) ||
						javaIOFilesTest.get(j).getName().equalsIgnoreCase(
								src.getName().substring(0, src.getName().length()-5)+"test.java")) {
					test = javaIOFilesTest.get(j);
				}
			}
			if(test != null) {
				result.add(new Tuple<FileSrc, FileTest>(new FileSrc(src), new FileTest(test)));
			}
			System.out.println("Couplage et construction des AST : "+(i+1)+" / "+javaIOFilesSrc.size());
		}
		return result;
	}
	
	/**
	 * Cette methode affiche quelques caracteristique sur la base de donnee. Elle a juste servie a une courte presentation.
	 * @param workspace Le chemin vers le fichier ou se trouvent les fichiers "src" et "test".
	 */
	public static void test2(String workspace) {
		ArrayList<java.io.File> javaIOFilesSrc = Utils.readWorkingDirectory(workspace+"\\src\\");
		ArrayList<java.io.File> javaIOFilesTest = Utils.readWorkingDirectory(workspace+"\\test\\");
		int taille = javaIOFilesSrc.size() + javaIOFilesTest.size();
		ArrayList<FileSrc> src = new ArrayList<FileSrc>();
		for(int i = 0; i < javaIOFilesSrc.size(); ++i) {
			System.out.println((i+1)+" / "+taille);
			src.add(new FileSrc(javaIOFilesSrc.get(i)));
		}
		ArrayList<FileSrc> test = new ArrayList<FileSrc>();
		for(int i = 0; i < javaIOFilesTest.size(); ++i) {
			System.out.println((i + 1 + src.size())+" / "+taille);
			test.add(new FileSrc(javaIOFilesTest.get(i)));
		}
		ArrayList<FileSrc> fileWithGettersAndSetters = new ArrayList<FileSrc>();
		ArrayList<FileSrc> fileWithReturnMethods = new ArrayList<FileSrc>();
		ArrayList<FileSrc> fileWithInnerClasses = new ArrayList<FileSrc>();
		for(int i = 0; i < src.size(); ++i) {
			if(src.get(i).getGetters().size() + src.get(i).getSetters().size() > 0) {
				fileWithGettersAndSetters.add(src.get(i));
			}
			if(src.get(i).getReturnMethods().size() > 0) {
				fileWithReturnMethods.add(src.get(i));
			}
			if(src.get(i).getInnerClasses().size() > 0) {
				fileWithInnerClasses.add(src.get(i));
			}
		}
		int compteur = 0;
		for(int i = 0; i < fileWithGettersAndSetters.size(); ++i) {
			compteur += fileWithGettersAndSetters.get(i).getGetters().size() + fileWithGettersAndSetters.get(i).getSetters().size();
		}
		System.out.println("\n\n\n\nil y a "+fileWithGettersAndSetters.size()+" classes avec des getters et/ou setters.");
		System.out.println("Au total il y a "+compteur+" getters et/ou setters.\n");
		compteur = 0;
		for(int i = 0; i < fileWithReturnMethods.size(); ++i) {
			compteur += fileWithReturnMethods.get(i).getReturnMethods().size();
		}
		System.out.println("il y a "+fileWithReturnMethods.size()+" classes avec des return.");
		System.out.println("Au total il y a "+compteur+" methodes avec des return.\n");
		compteur = 0;
		for(int i = 0; i < fileWithInnerClasses.size(); ++i) {
			compteur += fileWithInnerClasses.get(i).getInnerClasses().size();
		}
		System.out.println("il y a "+fileWithInnerClasses.size()+" classes avec des classes internes.");
		System.out.println("Au total il y a "+compteur+" classes internes.\n");
	}
	
	/**
	 * Cette methode permet de trier tous les .java dans 2 fichiers, les tests dans le fichier tests et les classes correspondantes dans le fichier src.
	 * Construit egalement l'arbre ast pour chacune d'entre elle afin se savoir si leur syntaxe est correcte.
	 * @param workspace Le chemin vers le dossier ou se trouve toutes les classes a trier.
	 */
	public static void tri(String workspace) {
		ArrayList<java.io.File> javaIOFiles = Utils.readWorkingDirectory(workspace);
		ArrayList<Tuple<FileSrc, FileSrc>> files = new ArrayList<Tuple<FileSrc, FileSrc>>();
		for(int i = 0; i < javaIOFiles.size(); ++i) {
			for(int j = 0; j < javaIOFiles.size(); ++j) {
				if(javaIOFiles.get(j).getName().equalsIgnoreCase("test"+javaIOFiles.get(i).getName()) ||
						javaIOFiles.get(j).getName().equalsIgnoreCase(
								javaIOFiles.get(i).getName().substring(0, javaIOFiles.get(i).getName().length()-5)+"test.java")) {
					files.add(new Tuple<FileSrc, FileSrc>(new FileSrc(javaIOFiles.get(i)), new FileSrc(javaIOFiles.get(j))));
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
		for(int i = 0; i < files.size(); ++i) {System.out.println(files.get(i).getX().getName()+"          "+files.get(i).getY().getName());
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
		ArrayList<FileSrc> files = new ArrayList<FileSrc>();
		for(int i = 0; i < javaIOFiles.size(); ++i) {
			files.add(new FileSrc(javaIOFiles.get(i)));
		}
		if(files.size() > 0) {
			FileSrc current = files.get(0);
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
