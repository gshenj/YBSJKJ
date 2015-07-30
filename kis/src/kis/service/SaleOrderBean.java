package kis.service;

import kis.entity.Company;
import kis.entity.Customer;
import kis.entity.KisUser;

import java.util.List;

/**
 * Created by jim on 2015/7/10.
 */
public class SaleOrderBean {

    int saleOrderId;

    String saleOrderNumber;

    public String getSaleOrderNumber() {
        return saleOrderNumber;
    }

    public void setSaleOrderNumber(String saleOrderNumber) {
        this.saleOrderNumber = saleOrderNumber;
    }

    String saleDate;
    List<ModalBean> modals;

    KisUser createUser;
    Company company;
    Customer customer;
    String totalSum;

    int flag = 0;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(int saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public KisUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(KisUser createUser) {
        this.createUser = createUser;
    }

    //判断是否是请求模式
    //1:打印， 2：预览， 3列表显示
    int requestType;


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }



    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public List<ModalBean> getModals() {
        return modals;
    }

    public void setModals(List<ModalBean> modals) {
        this.modals = modals;
    }


}

