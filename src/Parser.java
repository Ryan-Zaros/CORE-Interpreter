import java.util.*;

class Parser {
	//scanner is stored here as a static field so it is avaiable to the parse method
	public static Scanner scanner;
	
	//scopes is a data structure for the semantic checks performed after parsing
	public static Stack<ArrayList<String>> scopes;
	
	//funcNames is a data structure used for the semantic checks (checking for duplicate function names, checking funciton calls have a target)
	public static List<String> funcNames;
	
	//helper method for the semantic checks
	//returns true if the string x is the name of a variable that is in scope
	static boolean nestedScopeCheck(String x) {
		boolean match = false;
		if (!scopes.empty()) {
			ArrayList<String> temp = scopes.pop();
			match = temp.contains(x);
			if (!match) {
				match = nestedScopeCheck(x);
			}
			scopes.push(temp);
		}
		return match;
	}
	
	//helper method for the semantic checks
	//returns true if the string x is the name of a variable that was declared in the current scope
	static boolean currentScopeCheck(String x) {
		boolean match = false;
		if (!scopes.empty()) {
			match = scopes.peek().contains(x);
		}
		return match;
	}
	
	//helper method for the semantic checks
	//returns true if x is the name of a function that was declared
	static boolean functionDeclaredCheck(String x) {
		return funcNames.contains(x);
	}
	
	//helper method for handling error messages, used by the parse methods
	static void expectedToken(Core expected) {
		if (scanner.currentToken() != expected) {
			System.out.println("ERROR: Expected " + expected + ", recieved " + scanner.currentToken());
			System.exit(0);
		}
	}

}