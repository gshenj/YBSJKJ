package kis.service;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by jim on 2015/7/19.
 */
public class SaleOrderListBean {

    @JSONField(name="total")
    Long count;

    @JSONField(name="rows")
    List<SaleOrderItemBean> rows;


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<SaleOrderItemBean> getRows() {
        return rows;
    }

    public void setRows(List<SaleOrderItemBean> rows) {
        this.rows = rows;
    }
}
