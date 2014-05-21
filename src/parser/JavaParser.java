package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

//import org.apache.commons.lang.StringUtils;





import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;

public class JavaParser {
	public static Iterable<? extends CompilationUnitTree> parse(List<File> dirs,
			List<File> dependencies) throws Exception {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(dirs);
		/**
		 * concreteJavaToConcretePlatform We will now setup the options to Sun's
		 * java compiler This requires working out the dependencies
		 */
		// convert dependencies into a list of file paths to reach them
		List<String> stringDependencies = new ArrayList<String>();
		if (dependencies != null && !dependencies.isEmpty()) {
			for (File file : dependencies) {
				stringDependencies.add(file.getAbsolutePath());
			}
		}
		// with the dependencies all set, we can make up the options
		List<String> options = null;
		/*if(dependencies != null)
			options = Arrays.asList("-classpath", StringUtils.join(stringDependencies, File.pathSeparator));*/

		/**
		 * We now parse using Sun's java compiler
		 */
		JavacTask task = (JavacTask) compiler.getTask(null, null, null,
				options, null, compilationUnits);
		return task.parse();
	}
	
	public static Iterable<? extends CompilationUnitTree> parse(File dir,
			List<File> dependencies) throws Exception {
		List<File> dirs = new ArrayList<File>();
		dirs.add(dir);
		return parse(dirs, dependencies);
	}
}
