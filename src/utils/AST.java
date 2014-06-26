package utils;

import java.util.ArrayList;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;

import files.AnalysisRelativeMeth;

public class AST {

	public static ArrayList<StatementTree> getStatement(MethodTree method) {
		ArrayList<StatementTree> result = new ArrayList<StatementTree>();
		if(method.getBody() != null) {
			result.addAll(method.getBody().getStatements());
		}
		return result;
	}
	
	/**
	 * Use this function to know what methods are called in an other.
	 * @param meth The method to analyse.
	 * @param methods A list of methods.
	 * @return A sub-group of thoose methods that are use in meth.
	 */
	public static ArrayList<MethodTree> RelatedMethods(MethodTree meth, ArrayList<MethodTree> methods) {
		ArrayList<MethodTree> result = new ArrayList<MethodTree>();
		AnalysisRelativeMeth rm = new AnalysisRelativeMeth(meth);
		boolean found;
		int j, k;
		for(int i = 0; i < methods.size(); ++i) {
			found = false;
			j = 0;
			while(j < rm.getCalledMethods().size() && !found) {
				String str = rm.getCalledMethods().get(j);
				k = str.length() - 1;
				while(k >= 0 && str.charAt(k) != '.') {
					k--;
				}
				if(k >= 0) {
					str = str.substring(k+1);
				}
				if(methods.get(i).getName().toString().equals(str)) {
					found = true;
					result.add(methods.get(i));
				}
				++j;
			}
		}
		return result;
	}
	
	/**
	 * Use this to know what methods are testing an other.
	 * @param meth The method to analyse.
	 * @param methods The list of testing methods.
	 * @return A sub-group of thoose methods that are testing meth.
	 */
	public static ArrayList<MethodTree> TestingMethods(MethodTree meth, ArrayList<MethodTree> methods) {
		ArrayList<MethodTree> result = new ArrayList<MethodTree>();
		ArrayList<MethodTree> list = new ArrayList<MethodTree>();
		list.add(meth);
		for(int i = 0; i < methods.size(); ++i) {
			if(RelatedMethods(methods.get(i), list).size() > 0) {
				result.add(methods.get(i));
			}
		}
		return result;
	}
}
