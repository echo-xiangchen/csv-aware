package expr.composite;

import expr.visitor.Visitor;

public class Equal extends BinaryExpr {

	public Equal(Expr expr1, Expr expr2) {
		super(expr1, expr2);
	}
	
	public void accept(Visitor v) {
		v.visitEqual(this);
	}

}
