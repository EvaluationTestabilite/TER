package shaheen;
/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 17, 2009
 */

import java.io.*;
import java.util.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.*;


public class Trial {

	/**
	 * contains the full package name for all classes
	 */
	Vector<String> filesList = new Vector<String>(); 
	
	/**
	 * contains the full path package name all classes but it ignores inner ones
	 */
	Vector<String> filesListwithoutinner = new Vector<String>(); 		
	
	/**
	 * contains direct super class  of classes of same application. Each element in directSuperClass represents the direct super class of the class in
	 * filesListwithoutinner that has the same index.
	 */
	 Vector<String> directSuperClass = new Vector<String>();
	 
	 
	 /**
	  * contains number of direct children of classes of same application. Each element in NOCList represents the NOC value of the class in
	  * filesListwithoutinner that has the same index.
	  */
	 Vector<Integer> NOCList = new Vector<Integer>();
	 
	 
	/**
	 *  each element in packageList correspond to package name of the element in filesListwithoutinner which 
	 *  has same index. 
	 */
	Vector<String> packageList = new Vector<String>();
	/**
	 * is the absolute path where the application packages saved. It must NOT
	 * include the package name.
	 */
	String path ="";
	//String path ="D:/projects/HTMLcleaner/bin";
	
	/**
	 * contains the complete trees of all classes. Each element in classesTrees represents 
	 * the complete tree of a class in filesListwithoutinner that has the same index.
	 */
	Vector<Vector<JavaClass>> classesTrees = new Vector<Vector<JavaClass>>();
	
	/**
	 * contains all public methods in all classes. Each element in publicClassMethods
	 * corresponds to the element in filesListwithoutinner that has the same index.
	 */
	Vector<Vector> publicClassMethods = new Vector<Vector>();
	
	/**
	 * contains all protected methods in all classes. Each element in protectedClassMethods
	 * corresponds to the element in filesListwithoutinner that has the same index.
	 */
	Vector<Vector> protectedClassMethods = new Vector<Vector>();
	
	/**
	 * contains all private methods in all classes. Each element in privateClassMethods
	 * corresponds to the element in filesListwithoutinner that has the same index.
	 */
	Vector<Vector> privateClassMethods = new Vector<Vector>();
	
	/**
	 * contains all package methods (methods of package modifier) in all classes. Each element in packageClassMethods
	 * corresponds to the element in filesListwithoutinner that has the same index.
	 */
	Vector<Vector> packageClassMethods = new Vector<Vector>();		

	/**
	 * contains the metrics values, each element corresponds to the element of filesListwithoutinner which has 
	 * same index.
	 */
	Vector<MetricsValues> metricsValuesSet = new Vector<MetricsValues>();
	

	
	/**
	 * contains all super classes of the application classes. This set ONLY includes 
	 * the super classes that do NOT belong to application classes, i.e if a super class
	 * is an application class then it will not appear in this set, but if it is belong
	 * to some jar library or JDK ..., then it will appear in this set.
	 */
	Vector<String> superClasses = new Vector<String>();
	
	
	/**
	 * contains ClassInfo objects, each element corresponds to the element of superClasses which has
	 * same index.
	 */
	Vector<ClassInfo>  supersInfo = new Vector<ClassInfo>();
	
	/**
	 * constructor for setting the path of the application to be analyzed
	 * @path application path 
	 */
	
	private int RFC_Level = 0;
	
	public Trial(String path)
	{
		this.path = path;
		
		System.setProperty("java.class.path", path +  ";" + Configuration.getClassPath());
		//System.out.println(org.apache.bcel.util.ClassPath.SYSTEM_CLASS_PATH);
		//ClassPath cp = new ClassPath(path +  ";" + Configuration.getClassPath());
		//System.out.println(System.getProperty("java.class.path"));
	}
	
