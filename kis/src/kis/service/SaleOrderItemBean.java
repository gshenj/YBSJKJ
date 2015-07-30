package kis.service;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 列表展示用
 * Created by jim on 2015/7/19.
 */
public class SaleOrderItemBean {

    @JSONField(name="id")
    int id;

    @JSONField(name="order_number")
    String orderNumber;

    @JSONField(name="sale_date")
    String saleDate;

    @JSONField(name="customer")
    String customer;
    @JSONField(name="create_user")
    String createUser;
    @JSONField(name="create_date",format="yyyy/MM/dd HH:mm")
    Date createDate;

    @JSONField(name="content_thumbnail")
    String contentThumbnail;

    @JSONField(name="flag")
    int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContentThumbnail() {
        return contentThumbnail;
    }

    public void setContentThumbnail(String contentThumbnail) {
        this.contentThumbnail = contentThumbnail;
    }
}
