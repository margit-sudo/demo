package ee.ttu.thesis;

import ee.ttu.thesis.parser.XmlParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootTest
class ThesisApplicationTests {

    @Test
    public void testParse() throws IOException {
        XmlParser parser = new XmlParser();
        //parser.parseFile(file);
    }
}
