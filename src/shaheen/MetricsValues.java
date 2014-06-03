package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 17, 2009
 */

import java.util.*;
import org.apache.bcel.classfile.*;

public class MetricsValues {		
	/**
	 * number of defined methods
	 */
	public int NOM =  0; 
	
	/**
	 * number of public defined methods
	 */
	public int NPuM =  0;
	
	/**
	 * number of private defined methods
	 */
	public int NPrM =  0;
	
	/**
	 * number of protected defined methods
	 */
	public int NProM =  0;
	
	/**
	 * number of package defined methods
	 */
	public int NPaM =  0;
	
	/**
	 * number of attributes
	 */
	public int NOF = 0; 
	
	/**
	 * number of immediate children
	 */
	public int NOC = 0;
	
	/**
	 * McCabe cyclomatic complexity values of all methods
	 */
	public PairVector CCs = new PairVector();  
	
	/**
	 * Depth of Inheritance Tree
	 */
	public int DIT = 0; 
	
	/**
	 * Depth of Inheritance Tree restricted to the Application
	 */
	public int DIT_A = 0; 
	
	/**
	 * WMC of all methods
	 */
	public int WMC = 0; 
	
	/**
	 * WMC of public methods only 
	 */
	public int WMC_Public = 0; 
	
	/**
	 * WMC of protected methods only
	 */
	public int WMC_Protected = 0;
	
	/**
	 * WMC of private methods only
	 */
	public int WMC_Private = 0;
	
	/**
	 * WMC of package (default) methods only
	 */
	public int WMC_Package =0;
	
	/**
	 * Weighted Methods per Class for entire application class hierarchy
	 */
	public int WMH = 0;  
	
	/**
	 * Weighted Methods per Class for entire application class hierarchy restricted to the application
	 */
	public int WMH_A = 0; 

	/**
	 * Response For Class 
	 */
	public int RFC = 0;
	
	/**
	 * Number of total inherited methods 
	 */
	public int NOH = 0;
	
	/**
	 * Number of inherited methods from application or libraries with the exception of Java ones.
	 */
	public int NOH_A = 0; 

}
