package xpath;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathEvaluationResult;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathNodes;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This program evaluates XPath expressions.
 *
 * @author Cay Horstmann
 * @version 1.1 2018-04-06
 */
public class XPathTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Avoid a delay in parsing an XHTML file - see the first note in Section 3.3.1
        builder.setEntityResolver(CatalogManager.catalogResolver(
                CatalogFeatures.defaults(),
                Paths.get("xpath/catalog.xml").toAbsolutePath().toUri()));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath path = xPathFactory.newXPath();

        try (var in = new Scanner(System.in)) {
            String filename;
            if (args.length == 0) {
                String defaultFilename = "core-java/volume-II-advanced-features/xml/src/main/resources/config-schema.xml";
                System.out.printf("Input file (default \"%s\"): ", defaultFilename);
                filename = in.nextLine();
                if (filename == null || filename.isEmpty()) {
                    filename = defaultFilename;
                }
            } else {
                filename = args[0];
            }

            Document document = builder.parse(filename);
            var done = false;
            while (!done) {
                System.out.print("XPath expression (empty line to exit): ");
                String expression = in.nextLine();
                if (expression.trim().isEmpty()) {
                    done = true;
                } else {
                    try {
                        XPathEvaluationResult<?> result = path.evaluateExpression(expression, document);
                        if (result.type() == XPathEvaluationResult.XPathResultType.NODESET) {
                            for (Node n : (XPathNodes) result.value()) {
                                System.out.println(description(n));
                            }
                        } else if (result.type() == XPathEvaluationResult.XPathResultType.NODE) {
                            System.out.println(result.value());
                        } else {
                            System.out.println(result.value());
                        }
                    } catch (XPathExpressionException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    private static String description(Node node) {
        if (node instanceof Element) {
            return "Element " + node.getNodeName();
        } else if (node instanceof Attr) {
            return "Attribute " + node;
        } else {
            return node.toString();
        }
    }
}
