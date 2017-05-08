package pddl_datatype;

import java.util.ArrayList;
import java.util.Iterator;

public class Action {
	public String name = "";
	public ArrayList paramList = new ArrayList();
	public ArrayList preconditionList = new ArrayList();
	public ArrayList effectsList = new ArrayList();
	public ArrayList paramPredicateList = new ArrayList();
	public String pddl;
	
	public void removeDoubleInputParameter() {
	    // removables contains the list of double entires, that have to removed in the second step
	    ArrayList removables = new ArrayList();
	    for (int i = 0; i<this.paramList.size(); i++) {
	        Param te = (Param)this.paramList.get(i);
		    for (int j=i+1; j<this.paramList.size(); j++) {
		    	Param ite = (Param)this.paramList.get(j);
		        if (te.parameter.equals(ite.parameter))
		            removables.add(ite);
		    }
		
	    }
	    
	    // remove
		for (Iterator h = removables.iterator(); h.hasNext();) {
			Param te = (Param)h.next();
		    this.paramList.remove(te);
		}
	}
	
	public void removeDoubleParamPredicate() {
	    // removables contains the list of double entires, that have to removed in the second step
	    ArrayList removables = new ArrayList();
	    for (int i = 0; i<this.paramPredicateList.size(); i++) {
	    	if (this.paramPredicateList.get(i) instanceof Param) {
		        Param te = (Param)this.paramPredicateList.get(i);
			    for (int j=i+1; j<this.paramPredicateList.size(); j++) {
			    	if (this.paramPredicateList.get(j) instanceof Param) {
				    	Param ite = (Param)this.paramPredicateList.get(j);
				        if (te.parameter.equals(ite.parameter))
				            removables.add(ite);
			    	}
			    }
	    	} else {
	    		if (this.paramPredicateList.get(i) instanceof Predicate) {
	    			Predicate te = (Predicate)this.paramPredicateList.get(i);
				    for (int j=i+1; j<this.paramPredicateList.size(); j++) {
				    	if (this.paramPredicateList.get(j) instanceof Predicate) {
				    		Predicate ite = (Predicate)this.paramPredicateList.get(j);
					        if (te.name.equals(ite.name))
					            removables.add(ite);
				    	}
				    }
		    	}
	    	}
		
	    }
	    
	    // remove
		for (Iterator h = removables.iterator(); h.hasNext();) {
			Param te = (Param)h.next();
		    this.paramPredicateList.remove(te);
		}
	}
	
	public String getPddl(){
		StringBuilder pddl = new StringBuilder();
		pddl.append("(:action " + name + "\n");
        
		pddl.append("\t:parameters ");
        for(int i = 0; i < paramList.size(); i++) {
        	if (i == 0)pddl.append("(");
        	Param r = (Param)paramList.get(i);
        	pddl.append("?" + r.parameter + " ");
        }
        pddl.append(")\n");

        pddl.append("\t:precondition ");
        int precondSize = preconditionList.size();
        if (precondSize > 1) {
        	pddl.append("(and ");
        } 
        for(int i = 0; i < precondSize ; i++) {
        	String print = "";
        	if (preconditionList.get(i) instanceof Param) {
        		Param r = (Param)preconditionList.get(i);
            	print = "(" + r.type + " ?" + r.parameter + ")";
        	} else if (preconditionList.get(i) instanceof Predicate) {
        		Predicate r = (Predicate)preconditionList.get(i);
        		print = "(" + r.name;
        		for (Param par : r.params) {
        			print += " " + par.type + " ?" + par.parameter;	
        		}
        		print += ")";
        	}
        	if (i > 0) print = "\t\t" + print;
        	pddl.append(print);
        	if (i != precondSize-1) pddl.append("\n");
        	
        }
        if (precondSize > 1) {
        	pddl.append("\n\t\t)");
        } 

        pddl.append("\n\t:effect ");
        int effectSize = effectsList.size();
        if (effectSize > 1) {
        	pddl.append("(and ");
        } 
        for(int i = 0; i < effectSize ; i++) {
        	String print = "";
        	if (effectsList.get(i) instanceof Param) {
        		Param r = (Param)effectsList.get(i);
            	print = "(" + r.type + " ?" + r.parameter + ")";
        	} else if (effectsList.get(i) instanceof Predicate) {
        		Predicate r = (Predicate)effectsList.get(i);
        		print = "(" + r.name + " ";
        		
        		for (Param par : r.params) {
        			print += par.type + " ?" + par.parameter + " ";	
        		}
        		print += ")";
        	}
        	if (i > 0) print = "\t\t" + print;
        	pddl.append(print + "\n");
        }
        if (effectSize > 1) {
        	pddl.append("\t\t) \n");
        } 
        pddl.append(")\n");

		return pddl.toString();
	}
	
//	public void print(){
//		Action result = this;
//        System.out.println("(:action " + result.name);
//        
//        System.out.print("\t:parameters ");
//        for(int i = 0; i < result.inputParamList.size(); i++) {
//        	if (i == 0)System.out.print("(");
//        	Param r = (Param)result.inputParamList.get(i);
//        	System.out.print("?" + r.parameter + " ");
//        }
//        System.out.println(")");
//        
//        System.out.println("\t:precondition ");
//        int precondSize = result.preconditionList.size();
//        if (precondSize > 1) {
//        	System.out.print("\t\t(and ");
//        } 
//        for(int i = 0; i < precondSize ; i++) {
//        	String print = "";
//        	if (result.preconditionList.get(i) instanceof Param) {
//        		Param r = (Param)result.preconditionList.get(i);
//            	print = "(" + r.type + " ?" + r.parameter + ")";
//        	} else if (result.preconditionList.get(i) instanceof Predicate) {
//        		Predicate r = (Predicate)result.preconditionList.get(i);
//        		print = "(" + r.name;
//        		for (Param par : r.params) {
//        			print += " " + par.type + " ?" + par.parameter;	
//        		}
//        		print += ")";
//        	}
//        	if (i > 0) print = "\t\t" + print;
//        	System.out.println(print);
//        	
//        }
//        if (precondSize > 1) {
//        	System.out.println("\t\t)");
//        } 
//        
//        System.out.println("\t:effect ");
//        int effectSize = result.effectsList.size();
//        if (effectSize > 1) {
//        	System.out.print("\t\t(and ");
//        } 
//        for(int i = 0; i < effectSize ; i++) {
//        	String print = "";
//        	if (result.effectsList.get(i) instanceof Param) {
//        		Param r = (Param)result.effectsList.get(i);
//            	print = "(" + r.type + " ?" + r.parameter + ")";
//        	} else if (result.effectsList.get(i) instanceof Predicate) {
//        		Predicate r = (Predicate)result.effectsList.get(i);
//        		print = "(" + r.name + " ";
//        		
//        		for (Param par : r.params) {
//        			print += par.type + " ?" + par.parameter + " ";	
//        		}
//        		print += ")";
//        	}
//        	if (i > 0) print = "\t\t" + print;
//        	System.out.println(print);
//        }
//        if (effectSize > 1) {
//        	System.out.println("\t\t)");
//        } 
//        System.out.println(")");
//	}
}
