package pddl_datatype;

import java.util.ArrayList;

public class Predicate {
	public String name;
	public ArrayList<Param> params = new ArrayList<Param>();
	
	public String toString() {
		StringBuilder pred = new StringBuilder();
		pred.append(name);
		
		for (Param par : params) {
			pred.append (" " + par.type + " ?" + par.parameter);	
		}
		return pred.toString();
	}
}
