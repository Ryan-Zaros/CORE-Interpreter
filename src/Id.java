class Id {
	String identifier;
	
	void parse() {
		Parser.expectedToken(Core.ID);
		identifier = Parser.scanner.getID();
		Parser.scanner.nextToken();
	}
	
	// Altered to work with '0' and '1' variables.
	void semantic() {
		String intIdentifier = '0' + identifier;
		String refIdentifier = '1' + identifier;
		if ((!Parser.nestedScopeCheck(intIdentifier)) && (!Parser.nestedScopeCheck(refIdentifier)) && (!Parser.nestedScopeCheck(identifier))) {
			System.out.println("ERROR: No matching int or reference declaration found: " + identifier);
			new Exception().printStackTrace();
			System.exit(0);
		}
	}
	
	//Called by Decl.semantic to add the variable to the scopes data structure
	void addToScope() {
		Parser.scopes.peek().add(identifier);
	}
	
	//Called by Decl.semantic to check for doubly declared variables
	void doublyDeclared() {
		if (Parser.currentScopeCheck(identifier)) {
			System.out.println("ERROR: Doubly declared variable detected: " + identifier);
			System.exit(0);
		}
	}
	
	void print() {
		System.out.print(identifier);
	}
	
	//Called by IdList.execute
	void executeAllocate() {
		Executor.varInit(identifier);
	}
	
	//Called by Assign.execute and Input.execute
	void executeAssign(int value) {
		Executor.varSet(identifier, value);
	}
	
	//Called by Factor.execute
	// Altered to work with '0' and '1' variables.
	int executeValue() {
		Integer value = null;
		// Check if it's a reference variable
		if (Executor.varGet('1' + identifier) != null) {
			value = Executor.heap.get(Executor.varGet('1' + identifier));
		// Otherwise it's a normal integer variable
		} else if (Executor.varGet('0' + identifier) != null) {
			value = Executor.varGet('0' + identifier);
		} else {
			System.out.println("Error: Using uninitialized variable " + identifier);
			new Exception().printStackTrace();
			System.exit(0);
		}
		return value;
	}
	
	String getString() {
		return identifier;
	}
	
	void renameToInt() {
		identifier = '0' + identifier;
	}
	
	void renameToRef() {
		identifier = '1' + identifier;
	}
}