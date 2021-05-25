import java.util.*;

class Executor {
	//data file is stored here as a static field so it is avaiable to the Input execute method
	public static Scanner scanner;
	
	//Called from Program class to initialize all data structures
	static void initExecutor() {
		variables = new Stack<Stack<HashMap<String, Integer>>>();
		globalVars = new HashMap<String, Integer>();
		funcMap = new HashMap<String, FuncDecl>();
		heap = new ArrayList<Integer>();
		garbageCollector = new ArrayList<Integer>();
	}
	
	/*
	*
	* These are the data structures and helper functions to handle variables
	*
	*/
	
	//gobalVars is a data structure to store the global variable values
	public static HashMap<String, Integer> globalVars;
	
	//variables represents the call stack
	public static Stack<Stack<HashMap<String, Integer>>> variables;
	
	// A list to represent the heap, which stores reference variables
	public static ArrayList<Integer> heap;
	
	// A list to store counts for references to variables in the heap
	public static ArrayList<Integer> garbageCollector;
	
	//Push a nested scope onto the current frame, handles new nested scope for If or Loop statements
	static void pushScope() {
		variables.peek().push(new HashMap<String, Integer>());
	}
	
	//Pop a nested scope off of the current frame
	// Altered from original to get the HashMap returned, so that we can access its variables.
	// Then, decrement each reference variable on the GC to 0.
	static void popScope() {
		HashMap<String, Integer> temp = variables.peek().pop();
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			Integer index = entry.getValue();
			if (index != null) {
				Executor.decrementReference(index);
			}
		}
	}
	
	//Called from Id class to handle assigning variables, handles new nested scope for If or Loop statements
	static void varSet(String x, int value) {
		if (!variables.peek().empty()) {
			HashMap<String, Integer> temp = variables.peek().pop();
			if (temp.containsKey(x)) {
				temp.put(x, value);
			} else {
				varSet(x, value);
			}
			variables.peek().push(temp);
		} else {
			// If we get here, it must be a global variable
			globalVars.put(x, value);
		}
	}
	
	//Called from Id class to handle fetching the value of a variable
	static Integer varGet(String x) {
		Integer value = null;
		if (!variables.peek().empty()) {
			HashMap<String, Integer> temp = variables.peek().pop();
			if (temp.containsKey(x)) {
				value = temp.get(x);
			} else {
				value = varGet(x);
			}
			variables.peek().push(temp);
		} else {
			value = globalVars.get(x);
		}
		return value;
	}
	
	//Called from Id class to handle declaring a variable
	static void varInit(String x) {
		//Put null here so we can tell later if variable is uninitialized
		//If variables.peek() is null, initializing a global variable
		if (variables.size() == 0) {
			globalVars.put(x, null);
		} else {
			variables.peek().peek().put(x, null);
		}
	}
	
	/*
	*
	* These are the data structures and helper functions to handle function calls
	*
	*/

	//funcMap is a data structure to associate funciton names with definition, used in FuncDecl and FuncCall
	public static HashMap<String, FuncDecl> funcMap;

	//Called from FuncDecl to add function definition to funcMap
	static void registerFunction(Id id, FuncDecl temp) {
		funcMap.put(id.getString(), temp);		
	}
	
	//Called from FuncCall to retrieve definition of called function
	static FuncDecl retrieveFunction(Id id) {
		return funcMap.get(id.getString());
	}
	
	/*
	*
	* These are the helper functions to handle maintaining the call stack
	*
	*/

	//Called to push a new frame onto the call stack
	static void pushFrame() {
		variables.push(new Stack<HashMap<String, Integer>>());
		variables.peek().push(new HashMap<String, Integer>());
	}
	
	//Called to pop a frame off the call stack
	static void popFrame() {
		variables.pop();
	}
	
	//Called to push a new frame onto the call stack and pass parameters
	static void pushFrame(List<String> formals, List<String> arguments) {
		Stack<HashMap<String, Integer>> newFrame = new Stack<HashMap<String, Integer>>();
		newFrame.push(new HashMap<String, Integer>());
		for (int i=0; i < formals.size(); i++) {
			newFrame.peek().put(formals.get(i), varGet(arguments.get(i)));
		}
		variables.push(newFrame);
	}
	
	//Called to pop a frame off the call stack and pass back parameters
	static void popFrame(List<String> formals, List<String> arguments) {
		Stack<HashMap<String, Integer>> oldFrame = variables.pop();
		for (int i=0; i < formals.size(); i++) {
			varSet(arguments.get(i), oldFrame.peek().get(formals.get(i)));
		}
	}	
	
	/*
	 * Helper functions for garbage collection
	 */
	
	/*
	 * Increments a reference in the GC given a variable value.
	 * Could've passed the index instead but chose not to since only used in the "new" case.
	 */
	static void incrementReference(int value) {
		int index = Executor.heap.indexOf(value);
		// For when adding it the first time.
		if (Executor.garbageCollector.get(index) == null) {
			Executor.garbageCollector.add(index, 1);
		// Otherwise, incrementing it normally.
		} else {
			int num = Executor.garbageCollector.get(index);
			num++;
			Executor.garbageCollector.set(index, num);
		}
	}
	
	/*
	 * Decrements a reference in the GC given its index.
	 */
	static void decrementReference(int index) {
		int num = Executor.garbageCollector.get(index);
		num--;
		Executor.garbageCollector.set(index, num);
		// If we decrement to 0, print out GC count.
		if (num == 0) {
			Executor.printGC();
		}
	}
	
	/*
	 * Iterate through GC, count up all nonzero indices, and print the count.
	 */
	static void printGC() {
		int gcCount = 0;
		for (int i = 0; i < garbageCollector.size(); i++) {
			if (garbageCollector.get(i) > 0) {
				gcCount++;
			}
		}
		System.out.println("gc:" + gcCount);
	}
}
