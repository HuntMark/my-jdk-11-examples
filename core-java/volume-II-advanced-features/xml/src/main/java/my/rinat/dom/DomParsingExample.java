package my.rinat.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class DomParsingExample {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        File xml = Paths.get("core-java/volume-II-advanced-features/xml/src/main/resources/simple-example.xml").toFile();
        Document document = builder.parse(xml);

        NodeList children = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                var childElement = (Element) child;
                var textNode = (Text) childElement.getFirstChild();
                String text = textNode.getData().trim();
                System.out.println(text);
            }
        }

    }
}
