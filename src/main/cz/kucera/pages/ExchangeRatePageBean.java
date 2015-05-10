package main.cz.kucera.pages;

import main.cz.kucera.parser.exchangereate.ExchangeRateMap;
import main.cz.kucera.services.currency.rate.ExchangeRateService;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by Filip on 4. 5. 2015.
 */
@ManagedBean
public class ExchangeRatePageBean implements Serializable {

    @ManagedProperty(value = "#{exchangeRateParser}")
    ExchangeRateService exchangeRateService;

    BigDecimal exchangeRate = new BigDecimal(9);
    String string = "oooo";
    private LineChartModel lineChartModel;

   public String doLineChartModel() {
       ExchangeRateMap exchangeRateMap = exchangeRateService.getExchangeRatesAll();
       //TODO data musi jit postupne, predelat na list
       Set<Date> dates = exchangeRateMap.getRates().keySet();

       lineChartModel = new LineChartModel();
       LineChartSeries series = new LineChartSeries();
       lineChartModel.setTitle("Model 1");
       series.setLabel("Series 1");

       Integer val1 = 0;
       for (Date date : dates){
           //TODO pridat dalsi meny
           BigDecimal exchangeRateCZK = exchangeRateMap.getRates().get(date).get("CZK");

           val1 = val1 + 1;
           series.set(val1, exchangeRateCZK);
       }
       lineChartModel.addSeries(series);
       return "lineChart";
   }

    public LineChartModel getLineChartModel() {
        return lineChartModel;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public ExchangeRateService getExchangeRateService() {
        return exchangeRateService;
    }
}

