/**
 * ClassInfo defines just a strucutre to store some values about a java class.
 */
package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 20 mai 09
 */
public class ClassInfo {
	/**
	 * number of public methods
	 */
	public int numPubMethods;
	
	/**
	 * number of private methods
	 */
	public int numPriMethods;
	
	/**
	 * nnumber of protected methods
	 */
	public int numProMethods;
	
	/**
	 * number of package methods
	 */
	public int numPacMethods;
	
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
}
