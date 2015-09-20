package kis.entity;

// Generated 2015-7-17 20:11:35 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SaleOrder generated by hbm2java
 */
public class SaleOrder implements java.io.Serializable {

	private int id;
	private Customer customer;
	private KisUser kisUser;
	private String dateText;
	private BigDecimal totalSum;
	private Date createDatetime;
	private String comment;
	private String orderNumber;


	private String contentThumbnail;
	private int flag;

	private String customerName;
	private String customerAddress;
	private String customerPrincipal;
	private String customerContactNumber;
	private String createUserName;

	private String companyName;
	private String companyAddress;
	private String companyFax;
	private String companyContactNumber;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getCompanyContactNumber() {
		return companyContactNumber;
	}

	public void setCompanyContactNumber(String companyContactNumber) {
		this.companyContactNumber = companyContactNumber;
	}

	private Set<SaleOrderItem> saleOrderItems = new HashSet<SaleOrderItem>(0);

	public SaleOrder() {
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPrincipal() {
		return customerPrincipal;
	}

	public void setCustomerPrincipal(String customerPrincipal) {
		this.customerPrincipal = customerPrincipal;
	}

	public String getCustomerContactNumber() {
		return customerContactNumber;
	}

	public void setCustomerContactNumber(String customerContactNumber) {
		this.customerContactNumber = customerContactNumber;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public SaleOrder(int id) {
		this.id = id;
	}

	public SaleOrder(int id, Customer customer, KisUser kisUser,
			String dateText, BigDecimal totalSum, Date createDatetime,
			String comment, String orderNumber, String contentThumbnail, int flag,
			Set<SaleOrderItem> saleOrderItems) {
		this.id = id;
		this.customer = customer;
		this.kisUser = kisUser;
		this.dateText = dateText;
		this.totalSum = totalSum;
		this.createDatetime = createDatetime;
		this.comment = comment;
		this.orderNumber = orderNumber;
		this.contentThumbnail = contentThumbnail;
		this.flag = flag;
		this.saleOrderItems = saleOrderItems;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public KisUser getKisUser() {
		return this.kisUser;
	}

	public void setKisUser(KisUser kisUser) {
		this.kisUser = kisUser;
	}

	public String getDateText() {
		return this.dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public BigDecimal getTotalSum() {
		return this.totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getContentThumbnail() {
		return this.contentThumbnail;
	}

	public void setContentThumbnail(String contentThumbnail) {
		this.contentThumbnail = contentThumbnail;
	}

	public Set<SaleOrderItem> getSaleOrderItems() {
		return this.saleOrderItems;
	}

	public void setSaleOrderItems(Set<SaleOrderItem> saleOrderItems) {
		this.saleOrderItems = saleOrderItems;
	}

}
