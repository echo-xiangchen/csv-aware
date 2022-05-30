package expr.composite;

import expr.visitor.Visitor;

public class LessThan extends BinaryExpr {

	public LessThan(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitLessThan(this);
	}
}
