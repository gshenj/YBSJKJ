package kis.service;

import java.math.BigDecimal;

/**
 * Created by jim on 2015/7/25.
 */
public class ProductModalBean {

    int id;
    String name;
    String description;

    int categoryId;
    String categoryName;

    int productUnitsId;
    String productUnitsName;

    BigDecimal suggestUnitPrice;

    int customerId;
    String customerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getProductUnitsId() {
        return productUnitsId;
    }

    public void setProductUnitsId(int productUnitsId) {
        this.productUnitsId = productUnitsId;
    }

    public String getProductUnitsName() {
        return productUnitsName;
    }

    public void setProductUnitsName(String productUnitsName) {
        this.productUnitsName = productUnitsName;
    }

    public BigDecimal getSuggestUnitPrice() {
        return suggestUnitPrice;
    }

    public void setSuggestUnitPrice(BigDecimal suggestUnitPrice) {
        this.suggestUnitPrice = suggestUnitPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
