package pddl_datatype;

import java.util.ArrayList;
import java.util.Iterator;

public class Action {
	public String name = "";
	public ArrayList inputParamList = new ArrayList();
	public ArrayList preconditionList = new ArrayList();
	public ArrayList effectsList = new ArrayList();
	
	public void removeDoubleInputParameter() {
	    // removables contains the list of double entires, that have to removed in the second step
	    ArrayList removables = new ArrayList();
	    for (int i = 0; i<this.inputParamList.size(); i++) {
	        Param te = (Param)this.inputParamList.get(i);
		    for (int j=i+1; j<this.inputParamList.size(); j++) {
		    	Param ite = (Param)this.inputParamList.get(j);
		        if (te.parameter.equals(ite.parameter))
		            removables.add(ite);
		    }
		
	    }
	    
	    // remove
		for (Iterator h = removables.iterator(); h.hasNext();) {
			Param te = (Param)h.next();
		    this.inputParamList.remove(te);
		}
	}
}
