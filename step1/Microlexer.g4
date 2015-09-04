lexer grammar Microlexer;
KEYWORD
	:	'PROGRAM'
	|	'BEGIN'
	|	'END'
	|	'FUNCTION'
	|	'READ'
	|	'WRITE'
	|	'IF'
	|	'ELSE'
	|	'FI'
	|	'FOR'
	|	'ROF'
	|	'CONTINUE'
	|	'BREAK'
	|	'RETURN'
	|	'INT'
	|	'VOID'	
	|	'STRING'
	|	'FLOAT'
	;

IDENTIFIER
	:	[_a-zA-Z][_a-zA-Z0-9]*
	;

INTLITERAL
	:	[0-9]+
	;

FLOATLITERAL
	:   [0-9]* '.' [0-9]+
	;

STRINGLITERAL
	:	'"'(~'"')*'"'
	;

COMMENT
	:	('--'(~'\n')* '\n') -> skip;


OPERATOR
	:	':='
	|	'+'
	|	'-'
	|	'*'
	|	'/'
	|	'='
	|	'!='
	|	'<'
	|	'>'
	|	'('
	|	')'
	|	';'
	|	','
	|	'<='
	|	'>='
	;

WS	:	('\t' | ' ' | '\r'|'\n'|'\u000C')+ -> skip;