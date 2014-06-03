/**
 * 
 */
package shaheen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.*;


/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 18 mai 09
 */
public class McCabeCyclomaticComplexity {
	
	/**
	 * contains a set of pairs <key,value> where the the key is the a Method object, and 
	 * the key is the cyclomatic complexity of the Method. 
	 */
	private PairVector ccList = new PairVector();
	
	/**
	 * is WMC for the private methods only.
	 */
	private int wmcPrivate;
	
	/**
	 * is WMC for the protected methods only.
	 */
	private int wmcProtected; 
	
	/**
	 * is WMC for the public methods only.
	 */
	private int wmcPublic;
	
	/**
	 * is WMC for the package methods (package or default modifier) only.
	 */
	private int wmcPackage;
	
	/**
	 * is the WMC for the entire class (sum of CC of all methods)
	 */
	private int WMC;
	
	public McCabeCyclomaticComplexity(JavaClass jc){
		Method[] methods = jc.getMethods();
		int cc;
		
		for (Method m : methods)
		{
			cc = calculatesCC(m);
			ccList.put(m, cc);
			 /**
			  * @ToDO calculates the CC for the inner classes.
			  */
						
			if(m.isPrivate())
				wmcPrivate += cc;
			else if (m.isProtected())
				wmcProtected += cc;
			else if (m.isPublic())
				wmcPublic += cc;
			else
				wmcPackage += cc;					
		}
		//System.out.print("WMC = " );
		//System.out.println( wmcPrivate + wmcProtected + wmcPublic + wmcPackage);
	}
	
	/**
	 * Calculates the weighted methods per class (sum of CC of all methods in c)
	 * @return the sum of cyclomatic complexity of all class methods
	 */
	public int getWMC(){
		int WMC = wmcPrivate + wmcProtected + wmcPublic + wmcPackage;
		
		return WMC;		
	}
	
	/**
	 * get list of the cyclomatic complexity for all defined methods in the specified class
	 * @param c is the class to calculate CC for its methods
	 * @return PairVector object where the key is the Method object and the value is the CC vlaue.
	 */
	public PairVector getCCs(JavaClass c){				
		return ccList;
	}
	
	/**
	 * calculates the cyclomatic complexity of specified method.
	 * @param m is the method to calculate its CC.
	 * @return CC of the specified method.
	 */
	private int calculatesCC(Method m){
		int CC = 0;	
		
		if(m.isAbstract())
			return 0;
		else
		{
			Code code = m.getCode();
			//System.out.print("\n" + m.getName());
			
			//if(m.getName().equals("<init>") || m.getName().equals("<clinit>") )
			//	return 0;
			
			if(code==null) // there is no implementation i.e native or abstract method
			{				
				return 0;
			}
			String disassembler = code.toString();								
			
			// Couting the number of conditions
			Pattern pattern = Pattern.compile("\\s(if)");
			Matcher mat = pattern.matcher(disassembler);
			int totalMatch = 0;
			int ifCount = 0;
			int swichCaseCount = 0;
			
			while (mat.find())
				ifCount++;				
			
			 
			// Counting the number of switch cases
			pattern = Pattern.compile("\\s(lookupswitch\\{ //\\d+)");
			mat = pattern.matcher(disassembler);
						
			while (mat.find())
			{
				String str = mat.group();
				str = str.substring(str.lastIndexOf("/") + 1);  //number of the branches in switch
				swichCaseCount += Integer.valueOf(str);		
			}
			
			// Total CC including the exceptions
			totalMatch = ifCount + swichCaseCount + getExceptionCount(disassembler);;
			CC = totalMatch;
						
			CC = CC + 1;
		}
		
		//System.out.println( " CC= " + CC);			
		
		return CC;
	}
	
	/**
	 * Return the number of exceptions caught by a specified code. 
	 * @param code is the code of ONE method that is returned by JavaClass class 
	 * @return number of exception caught by the method.
	 */
	private int getExceptionCount(String code){
		String exceptionSection = "";
		int exceptionCount = 0;
		if(code.contains("Exception handler(s) ="))
		{
			exceptionSection = code.substring(code.indexOf("Exception handler(s) ="), code.indexOf("Attribute(s) ="));
			exceptionCount = exceptionSection.split("\n").length - 2;			
		}
		
		return exceptionCount;
	}
		
	public int getWMCPrivate(){
		return wmcPrivate;
	}
	
	public int getWMCProtected(){
		return wmcProtected;
	}
	
	public int getWMCPublic(){
		return wmcPublic;
	}
	
	public int getWMCPackage(){
		return wmcPackage;
	}
	
	/**
	 * gets the McCabe Cyclomatic complexity of a method
	 * @param m the method to calculate its CC
	 * @return integer value represents the cyclomatic complexity
	 */
/*	public static int getCC(Method m){
		int CC = 0;

		return CC;
	}
*/	
		
}
