package se.yrgo.schedule;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

/**
 * Formatter for formatting assignments as XML.
 */
public class XMLFormatter implements Formatter {

    @Override
    public String format(List<Assignment> assignments) {
        // Check if the list is empty and return an empty XML string if it is
        if (assignments.isEmpty()) {
            return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<schedules></schedules>\n";
        } else {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("schedules");
                doc.appendChild(rootElement);

                for (Assignment assignment : assignments) {
                    Element scheduleElement = doc.createElement("schedule");
                    scheduleElement.setAttribute("date", assignment.date());

                    // Create and add <school> and <substitute> elements here

                    rootElement.appendChild(scheduleElement);
                }

                // Convert the XML document to an XML string with proper indentation
                StringWriter xml = new StringWriter();
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xml);
                transformer.transform(source, result);

                return xml.toString();
            } catch (ParserConfigurationException | SAXException e) {
                return "XML Error";
            } catch (Exception e) {
                return "Error";
            }
        }
    }
}
