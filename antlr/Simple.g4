grammar Simple;

@header
{
	package simple.antlr;
}


program: declaration+ EOF;

COMMENT: '//' ~('\r' | '\n')* -> skip;

declaration: vardeclaration ';'
	| functiondeclaration 
	;

vardeclaration: type NAME 
	;
type: INTTYPE | STRTYPE 
	;

functiondeclaration: type NAME '(' args? ')' block 
	;
args: vardeclaration (',' vardeclaration)* 
	;
block: '{' statement* '}' 
	;

statement:declaration
	| assignment ';'
	| value ';'
	| returnstatement ';'
	| ifblock 
	| whileblock
	| forblock 
	;

assignment: NAME '=' value
	| NAME '=' compareop
	;


value: NAME
	| literal
	| functioncall
	| strop
	| exp
	;

strop: ( NAME | STRING ) '[' value ']' #NthChar
	| ( NAME | STRING ) '[' ',' value ']' #ToNthChar
	| ( NAME | STRING ) '[' value ',' ']' #FromNthChar
	| ( NAME | STRING ) '[' value ',' value ']' #FromNthToMthChar
	| ( NAME | STRING ) '#' value #FirstIndexOf 
	| ( NAME | STRING ) '##' value #LastIndexOF
	| ( NAME | STRING ) '%' value #FrontTrim
	| ( NAME | STRING ) '%%' value #BackTrim
	;

literal: STRING
	| NUMBER
	;

functioncall: NAME '(' callargs? ')' 
	;
callargs: value (',' value)* 
	;


exp: mexp (PLUSMINUS mexp)*
	;

mexp: pexp (TIMESDIVMOD pexp)*
	;

pexp: atom (POW atom)*
	;

atom : NUMBER
	| functioncall
	| NAME
	| strop
	| '(' exp ')'
	;

ifblock: 'if' '(' (value | compareop ) ')' block ('else' block)?
	;

whileblock: 'while' '(' ( value | compareop ) ')' block
	;

forblock: 'for' '(' INTTYPE NAME ',' NUMBER ',' NUMBER ')' block
	;

returnstatement: 'return' value 
	;

compareop: value COP value
	;


PLUSMINUS: '+' | '-' ;
TIMESDIVMOD: '*' | '/' | '%' ;

POW: '**' ;

INTTYPE: 'int ' ;
STRTYPE: 'str ' ;
COP: '<' | '>' | '<=' | '>=' | '==' | '!=' ;
NAME: [a-zA-Z_]+ [a-zA-Z0-9_]* ;
NUMBER: '-'? ([0-9]+) | ('-' [1-9][0-9]*) ;
STRING: '\'' (~'\'')* '\'' | '\"' (~'\"')* '\"';

WS: [ \t\r\n] -> skip;