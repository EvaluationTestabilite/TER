/**
 * NumberOfChildren calculates the number of children for pre-calculated data from XXX class
 */

package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 22 mai 09
 */
 import java.util.*;
 
public class NumberOfChildren {
	
	/**
	 * calculates the frequency of a set of keys. 
	 *
	 * @param keys are non-repeated objects
	 * @param values the objects in which to look for the frequency of keys
	 * @return vector of integer represents the frequency of each object in keys that has same index.
	 *
	 * For example keys={A,B,C,D,E,X,Z} and values={X,C,A,A,C,Z,D} the result will be {2,0,2,1,0,1,1}
	 * Some cases result vector size may be smaller than keys size as in :
	 * keys={A,X,Y,Z} and values={A,X,A,A} the returned result is {3,1}  NOT {3,1,0,0}  the code could be modified to return {3,1,0,0}
	 * but that could slow the process in some cases. Therefore it is better to test the size of retruned result
	 */
	 public static Vector<Integer> calculatesFrequency(Vector keys, Vector values)
	{
		 int keysCount  = keys.size();		 
		 int frequency = 0;
		 
		 Vector<Integer> frequencyResult = new Vector<Integer>();
		 
		for(int i=0; i<keysCount;i++)		
		{
			Object key = keys.get(i);
			if(values.size()==0)
				break;		// stop searching
			
			int j =0;
			frequency=0;
			
			while(values.size()!=0 && j<values.size())
			{
				Object value = values.get(j);
				
				if(key.equals(value))
				{
					frequency++;
					values.remove(j);  // remove value from values if(key = values) that will reduce the size of values vector, which in turn will speed the search process
				}
				else
				{
					j++;
				}
			}
			
			frequencyResult.add(frequency);
		}
		
		return frequencyResult;
	}
}
