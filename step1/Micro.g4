grammar Micro;
IDENTIFIER
	:	/b([a-zA-Z])([a-zA-Z0-9])*/b
	;

INTLITERAL
	:	/b[0-9]+/b
	;

FLOATLITERAL
	:   /b[0-9]*/.[0-9]+/b
	;

STRINGLITERAL
	:	'"'(~'"')*'"'
	;

COMMENT
	:	'--'.*('/r'?'/n'|'r')
	;

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
