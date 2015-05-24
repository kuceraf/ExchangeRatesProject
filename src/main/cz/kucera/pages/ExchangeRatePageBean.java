package main.cz.kucera.pages;

import main.cz.kucera.parser.exchangereate.ExchangeRateMap;
import main.cz.kucera.services.currency.rate.ExchangeRateService;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

/**
 * Created by Filip on 4. 5. 2015.
 */
@ManagedBean
public class ExchangeRatePageBean implements Serializable {

    @ManagedProperty(value = "#{exchangeRateParser}")
    ExchangeRateService exchangeRateService;
    Date beginDate;
    Date endDate;

    BigDecimal exchangeRate = new BigDecimal(9);
    private LineChartModel lineChartModel;

    //TODO predelat jako factory method
   public String doLineChartModel() {
       ExchangeRateMap exchangeRateMap = exchangeRateService.getExchangeRatesAll();
       Set<Date> dates = exchangeRateMap.getRates().keySet();
       List<Date> orderedDates = new ArrayList<Date>(dates);
       Collections.sort(orderedDates);

       lineChartModel = new LineChartModel();
       LineChartSeries series = new LineChartSeries();
       lineChartModel.setTitle("Exchange rates - daily");
       series.setLabel("EUR to CZK");

       for (Date date : orderedDates) {
           //TODO pridat dalsi meny
           if (applyDateFilters(date)) {
               BigDecimal exchangeRateCZK = exchangeRateMap.getRates().get(date).get("CZK");
               series.set(DateFormat.getDateInstance().format(date), exchangeRateCZK);
           }
       }
       lineChartModel.addSeries(series);
       lineChartModel.getAxes().put(AxisType.X, new CategoryAxis("Date"));
       lineChartModel.getAxis(AxisType.X).setTickAngle(280);
       lineChartModel.getAxis(AxisType.Y).setLabel("Exchange rate");
       return "line-chart";
   }

    //TODO predelat jako closure
    private boolean applyDateFilters(Date date) {
        boolean isAfterBeginDate = true;
        boolean isBeforeEndDate = true;
        if(beginDate != null) {
            isAfterBeginDate = date.after(beginDate) || date.equals(beginDate);
        }

        if(endDate != null) {
            isBeforeEndDate = date.before(endDate) || date.equals(endDate);
        }

        return isAfterBeginDate && isBeforeEndDate;
    }

    //getters, setters


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public LineChartModel getLineChartModel() {
        return lineChartModel;
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

