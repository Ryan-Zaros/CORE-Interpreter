class Term {
	Factor factor;
	Term term;
	
	void parse() {
		factor = new Factor();
		factor.parse();
		if (Parser.scanner.currentToken() == Core.MULT) {
			Parser.scanner.nextToken();
			term = new Term();
			term.parse();
		}				
	}
	
	void semantic() {
		factor.semantic();
		if (term != null) {
			term.semantic();
		}
	}
	
	void print() {
		factor.print();
		if (term != null) {
			System.out.print("*");
			term.print();
		}
	}
	
	//Returns the int value of the term
	int execute() {
		int value = factor.execute();
		if (term != null) {
			value *= term.execute();
		}
		return value;
	}
}