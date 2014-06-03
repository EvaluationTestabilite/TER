/**
 * 
 */
package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 17, 2009
 */
import java.lang.reflect.Modifier;
import java.util.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.*;

public class Methods {
	
	
	/**
	 * get the public declared methods in a specified class
	 * @param javaClass the class to get its methods
	 * @return Vector of Method objects that are declared in the specified class.
	 */
	public static Vector<Method> getPublicMethods(JavaClass javaClass){
		Vector<Method> v = new Vector<Method>();
		
		for(Method m : javaClass.getMethods())
		{			
			if (m.isPublic())
				v.add(m);
		}
		return v; 
	}
	
	/**
	 * get the protected declared methods in a specified class
	 * @param javaClass the class to get its methods
	 * @return Vector of Method objects that are declared in the specified class.
	 */
	public static Vector<Method> getProtectedMethods(JavaClass javaClass){
		Vector<Method> v = new Vector<Method>();
		
		for(Method m : javaClass.getMethods())
		{			
			if (m.isProtected())
				v.add(m);
		}
		return v; 
	}
	
	/**
	 * get the private declared methods in a specified class
	 * @param javaClass the class to get its methods
	 * @return Vector of Method objects that are declared in the specified class.
	 */
	public static Vector<Method> getPrivateMethods(JavaClass javaClass){
		Vector<Method> v = new Vector<Method>();
		
		for(Method m : javaClass.getMethods())
		{			
			if (m.isPrivate())
				v.add(m);
		}
		return v; 
	}
	
	/**
	 * get the package declared (default) methods (that have package(default) modifier) in a specified class
	 * @param javaClass the class to get its methods
	 * @return Vector of Method objects that are declared in the specified class.
	 */
	public static Vector<Method> getPackageMethods(JavaClass javaClass){
		Vector<Method> v = new Vector<Method>();
		
		for(Method m : javaClass.getMethods())
		{			
			if (!(m.isPublic() || m.isPrivate() || m.isProtected()))
				v.add(m);
		}
		return v; 
	}
	
}
