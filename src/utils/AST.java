package utils;

import java.util.ArrayList;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;

public class AST {

	public static ArrayList<StatementTree> get(MethodTree method) {
		ArrayList<StatementTree> result = new ArrayList<StatementTree>();
		result.addAll(method.getBody().getStatements());
		return result;
	}
}
