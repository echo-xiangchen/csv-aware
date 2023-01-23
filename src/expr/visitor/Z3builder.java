package expr.visitor;

import expr.composite.*;
import java.util.*;

import com.microsoft.z3.*;




public class Z3builder implements Visitor {
	
	// hashmap stores the variables
	private static Map<String, BoolExpr> varMap = new LinkedHashMap<String, BoolExpr>();
	
	// create static context
	public static Context ctx = new Context();
	
	// create BoolExpr object
	private static BoolExpr boolExpr;
	
	public Z3builder() {

	}
	
	public BoolExpr getBoolExpr() {
		return boolExpr;
	}
	
	@Override
	public void visitAnd(Conjunction conjunction) {
		Z3builder left = new Z3builder();
		Z3builder right = new Z3builder();
		
		/* need to store the left address and right address
		 * because after calling disjunction.right().accept(right) cannot make sure that 
		 * the last two element is actually the left child's address and right child's address
		*/
		conjunction.left().accept(left);
		BoolExpr leftExpr = boolExpr;
		
		conjunction.right().accept(right);
		//long rightAddress = address.get(address.size() - 1);
		BoolExpr rightExpr = boolExpr;
		
		boolExpr = ctx.mkAnd(leftExpr, rightExpr);
		
	}

	@Override
	public void visitOr(Disjunction disjunction) {
		Z3builder left = new Z3builder();
		Z3builder right = new Z3builder();
		
		/* need to store the left address and right address
		 * because after calling disjunction.right().accept(right) cannot make sure that 
		 * the last two element is actually the left child's address and right child's address
		*/
		disjunction.left().accept(left);
		BoolExpr leftExpr = boolExpr;
		
		disjunction.right().accept(right);
		//long rightAddress = address.get(address.size() - 1);
		BoolExpr rightExpr = boolExpr;
		
		boolExpr = ctx.mkOr(leftExpr, rightExpr);
	}

	@Override
	public void visitNot(Negation negation) {
		Z3builder bb = new Z3builder();
		negation.child.accept(bb);

		
		boolExpr = ctx.mkNot(boolExpr);
	}

	@Override
	public void visitBoolFalse(BoolFalse boolFalse) {
		boolExpr = ctx.mkFalse();
	}

	@Override
	public void visitBoolTrue(BoolTrue boolTrue) {
		boolExpr = ctx.mkTrue();
	}

	@Override
	public void visitBoolVar(BoolVar boolVar) {
		// need to check if the variable has been created
		if (!varMap.containsKey(boolVar.name)) {
			// create the variable 
			boolExpr = ctx.mkBoolConst(boolVar.name);
			
			// store the variable to the map
			varMap.put(boolVar.name, boolExpr);
		}
		
		//address.add(varMap.get(boolVar.name));
		boolExpr = varMap.get(boolVar.name);
	}

	@Override
	public void visitEqual(Equal equal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitGreaterThan(GreaterThan greaterThan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLessThan(LessThan lessThan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitGreaterEqual(GreaterEqual greaterEqual) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLessEqual(LessEqual lessEqual) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitShiftLeft(ShiftLeft shiftLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitFunction(Function function) {
		// TODO Auto-generated method stub
		
	}
}
