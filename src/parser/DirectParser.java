package parser;
import java.io.File;

import pddl_datatype.Action;
import pddl_datatype.Param;
import pddl_datatype.Predicate;

public class DirectParser {

	public static void main(String[] args) {
		
		File file = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/s91.owls");
		System.out.println("Opening: " + file.getParent() + ".\n");
		
		Parser parser = new Parser();
        parser.parse(file);
        
        Action result = parser.getAction();
        System.out.println("(:action " + result.name);
        
        System.out.print("\t:parameters ");
        for(int i = 0; i < result.inputParamList.size(); i++) {
        	Param r = (Param)result.inputParamList.get(i);
        	System.out.print("?" + r.parameter + " ");
        }
        System.out.println(")");
        
        System.out.println("\t:precondition ");
        int precondSize = result.preconditionList.size();
        if (precondSize > 1) {
        	System.out.print("\t\t(and ");
        } 
        for(int i = 0; i < precondSize ; i++) {
        	String print = "";
        	if (result.preconditionList.get(i) instanceof Param) {
        		Param r = (Param)result.preconditionList.get(i);
            	print = "(" + r.type + " ?" + r.parameter + ")";
        	} else if (result.preconditionList.get(i) instanceof Predicate) {
        		Predicate r = (Predicate)result.preconditionList.get(i);
        		print = "(" + r.name;
        		for (Param par : r.params) {
        			print += " " + par.type + " ?" + par.parameter;	
        		}
        		print += ")";
        	}
        	if (i > 0) print = "\t\t" + print;
        	System.out.println(print);
        	
        }
        if (precondSize > 1) {
        	System.out.println("\t\t)");
        } 
        
        System.out.println("\t:effect ");
        int effectSize = result.effectsList.size();
        if (effectSize > 1) {
        	System.out.print("\t\t(and ");
        } 
        for(int i = 0; i < effectSize ; i++) {
        	String print = "";
        	if (result.effectsList.get(i) instanceof Param) {
        		Param r = (Param)result.effectsList.get(i);
            	print = "(" + r.type + " ?" + r.parameter + ")";
        	} else if (result.effectsList.get(i) instanceof Predicate) {
        		Predicate r = (Predicate)result.effectsList.get(i);
        		print = "(" + r.name + " ";
        		
        		for (Param par : r.params) {
        			print += par.type + " ?" + r.name;	
        		}
        		print += ")";
        	}
        	if (i > 0) print = "\t\t" + print;
        	System.out.println(print);
        }
        if (effectSize > 1) {
        	System.out.println("\t\t)");
        } 
        System.out.println(")");
        //This is where a real application would open the file.
        
	}

}
