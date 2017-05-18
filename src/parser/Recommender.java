package parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import pddl_datatype.Action;
import pddl_datatype.Param;

public class Recommender {

	public static void main(String[] args) throws IOException {
		List<Action> listAction = new ArrayList<Action>();
		
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
		
		List<Param> listDesiredOutput = new ArrayList<Param>();
		Param o1 = new Param("","ontology/SUMO.owl#DiagnosticProcess");
		Param o2 = new Param("","ontology/finance_th_web.owl#cost");
		
		
//		List<String> fileNames = new ArrayList<String>();
//		fileNames.add("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/s91.owls")
//		
//		File file = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/s91.owls");
//		Parser parser = new Parser();
//        parser.parse(file);
//        
//        Action add = parser.getAction();
//        System.out.println(add.getPddl());
//        
        
        
//        System.out.println(parser.getAction().getPddl());
	}

}
