package expr.visitor;

import java.util.LinkedHashMap;
import java.util.Map;

import expr.composite.*;

public class FeatureVarextractor implements Visitor {
	
	// hashmap stores the variables
	public static Map<String, Long> varMap = new LinkedHashMap<String, Long>();

	public void visitBinary(BinaryExpr e) {
		FeatureVarextractor left = new FeatureVarextractor();
		FeatureVarextractor right = new FeatureVarextractor();
		
		e.left().accept(left);
		e.right().accept(right);
	}
	
	@Override
	public void visitAnd(Conjunction conjunction) {
		visitBinary(conjunction);
	}

	@Override
	public void visitOr(Disjunction disjunction) {
		visitBinary(disjunction);
	}

	@Override
	public void visitNot(Negation negation) {
		FeatureVarextractor bb = new FeatureVarextractor();
		negation.child.accept(bb);
		
	}

	@Override
	public void visitBoolFalse(BoolFalse boolFalse) {
		if (!varMap.containsKey(boolFalse.name)) {
			varMap.put(boolFalse.name, null);
		}
	}

	@Override
	public void visitBoolTrue(BoolTrue boolTrue) {
		if (!varMap.containsKey(boolTrue.name)) {
			varMap.put(boolTrue.name, null);
		}
	}

	@Override
	public void visitBoolVar(BoolVar boolVar) {
		if (!varMap.containsKey(boolVar.name)) {
			varMap.put(boolVar.name, null);
		}
	}

	@Override
	public void visitEqual(Equal equal) {
		visitBinary(equal);
	}

	@Override
	public void visitGreaterThan(GreaterThan greaterThan) {
		visitBinary(greaterThan);
	}

	@Override
	public void visitLessThan(LessThan lessThan) {
		visitBinary(lessThan);
	}

	@Override
	public void visitGreaterEqual(GreaterEqual greaterEqual) {
		visitBinary(greaterEqual);
	}

	@Override
	public void visitLessEqual(LessEqual lessEqual) {
		visitBinary(lessEqual);
	}

	@Override
	public void visitShiftLeft(ShiftLeft shiftLeft) {
		visitBinary(shiftLeft);
	}

	@Override
	public void visitFunction(Function function) {
		if (!varMap.containsKey(function.name)) {
			varMap.put(function.name, null);
		}
	}
}
