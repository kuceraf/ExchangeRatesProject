import junit.framework.Assert;
import main.cz.kucera.parser.exchangereate.EcbExchangeRateParser;
import main.cz.kucera.parser.exchangereate.ExchangeRateMap;
import main.cz.kucera.parser.exchangereate.ExchangeRateParser;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Filip on 3. 5. 2015.
 */
public class ExchangeRateParserTest {
    private ExchangeRateParser exchangeRateParser;

    @Before
    public void createInstance() {
        exchangeRateParser = new EcbExchangeRateParser();
    }

    @Test
    public void testParseAll(){
        ExchangeRateMap exchangeRates = exchangeRateParser.parseAll();
        Assert.assertTrue(!exchangeRates.getRates().isEmpty());
    }
}
