package main.cz.kucera.services.currency.rate;

import main.cz.kucera.parser.exchangereate.ExchangeRateMap;

/**
 * Created by Filip on 18. 4. 2015.
 */
public interface ExchangeRateService {
    ExchangeRateMap getExchangeRatesAll();
    ExchangeRateMap getExchangeRatesByDate();
}
