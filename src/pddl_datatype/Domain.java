package pddl_datatype;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import parser.Parser;

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
	
//	public void print(){
//		System.out.println("(define (domain " + name + ")");
//		System.out.println("(:requirements[:strips])");
//		for (Action act : listAction) {
//			act.print();
//		}
//		System.out.println(")");
//	}
	
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
	
	public static void main(String[] args) {

		File file1 = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/s91.owls");
		File file2 = new File("E:/Master/2. UFRT/4. XML & Web Technology/OWL-S WEB SERVICES/OWL-S WEB SERVICES/Services/economy-car_price_service.owls/_RedFerrariprice_service.owls");
		
		List<File> listFile = new ArrayList<File>();
		listFile.add(file1);
		listFile.add(file2);
		
		Parser parser = new Parser();
        parser.parse(file1);
//        parser.getAction().print();
        
        Parser parser2 = new Parser();
        parser2.parse(file2);
//        parser2.getAction().print();
        
        List<Action> listAction = new ArrayList<Action>();
        listAction.add(parser.getAction());
        listAction.add(parser2.getAction());
		Domain dom = new Domain(listAction);
		System.out.println(dom.getPddl());

		

	}
}
