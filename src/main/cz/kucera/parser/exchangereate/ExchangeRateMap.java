package main.cz.kucera.parser.exchangereate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by Filip on 3. 5. 2015.
 */

public class ExchangeRateMap {

    Map<Date, Map<String, BigDecimal>> rates;

    ExchangeRateMap(Map<Date, Map<String, BigDecimal>> rates) {
        this.rates = rates;
    }

    public Map<Date, Map<String, BigDecimal>> getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRateMap that = (ExchangeRateMap) o;

        if (rates != null ? !rates.equals(that.rates) : that.rates != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rates != null ? rates.hashCode() : 0;
    }
}
