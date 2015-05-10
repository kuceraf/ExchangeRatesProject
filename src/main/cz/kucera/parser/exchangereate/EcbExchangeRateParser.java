package main.cz.kucera.parser.exchangereate;

/**
 * Created by Filip on 3. 5. 2015.
 */

import main.cz.kucera.tools.xml.XmlLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static main.cz.kucera.parser.exchangereate.EcbNamespaceContext.NS_ECB;
import static main.cz.kucera.parser.exchangereate.EcbNamespaceContext.NS_GMS;

public class EcbExchangeRateParser implements ExchangeRateParser {
    private static final String RATES_XML_DAILLY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    public static final String TIME_XPATH = "/" + NS_GMS + ":Envelope//" + NS_ECB + ":Cube[@time]";
    public static final String CURRENCY_XPATH = "./" + NS_ECB + ":Cube";

    @Override
    public ExchangeRateMap parseAll() {
        Document document = XmlLoader.getDocument(RATES_XML_DAILLY);
        try {
            Map<Date, Map<String, BigDecimal>> resultMap = parseXmlDocument(document);
            return new ExchangeRateMap(resultMap);
        } catch (XPathExpressionException e) {

        }
        return null;
    }

    private Map<Date, Map<String, BigDecimal>> parseXmlDocument(Document document) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new EcbNamespaceContext());

        Map<Date, Map<String, BigDecimal>> resultMap = new HashMap<Date, Map<String, BigDecimal>>();
        XPathExpression timeXPath = xpath.compile(TIME_XPATH);
        XPathExpression currencyXPath = xpath.compile(CURRENCY_XPATH);
        NodeList timeNodes = (NodeList) timeXPath.evaluate(document, XPathConstants.NODESET);

        for (int timeNodeNo = 0; timeNodeNo < timeNodes.getLength(); timeNodeNo++) {
            Node timeNode = timeNodes.item(timeNodeNo);
            String time = timeNode.getAttributes().getNamedItem("time").getNodeValue();
            NodeList currencyNodes = (NodeList) currencyXPath.evaluate(timeNode, XPathConstants.NODESET);

            Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
            for (int currencyNodeNo = 0; currencyNodeNo < currencyNodes.getLength(); currencyNodeNo++) {
                Node currencyNode = currencyNodes.item(currencyNodeNo);
                String currencyCode = currencyNode.getAttributes().getNamedItem("currency").getNodeValue();
                BigDecimal currencyRate = new BigDecimal(currencyNode.getAttributes().getNamedItem("rate").getNodeValue());
                rates.put(currencyCode, currencyRate);
            }
            resultMap.put(parseValidityDate(time), rates);
        }
        return resultMap;
    }

    /**
     * Parses string date to {@code java.util.Date}
     *
     * @param date string date representation
     * @return date or {@code null} when parse error occurs
     */
    private static Date parseValidityDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("y-M-d");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }
}