	/** 
	 * Fills filesList/filesListwithoutinner vectors with the 
	 * full package name of classes including/excluding inner classes respectively.
	 * 
	 * @param path where to search for classes files.
	 */
	private void getClassesFiles(String path){
		File f = new File(path);
		String FQN = ""; // represents the full name of class (with its package)
		
		for (File x : f.listFiles())
		{
			if (x.isDirectory())
				getClassesFiles(x.getAbsolutePath());
			else
				if (x.getName().endsWith(".class"))
				{				
					FQN = getFullClassName(this.path,x.getAbsolutePath());
					filesList.add(FQN);
					if (!FQN.contains("$"))
						filesListwithoutinner.add(FQN);
				}
		}
	}
	
	/**
	 * Returns the full name of class with its package name
	 * @param path is the absolute path where application packages exist
	 * @param fullClassPath absolute path of a class with its extension
	 * @return full class name with package name 
	 */
	private String getFullClassName(String path, String fullClassPath){		
		fullClassPath = fullClassPath.substring(path.length()+1, fullClassPath.length()-".class".length());
		fullClassPath=fullClassPath.replace(File.separator, ".");
		
		return fullClassPath;
	}

	
	/**
	 * 
	 * @throws ClassNotFoundException
	 */
	public void getClassesTrees()throws ClassNotFoundException
	{
		JavaClass javaclass;
		JavaClass superclasses[];
		Vector<JavaClass> v ;
		String superclassname = "";		
		
		
		for(String c :filesListwithoutinner)
		{									
			//System.out.println("LIST : " + c);
			javaclass = Repository.lookupClass(c);
			packageList.add(javaclass.getPackageName());
			
			//System.out.print(c.toString());
			//System.out.println(" " + getNOM(javaclass));		
			getAllMethods(javaclass);
			
			/****** Analysing current class *****/
			 MetricsValues mv;			 
			 mv = analyze(javaclass);

			 /**
			  * Adding the metrics values of current class to metricsValuesSet which contains the metrics values
			  * of other classes.
			  */			 
			 metricsValuesSet.add(mv);
			 
		 			 			 
			/*********/
			 
			superclasses = javaclass.getSuperClasses();
			
			/**
			 *  get direct super class
			 */
			directSuperClass.add(superclasses[0].getClassName());
						
			
			/**
			 *  for each super class that do not belong to the application classes, we get its metrics.
			 */
			v = new Vector<JavaClass>();
			int i=-1;
			for (JavaClass jc : superclasses)	
			{
				//System.out.println(jc.getClassName().toString() +",");
				superclassname = jc.getClassName();
				v.add(jc);
				if (!filesListwithoutinner.contains(superclassname)  & !superClasses.contains(superclassname))
				{
					superClasses.add(superclassname);
					//System.out.println("SUPERS : " + superclassname);
					ClassStatistic cs = new ClassStatistic(jc);
					ClassInfo ci = new ClassInfo();
					ci.numPacMethods = cs.getNPaM();
					ci.numPriMethods = cs.getNPrM();
					ci.numProMethods = cs.getNProM();
					ci.numPubMethods = cs.getNPuM();
					
					McCabeCyclomaticComplexity CCsuper = new McCabeCyclomaticComplexity(jc);
					ci.WMC_Public = CCsuper.getWMCPublic();
					ci.WMC_Protected = CCsuper.getWMCProtected();
					ci.WMC_Package = CCsuper.getWMCPackage();
					ci.WMC_Private = CCsuper.getWMCPrivate();
					
					supersInfo.add(ci);
				}
			}
			//System.out.println("\n----------------------");
			classesTrees.add(v);
		}
		
		heirarchicalMetricAnalyse();
		
		NOCList = MetricCalculator.getNOCList(filesListwithoutinner,directSuperClass);
		
		int index = 0;
		for(String classname : filesListwithoutinner)
		{
			/**
			 * update the metrics values of current class
			 */
			MetricsValues mv = metricsValuesSet.get(index);
			if (index < NOCList.size())
				mv.NOC = NOCList.get(index);
			else
				mv.NOC = 0;

			index++;			
		}
		
	}
	
