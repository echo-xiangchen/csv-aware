package expr.composite;

import expr.visitor.Visitor;

public class LessEqual extends BinaryExpr {

	public LessEqual(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitLessEqual(this);
	}
}
