package expr.visitor;

import expr.composite.*;

public interface Visitor {

	void visitAnd(Conjunction conjunction);

	void visitOr(Disjunction disjunction);

	void visitNot(Negation negation);

	void visitBoolFalse(BoolFalse boolFalse);

	void visitBoolTrue(BoolTrue boolTrue);

	void visitBoolVar(BoolVar boolVar);

	void visitEqual(Equal equal);

	void visitGreaterThan(GreaterThan greaterThan);

	void visitLessThan(LessThan lessThan);

	void visitGreaterEqual(GreaterEqual greaterEqual);

	void visitLessEqual(LessEqual lessEqual);

	void visitShiftLeft(ShiftLeft shiftLeft);

	void visitFunction(Function function);
}
