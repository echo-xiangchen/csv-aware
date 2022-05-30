package expr.composite;

import expr.visitor.Visitor;

public class Function extends Expr {

	public Function(String name) {
		this.name = name;
		
	}
	
	public void accept(Visitor v) {
		v.visitFunction(this);
	}
}
