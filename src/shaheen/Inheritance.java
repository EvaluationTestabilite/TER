/**
 * 
 */
package shaheen;

import org.apache.bcel.classfile.*;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 19, 2009
 */
public class Inheritance {
	
	private JavaClass javaclass;
	private int DIT;
	private int DIT_A;
	
	/**
	 * Number of public inherited methods
	 */
	private int NPuIn;
	
	/**
	 * Number of protected inherited methods
	 */
	private int NPrIn;
	
	/**
	 * Number of packaged inherited methods
	 */
	private int NPaIn;
	
	
	public Inheritance(JavaClass jc){
		javaclass = jc;
		try{
			analyze();
		}
		catch (ClassNotFoundException e)
		{
			System.err.println(e.toString());
		}		
	}
	
	
	/**
	 * calculates the depth of inheritance tree of the specified class. It considers the entire 
	 * hierarchy tree.
	 * @return integer value represents the depth of the inheritance tree.
	 */
	public int getDIT(){
		return DIT;
	}
	
	/**
	 * calculates the depth of inheritance tree restricted to the application of the specified class.
	 * It does not consider classes that belong to Java packages such as JDK
	 * @return integer value represents the depth of the inheritance tree restricted to application.
	 * @throws ClassNotFoundException
	 */
	public int getDIT_A()throws ClassNotFoundException{								
		return DIT_A;
	}
	
	private void analyze()throws ClassNotFoundException{		
		JavaClass[] supers = javaclass.getSuperClasses();
		DIT = javaclass.getSuperClasses().length;		
		
		for(JavaClass jc : supers)
		{
			if (!jc.getClassName().startsWith("java.") && !jc.getClassName().startsWith("javax."))
				DIT_A++;
		}			
	}

}
