class Assign implements Stmt {
	Id id;
	Id refId;
	Expr expr;
	
	public void parse() {
		id = new Id();
		id.parse(); // Parse and consume ID
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken(); // Consume ASSIGN
		// Case for ID = define ID
		if (Parser.scanner.currentToken() == Core.DEFINE) {
			Parser.scanner.nextToken(); // Consume DEFINE
			refId = new Id();
			refId.parse(); // Parse and consume second ID
			// Rename both ID's to indicate references
			refId.renameToRef();
			id.renameToRef();
		// Case for ID = new EXPR
		} else if (Parser.scanner.currentToken() == Core.NEW) {
			Parser.scanner.nextToken(); // Consume NEW
			expr = new Expr();
			expr.parse(); // Parse and consume EXPR
			id.renameToRef(); // Rename to indicate reference
		// Original case for ID = EXPR
		} else {
			expr = new Expr();
			expr.parse(); // Parse and consume EXPR
			id.renameToInt(); // Rename to indicate integer
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken(); // Consume SEMICOLON
	}
	
	public void semantic() {
		//id.semantic();
//		if (refId != null) {
//			refId.semantic();
//		}
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		id.print();
		System.out.print("=");
		expr.print();
		System.out.println(";");
	}
	
	public void execute() {
		if (expr != null) { // If there's no EXPR, then it's not the 3rd case
			int exprResult = expr.execute();
			if (id.getString().charAt(0) == '0') { // Original, first case
				id.executeAssign(exprResult); // assign normally
			} else if (id.getString().charAt(0) == '1') { // Reference variable "new" case
				Executor.heap.add(exprResult); // add to heap
				id.executeAssign(Executor.heap.indexOf(exprResult)); // assign with list index
				Executor.garbageCollector.add(Executor.heap.indexOf(exprResult), 1); // add index to GC
				Executor.printGC(); // print since we added 1 to the GC count
			}
		} else { // Else it's the 3rd case, "define"
			if (Executor.varGet(refId.getString()) != null) {
				id.executeAssign(Executor.varGet(refId.getString())); // get index of the 2nd ID and copy/assign it to the 1st
			}
		}
	}
}
