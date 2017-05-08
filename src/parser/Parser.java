package parser;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pddl_datatype.Action;
import pddl_datatype.Param;
import pddl_datatype.Predicate;

public class Parser {
	
	private Action action;
	
	public Action getAction(){
		return action;
	}
	
	public void printToFile() {
		try{
		    PrintWriter writer = new PrintWriter(action.name + "_Action.pddl", "UTF-8");
		    writer.println("(:action " + action.name);
	        
		    writer.print("\t:parameters ");
	        for(int i = 0; i < action.paramList.size(); i++) {
	        	Param r = (Param)action.paramList.get(i);
	        	writer.print("?" + r.parameter + " ");
	        }
	        writer.println(")");
	        
	        writer.println("\t:precondition ");
	        int precondSize = action.preconditionList.size();
	        if (precondSize > 1) {
	        	writer.print("\t\t(and ");
	        } 
	        for(int i = 0; i < precondSize ; i++) {
	        	String print = "";
	        	if (action.preconditionList.get(i) instanceof Param) {
	        		Param r = (Param)action.preconditionList.get(i);
	            	print = "(" + r.type + " ?" + r.parameter + ")";
	        	} else if (action.preconditionList.get(i) instanceof Predicate) {
	        		Predicate r = (Predicate)action.preconditionList.get(i);
	        		print = "(" + r.name;
	        		for (Param par : r.params) {
	        			print += " " + par.type + " ?" + par.parameter;	
	        		}
	        		print += ")";
	        	}
	        	if (i > 0) print = "\t\t" + print;
	        	writer.println(print);
	        	
	        }
	        if (precondSize > 1) {
	        	writer.println("\t\t)");
	        } 
	        
	        writer.println("\t:effect ");
	        int effectSize = action.effectsList.size();
	        if (effectSize > 1) {
	        	writer.print("\t\t(and ");
	        } 
	        for(int i = 0; i < effectSize ; i++) {
	        	String print = "";
	        	if (action.effectsList.get(i) instanceof Param) {
	        		Param r = (Param)action.effectsList.get(i);
	            	print = "(" + r.type + " ?" + r.parameter + ")";
	        	} else if (action.effectsList.get(i) instanceof Predicate) {
	        		Predicate r = (Predicate)action.effectsList.get(i);
	        		print = "(" + r.name + " ";
	        		
	        		for (Param par : r.params) {
	        			print += par.type + " ?" + par.parameter + " ";	
	        		}
	        		print += ")";
	        	}
	        	if (i > 0) print = "\t\t" + print;
	        	writer.println(print);
	        }
	        if (effectSize > 1) {
	        	writer.println("\t\t)");
	        } 
	        writer.println(")");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	
	
	public void parse(File file){
	    ArrayList input = new ArrayList();
	    ArrayList output = new ArrayList();
	    
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("service:Service");
			
			Element actionNode = (Element)nList.item(0);
			this.action = new Action();
			action.name = actionNode.getAttribute("rdf:ID");
            
			// parse input
            NodeList inputList = doc.getElementsByTagName("process:Input");
            for (int idx = 0; idx < inputList.getLength(); idx++) {
            	Element el = (Element) inputList.item(idx);
            	String inputPar = el.getAttribute("rdf:ID");
            	
            	NodeList inputTypeNode = el.getElementsByTagName("process:parameterType");
            	String inputType = this.cleanType(inputTypeNode.item(0).getTextContent());
            	
            	inputPar = inputPar.replace("_", "");
            	
            	Param par = new Param();
            	par.parameter = inputPar;
            	par.type = inputType;
            	input.add(par);
            }
            // end parse input
            
            // precondition
            NodeList precondList = doc.getElementsByTagName("expr:SWRL-Condition");
            for (int idx = 0; idx < precondList.getLength(); idx++) {
            	Predicate pred = new Predicate();
            	Element el = (Element) precondList.item(idx);
            	String precondName = el.getAttribute("rdf:ID").replace("_", "");
            	
            	pred.name = precondName;
            	
            	int argIdx = 1;
            	NodeList args = el.getElementsByTagName("swrl:argument" + argIdx);
            	while (args.getLength() > 0) {            		
            		Element arg = (Element) args.item(0);
            		String type = this.cleanType(arg.getAttribute("rdf:resource"));
            		
            		Param par = new Param();
                	par.parameter = this.getParam(type);
                	par.type = type;
                	pred.params.add(par);
                	
            		argIdx ++ ;
            		args = el.getElementsByTagName("swrl:argument" + argIdx);
            	}
            	input.add(pred);
            }
 
            NodeList outputList = doc.getElementsByTagName("process:Output");
            for (int idx = 0; idx < outputList.getLength(); idx++) {
            	Element el = (Element) outputList.item(idx);
            	String outputPar = el.getAttribute("rdf:ID");
            	outputPar = outputPar.replace("_", "");
            	
            	NodeList outputTypeNode = el.getElementsByTagName("process:parameterType");
            	String outputType = this.cleanType(outputTypeNode.item(0).getTextContent());
            	
            	Param par = new Param();
            	par.parameter = outputPar;
            	par.type = outputType;
            	output.add(par);
            }
            // end parse output
            
            // parse result
            NodeList resultList = doc.getElementsByTagName("process:Result");
            for (int idx = 0; idx < resultList.getLength(); idx++) {
            	Predicate pred = new Predicate();
            	
            	Element el = (Element) resultList.item(idx);
            	String resultPar = el.getAttribute("rdf:ID").replace("_", "");
            	pred.name = resultPar;
//            	System.out.println("result :"+ resultPar);
            	
            	int argIdx = 1;
            	NodeList args = el.getElementsByTagName("swrl:argument" + argIdx);
            	while (args.getLength() > 0) {
            		Element arg = (Element) args.item(0);
            		String type = this.cleanType(arg.getAttribute("rdf:resource"));
            		
            		Param par = new Param();
                	par.parameter = this.getParam(type);
                	par.type = type;
                	pred.params.add(par);
                	
            		argIdx ++ ;
            		args = el.getElementsByTagName("swrl:argument" + argIdx);
            	}
            	output.add(pred);
            }
            // end parse result
            
            // parse input 
//          NodeList hasInputList = doc.getElementsByTagName("profile:hasInput");
//          for (int idx = 0; idx < hasInputList.getLength(); idx++) {
//          	Element el = (Element) hasInputList.item(idx);
//          	String inputPar = el.getAttribute("rdf:resource").replace("#_", "");
//          	
//          	System.out.println("input :"+ inputPar);
//          	
//          	Param par = new Param();
//          	par.parameter = inputPar;
//          	input.add(par);
//          }
            
            // parse output 
//            NodeList hasOutputList = doc.getElementsByTagName("profile:hasOutput");
//            for (int idx = 0; idx < hasOutputList.getLength(); idx++) {
//            	Element el = (Element) hasOutputList.item(idx);
//            	String outputPar = el.getAttribute("rdf:resource");
//            	outputPar = outputPar.replace("#_", "");
//            	System.out.println("output :"+ outputPar);
//            	
//            	Param par = new Param();
//            	par.parameter = outputPar;
//            	output.add(par);
//            }
//            
            
            
            action.preconditionList.addAll(input);
            action.effectsList.addAll(output);
            
            for(int i = 0; i < input.size(); i++) {
            	action.paramPredicateList.add(input.get(i));
            	if (input.get(i) instanceof Param) {
            		action.paramList.add(input.get(i));
            	} else if (input.get(i) instanceof Predicate) {
            		Predicate pre = (Predicate)input.get(i);
            		action.paramList.addAll(pre.params);
            		action.paramPredicateList.addAll(pre.params);
            	}
            }

            for(int i = 0; i < output.size(); i++) {
            	action.paramPredicateList.add(output.get(i));
            	if (output.get(i) instanceof Param) {
            		action.paramList.add(output.get(i));
            	} else if (output.get(i) instanceof Predicate) {
            		Predicate pre = (Predicate)output.get(i);
            		action.paramList.addAll(pre.params);
            		action.paramPredicateList.addAll(pre.params);
            	}
            }
            
            action.removeDoubleInputParameter();
            action.removeDoubleParamPredicate();
//            System.out.println("============");
            
//            for (int i = 0; i < action.inputParamList.size(); i++) {
//            	Param p = (Param)action.inputParamList.get(i);
//            	System.out.println(p.parameter);
//            	System.out.println(p.type);
//            }
//            System.out.println("=================== PRECONDITION ===================");
//            for (int i = 0; i < action.preconditionList.size(); i++) {
//            	if (action.preconditionList.get(i) instanceof Param) {
//            		Param p = (Param)action.preconditionList.get(i);
//                	System.out.println(p.parameter);
//                	System.out.println(p.type);
//            		
//            	} else if (action.preconditionList.get(i) instanceof Predicate) {
//            		Predicate pre = (Predicate)action.preconditionList.get(i);
//            		System.out.println(pre.name);
//            		for (Param x : pre.params){
//            			System.out.println(x.parameter);
//                    	System.out.println(x.type);
//            		}
//                	
//            	}
//            	
//            }
//            
//            System.out.println("=================== EFFECT ===================");
//            for (int i = 0; i < action.effectsList.size(); i++) {
//            	if (action.effectsList.get(i) instanceof Param) {
//            		Param p = (Param)action.effectsList.get(i);
//                	System.out.println(p.parameter);
//                	System.out.println(p.type);
//            		
//            	} else if (action.effectsList.get(i) instanceof Predicate) {
//            		Predicate pre = (Predicate)action.effectsList.get(i);
//            		System.out.println(pre.name);
//            		for (Param x : pre.params){
//            			System.out.println(x.parameter);
//                    	System.out.println(x.type);
//            		}
//                	
//            	}
//            	
//            }
           
            
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String cleanType(String type) {
		return type.split("http://127.0.0.1/")[1];
	}
	
	public String getParam(String type) {		
		return type.split("#")[1];
	}
}
