package kis.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import kis.dao.*;
import kis.entity.*;
import kis.service.*;
import kis.util.JSONResult;
import kis.util.Pagination;
import kis.util.Settings;
import kis.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 2015/7/14.
 */
@WebServlet(name = "SaleServiceServlet", urlPatterns = "/sale/order_service")
public class SaleServiceServlet extends HttpServlet {

    CityHome cityHome = new CityHome();
    ProductCategoryHome productCategoryHome = new ProductCategoryHome();
    SaleOrderHome saleOrderHome = new SaleOrderHome();
    ProductModalHome productModalHome = new ProductModalHome();
    CustomerHome customerHome = new CustomerHome();
    ProductUnitsHome productUnitsHome = new ProductUnitsHome();


    public void deleteModal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = WebUtils.getIntParamValue(request, "id");
        JSONResult result = new JSONResult();
        try {
            productModalHome.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    public void saveOrUpdateModal(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = WebUtils.getIntParamValue(request, "id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int productUnitsId = WebUtils.getIntParamValue(request, "productUnitsId");
        BigDecimal suggestUnitPrice = WebUtils.getBigDecimalParamValue(request, "suggestUnitPrice");

        JSONResult result = new JSONResult();
        try {
            if (id > 0) {
                // edit mode
                ProductModal modal = productModalHome.findById(id);
                if (modal != null) {
                    modal.setName(name);
                    modal.setDescription(description);
                    modal.setSuggestUnitPrice(suggestUnitPrice);
                    ProductUnits units = productUnitsHome.findById(productUnitsId);
                    modal.setProductUnits(units);
                    productModalHome.saveOrUpdate(modal);

                    result.setSuccess(true);
                } else {
                    result.setErrorMsg("产品型号不存在！");
                }

            } else {
                // add mode
                int customerId = WebUtils.getIntParamValue(request, "customerId");
                int categoryId = WebUtils.getIntParamValue(request, "categoryId");
                ProductModal modal = new ProductModal();
                modal.setName(name);
                modal.setDescription(description);
                modal.setSuggestUnitPrice(suggestUnitPrice);
                modal.setProductUnits(productUnitsHome.findById(productUnitsId));
                modal.setCustomer(customerHome.findById(customerId));
                modal.setProductCategory(productCategoryHome.findById(categoryId));
                productModalHome.saveOrUpdate(modal);

                result.setSuccess(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    public void getModals(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int customerId = WebUtils.getIntParamValue(request, "customerId");
        int categoryId = WebUtils.getIntParamValue(request, "categoryId");

        if (customerId <= 0 || categoryId <= 0) {
            return;
        }

        List<ProductModal> modals = productModalHome.findProductModalsByCustomerAndCategory(customerId, categoryId);

        List<ProductModalBean> modalBeans = new ArrayList<ProductModalBean>();
        for (ProductModal modal : modals) {
            ProductModalBean bean = new ProductModalBean();
            bean.setName(modal.getName());
            bean.setId(modal.getId());
            bean.setDescription(modal.getDescription());
            bean.setSuggestUnitPrice(modal.getSuggestUnitPrice());
            bean.setProductUnitsId(modal.getProductUnits().getId());
            bean.setProductUnitsName(modal.getProductUnits().getCnName());
            bean.setCategoryId(modal.getProductCategory().getId());
            bean.setCategoryName(modal.getProductCategory().getName());
            bean.setCustomerId(modal.getCustomer().getId());
            bean.setCustomerName(modal.getCustomer().getName());
            modalBeans.add(bean);
        }

        JSON.writeJSONStringTo(modalBeans, response.getWriter());
    }


    public void getProductUnits(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<ProductUnits> unitsList = productUnitsHome.findAll();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ProductUnits.class, "id", "cnName");
        response.getWriter().write(JSON.toJSONString(unitsList, filter));
    }


    public void getCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company company = (Company) request.getSession().getAttribute("company");
        List<CustomerBean> customerBeans = new ArrayList<CustomerBean>();

        int addall = WebUtils.getIntParamValue(request, "addall");
        if (addall == 1) {
            CustomerBean bean = new CustomerBean();
            bean.setId(Settings.ID_NEGATIVE_1);
            bean.setName("所有客户");
            bean.setCityName("");
            bean.setCityId(Settings.ID_NEGATIVE_1);
            customerBeans.add(bean);
        }

        List<Customer> customers = customerHome.findCompanyCustomers(company);
        for (Customer customer : customers) {
            CustomerBean bean = new CustomerBean();
            bean.setId(customer.getId());
            bean.setName(customer.getName());
            bean.setAddress(customer.getAddress());
            bean.setMobileNumber(customer.getMobileNumber());
            bean.setTelNumber(customer.getTelNumber());
            bean.setPrincipal(customer.getPrincipal());
            bean.setSaleBillInfo(customer.getSaleBillInfo());
            bean.setCityId(customer.getCity().getId());
            bean.setCityName(customer.getCity().getName());

            customerBeans.add(bean);
        }



        JSON.writeJSONStringTo(customerBeans, response.getWriter());
    }


    public void saveOrUpdateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String principal = request.getParameter("principal");
        String telNumber = request.getParameter("telNumber");
        String mobileNumber = request.getParameter("mobileNumber");
        String address = request.getParameter("address");
        String saleBillInfo = request.getParameter("saleBillInfo");

        int id = WebUtils.getIntParamValue(request, "id");
        int cityId = WebUtils.getIntParamValue(request, "cityId");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setTelNumber(telNumber);
        customer.setMobileNumber(mobileNumber);
        customer.setSaleBillInfo(saleBillInfo);
        customer.setPrincipal(principal);

        Company company = (Company) request.getSession().getAttribute("company");
        customer.setCompany(company);


        if (id > 0) {
            customer.setId(id);
        }

        if (cityId > 0) {
            City city = cityHome.findById(cityId);
            customer.setCity(city);
        }

        JSONResult result = new JSONResult();
        try {
            customerHome.saveOrUpdate(customer);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    public void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = WebUtils.getIntParamValue(request, "id");
        JSONResult result = new JSONResult();
        try {
            customerHome.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    public void getCategories(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<ProductCategory> cities = productCategoryHome.findAll();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ProductCategory.class, "id", "name", "description");
        String s = JSON.toJSONString(cities, filter);
        response.getWriter().write(s);
    }

    public void saveOrUpdateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        int id = WebUtils.getIntParamValue(request, "id");

        ProductCategory category = new ProductCategory();
        category.setName(name);
        category.setDescription(desc);
        if (id > 0) {
            category.setId(id);
        }
        JSONResult result = new JSONResult();
        try {
            productCategoryHome.saveOrUpdate(category);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }

    public void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = WebUtils.getIntParamValue(request, "id");
        //ProductCategory category = new ProductCategory();
        //category.setId(id);
        JSONResult result = new JSONResult();
        try {
            productCategoryHome.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    public void getCities(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<City> cities = cityHome.findAll();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(City.class, "id", "name", "province");
        String s = JSON.toJSONString(cities, filter);
        response.getWriter().write(s);
        // response.getWriter().write(Settings.CITIES_JSON_STR);
    }


    public void getSaleOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("execute getSaleOrders");

        boolean exportMode = false;
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        int flag = WebUtils.getIntParamValue(request, "flag", Settings.SALE_ORDER_ALL);
        int customerId = WebUtils.getIntParamValue(request, "customerId", Settings.SALE_ORDER_ALL);
        Company company = (Company) request.getSession().getAttribute("company");

        //page=1&rows=30
        int page = WebUtils.getIntParamValue(request, "page", Settings.SALE_ORDER_ALL);
        int rows = WebUtils.getIntParamValue(request, "rows", Settings.SALE_ORDER_ALL);


        SaleOrderQueryCondition condition = new SaleOrderQueryCondition();
        condition.setCompanyId(company.getId());
        condition.setFlag(flag);
        condition.setCustomerId(customerId);
        condition.setBeginDate(beginDate);
        condition.setEndDate(endDate);

        if (page==Settings.SALE_ORDER_ALL && rows==Settings.SALE_ORDER_ALL) {    // else exportMode
            page = 1;
            rows = 100000;   // 默认最多支持导出100000行
            exportMode = true;
        }

        Pagination<SaleOrder> pagination = new Pagination<SaleOrder>(page, rows);
        condition.setPagination(pagination);

        Pagination<SaleOrder> pagination1 = saleOrderHome.findAllSaleOrderPagination(condition);
        List<SaleOrder> data = pagination1.getData();

        if (exportMode) {
            // 導出請求
                // doExport(request,response);
        } else {
            // 查詢請求
            List<SaleOrderItemBean> orderItemRows = new ArrayList<SaleOrderItemBean>();
            for (SaleOrder saleOrder : data) {

                SaleOrderItemBean bean = new SaleOrderItemBean();
                bean.setOrderNumber(saleOrder.getOrderNumber());
                bean.setSaleDate(saleOrder.getDateText());
                bean.setCustomer(saleOrder.getCustomer().getName());
                bean.setCreateUser(saleOrder.getKisUser().getName());
                bean.setCreateDate(saleOrder.getCreateDatetime());
                bean.setContentThumbnail(WebUtils.nullToBlank(saleOrder.getContentThumbnail()));
                bean.setId(saleOrder.getId());
                bean.setFlag(saleOrder.getFlag());
                orderItemRows.add(bean);
            }

            SaleOrderListBean listBean = new SaleOrderListBean();
            listBean.setCount(pagination1.getTotalCount());
            listBean.setRows(orderItemRows);

            JSON.writeJSONStringTo(listBean, response.getWriter());
        }
    }


    public void invalidSaleOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("execute invalidSaleOrder");
        int id = WebUtils.getIntParamValue(request, "saleOrderId");
        JSONResult result = new JSONResult();
        try {
            saleOrderHome.invalid(id);
            result.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }


    /**
     * 可以使用customer_modals.jsp替代
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCustomerModals(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("execute getCustomerModals...");
        String customerIdStr = request.getParameter("customerId");
        int customerId = NumberUtils.toInt(customerIdStr);

        List<ProductModal> modals = productModalHome.findProductModalsByCustomer(customerId);
        List<ModalBean> beans = new ArrayList<ModalBean>();
        //JsonArrayBuilder ja = Json.createArrayBuilder();
        for (ProductModal modal : modals) {
            ModalBean bean = new ModalBean();
            bean.setId(modal.getId());
            bean.setModalName(modal.getName());
            bean.setCategoryName(modal.getProductCategory().getName());
            bean.setUnitsName(modal.getProductUnits().getCnName());
            BigDecimal price = modal.getSuggestUnitPrice();
            bean.setUnitPrice(price==null?null:price.toString());
            beans.add(bean);


           /* int modalId = modal.getId();
            String categoryName = modal.getProductCategory().getName();
            String unitsName = modal.getProductUnits().getCnName();
            String modalName = modal.getName();

            JsonObjectBuilder json = Json.createObjectBuilder();
            json.add("modal_id", modalId);
            json.add("suggest_unit_price", price);
            json.add("modal_name", modalName);
            json.add("modal_units", unitsName);
            json.add("category_name", categoryName);
            ja.add(json);*/
        }

        JSON.writeJSONStringTo(beans, response.getWriter());
       /* JsonWriter writer = Json.createWriter(response.getWriter());
        JsonArray array = ja.build();
        writer.writeArray(array);*/
    }


    /**
     * 可以使用company_customers.jsp替代
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCompanyCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("execute getCompanyCustomers...");
        String companyIdStr = request.getParameter("company_id");
        Integer companyId = NumberUtils.toInt(companyIdStr);
        List<Customer> customers = customerHome.findCompanyCustomers(companyId);
        JsonArrayBuilder ja = Json.createArrayBuilder();
        for (Customer customer : customers) {
            JsonObjectBuilder _json = Json.createObjectBuilder();
            _json.add("city", customer.getCity().getName());   // customer city
            _json.add("id", customer.getId());     // customer id;
            _json.add("name", customer.getName());  // customer name
            _json.add("text", customer.getName());  // customer name
            _json.add("address", customer.getAddress());
            _json.add("principal", customer.getPrincipal());
            _json.add("tel_number", customer.getTelNumber());
            _json.add("mobile_number", customer.getMobileNumber());
            ja.add(_json);
        }

        JsonWriter writer = Json.createWriter(response.getWriter());
        JsonArray array = ja.build();
        System.out.println(array.toString());
        writer.writeArray(array);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpServletRequest fRequest = request;
        final HttpServletResponse fResponse = response;
        String method = request.getParameter("method");
        try {
            this.getClass()
                    .getMethod(method, new Class[]{HttpServletRequest.class, HttpServletResponse.class})
                    .invoke(this, new Object[]{
                            fRequest, fResponse
                    });
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":9}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
