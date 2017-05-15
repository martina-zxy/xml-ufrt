package parser;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import pddl_datatype.Action;
import pddl_datatype.Param;

public class DirectParser {

	public static void main(String[] args) {
		
		File file = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/s91.owls");
		
		Parser parser = new Parser();
        parser.parse(file);
        System.out.println(parser.getAction().getPddl());
        
        Set set = new HashSet();
        Action result = parser.getAction();
        for (int i = 0; i < result.paramPredicateList.size(); i++) {
			
			set.add(result.paramPredicateList.get(i));
		}
        for (Object obj : set) {
        	System.out.println(obj.toString());
		}
        System.out.println("=============");
        Param par1 = new Param();
        par1.parameter = "PRICE";
        par1.type = "ontology/concept.owl#Price";
        set.add(par1);
//        
        for (Object obj : set) {
        	System.out.println(obj.toString());
        }
        
	}

}
