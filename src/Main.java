class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);
		Parser.scanner = S;
		// Initialize another scanner with the data file
		Scanner D = new Scanner(args[1]);
		Executor.scanner = D;
		
		Program prog = new Program();
		prog.parse();
		prog.semantic();
		//prog.print();
		prog.execute();
	}
}