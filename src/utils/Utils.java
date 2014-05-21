package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Utils {
	/**
	 * Permet de mettre la totalite d'un File dans un String
	 * @param file le file en question (il doit être valide)
	 * @return La chaine de caractère complète.
	 */
	public static String fileToString(File file) {
		String result = "";
		try {
			FileInputStream inputStream = new FileInputStream(file);
			int content = inputStream.read();
			while(content != -1) {
				result += (char)content;
				content = inputStream.read();
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Permet de parcourir une arborescence et de construire une ArrayList de File contenant tout les .java de l'arborescence.
	 * @param path La racine de la recherche.
	 * @return La liste des .java
	 */
	public static ArrayList<File> readWorkingDirectory(String path) {
		ArrayList<File> result = new ArrayList<File>();
		readWorkingDirectoryAlgo(path, result);
		return result;
	}
	
	private static void readWorkingDirectoryAlgo(String path, ArrayList<File> files) {
		File root = new File(path);
		File[] f = root.listFiles();
		for(int i = 0; i < f.length; ++i) {
			if(f[i].isDirectory())
				readWorkingDirectoryAlgo(f[i].getAbsolutePath(), files);
			else if(f[i].getAbsolutePath().length() > 5 && f[i].getAbsolutePath().substring(f[i].getAbsolutePath().length()-5, f[i].getAbsolutePath().length()).equals(".java"))
				files.add(f[i]);
		}
	}
}
