package expr;

import static info.scce.addlib.cudd.Cudd.*;

import java.util.*;
import expr.antlr.*;
import expr.antlr.PCparserParser.*;
import expr.composite.*;

public class Antlr2BDD extends PCparserBaseVisitor<Long> {
	
	
	/* *****************************************************************************************
	 * TODO Methods for boolExpr rule
	 * *****************************************************************************************
	 */
	/* Initialize DDManager with default values */
	public static long ddManager = Cudd_Init(0, 0, CUDD_UNIQUE_SLOTS, CUDD_CACHE_SLOTS, 0);
	
	private static int varIndex = 0;
	
	// hashmap stores the variables
	private static Map<String, Long> varMap = new LinkedHashMap<String, Long>();
	
	// array list that stores all the addresses
	//private static ArrayList<Long> address = new ArrayList<Long>();
	
	// Negation
	@Override
	public Long visitNot(NotContext ctx) {
		long not = Cudd_Not(visit(ctx.boolExpr()));
		
		Cudd_Ref(not);
		return not;
	}
	
	// Conjunction
	@Override
	public Long visitAnd(AndContext ctx) {
		long and = Cudd_bddAnd(ddManager, visit(ctx.boolExpr(0)), visit(ctx.boolExpr(1)));
		
		Cudd_Ref(and);
		return and;
	}
	
	// Disjunction
	@Override
	public Long visitOr(OrContext ctx) {
		long or = Cudd_bddOr(ddManager, visit(ctx.boolExpr(0)), visit(ctx.boolExpr(1)));
		
		Cudd_Ref(or);
		return or;
	}
		
	// boolean true declaration
		@Override
		public Long visitBoolTrue(BoolTrueContext ctx) {
			return Cudd_ReadOne(ddManager);
		}
		
		// boolean false declaration
		@Override
		public Long visitBoolFalse(BoolFalseContext ctx) {
			return Cudd_ReadLogicZero(ddManager);
		}
		
	// boolean variable verification
	@Override
	public Long visitBoolVar(BoolVarContext ctx) {
		// need to check if the variable has been created
		if (!varMap.containsKey(ctx.ID().getText())) {
			// create the variable and then increment the index
			long var = Cudd_bddIthVar(ddManager, varIndex);
			varIndex++;
			//Cudd_Ref(var);
			
			// store the variable to the map
			varMap.put(ctx.ID().getText(), var);
		}
		
		//address.add(varMap.get(boolVar.name));
		return varMap.get(ctx.ID().getText());
	}
	
	
	// parentheses
	@Override
	public Long visitParen(ParenContext ctx) {
		return visit(ctx.boolExpr());
	}
}
