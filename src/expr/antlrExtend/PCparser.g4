grammar PCparser;

//@header {
//	package antlr;
//}

stat : expr* ;

expr 
	: NOT expr								# Not
	| expr AND expr							# And
	| expr OR expr								# Or
//	| expr IFF expr							# Iff
	| expr EQUAL expr							# Equal
	| expr NOTEQUAL expr							# NotEqual
	| expr GREATERTHAN expr						# GreaterThan
	| expr LESSTHAN expr		    					# LessThan
	| expr GREATEROREQUAL expr						# GreaterOrEqual
	| expr LESSOREQUAL expr		  				# LessOrEqual
	| expr SHIFTLEFT expr							# ShiftLeft
	| TRUE 								# BoolTrue
	| FALSE 								# BoolFalse
//	| FUNC									# Func
	| VAR 									# BoolVar
	| '(' expr ')'								# Paren
//	| relationalExpr							# Relate 
	;



//relationalExpr
//	: relationalExpr EQUAL relationalExpr			    		# Equal
//	| relationalExpr GREATERTHAN relationalExpr		  		# GreaterThan
//	| relationalExpr LESSTHAN relationalExpr		    		# LessThan
//	| relationalExpr GREATEROREQUAL relationalExpr			# GreaterOrEqual
//	| relationalExpr LESSOREQUAL relationalExpr		  		# LessOrEqual
//	| relationalExpr SHIFTLEFT relationalExpr		  		# ShiftLeft
//	| VAR									# RelationVar
//	| '(' relationalExpr ')'						# RelationParen
//	;


//arithmeticExpr
//	: arithmeticExpr op=(MUL|DIV) arithmeticExpr		# MulDiv
//	| arithmeticExpr op=(ADD|SUB) arithmeticExpr		# AddSub
//	| ID 												# ArithmeticVar
//	| INTNUM											# Integer
//	| REALNUM											# Real
//	| '(' arithmeticExpr ')' 							# ArithParen
//	;


// boolean constant keywords
TRUE : 'true' | 'TRUE' | 'True';
FALSE : 'false' | 'FALSE' | 'False';

// logical expr keywords
NOT : '!';
AND : '&&';
OR : '||';

// relational expr keywords
EQUAL : '==';
NOTEQUAL : '!=';
GREATERTHAN : '>';
LESSTHAN : '<';
GREATEROREQUAL : '>=';
LESSOREQUAL : '<=';
SHIFTLEFT : '<<';

// specify ID
//FUNC : ID'()';
VAR : ID | ID'.'ID | ID'()' | ID'('ID')';
ID : [a-zA-Z0-9_/-]+;

//NOT : '!' | 'not';
//AND : '&&' | 'and' | '/\\';
//OR : '||' | 'or' | '\\/';
// IMPLIES : '=>';
// IFF : '<=>';



// arithmetic expr keyworkds
// MUL : '*';
// DIV : '/';
// ADD : '+';
// SUB : '-';

// ignore the comments and whitespace
//COMMENT : '--' ~[\r\n]* -> skip;
WS  :   [ \t\n]+ -> skip ;

// number lexer rules
// INTNUM : '0'|'-'?[1-9][0-9]*;
// REALNUM : '-'?[0-9]* '.' [0-9]+;
