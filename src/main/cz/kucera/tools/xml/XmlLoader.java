package main.cz.kucera.tools.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Filip on 3. 5. 2015.
 */
public class XmlLoader {

    public static Logger logger = Logger.getLogger(XmlLoader.class);

    /**
     * Loads XML file with actual currency rates from URL saved in configuration file
     * and parses it into {@code org.w3c.dom.Document}
     * @return parsed document or {@code null} when any error occurs
     */
    public static Document getDocument(String xmlUrl) {
        Document document = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(xmlUrl);
            inputStream = url.openStream();

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);

            DocumentBuilder builder = domFactory.newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (MalformedURLException e) {
            logger.error("Failed to retrieve XML file with currency rates.", e);
        } catch (IOException e) {
            logger.error("Failed to open XML file with currency rates.", e);
        } catch (SAXException e) {
            logger.error("Failed to parse XML file with currency rates.", e);
        } catch (ParserConfigurationException e) {
            logger.error("Failed to configure currency rates XML file parser.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Failed to close input stream fo currency rates XML file", e);
                }
            }
        }

        return document;
    }
}
