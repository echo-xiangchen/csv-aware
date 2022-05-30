package expr;

import expr.antlrExtend.*;
import expr.antlrExtend.PCparserParser.*;
import expr.composite.*;

public class Antlr2ExprExtend extends PCparserBaseVisitor<Expr> {
	
	
	/* *****************************************************************************************
	 * TODO Methods for boolExpr rule
	 * *****************************************************************************************
	 */
	
	// Negation
	@Override
	public Expr visitNot(NotContext ctx) {
		return new Negation(visit(ctx.expr()));
	}
	
	// Conjunction
	@Override
	public Expr visitAnd(AndContext ctx) {
		return new Conjunction(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Disjunction
	@Override
	public Expr visitOr(OrContext ctx) {
		return new Disjunction(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Equal
	@Override
	public Expr visitEqual(EqualContext ctx) {
		return new Equal(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Less than
	@Override
	public Expr visitLessThan(LessThanContext ctx) {
		return new LessThan(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Less equal
	@Override
	public Expr visitLessOrEqual(LessOrEqualContext ctx) {
		return new LessEqual(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Greater than
	@Override
	public Expr visitGreaterThan(GreaterThanContext ctx) {
		return new GreaterThan(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Greater equal
	@Override
	public Expr visitGreaterOrEqual(GreaterOrEqualContext ctx) {
		return new GreaterEqual(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
	
	// Shift left
	@Override
	public Expr visitShiftLeft(ShiftLeftContext ctx) {
		return new ShiftLeft(visit(ctx.expr(0)), visit(ctx.expr(1)));
	}
		
	// boolean true declaration
	@Override
	public Expr visitBoolTrue(BoolTrueContext ctx) {
		return new BoolTrue(ctx.TRUE().getText());
	}
		
		// boolean false declaration
		@Override
		public Expr visitBoolFalse(BoolFalseContext ctx) {
			return new BoolFalse(ctx.FALSE().getText());
		}
		
	// boolean variable verification
	@Override
	public Expr visitBoolVar(BoolVarContext ctx) {
		return new BoolVar(ctx.VAR().getText());
	}
	
	// function
//	@Override
//	public Expr visitFunc(FuncContext ctx) {
//		return new Function(ctx.FUNC().getText());
//	}
	
	
	// parentheses
	@Override
	public Expr visitParen(ParenContext ctx) {
		return visit(ctx.expr());
	}
	
	// relationalExpr
//	@Override
//	public Expr visitRelate(RelateContext ctx) {
//		return visit(ctx.relationalExpr());
//	}
//	
//	@Override
//	public Expr visitRelationVar(RelationVarContext ctx) {
//		return new BoolVar(ctx.VAR().getText());
//	}
//	
//	@Override
//	public Expr visitRelationParen(RelationParenContext ctx) {
//		return visit(ctx.relationalExpr());
//	}
}
