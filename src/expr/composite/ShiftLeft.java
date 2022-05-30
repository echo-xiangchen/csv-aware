package expr.composite;

import expr.visitor.Visitor;

public class ShiftLeft extends BinaryExpr {

	public ShiftLeft(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitShiftLeft(this);
	}
}
