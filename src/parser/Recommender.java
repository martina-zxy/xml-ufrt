package parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import pddl_datatype.Action;
import pddl_datatype.Domain;
import pddl_datatype.Param;
import pddl_datatype.Problem;
import pddl_datatype.State;

public class Recommender {
	
	private LinkedList<State> stateQueue = new LinkedList<State>();
	private boolean isGoalReached = false;
	private List<Action> actionList = new ArrayList<Action>();
	private ArrayList desiredEffect = new ArrayList<>();
	private List<List<Action>> trace = new ArrayList<List<Action>>();
	private ArrayList achievedEffect = new ArrayList<>();
	private ArrayList input = new ArrayList<>();
	private List<Action> achievingAction = new ArrayList<Action>();
	
	private List<Action> getAchievingAction() {
		return this.achievingAction;
	}
	public Recommender(List<Action> actionList, ArrayList desiredEffect) {
		this.actionList = actionList;
		this.desiredEffect = desiredEffect;
	}
	
	/**
	 * run the recommender
	 */
	public ArrayList recommend() {
		
		State currState = new State(desiredEffect, null);
		stateQueue.add(currState);
		int counter = 0;
		
		while(!stateQueue.isEmpty() && counter <= 1000) {
			
			State obsState = stateQueue.removeFirst();
			
			for (Action action : actionList) {
				
				achievingAction.add(action);
				if(action.isIntersect(desiredEffect)) {
					
					this.input.addAll(action.preconditionList);
					
					for (Object object : action.effectsList) {
						if (!achievedEffect.contains(object)){
							achievedEffect.add(object);
						}
					}
					
					if (achievedEffect.containsAll(desiredEffect)) {
						return this.input;
					}
					State newState = new State(action.preconditionList, action);
					newState.predecessor = obsState;
					obsState.ancestor = newState;
					
					stateQueue.add(newState);
				}
			}
			counter++;
		}
		
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		LinkedList<Action> actionQueue = new LinkedList<Action>();
		List<Action> listAction = new ArrayList<Action>();
		List<Action> actionContainer = new ArrayList<Action>();
		
		
		
		try(Stream<Path> paths = Files.walk(Paths.get("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/medical-hospital_investigating_service"))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            File file = new File(filePath.toString());
		    		Parser parser = new Parser();
		            parser.parse(file);
		            
		            Action add = parser.getAction();
		            listAction.add(add);
		        }
		    });
		} 
		
		ArrayList listDesiredOutput = new ArrayList();
		Param o1 = new Param("","ontology/SUMO.owl#DiagnosticProcess");
		Param o2 = new Param("","ontology/finance_th_web.owl#cost");
		Param o3 = new Param("","ontology/SUMO.owl#IntentionalPsychologicalProcess");
		Param o4 = new Param("","ontology/SUMO.owl#IntentionalPsychologicalProcess");
		
		listDesiredOutput.add(o1);
		listDesiredOutput.add(o2);
		listDesiredOutput.add(o3);
		listDesiredOutput.add(o4);
		Recommender rec = new Recommender(listAction, listDesiredOutput);
		ArrayList res = rec.recommend();
		System.out.println(res.size());
		for (Object object : res) {
			System.out.println(res.toString());
		}
		for (Action a : rec.achievingAction) {
			System.out.println(a.name);
		}

	}

}
