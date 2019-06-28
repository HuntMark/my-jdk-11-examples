package dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * This program displays an XML document as a tree in JSON format.
 *
 * @author Cay Horstmann
 * @version 1.2 2018-04-02
 */
public class JSONConverter {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String filename;
        if (args.length == 0) {
            try (var in = new Scanner(System.in)) {
                String defaultFilename = "core-java/volume-II-advanced-features/xml/src/main/resources/example.xml";
                System.out.printf("Input file (default \"%s\"): ", defaultFilename);
                filename = in.nextLine();
                if (filename == null || filename.isEmpty()) {
                    filename = defaultFilename;
                }
            }
        } else {
            filename = args[0];
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(filename);
        Element root = document.getDocumentElement();
        System.out.println(convert(root, 0));
    }

    private static StringBuilder convert(Node node, int level) {
        if (node instanceof Element) {
            return elementObject((Element) node, level);
        } else if (node instanceof CharacterData) {
            return characterString((CharacterData) node, level);
        } else {
            return pad(new StringBuilder(), level).append(jsonEscape(node.getClass().getName()));
        }
    }

    private static Map<Character, String> replacements = Map.of(
            '\b', "\\b",
            '\f', "\\f",
            '\n', "\\n",
            '\r', "\\r",
            '\t', "\\t",
            '"', "\\\"",
            '\\', "\\\\"
    );

    private static StringBuilder jsonEscape(String str) {
        var result = new StringBuilder("\"");
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String replacement = replacements.get(ch);
            if (replacement == null) {
                result.append(ch);
            } else {
                result.append(replacement);
            }
        }
        result.append("\"");
        return result;
    }

    private static StringBuilder characterString(CharacterData node, int level) {
        var result = new StringBuilder();
        if (node instanceof Comment) {
            StringBuilder data = jsonEscape(node.getData());
            data.insert(1, "Comment: ");
            pad(result, level).append(data);
        }
        return result;
    }

    private static StringBuilder elementObject(Element elem, int level) {
        var result = new StringBuilder();
        pad(result, level).append("{\n");
        pad(result, level + 1).append("\"name\": ");
        result.append(jsonEscape(elem.getTagName()));
        NamedNodeMap attrs = elem.getAttributes();
        if (attrs.getLength() > 0) {
            pad(result.append(",\n"), level + 1).append("\"attributes\": ");
            result.append(attributeObject(attrs, level + 1));
        }
        NodeList children = elem.getChildNodes();
        if (children.getLength() > 0) {
            pad(result.append(",\n"), level + 1).append("\"children\": [\n");
            for (int i = 1; i < children.getLength(); i++) {
                if (i > 1 && (children.item(i) instanceof Element)) {
                    result.append(",\n");
                }
                result.append(convert(children.item(i), level + 2));
            }
            pad(result, level + 1).append("]\n");
        }
        pad(result, level).append("}");
        return result;
    }

    private static StringBuilder pad(StringBuilder builder, int level) {
        return builder.append("  ".repeat(level));
    }

    private static StringBuilder attributeObject(NamedNodeMap attrs, int level) {
        var result = new StringBuilder("{\n");
        for (int i = 0; i < attrs.getLength(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            pad(result, level + 1).append(jsonEscape(attrs.item(i).getNodeName()));
            result.append(": ");
            result.append(jsonEscape(attrs.item(i).getNodeValue()));
            result.append("\n");
        }
        pad(result, level).append("}");
        return result;
    }
}
