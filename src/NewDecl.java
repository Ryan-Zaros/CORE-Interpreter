//import java.util.*; 

public class NewDecl implements Stmt {
	Id id;
	
	public void parse() {
		Parser.expectedToken(Core.DEFINE);
		Parser.scanner.nextToken(); // Consume DEFINE
		Parser.expectedToken(Core.ID);
		id = new Id();
		id.parse(); // Parse and consume ID
		id.renameToRef(); // Rename to indicate reference variable
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken(); // Consume SEMICOLON
	}
	
	public void semantic() {
		id.addToScope();
	}
	
	public void print(int indent) {
	}
	
	public void execute() {
		id.executeAllocate();
	}
}
