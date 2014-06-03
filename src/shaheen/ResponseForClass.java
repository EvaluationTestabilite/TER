/**
 * RFC or Response For Class is software metric that represents the number of methods in class 
 * in addition to the number of calls (within every method in the class) to other method. 
 */
package shaheen;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.*;
/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 20 mai 09
 */
public class ResponseForClass {
	private int RFC0;  // for including all calls (even internal ones)
	private int RFC1;  // for excluding special calls such as StringBuilder.append and toString methods; these methods appear the byte code even if they do not appear in the source code
	private int RFC2;  // for excluding the calls for the constructors during the creating of objects
	private int RFC3;  // for excluding special calls and constructors.
	
	private int returnedRFC;
	private int level;
	
	private JavaClass javaclass;
		
	/**
	 * 
	 * @param jc JavaClass object to get its RFC
	 */
	public ResponseForClass(JavaClass jc){
		javaclass = jc;
		calculate();		
	}
	
	private void calculate(){
		Method[] methods = javaclass.getMethods();		
		
		for(Method m : methods)
		{			
			if(m.isAbstract())
				; // do nothing
			else
			{
				Code code = m.getCode();
				if (code==null)
					returnedRFC += 0;
				else
				{
					
					int x = getNumSpecialCalls(code);
					int y = getNumConstructorCalls(code);
					int z = getNumCalls(code);
					RFC0 += z;
					RFC1 += z - x ;
					RFC2 += z - y;
					RFC3 += z - x - y;
				}	
			}			
		}						
	}
	
	/**
	 * get number of calls from a specified methods. this method corresponds to level 0.
	 * @param code of a method to get number of calls in it  
	 * @return integer value represents number of calls
	 */
	private int getNumCalls(Code code){
		int num =0;
		Pattern pattern = Pattern.compile("\\s(invoke)");
		Matcher mat = pattern.matcher(code.toString());
		
		while (mat.find())
		{
			num++;			
		}		
		
		return num;
	}
	
	/**
	 * get special call of StringBuilder.append and other
	 * @param code
	 * @return
	 */
	private int getNumSpecialCalls(Code code){
		int num =0;
		Pattern pattern = Pattern.compile("\\sinvoke.*java.lang.StringBuilder.append");		
		Matcher mat = pattern.matcher(code.toString());
		
		while (mat.find())
		{
			num++;
			//if(javaclass.getClassName().equals("org.xmlmath.operands.Declarations"))
			//	System.out.println(mat.group());
		}
		
		pattern = Pattern.compile("\\sinvoke.*java.lang.StringBuilder.toString");		
		mat = pattern.matcher(code.toString());
		
		while (mat.find())
		{
			num++;
			//if(javaclass.getClassName().equals("org.xmlmath.operands.Declarations"))
			//	System.out.println(mat.group());
		}
		
		return num;
	}
	
	/**
	 * get number of constructors calls
	 * @param code
	 * @return
	 */
	private int getNumConstructorCalls(Code code){
		int num =0;
		Pattern pattern = Pattern.compile("\\sinvoke.*<init>");		
		Matcher mat = pattern.matcher(code.toString());
		
		while (mat.find())
		{			
			++num;
			//if(javaclass.getClassName().equals("org.xmlmath.operands.Declarations"))
				//{System.out.println(mat.group());}
		}
		//if(javaclass.getClassName().equals("org.xmlmath.operands.Declarations"))
		//{System.out.println("Cosntruc : " + num);}
		
		return num;
	}
	
	/**
	 * Return the Response for class (RFC) value of the specified class
	 * @param level is one of four values:
	 * 0 for including all calls (even internal ones)
	 * 1 for excluding special calls such as StringBuilder.append and toString methods; these methods appear the byte code even if they do not appear in the source code
	 * 2 for excluding the calls for the constructors during the creating of objects
	 * 3 for excluding special calls and constructors.
	 * @return integer value represents the RFC metric. If -1 is returned then level value is not 0,1,2 nor 3.
	 */
	public int getRFC(int level){
		this.level = level;
		if(level==0)
			return RFC0 += javaclass.getMethods().length;
		else if(level ==1)
			return RFC1 += javaclass.getMethods().length;
		else if(level ==2)
			return RFC2 += javaclass.getMethods().length;					
		else if(level ==3)
			return RFC3 += javaclass.getMethods().length;
		else
			return -1;

	}		
}
