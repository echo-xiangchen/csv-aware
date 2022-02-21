grammar PCparser;

@header {
	package antlr;
}

stat : boolExpr* ;

boolExpr 
	: NOT boolExpr									# Not
	| boolExpr AND boolExpr							# And
	| boolExpr OR boolExpr							# Or
//	| boolExpr IFF boolExpr							# Iff
	| ID 											# BoolVar
	| TRUE 											# BoolTrue
	| FALSE 										# BoolFalse
	| '(' boolExpr ')'								# Paren
//	| relationalExpr								# Relate 
	;



//relationalExpr
//	: arithmeticExpr EQUAL arithmeticExpr			    # Equal
//	| arithmeticExpr GREATERTHAN arithmeticExpr		  	# GreaterThan
//	| arithmeticExpr LESSTHAN arithmeticExpr		    # LessThan
//	| arithmeticExpr GREATEROREQUAL arithmeticExpr		# GreaterOrEqual
//	| arithmeticExpr LESSOREQUAL arithmeticExpr		  	# LessOrEqual
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
NOT : '!' | 'not';
AND : '&&' | 'and' | '/\\';
OR : '||' | 'or' | '\\/';
// IMPLIES : '=>';
// IFF : '<=>';

// relational expr keywords
// EQUAL : '=';
// GREATERTHAN : '>';
// LESSTHAN : '<';
// GREATEROREQUAL : '>=';
// LESSOREQUAL : '<=';

// arithmetic expr keyworkds
// MUL : '*';
// DIV : '/';
// ADD : '+';
// SUB : '-';

// ignore the comments and whitespace
COMMENT : '--' ~[\r\n]* -> skip;
WS  :   [ \t\n]+ -> skip ;

ID : [a-zA-Z0-9_]+;

// number lexer rules
// INTNUM : '0'|'-'?[1-9][0-9]*;
// REALNUM : '-'?[0-9]* '.' [0-9]+;
