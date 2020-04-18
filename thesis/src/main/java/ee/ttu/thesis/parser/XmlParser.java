package ee.ttu.thesis.parser;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.domain.User;
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

    public List<Transaction> parseFile(MultipartFile file, User user) throws IOException {
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
                transactions.add(getTransaction(nodeList.item(i), user));
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
        } catch (NullPointerException e) {
            return null;
        }
    }


    private static Transaction getTransaction(Node node, User u) {
        Transaction t = new Transaction();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            t = Transaction.builder().
                    details(getTagValue("Ustrd", element)).
                    debitOrCredit(getTagValue("CdtDbtInd", element)).
                    date(LocalDate.parse((getTagValue("Dt", element)), DateTimeFormatter.ofPattern("yyyy-MM-dd"))).
                    beneficiaryOrPayerAccount(getTagValue("IBAN", element)).
                    beneficiaryOrPayerName(getTagValue("Nm", element)).
                    user(u).
                    incomeStatementType(IncomeStatementType.MÄÄRAMATA).
                    currency(getTagAttributeValue("Amt", "Ccy", element)).build();

            if (t.getDebitOrCredit().equals("CRDT")) t.setAmount(new BigDecimal(getTagValue("Amt", element)));
            else t.setAmount(new BigDecimal(getTagValue("Amt", element)).multiply(new BigDecimal(-1)));
        }
        return t;
    }

    private static String getTagAttributeValue(String tag, String attribute, Element element) {
        try {
            return element.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
