package ee.ttu.thesis.parser;

import ee.ttu.thesis.domain.Transaction;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    public List<Transaction> parseFile(MultipartFile file) throws IOException {
        //String file = "C:\\Users\\birgi\\Documents\\Äriinfotehnoloogia\\lõputöö\\CVS failid jne\\2019 full xml.xml";
       // File xmlFile = new File(String.valueOf(file));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<Transaction> transactions = new ArrayList<>();

        try {
            InputStream is = file.getInputStream();
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("Ntry");

            for (int i = 0; i < nodeList.getLength(); i++) {
                transactions.add(getTransaction(nodeList.item(i)));
            }
            //DEBUG
            for (Transaction t : transactions) {
                System.out.println(t.toString());
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static String getTagValue(String tag, Element element) {
        try {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }
        catch(NullPointerException e) {
            return null;
        }
    }


    private static Transaction getTransaction(Node node) {
        Transaction t = new Transaction();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            t.setDetails(getTagValue("Ustrd", element));
            t.setDebitOrCredit(getTagValue("CdtDbtInd", element));
            t.setDate(LocalDate.parse((getTagValue("Dt", element)), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            t.setBeneficiaryOrPayerAccount(getTagValue("IBAN", element));
            t.setBeneficiaryOrPayerName(getTagValue("Nm", element));

            if(t.getDebitOrCredit().equals("CRDT"))   t.setAmount(new BigDecimal(getTagValue("Amt", element)));
            else t.setAmount(new BigDecimal(getTagValue("Amt", element)).multiply(new BigDecimal(-1)));
        }
        return t;
    }
}
