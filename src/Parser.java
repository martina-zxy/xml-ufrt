import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	
	public void parse(File file){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("service:Service");//getElementsByTagNameNS("service", "Service");
			System.out.println(nList.getLength());
			Element nNode = (Element)nList.item(0);
            System.out.println("action :"+ nNode.getAttribute("rdf:ID"));
            
            NodeList inputList = doc.getElementsByTagName("profile:hasInput");
            for (int idx = 0; idx < inputList.getLength(); idx++) {
            	Element el = (Element) inputList.item(idx);
            	String inputRaw = el.getAttribute("rdf:resource");
            	inputRaw = inputRaw.replace("#_", "");
            	System.out.println("input :"+ inputRaw);
            }
            
            NodeList preconditionList = doc.getElementsByTagName("process:hasPrecondition");
            
            NodeList outputList = doc.getElementsByTagName("profile:hasOutput");
            for (int idx = 0; idx < outputList.getLength(); idx++) {
            	Element el = (Element) inputList.item(idx);
            	String outputRaw = el.getAttribute("rdf:resource");
            	outputRaw = outputRaw.replace("#_", "");
            	System.out.println("output :"+ outputRaw);
            }
            
            NodeList resultList = doc.getElementsByTagName("profile:hasOutput");
            for (int idx = 0; idx < outputList.getLength(); idx++) {
            	Element el = (Element) inputList.item(idx);
            	String outputRaw = el.getAttribute("rdf:resource");
            	outputRaw = outputRaw.replace("#_", "");
            	System.out.println("output :"+ outputRaw);
            }
            
            
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
}
