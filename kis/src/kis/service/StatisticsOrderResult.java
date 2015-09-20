package kis.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 2015/3/5.
 */
public class StatisticsOrderResult {
    String customerName;
    List<StatisticsItemResult> itemResults = new ArrayList<StatisticsItemResult>();

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<StatisticsItemResult> getItemResults() {
        return itemResults;
    }

    public void setItemResults(List<StatisticsItemResult> itemResults) {
        this.itemResults = itemResults;
    }
}
