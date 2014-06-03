package shaheen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.apache.bcel.classfile.Method;


public class RecupCompl {

	private String applicationPath;	
	private int RFC_Level = 0;
	private Trial t;
	private Vector<String> classesNames;
	private PairVector CCs;
	private boolean analyseOk;
								
	public RecupCompl(String path){
		
		this.applicationPath = path;
		Trial tr = new Trial(this.applicationPath);;
		tr.set(RFC_Level);
		this.t=tr;
		analyseOk= false;				
		try
		{						
			t.run();
			analyseOk = true;						
		}
		catch(ClassNotFoundException e1)
		{
			System.out.println("Some libraries are missing.\n Verify the class path settings");
			System.err.println(e1.toString());					
		}										
	}
	
	public boolean analyseOK(){
		return analyseOk;
	}
	
	public int getComplexityClasse(String className) {
		String classe = "";
		int complexity = -1;
		
		classesNames = t.getClassesNames();
		
		for(String classname : classesNames)
		{
			classe = classname;
			
			if(classe.compareToIgnoreCase(classname)==0){
				complexity = 0;
				CCs = t.getMethodCCs(classname);
				for(int i =0; i< CCs.size();i++) {
					complexity += (Integer)CCs.getValue(i);
				}
			}
		}
		return complexity;
	}
	
	public int getComplexityMethode(String className, String methodeName) {
		String classe = "";
		String methode = "";
		int complexity = -1;
		
		classesNames = t.getClassesNames();
		
		for(String classname : classesNames)
		{
			classe = classname;
			
			if(classe.compareToIgnoreCase(classname)==0){
				CCs = t.getMethodCCs(classname);
				for(int i =0; i< CCs.size();i++) {
					methode = ((Method)CCs.getKey(i)).getName();
					
					if(methode.compareToIgnoreCase(methodeName)==0){
						complexity = 0;
						complexity += (Integer)CCs.getValue(i);
					}
				}
					
			}
		}
		return complexity;
	}
	
	public void ecrire()
	{
		FileWriter fw= null;
		String buf = "";
		String classe = "";
		String methode = "";
		String complex = "";
		
		try {
			fw=new FileWriter(new File("complexity.txt"));
			
			classesNames = t.getClassesNames();
			
			for(String classname : classesNames)
			{
				classe = classname;
				
				CCs = t.getMethodCCs(classname);
				for(int i =0; i< CCs.size();i++) {
					methode = ((Method)CCs.getKey(i)).getName();
					complex = ((Integer)CCs.getValue(i)).toString();
					
					buf = classe + ":" + methode + "-" + complex;
					
					fw.write(buf);
					buf="\n";
					fw.write(buf);
				}
			}
	         
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public static void main(String[] args) throws ClassNotFoundException
	{
		RecupCompl recup = new RecupCompl("C:\\Users\\Blandine\\Documents\\Cours UJF\\M1\\S2\\TLI\\TP3\\Début\\TP2-TLI debut\\bin");	
		recup.ecrire();
	}
}
