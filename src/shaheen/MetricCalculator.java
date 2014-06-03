/**
 * 
 */
package shaheen;

import org.apache.bcel.classfile.*;
import org.apache.bcel.*;
import java.util.*;

import org.apache.bcel.classfile.JavaClass;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 17, 2009
 */
public class MetricCalculator {

	private McCabeCyclomaticComplexity mccabe;
	private Inheritance inherit;
	private JavaClass javaclass;
	private ClassStatistic classStat;
	private ResponseForClass responseFC;
	
	/**
	 * calculates the number of defined methods in the class c.
	 * @param c class to get the number of its methods.
	 * @return integer represents number of methods defined in c.
	 */
	
	public MetricCalculator(JavaClass jc){
		javaclass = jc;
		mccabe = new McCabeCyclomaticComplexity(jc);
		inherit = new Inheritance(jc);
		classStat = new ClassStatistic(jc);
		responseFC = new ResponseForClass(jc);
	}
	
	public int getNOM(){			
		return javaclass.getMethods().length;
	}
	
	/**
	 * returns the weighted methods per class (sum of CC of all methods in the specified class)
	 * @return an integer represents WMC
	 */
	public int getWMC(){
		 
		return mccabe.getWMC();		
	}
	
	/**
	 * returns the weighted public methods per class (sum of CC of all public methods in the specified class)
	 * @return an integer represents WMC
	 */
	public int getWMCPublic(){
		 
		return mccabe.getWMCPublic();		
	}
	
	/**
	 * returns the weighted private methods per class (sum of CC of all private methods in the specified class)
	 * @return an integer represents WMC
	 */
	public int getWMCPrivate(){
		 
		return mccabe.getWMCPrivate();		
	}
	
	/**
	 * returns the weighted protected methods per class (sum of CC of all protected methods in the specified class)
	 * @return an integer represents WMC
	 */
	public int getWMCProtected(){
		 
		return mccabe.getWMCProtected();		
	}
	
	/**
	 * returns the weighted package methods per class (sum of CC of all package methods in the specified class)
	 * @return an integer represents WMC
	 */
	public int getWMCPackaged(){
		 
		return mccabe.getWMCPackage();		
	}
	
	/**
	 * get the list of CC for each method in the specified class
	 * @return a PairVector object represents a list of pairs <Method object and CC value>
	 */
	public PairVector getCCs(){
		return mccabe.getCCs(javaclass);
	}
	
	/**
	 * return depth of inheritance tree
	 * @return integer represent the depth of inheritance tree
	 * @throws ClassNotFoundException
	 */
	public int getDIT() throws ClassNotFoundException{
		return inherit.getDIT();
	}
	
	/**
	 * returns depth of inheritance tree restricted to the application
	 * @return integer value represents the depth of the inheritance tree restricted to application
	 * @throws ClassNotFoundException
	 */
	public int getDIT_A()throws ClassNotFoundException{
		return inherit.getDIT_A();
	}
	
	/**
	 * Number of public methods
	 * @return integer 
	 */
	public int getNumPubMethods(){
		return classStat.getNPuM();
	}
	
	/**
	 * Number of protected methods
	 * @return integer 
	 */
	public int getNumProMethods(){
		return classStat.getNProM();
	}

	/**
	 * Number of private methods
	 * @return integer 
	 */
	public int getNumPriMethods(){
		return classStat.getNPrM();
	}

	/**
	 * Number of package (default modifier) methods
	 * @return integer 
	 */
	public int getNumPaMethods(){
		return classStat.getNPaM();
	}

	/** 
	 * get Response For Class (RFC) value
	 */
	public int getRFC(int level){
		return responseFC.getRFC(level); 
	}

	/**
	 * get list if NOC for classes passed as keys vectors. 
	 * @param keys are classes names vector
	 * @param values are supers class names vector
	 *
	 */
	public  static Vector<Integer>  getNOCList(Vector keys, Vector values)
	{
		
		return NumberOfChildren.calculatesFrequency(keys,values);
	}
	
}
