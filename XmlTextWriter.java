package Common;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlTextWriter {

	public Object Formatting;

	Boolean ElementComplete;
	
	Element rootElement;
	Element currentElement;
	//Element parentElement;
	Document doc;
	String fileName;
	
	public XmlTextWriter(String filename, Object object) {
		
		fileName  = filename;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		// root elements
		doc = docBuilder.newDocument();
		rootElement = null;
		currentElement = null;
		//parentElement = null;
	}

	
	
	public void Close()
	{
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fileName));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		System.out.println("File saved!");
		
	}

	/*public void AddChildren() 
	{
		parentElement = currentElement;
	}*/

	//ALLOWS RECURSIVE ELEMENT CREATION
	public void WriteStartElement(String name, String value, String string3) {
		if(rootElement==null)
		{
			if(value!="")
			{
				rootElement = doc.createElement(name + ":" +value);
			}
			else
			{
				rootElement = doc.createElement(name);
			}
			doc.appendChild(rootElement);
			currentElement = rootElement;
			//parentElement = null;
		}
		else
		{
			//child of root			
			if(value!="")
			{
				currentElement = doc.createElement(name + ":" +value);
			}
			else
			{
				currentElement = doc.createElement(name);
			}
			
			rootElement.appendChild(currentElement);
			
		}
	}



	public void WriteEndElement() {
		// TODO Auto-generated method stub
		
	}


	public void WriteTag(String name, String value)
	{
		Element textNode = doc.createElement(name);
		
		textNode.appendChild(doc.createTextNode(value));
		currentElement.appendChild(textNode);
	}

	public void WriteAttributeString(String name, String string2,
			String value) {
		// TODO Auto-generated method stub
		
		Attr attr = doc.createAttribute(name);
		attr.setValue(value);
		currentElement.setAttributeNode(attr);
		
	}



	public void Flush() {
		// TODO Auto-generated method stub
		
	}
}
