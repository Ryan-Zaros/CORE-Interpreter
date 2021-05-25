import java.util.*; 

class FuncDecl {
	Id name;
	Formals list;
	StmtSeq body;
	
	void parse() {
		Parser.expectedToken(Core.ID);
		name = new Id();
		name.parse();
		Parser.expectedToken(Core.LPAREN);
		Parser.scanner.nextToken();
		list = new Formals();
		list.parse();
		Parser.expectedToken(Core.RPAREN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.BEGIN);
		Parser.scanner.nextToken();
		body = new StmtSeq();
		body.parse();
		Parser.expectedToken(Core.ENDFUNC);
		Parser.scanner.nextToken();
	}
	
	void semantic() {
		if (Parser.functionDeclaredCheck(name.getString())) {
			//Function name has already been used, print error msg
			System.out.println("ERROR: function " + name.getString() + " declared twice!");
			System.exit(0);
		}
		Parser.funcNames.add(name.getString());
		List<String> formals = list.getListOfStrings();
		for (String test : formals) {
			if (formals.indexOf(test) != formals.lastIndexOf(test)) {
				System.out.println("ERROR: " + test + " used twice as formal parameter!");
				System.exit(0);
			}
		}
	}
	
	void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		name.print();
		System.out.print("(");
		list.print();
		System.out.println(") begin");
		body.print(indent+1);
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.println("endfunc");
	}
	
	void execute() {
		Executor.registerFunction(name, this);
	}
	
	List<String> getFormalParametersList() {
		return list.getListOfStrings();
	}
	
	StmtSeq getBody() {
		return body;
	}
}