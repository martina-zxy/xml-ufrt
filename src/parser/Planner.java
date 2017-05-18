package parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pddl_datatype.Action;
import pddl_datatype.Domain;
import pddl_datatype.Problem;
import pddl_datatype.State;

public class Planner {
	private Domain domain;
	private Problem problem;
	private LinkedList<State> stateQueue = new LinkedList<State>();
	private boolean isGoalReached = false;
	private List<Action> plan = new ArrayList<Action>();
	
	public Planner() {
		
	}
	
	public  List<Action> getPlan() {
		return plan; 
	}
	
	public Planner(Domain dom, Problem pro) {
		this.domain = dom;
		this.problem = pro;
	}
	
	
	public String getPlanStr() {
		
		if (plan == null || plan.size() == 0) {
			return "NO PLAN FOUND";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = plan.size()-1; i >= 0; i--) {
			sb.append(plan.get(i).getHeader());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param finalState
	 * @return list of action for the plan
	 */
	public List<Action> getPlan(State finalState) {
		List<Action> plan = new ArrayList<Action>();
		State obs = finalState;
		while (obs.ancestor != null) {
			plan.add(obs.action);
			obs = obs.ancestor;
		}
		return plan;
	}
	
	/**
	 * run the planner
	 */
	public void plan() {
		
		State currState = new State(problem.init, problem, null);
		stateQueue.add(currState);
		int counter = 0;
		
		while(!stateQueue.isEmpty() && !isGoalReached && counter < 1000) {
		
			State obsState = stateQueue.removeFirst();
			for (Action action : domain.listAction) {
				
				if(checkAction(action, obsState.condition)) {
					ArrayList newCondition = getActionResult(action, obsState.condition);
					State newState = new State(newCondition, problem, action);
					newState.ancestor = obsState;
		
					if(newState.cost == 0) {
						isGoalReached = true;
						plan = getPlan(newState);						
					}
					
					stateQueue.add(newState);
				}
			}
			counter++;
		}
	}
	
	/**
	 * 
	 * @param action
	 * @param condition
	 * @return get action effect if it can be run with the condition provided
	 */
	public ArrayList getActionResult(Action action, ArrayList condition) {
		if (!condition.containsAll(action.preconditionList)) {
			return null;
		}

		ArrayList result = new ArrayList();
		result.addAll(condition);
		for (Object o : action.effectsList) {
			if (!condition.contains(o)) {
				result.add(o);
			}						
		}
		return result;
	}
	
	public boolean checkAction(Action action, ArrayList currCondition) {

		return currCondition.containsAll(action.preconditionList);
	}

}
