import java.util.*; 

class Formals {
	Id id;
	Formals list;
	
	void parse() {
		id = new Id();
		id.parse();
		if (Parser.scanner.currentToken() == Core.COMMA) {
			Parser.scanner.nextToken();
			list = new Formals();
			list.parse();
		} 
	}
	
	void semantic() {
		// Fill in later
	}
	
	void print() {
		id.print();
		if (list != null) {
			System.out.print(",");
			list.print();
		}
	}
	
	List<String> getListOfStrings() {
		List<String> temp = new ArrayList<String>();
		temp.add('1' + id.getString());
		//temp.add(id.getString());
		if (list == null) {
			return temp;
		} else {
			return list.getListOfStrings(temp);
		}
	}
	
	// Altered to work with reference variables.
	List<String> getListOfStrings(List<String> temp) {
		temp.add('1' + id.getString());
		if (list == null) {
			return temp;
		} else {
			return list.getListOfStrings(temp);
		}
	}
	
	List<Id> getListOfId() {
		List<Id> temp = new ArrayList<Id>();
		temp.add(id);
		if (list == null) {
			return temp;
		} else {
			return list.getListOfId(temp);
		}
	}
	
	List<Id> getListOfId(List<Id> temp) {
		temp.add(id);
		if (list == null) {
			return temp;
		} else {
			return list.getListOfId(temp);
		}
	}
}