	/**
	 * provide specific analysis to get certain metrics which are related to 
	 * hierarchy, such as NHM (number of inherited methods), WMH (Weighted methods per class for the Heirarchy)
	 * and others.
	 */
	private void heirarchicalMetricAnalyse(){
		int index = -1; // index of classname in the list
		for(String classname : filesListwithoutinner)
		{
			int NMH = 0;
			int NHM_A = 0;
			int WMH = 0;
			int WMH_A = 0;
			++index;
			
			Vector<JavaClass> supers = classesTrees.get(index);
			for(int j = 0; j<supers.size(); j++)
			{
				JavaClass superclass = supers.get(j);
				
				// test if the supers of specified class belong to the application itself, and if it is 
				// in same package, or it is belong to JDK library
				if(filesListwithoutinner.contains(superclass.getClassName()))
				{
					MetricsValues mv = metricsValuesSet.get(filesListwithoutinner.indexOf(superclass.getClassName()));
					
					if(packageList.get(index).equals(superclass.getPackageName()))
					{						
						NMH   += mv.NPaM + mv.NProM + mv.NPuM;
						NHM_A += mv.NPaM + mv.NProM + mv.NPuM;
						
						WMH   += mv.WMC_Package + mv.WMC_Protected + mv.WMC_Public;
						WMH_A += mv.WMC_Package + mv.WMC_Protected + mv.WMC_Public;
					}
					else
					{
						NMH   += mv.NProM + mv.NPuM;
						NHM_A += mv.NProM + mv.NPuM;
						
						WMH   += mv.WMC_Protected + mv.WMC_Public;
						WMH_A += mv.WMC_Protected + mv.WMC_Public;
					}
				}
				else // since super of specified class does not belong to same application, then get info of super  
				{
					int x = superClasses.indexOf(superclass.getClassName());
					ClassInfo superInfo = supersInfo.get(x);
					
					NMH += superInfo.numProMethods + superInfo.numPubMethods;
					WMH += superInfo.WMC_Protected + superInfo.WMC_Public;

					// calculate the number of inherited methods from application classes or other libraries excepts the ones from Java
					if(!superclass.getClassName().startsWith("java.") && !superclass.getClassName().startsWith("javax."))
					{
						NHM_A += superInfo.numProMethods + superInfo.numPubMethods;
						WMH_A += superInfo.WMC_Protected + superInfo.WMC_Public;;
					}
				}
					
			}
			/**
			 * update the metrics values of current class
			 */
			MetricsValues mv = metricsValuesSet.get(index);
			mv.NOH = NMH;
			mv.NOH_A = NHM_A;
			mv.WMH = WMH;
			mv.WMH_A = WMH_A;
		}
		
	}
	/**
	 * Adds to the vectors publicClassMethods,privateClassMethods,protectedClassMethods and packageClassMethods
	 * the methods defined in the javaclasss parameter
	 * @param javaclass the class to get its defined methods 
	 */
	private void getAllMethods(JavaClass javaclass){
		publicClassMethods.add(Methods.getPublicMethods(javaclass));
		privateClassMethods.add(Methods.getPrivateMethods(javaclass));
		protectedClassMethods.add(Methods.getProtectedMethods(javaclass));
		packageClassMethods.add(Methods.getPackageMethods(javaclass));
	}
	
	
	public MetricsValues analyze(JavaClass javaclass) throws ClassNotFoundException{
		MetricsValues mv = new MetricsValues();
		MetricCalculator mc = new MetricCalculator(javaclass);
		
		mv.CCs = mc.getCCs();
		mv.WMC = mc.getWMC();	
		
		mv.WMC_Public    = mc.getWMCPublic();
		mv.WMC_Package   = mc.getWMCPackaged();
		mv.WMC_Private   = mc.getWMCPrivate();
		mv.WMC_Protected = mc.getWMCProtected();
			
		mv.NOM   = mc.getNOM();
		mv.NPaM  = mc.getNumPaMethods();
		mv.NPrM  = mc.getNumPriMethods();
		mv.NProM = mc.getNumProMethods();
		mv.NPuM  = mc.getNumPubMethods();
		
		
		mv.DIT   = mc.getDIT();
		mv.DIT_A = mc.getDIT_A();
		
		mv.RFC = mc.getRFC(this.RFC_Level);
					
		return mv;		
	}
	
	
	public void showResults()
	{
		int index =0;
		
		for (String className : filesListwithoutinner)
		{
			System.out.print("Class name: " + className);
			Vector<JavaClass> tree = classesTrees.get(index);
			
			//print the ancestors of current class
			for(JavaClass ob : tree)
			{
				System.out.print("\n\t" + ob.getClassName() + ",");
			}
			
			System.out.println();
			//print metrics value of current class
			MetricsValues mv = metricsValuesSet.get(index);
			System.out.print("\t DIT="  + mv.DIT);
			System.out.print("\t DIT_A=" + mv.DIT_A);
			System.out.print("\t NOM="   + mv.NOM);
			System.out.print("\t NPaM="  + mv.NPaM);
			System.out.print("\t NPrM="  + mv.NPrM);
			System.out.print("\t NProM=" + mv.NProM);
			System.out.print("\t NPuM="  + mv.NPuM);
			System.out.print("\t WMC="   + mv.WMC);
			System.out.print("\t WMCPublic="   + mv.WMC_Public);			
			System.out.print("\t WMC=Protected"   + mv.WMC_Protected);
			System.out.print("\t WMC=Package"   + mv.WMC_Package);
			System.out.print("\t WMH="   + mv.WMH);
			System.out.print("\t WMH_A="   + mv.WMH_A);
			System.out.print("\t RFC="   + mv.RFC);
			System.out.print("\t NOH="   + mv.NOH);
			System.out.print("\t NOH_A="   + mv.NOH_A);
			System.out.print("\t NOC ="   + mv.NOC);
			
			// print CC for each method in current class
			for(int i =0; i< mv.CCs.size();i++)
			 {
				 Method method = (Method)mv.CCs.getKey(i);				 
				 Integer cc = (Integer)mv.CCs.getValue(i);
				// System.out.print("\n\t\t" +  method.getName() + ": CC = " + cc);
			 }
			
			index++;
			System.out.println("\n**********************\n");
		}
		
	}
	
