package Common;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class XmlTextReader {
	
	Document doc;
	Element currentElement;
	NodeList docChildren;
	int currentChild;
	Node currentNode;
	NamedNodeMap currentAttributes;
	int currentAttribute;
	File fXmlFile;
	
	public XmlTextReader(String filename)
	{
		fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		
		currentElement = doc.getDocumentElement();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		docChildren =  currentElement.getChildNodes();
		currentChild = 0;
	}

	
	
	public Boolean Read()
	{
		Boolean didRead = false;
		
		if(currentChild < docChildren.getLength())
		{
			currentNode = docChildren.item(currentChild);
			currentAttributes = currentNode.getAttributes();
			
			/*if(currentAttributes!=null)
			{
				MoveToNextAttribute();
			}
			else
			{*/
				Name = currentNode.getNodeName();
			//}
			didRead = true;
			currentAttribute = 0;
			currentChild++;
		}

	    //NodeList nList = doc.getElementsByTagName("record");
		
		
		return didRead;
	
	}
	
	public Boolean IsStartElement()
	{
		Boolean isStart = true;
		
		return isStart;
	
	}
	
	public Boolean MoveToNextAttribute()
	{
		Boolean moved = false;
		
		if(currentAttribute < currentAttributes.getLength())
		{
			Node AttribNode = currentAttributes.item(currentAttribute);
			Name = AttribNode.getNodeName();
			Value = AttribNode.getNodeValue();
			moved = true;
			currentAttribute++;
		}
		
		return moved;
	
	}
	
	public String Name;		//current xml node name
	public String Value;	//current xml node value
	public void Close() {
		// TODO Auto-generated method stub
	}
}	
