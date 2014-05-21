package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;

/**
 * Cette classe est une classe utilitaire dedie aux methodes du parser Java de sun.
 * @author Rexxar
 *
 */
public class JavaParser {
	
	/**
	 * Permet de construire une liste d'ast a partir d'une liste de fichiers Java.
	 * @param dirs La liste de fichier.
	 * @return La liste d'ast, dans l'ordre.
	 * @throws Exception
	 */
	public static Iterable<? extends CompilationUnitTree> parse(List<File> dirs) throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(dirs);
		JavacTask task = (JavacTask) compiler.getTask(null, null, null,
				null, null, compilationUnits);
		return task.parse();
	}
	
	/**
	 * Permet de construire un ast a partir d'un fichier Java.
	 * @param dir Le fichier.
	 * @return L'ast qui lui correspond.
	 * @throws Exception
	 */
	public static CompilationUnitTree parse(File dir) throws Exception {
		List<File> dirs = new ArrayList<File>();
		dirs.add(dir);
		return parse(dirs).iterator().next();
	}
}
