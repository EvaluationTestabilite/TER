/**
 * PairVector class similar to TreeMap, but it permits the duplicated pairs.
 */
package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 18 mai 09
 * 
 */

import java.util.*;

public class PairVector {
	private Vector<Object> keys;
	private Vector<Object> values;
	
	public PairVector() {
		keys = new Vector<Object>();
		values = new Vector<Object>();		
	}
	
	public void put(Object key, Object value){
		keys.add(key);
		values.add(value);		
	}
	
	public Object getKey(int index){
		return keys.get(index);
	}
	
	public Object getValue(int index){
		return values.get(index);
	}
	
	public Vector<Object> getKeys(){
		return keys;
	}
	
	public Vector<Object> getValues(){
		return values;
	}
	
	
	public void clear(){
		keys.clear();
		values.clear();
	}
	
	public int size(){
		return keys.size();
	}
	
}
