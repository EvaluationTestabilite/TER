package parser;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;

import files.AbstractFile;

/**
 * Cette classe est un visitor qui va parcourir l'ensemble des noeuds d'un ast créé par le parser de sun à partir du noeud où elle est appelé.
 * </br>Il est interessant de noter que toutes les feuilles qui n'ont pas methodes accept sont notes ici en commentaire, on y retrouve en majeur partie les noms des elements.
 * @author Rexxar
 *
 * @param <R> Le type de retour des methodes.
 * @param <D> Le type de parametre des methodes, il doit etendre AbstractFile. A chaque passage dans un noeuds "tree", la methode treat(tree) sera appelee.
 */
public class JavaVisitor<R, D extends AbstractFile> implements TreeVisitor<R, D> {

	@Override
	public R visitAnnotatedType(AnnotatedTypeTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getAnnotations() != null) {
			for(int i = 0; i < arg0.getAnnotations().size(); ++i) {
				arg0.getAnnotations().get(i).accept(this, arg1);
			}
		}
		if(arg0.getUnderlyingType() != null) {
			arg0.getUnderlyingType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitAnnotation(AnnotationTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getAnnotationType() != null) {
			arg0.getAnnotationType().accept(this, arg1);
		}
		if(arg0.getArguments() != null) {
			for(int i = 0; i < arg0.getArguments().size(); ++i) {
				arg0.getArguments().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitArrayAccess(ArrayAccessTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getIndex() != null) {
			arg0.getIndex().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitArrayType(ArrayTypeTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitAssert(AssertTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getDetail() != null) {
			arg0.getDetail().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitAssignment(AssignmentTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getVariable() != null) {
			arg0.getVariable().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitBinary(BinaryTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getLeftOperand() != null) {
			arg0.getLeftOperand().accept(this, arg1);
		}
		if(arg0.getRightOperand() != null) {
			arg0.getRightOperand().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitBlock(BlockTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getStatements() != null) {
			for(int i = 0; i < arg0.getStatements().size(); ++i) {
				arg0.getStatements().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitBreak(BreakTree arg0, D arg1) {
		// arg0.getLabel() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitCase(CaseTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getStatements() != null) {
			for(int i = 0; i < arg0.getStatements().size(); ++i) {
				arg0.getStatements().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitCatch(CatchTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBlock() != null) {
			arg0.getBlock().accept(this, arg1);
		}
		if(arg0.getParameter() != null) {
			arg0.getParameter().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitClass(ClassTree arg0, D arg1) {
		// arg0.getSimpleName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExtendsClause() != null) {
			arg0.getExtendsClause().accept(this, arg1);
		}
		if(arg0.getImplementsClause() != null) {
			for(int i = 0; i < arg0.getImplementsClause().size(); ++i) {
				arg0.getImplementsClause().get(i).accept(this, arg1);
			}
		}
		if(arg0.getMembers() != null) {
			for(int i = 0; i < arg0.getMembers().size(); ++i) {
				arg0.getMembers().get(i).accept(this, arg1);
			}
		}
		if(arg0.getModifiers() != null) {
			arg0.getModifiers().accept(this, arg1);
		}
		if(arg0.getTypeParameters() != null) {
			for(int i = 0; i < arg0.getTypeParameters().size(); ++i) {
				arg0.getTypeParameters().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitCompilationUnit(CompilationUnitTree arg0, D arg1) {
		// arg0.getSourceFile() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getPackageAnnotations() != null) {
			for(int i = 0; i < arg0.getPackageAnnotations().size(); ++i) {
				arg0.getPackageAnnotations().get(i).accept(this, arg1);
			}
		}
		if(arg0.getPackageName() != null) {
			arg0.getPackageName().accept(this, arg1);
		}
		if(arg0.getImports() != null) {
			for(int i = 0; i < arg0.getImports().size(); ++i) {
				arg0.getImports().get(i).accept(this, arg1);
			}
		}
		if(arg0.getTypeDecls() != null) {
			for(int i = 0; i < arg0.getTypeDecls().size(); ++i) {
				arg0.getTypeDecls().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitCompoundAssignment(CompoundAssignmentTree arg0,
			D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getVariable() != null) {
			arg0.getVariable().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitConditionalExpression(ConditionalExpressionTree arg0,
			D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getFalseExpression() != null) {
			arg0.getFalseExpression().accept(this, arg1);
		}
		if(arg0.getTrueExpression() != null) {
			arg0.getTrueExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitContinue(ContinueTree arg0, D arg1) {
		// arg0.getLabel() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitDoWhileLoop(DoWhileLoopTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getStatement() != null) {
			arg0.getStatement().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitEmptyStatement(EmptyStatementTree arg0, D arg1) {
		// Empty node.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitEnhancedForLoop(EnhancedForLoopTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getStatement() != null) {
			arg0.getStatement().accept(this, arg1);
		}
		if(arg0.getVariable() != null) {
			arg0.getVariable().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitErroneous(ErroneousTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getErrorTrees() != null) {
			for(int i = 0; i < arg0.getErrorTrees().size(); ++i) {
				arg0.getErrorTrees().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitExpressionStatement(ExpressionStatementTree arg0,
			D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitForLoop(ForLoopTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getInitializer() != null) {
			for(int i = 0; i < arg0.getInitializer().size(); ++i) {
				arg0.getInitializer().get(i).accept(this, arg1);
			}
		}
		if(arg0.getStatement() != null) {
			arg0.getStatement().accept(this, arg1);
		}
		if(arg0.getUpdate() != null) {
			for(int i = 0; i < arg0.getUpdate().size(); ++i) {
				arg0.getUpdate().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitIdentifier(IdentifierTree arg0, D arg1) {
		// arg0.getName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitIf(IfTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getThenStatement() != null) {
			arg0.getThenStatement().accept(this, arg1);
		}
		if(arg0.getElseStatement() != null) {
			arg0.getElseStatement().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitImport(ImportTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getQualifiedIdentifier() != null) {
			arg0.getQualifiedIdentifier().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitInstanceOf(InstanceOfTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitIntersectionType(IntersectionTypeTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBounds() != null) {
			for(int i = 0; i < arg0.getBounds().size(); ++i) {
				arg0.getBounds().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitLabeledStatement(LabeledStatementTree arg0, D arg1) {
		// arg0.getLabel() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getStatement() != null) {
			arg0.getStatement().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitLambdaExpression(LambdaExpressionTree arg0, D arg1) {
		// arg0.getBodyKind() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBody() != null) {
			arg0.getBody().accept(this, arg1);
		}
		if(arg0.getParameters() != null) {
			for(int i = 0; i < arg0.getParameters().size(); ++i) {
				arg0.getParameters().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitLiteral(LiteralTree arg0, D arg1) {
		// arg0.getValue() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitMemberReference(MemberReferenceTree arg0, D arg1) {
		// arg0.getMode() do not have an accept method.
		// arg0.getName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getQualifierExpression() != null) {
			arg0.getQualifierExpression().accept(this, arg1);
		}
		if(arg0.getTypeArguments() != null) {
			for(int i = 0; i < arg0.getTypeArguments().size(); ++i) {
				arg0.getTypeArguments().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitMemberSelect(MemberSelectTree arg0, D arg1) {
		// arg0.getIdentifier() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitMethod(MethodTree arg0, D arg1) {
		// arg0.getName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBody() != null) {
			arg0.getBody().accept(this, arg1);
		}
		if(arg0.getDefaultValue() != null) {
			arg0.getDefaultValue().accept(this, arg1);
		}
		if(arg0.getModifiers() != null) {
			arg0.getModifiers().accept(this, arg1);
		}
		if(arg0.getParameters() != null) {
			for(int i = 0; i < arg0.getParameters().size(); ++i) {
				arg0.getParameters().get(i).accept(this, arg1);
			}
		}
		if(arg0.getReceiverParameter() != null) {
			arg0.getReceiverParameter().accept(this, arg1);
		}
		if(arg0.getReturnType() != null) {
			arg0.getReturnType().accept(this, arg1);
		}
		if(arg0.getThrows() != null) {
			for(int i = 0; i < arg0.getThrows().size(); ++i) {
				arg0.getThrows().get(i).accept(this, arg1);
			}
		}
		if(arg0.getTypeParameters() != null) {
			for(int i = 0; i < arg0.getTypeParameters().size(); ++i) {
				arg0.getTypeParameters().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitMethodInvocation(MethodInvocationTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getArguments() != null) {
			for(int i = 0; i < arg0.getArguments().size(); ++i) {
				arg0.getArguments().get(i).accept(this, arg1);
			}
		}
		if(arg0.getMethodSelect() != null) {
			arg0.getMethodSelect().accept(this, arg1);
		}
		if(arg0.getTypeArguments() != null) {
			for(int i = 0; i < arg0.getTypeArguments().size(); ++i) {
				arg0.getTypeArguments().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitModifiers(ModifiersTree arg0, D arg1) {
		// arg0.getFlags() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getAnnotations() != null) {
			for(int i = 0; i < arg0.getAnnotations().size(); ++i) {
				arg0.getAnnotations().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitNewArray(NewArrayTree arg0, D arg1) {
		// Elements of arg0.getDimAnnotations() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getAnnotations() != null) {
			for(int i = 0; i < arg0.getAnnotations().size(); ++i) {
				arg0.getAnnotations().get(i).accept(this, arg1);
			}
		}
		if(arg0.getDimensions() != null) {
			for(int i = 0; i < arg0.getDimensions().size(); ++i) {
				arg0.getDimensions().get(i).accept(this, arg1);
			}
		}
		if(arg0.getInitializers() != null) {
			for(int i = 0; i < arg0.getInitializers().size(); ++i) {
				arg0.getInitializers().get(i).accept(this, arg1);
			}
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitNewClass(NewClassTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getArguments() != null) {
			for(int i = 0; i < arg0.getArguments().size(); ++i) {
				arg0.getArguments().get(i).accept(this, arg1);
			}
		}
		if(arg0.getClassBody() != null) {
			arg0.getClassBody().accept(this, arg1);
		}
		if(arg0.getEnclosingExpression() != null) {
			arg0.getEnclosingExpression().accept(this, arg1);
		}
		if(arg0.getIdentifier() != null) {
			arg0.getIdentifier().accept(this, arg1);
		}
		if(arg0.getTypeArguments() != null) {
			for(int i = 0; i < arg0.getTypeArguments().size(); ++i) {
				arg0.getTypeArguments().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitOther(Tree arg0, D arg1) {
		// Empty node.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitParameterizedType(ParameterizedTypeTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		if(arg0.getTypeArguments() != null) {
			for(int i = 0; i < arg0.getTypeArguments().size(); ++i) {
				arg0.getTypeArguments().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitParenthesized(ParenthesizedTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitPrimitiveType(PrimitiveTypeTree arg0, D arg1) {
		// arg0.getPrimitiveTypeKind() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitReturn(ReturnTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitSwitch(SwitchTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getCases() != null) {
			for(int i = 0; i < arg0.getCases().size(); ++i) {
				arg0.getCases().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitSynchronized(SynchronizedTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getBlock() != null) {
			arg0.getBlock().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitThrow(ThrowTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitTry(TryTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBlock() != null) {
			arg0.getBlock().accept(this, arg1);
		}
		if(arg0.getCatches() != null) {
			for(int i = 0; i < arg0.getCatches().size(); ++i) {
				arg0.getCatches().get(i).accept(this, arg1);
			}
		}
		if(arg0.getFinallyBlock() != null) {
			arg0.getFinallyBlock().accept(this, arg1);
		}
		if(arg0.getResources() != null) {
			for(int i = 0; i < arg0.getResources().size(); ++i) {
				arg0.getResources().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitTypeCast(TypeCastTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitTypeParameter(TypeParameterTree arg0, D arg1) {
		// arg0.getName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getAnnotations() != null) {
			for(int i = 0; i < arg0.getAnnotations().size(); ++i) {
				arg0.getAnnotations().get(i).accept(this, arg1);
			}
		}
		if(arg0.getBounds() != null) {
			for(int i = 0; i < arg0.getBounds().size(); ++i) {
				arg0.getBounds().get(i).accept(this, arg1);
			}
		}
		return null;
	}

	@Override
	public R visitUnary(UnaryTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getExpression() != null) {
			arg0.getExpression().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitUnionType(UnionTypeTree arg0, D arg1) {
		// arg0.getTypeAlternatives() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		return null;
	}

	@Override
	public R visitVariable(VariableTree arg0, D arg1) {
		// arg0.getName() do not have an accept method.
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getInitializer() != null) {
			arg0.getInitializer().accept(this, arg1);
		}
		if(arg0.getModifiers() != null) {
			arg0.getModifiers().accept(this, arg1);
		}
		if(arg0.getNameExpression() != null) {
			arg0.getNameExpression().accept(this, arg1);
		}
		if(arg0.getType() != null) {
			arg0.getType().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitWhileLoop(WhileLoopTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getCondition() != null) {
			arg0.getCondition().accept(this, arg1);
		}
		if(arg0.getStatement() != null) {
			arg0.getStatement().accept(this, arg1);
		}
		return null;
	}

	@Override
	public R visitWildcard(WildcardTree arg0, D arg1) {
		if(arg1 != null) {
			arg1.treat(arg0);
		}
		if(arg0.getBound() != null) {
			arg0.getBound().accept(this, arg1);
		}
		return null;
	}

}
