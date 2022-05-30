package expr.composite;

import expr.visitor.Visitor;

public class GreaterThan extends BinaryExpr {

	public GreaterThan(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitGreaterThan(this);
	}
}
