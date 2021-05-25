import java.util.*;

class If implements Stmt {
	Cond cond;
	StmtSeq ss1;
	StmtSeq ss2;
	
	public void parse() {
		Parser.scanner.nextToken();
		cond = new Cond();;
		cond.parse();
		Parser.expectedToken(Core.THEN);
		Parser.scanner.nextToken();
		ss1 = new StmtSeq();
		ss1.parse();
		if (Parser.scanner.currentToken() == Core.ELSE) {
			Parser.scanner.nextToken();
			ss2 = new StmtSeq();
			ss2.parse();
		}
		Parser.expectedToken(Core.ENDIF);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		cond.semantic();
		Parser.scopes.push(new ArrayList<String>());
		ss1.semantic();
		Parser.scopes.pop();
		if (ss2 != null) {
			Parser.scopes.push(new ArrayList<String>());
			ss2.semantic();
			Parser.scopes.pop();
		}
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("if ");
		cond.print();
		System.out.println(" then");
		ss1.print(indent+1);
		if (ss2 != null) {
			for (int i=0; i<indent; i++) {
				System.out.print("	");
			}
			System.out.println("else");
			ss2.print(indent+1);
		}
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.println("endif");
	}
	
	public void execute() {
		if (cond.execute()) {
			//Add a map for the new local scope, pop when done
			Executor.pushScope();
			ss1.execute();
			Executor.popScope();
		} else if (ss2 != null) {
			//Add a map for the new local scope, pop when done
			Executor.pushScope();
			ss2.execute();
			Executor.popScope();
		}
	}
}