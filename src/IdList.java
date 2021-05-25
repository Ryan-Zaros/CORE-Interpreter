class IdList {
	Id id;
	IdList list;
	
	void parse() {
		id = new Id();
		id.parse();
		if (Parser.scanner.currentToken() == Core.COMMA) {
			Parser.scanner.nextToken();
			list = new IdList();
			list.parse();
		} 
	}
	
	void semantic() {
		id.renameToInt();
		id.doublyDeclared();
		id.addToScope();
		if (list != null) {
			list.semantic();
		}
	}
	
	void print() {
		id.print();
		if (list != null) {
			System.out.print(",");
			list.print();
		}
	}
	
	void execute() {
		id.executeAllocate();
		if (list != null) {
			list.execute();
		}
	}
}