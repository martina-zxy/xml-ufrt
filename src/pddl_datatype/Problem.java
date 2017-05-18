package pddl_datatype;

import java.util.ArrayList;

/**
 * 
 * @author Martina
 * Problem file 
 */
public class Problem {
	public String name = "problemName";
	public ArrayList init = new ArrayList<>();
	public ArrayList goal = new ArrayList<>();
	
	/**
	 * 
	 * @return get pddl string
	 */
	public String getPddl() {
		
		StringBuilder pddl = new StringBuilder();
		pddl.append("(define (problem " + name + ")\n");
		pddl.append("(:domain domainName)\n");
		
		pddl.append("(:init");
		if (init.size() > 1) {
			pddl.append("(and");	
		}
		for (Object o : init){
			pddl.append("\n\t(");
			pddl.append(o.toString());
			pddl.append(")");
		}
		if (init.size() > 1) {
			pddl.append("\n\t)");	
		}
		pddl.append("\n)");
		
		pddl.append("\n(:goal");
		if (goal.size() > 1) {
			pddl.append("(and");	
		}
		for (Object o : goal){
			pddl.append("\n\t(");
			pddl.append(o.toString());
			pddl.append(")");
		}
		if (goal.size() > 1) {
			pddl.append("\n\t)");	
		}
		pddl.append("\n)");
		return pddl.toString();
	}
}
