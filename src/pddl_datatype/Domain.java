package pddl_datatype;

import java.util.ArrayList;
import java.util.List;

public class Domain {
	
	public String name = "domainName";
	public List<Action> listAction;
	
	public Domain() {
		this.listAction = new ArrayList<Action>();
	}
	
	public Domain(List<Action> listAction){
		this.listAction = listAction;
	}	
	
	public void addAction(Action action) {
		if (listAction == null) {
			listAction = new ArrayList<Action>();
		} 
		listAction.add(action);
	}
	
	/**
	 * 
	 * @return pddl String
	 */
	public String getPddl() {
		
		StringBuilder pddl = new StringBuilder();
		pddl.append("(define (domain " + name + ")\n");
		pddl.append("(:requirements[:strips])\n");
		for (Action act : listAction) {
			pddl.append(act.getPddl());
		}
		pddl.append(")");
		
		return pddl.toString();
	}
}