	public void run()throws ClassNotFoundException
	{					
		getClassesFiles(path);		
		getClassesTrees();
		
		//showResults();
		
		//JavaClass javaclass = Repository.lookupClass("TestJava6");
		//Code code = javaclass.getMethods()[1].getCode();
		//System.out.println(javaclass.getMethods()[1].getName());
		//System.out.println(code); 			
	}
	
	public Vector<String> getClassesNames(){
		
		return filesListwithoutinner;
	}
	
	public Vector<MetricsValues> getMetricsValues(){
		
		return metricsValuesSet;
	}
	
	/**
	 * get PairVector object contains the CC of every method in the class className
	 * @param className the full class name to get its methods CCs
	 * @return PairVector of CCs (methods names, CCs), return null if the specified class is not exist in the application classes
	 */
	public PairVector getMethodCCs(String className){
		int index = filesListwithoutinner.indexOf(className);
		
		if (index==-1)
			return null;
		else
		{			
			MetricsValues mv = metricsValuesSet.get(index);
			return mv.CCs;
		}
	}
	
	/**
	 * to specify each RFC level that is needed to be calculated
	 * @param RFC_level is either 0,1,2 or 3
	 * @see the method getRFC from the class ResponseForClass.
	 */
	public void set(int RFC_level)
	{
		this.RFC_Level = RFC_level;
	}
}
