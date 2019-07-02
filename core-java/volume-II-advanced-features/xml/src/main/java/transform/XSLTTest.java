package transform;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Paths.get;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

/**
 * @author Rinat_Zalyaletdinov
 */
public class XSLTTest {
    public static void main(String[] args) throws IOException, TransformerException {
        try (var xsl = newInputStream(get("core-java/volume-II-advanced-features/xml/src/main/resources/cd-catalog.xsl"))) {
            var styleSource = new StreamSource(xsl);
            Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
            try (var xml = newInputStream(get("core-java/volume-II-advanced-features/xml/src/main/resources/cd-catalog.xml"))) {
                t.transform(new StreamSource(xml), new StreamResult(System.out));
            }
        }
    }
}
