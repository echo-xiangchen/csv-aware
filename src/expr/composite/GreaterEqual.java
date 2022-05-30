package expr.composite;

import expr.visitor.Visitor;

public class GreaterEqual extends BinaryExpr {

	public GreaterEqual(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitGreaterEqual(this);
	}
}
