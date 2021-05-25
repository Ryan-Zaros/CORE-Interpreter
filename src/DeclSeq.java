class DeclSeq {
	Decl decl;
	FuncDecl function;
	DeclSeq ds;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.INT) {
			decl = new Decl();
			decl.parse();
		} else {
			function = new FuncDecl();
			function.parse();
		}
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void semantic() {
		if (decl != null) {
			decl.semantic();
		} else {
			function.semantic();
		}
		if (ds != null) {
			ds.semantic();
		}
	}
	
	void print(int indent) {
		if (decl!= null) {
			decl.print(indent);
		} else {
			function.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		if (decl!= null) {
			decl.execute();
		} else {
			function.execute();
		}
		if (ds != null) {
			ds.execute();
		}
	}
}