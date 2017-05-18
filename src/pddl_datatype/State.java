package pddl_datatype;

import java.util.ArrayList;

/**
 * 
 * @author Martina
 * States of Action in Plans
 *
 */
public class State {
	public ArrayList condition = new ArrayList();
	public Problem problem;
	public int cost;
	public State predecessor;
	public Action action;
	
	public State(ArrayList condition, Problem problem, Action action) {
		this.condition = condition;
		this.problem = problem;
		this.action = action;
		cost = 0;
		for (Object o: problem.goal) {
			if (!condition.contains(o)) {
				cost++;
			}
		}
	}	
}
