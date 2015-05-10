package main.cz.kucera.services.currency.rate;

import main.cz.kucera.parser.exchangereate.EcbExchangeRateParser;
import main.cz.kucera.parser.exchangereate.ExchangeRateMap;
import main.cz.kucera.parser.exchangereate.ExchangeRateParser;

import javax.faces.bean.ManagedBean;

/**
 * Created by Filip on 18. 4. 2015.
 */

@ManagedBean(name="exchangeRateParser")
public class CurrencyRateServiceImpl implements ExchangeRateService {
    //FIXME use spring autowire
    ExchangeRateParser exchangeRateParser = new EcbExchangeRateParser();
   @Override
    public ExchangeRateMap getExchangeRatesAll() {
        return exchangeRateParser.parseAll();
    }

    @Override
    public ExchangeRateMap getExchangeRatesByDate() {
        return null;
    }


}
