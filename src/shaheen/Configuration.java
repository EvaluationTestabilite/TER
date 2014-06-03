package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 17, 2009
 */

public class Configuration {
	private static String classPath;
	
	/**
	 * returns the class path.
	 * @return string represents the class path
	 */
	public static String getClassPath(){
		return classPath;
	}
	
	/**
	 * set the class path property
	 *@param path 
	 */
	public static void setClassPath(String path)
	{
		classPath = path;
	}
	

}
