package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * <p>This program demonstrates how to use a SAX parser. The program
 * prints all hyperlinks of an XHTML web page.</p>
 * <p>Usage: java sax.SAXTest URL</p>
 *
 * @author Cay Horstmann
 * @version 1.01 2018-05-01
 */
public class SAXTest {
    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
        String url;
        if (args.length == 0) {
            url = "https://www.w3.org/MarkUp";
            System.out.println("Using " + url);
        } else {
            url = args[0];
        }

        var handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (localName.equalsIgnoreCase("a") && attributes != null) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String name = attributes.getLocalName(i);
                        if (name.equalsIgnoreCase("href")) {
                            System.out.println(attributes.getValue(i));
                        }
                    }
                }
            }
        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser saxParser = factory.newSAXParser();
        InputStream in = new URL(url).openStream();
        saxParser.parse(in, handler);
    }
}
