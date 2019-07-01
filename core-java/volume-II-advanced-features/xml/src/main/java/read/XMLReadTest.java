package read;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This program shows how to use an XML file to describe Java objects.
 *
 * @author Cay Horstmann
 * @version 1.0 2018-04-03
 */
public class XMLReadTest {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        String filename;
        if (args.length == 0) {
            try (var in = new Scanner(System.in)) {
                String defaultFilename = "core-java/volume-II-advanced-features/xml/src/main/resources/config-schema.xml";
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

        factory.setValidating(true);

        if (filename.contains("-schema")) {
            factory.setNamespaceAware(true);
            final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
            final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        }

        factory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        builder.setErrorHandler(
                new ErrorHandler() {
                    @Override
                    public void warning(SAXParseException ex) {
                        System.err.println("Warning: " + ex.getMessage());
                    }

                    @Override
                    public void error(SAXParseException ex) {
                        System.err.println("Error: " + ex.getMessage());
                        System.exit(0);
                    }

                    @Override
                    public void fatalError(SAXParseException ex) {
                        System.err.println("Fatal error: " + ex.getMessage());
                        System.exit(0);
                    }
                }
        );

        Document doc = builder.parse(filename);
        Map<String, Object> config = parseConfig(doc.getDocumentElement());
        System.out.println(config);
    }

    private static Map<String, Object> parseConfig(Element element) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var result = new HashMap<String, Object>();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            var child = (Element) children.item(i);
            String name = child.getAttribute("id");
            Object value = parseObject((Element) child.getFirstChild());
            result.put(name, value);
        }
        return result;
    }

    private static Object parseObject(Element element) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tagName = element.getTagName();
        if (tagName.equals("factory")) {
            return parseFactory(element);
        } else if (tagName.equals("construct")) {
            return parseConstruct(element);
        } else {
            String childData = ((CharacterData) element.getFirstChild()).getData();
            if (tagName.equals("int")) {
                return Integer.valueOf(childData);
            } else if (tagName.equals("boolean")) {
                return Boolean.valueOf(childData);
            } else {
                return childData;
            }
        }
    }

    private static Object parseFactory(Element element) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String className = element.getAttribute("class");
        String methodName = element.getAttribute("method");
        Object[] args = parseArgs(element.getChildNodes());
        Class<?>[] parameterTypes = getParameterTypes(args);
        Method method = Class.forName(className).getMethod(methodName, parameterTypes);
        return method.invoke(null, args);
    }

    private static Object parseConstruct(Element element) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = element.getAttribute("class");
        Object[] args = parseArgs(element.getChildNodes());
        Class<?>[] parameterTypes = getParameterTypes(args);
        Constructor<?> constructor = Class.forName(className).getConstructor(parameterTypes);
        return constructor.newInstance(args);
    }

    private static Object[] parseArgs(NodeList elements) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var result = new Object[elements.getLength()];
        for (int i = 0; i < result.length; i++) {
            result[i] = parseObject((Element) elements.item(i));
        }
        return result;
    }

    private static Map<Class<?>, Class<?>> toPrimitive = Map.of(
            Integer.class, int.class,
            Boolean.class, boolean.class
    );

    private static Class<?>[] getParameterTypes(Object[] args) {
        var result = new Class<?>[args.length];
        for (int i = 0; i < result.length; i++) {
            Class<?> cl = args[i].getClass();
            result[i] = toPrimitive.get(cl);
            if (result[i] == null) {
                result[i] = cl;
            }
        }
        return result;
    }
}
