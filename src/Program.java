import java.util.*;

class Program {
	DeclSeq ds;
	StmtSeq ss;
	
	void parse() {
		Parser.expectedToken(Core.PROGRAM);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
		Parser.expectedToken(Core.BEGIN);
		Parser.scanner.nextToken();
		ss = new StmtSeq();
		ss.parse();
		Parser.expectedToken(Core.END);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.EOF);
	}
	
	void semantic() {
		Parser.funcNames = new ArrayList<String>();
		Parser.scopes = new Stack<ArrayList<String>>();
		Parser.scopes.push(new ArrayList<String>());
		if (ds != null) {
			ds.semantic();
		}
		Parser.scopes.push(new ArrayList<String>());
		ss.semantic();
		Parser.scopes.pop();
	}
	
	void print() {
		System.out.println("program");
		if (ds != null) {
			ds.print(1);
		}
		System.out.println("begin");
		ss.print(1);
		System.out.println("end");
	}
	
	void execute() {
		// Initialize the data structures in Executor
		Executor.initExecutor();
		if (ds != null) {
			ds.execute();
		}
		//Add a frame for the local scope
		Executor.pushFrame();
		ss.execute();
		Executor.popFrame();
		// Added code for GC. At the end of the program, garbage collect any remaining references.
		// (Decrement their counts to 0).
		for (int i = 0; i < Executor.garbageCollector.size(); i++) {
			while (Executor.garbageCollector.get(i) > 0) {
				Executor.decrementReference(i);
			}
		}
	}
}