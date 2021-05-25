# CORE_Interpreter

Includes a scanner, parser, and executor for a simple, made-up programming language called CORE. The grammar for the language can be found below. The parser and executor were advanced to handle functions, calling, and garbage collection as well.

Files are included for each non-terminal in the language, as well as for the 3 basic language components. Each non-terminal has parsing, semantic checking, and executing methods. 

The scanner using whitespace as delimiters and tokenizes the input. IDs and CONSTANTs are special tokens with values. The parsing and executing methods deal with each non-terminal accordingly. Various data structures are used to keep track of variables (global and local), pass parameter values in functions, and emulate a heap.


`<prog> ::= program <decl-seq> begin <stmt-seq> end | program begin <stmt-seq> end` <br>
`<decl-seq> ::= <decl> | <decl><decl-seq> | <func-decl> | <func-decl><decl-seq>` <br>
`<stmt-seq> ::= <stmt> | <stmt><stmt-seq>` <br>
`<decl> ::= int <id-list> ;` <br>
`<id-list> ::= id | id , <id-list>` <br>
`<func-decl> ::= id ( <formals> ) begin <stmt-seq> endfunc` <br>
`<formals> ::= id | id , <formals>` <br>
`<stmt> ::= <assign> | <if> | <loop> | <in> | <out> | <decl> | <func-call> | <new-decl>` <br>
`<new-decl> ::= define id;` <br>
`<func-call> ::= begin id ( <formals> ) ;` <br>
`<assign> ::= id = <expr> ; | id = new <expr> ; | id = define id ;` <br>
`<in> ::= input id ;` <br>
`<out> ::= output <expr> ;` <br>
`<if> ::= if <cond> then <stmt-seq> endif | if <cond> then <stmt-seq> else <stmt-seq> endif` <br>
`<loop> ::= while <cond> begin <stmt-seq> endwhile` <br>
`<cond> ::= <cmpr> | ! ( <cond> ) | <cmpr> or <cond>` <br>
`<cmpr> ::= <expr> == <expr> | <expr> < <expr> | <expr> <= <expr>` <br>
`<expr> ::= <term> | <term> + <expr> | <term> – <expr>` <br>
`<term> ::= <factor> | <factor> * <term>` <br>
`<factor> ::= id | const | ( <expr> )` <br>
