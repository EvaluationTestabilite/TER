/**
 * 
 */
package shaheen;
import org.apache.bcel.classfile.*;
/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 20 mai 09
 */
public class ClassStatistic {
	/**
	 * number of defined methods
	 */
	private int NOM =  0; 
	
	/**
	 * number of public defined methods
	 */
	private int NPuM =  0;
	
	/**
	 * number of private defined methods
	 */
	private int NPrM =  0;
	
	/**
	 * number of protected defined methods
	 */
	private int NProM =  0;
	
	/**
	 * number of package defined methods
	 */
	private int NPaM =  0;
	
	
	private JavaClass javaclass;
	
	public ClassStatistic(JavaClass jc){
		javaclass = jc;
		analyse();
	}
	
	private void analyse(){
		Method[] methods = javaclass.getMethods();
		
		for(Method m : methods)
		{
			if (m.isPublic())
				NPuM++;
			else if (m.isProtected())
				NProM++;
			else if(m.isPrivate())
				NPrM++;
			else 
				NPaM++;
		}
	}
	
	/**
	 * Number of public defined methods
	 * @return integer number of public methods
	 */
	public int getNPuM(){
		return NPuM;
	}
	
	/**
	 * Number of protected defined methods
	 * @return integer number of protected methods
	 */
	public int getNProM(){
		return NProM;
	}
	
	/**
	 * Number of private defined methods
	 * @return integer number of private methods
	 */
	public int getNPrM(){
		return NPrM;
	}
	
	/**
	 * Number of package defined methods
	 * @return integer number of package methods
	 */
	public int getNPaM(){
		return NPaM;
	}	
}
