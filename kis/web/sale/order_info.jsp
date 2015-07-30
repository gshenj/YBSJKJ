<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="kis.service.SaleOrderBean" %>
<%@ page import="kis.dao.*" %>
<%@ page import="kis.service.ModalBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kis.entity.*" %>
<%@ page import="kis.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String sale_info = request.getParameter("sale_order");
    SaleOrderBean saleOrderBean = JSON.parseObject(sale_info, SaleOrderBean.class);   //数字全部是字符串形式！处理字符串！  字符串自动转成数字了？  空数字一律转成null处理

    SaleOrderHome saleOrderHome = new SaleOrderHome();
    int requestType = saleOrderBean.getRequestType();
    if (requestType == 1) {
        // 预览请求
        String saleOrderNumber = (saleOrderHome.getSeqSaleOrderValue(false)+1) + "";
        saleOrderBean.setSaleOrderNumber(saleOrderNumber);
        Company company = (Company) session.getAttribute("company");
        saleOrderBean.setCompany(company);
        KisUser user = (KisUser) session.getAttribute("user");
        saleOrderBean.setCreateUser(user);

        Customer customer = new CustomerHome().findById(saleOrderBean.getCustomer().getId());
        saleOrderBean.setCustomer(customer);

    } else if (requestType == 2) {
        // 插入请求
        KisUser createUser = (KisUser) session.getAttribute("user");
        Company company = (Company) session.getAttribute("company");
        saleOrderBean.setCompany(company);

        SaleOrder saleOrder = new SaleOrder();

        Customer customer = new CustomerHome().findById(saleOrderBean.getCustomer().getId());
        saleOrder.setCustomer(customer);
        saleOrder.setCustomerName(customer.getName());
        saleOrder.setCustomerAddress(customer.getAddress());
        saleOrder.setCustomerPrincipal(customer.getPrincipal());
        saleOrder.setCustomerContactNumber(customer.getTelNumber());

        saleOrderBean.setCustomer(customer);

        saleOrder.setCreateDatetime(new Date());
        saleOrder.setDateText(saleOrderBean.getSaleDate());

        KisUser user = new KisUserHome().findById(createUser.getId());
        saleOrder.setCreateUserName(user.getName());
        saleOrder.setKisUser(user);
        saleOrderBean.setCreateUser(user);

        String saleOrderNumber = saleOrderHome.getSeqSaleOrderValue(true) + "";
        saleOrder.setOrderNumber(saleOrderNumber);
        saleOrderBean.setSaleOrderNumber(saleOrderNumber);

        int i = 1;
        StringBuilder contentThumbnailBuilder = new StringBuilder();
        //BigDecimal mTotal = new BigDecimal(0);
        List<SaleOrderItem> items = new ArrayList<SaleOrderItem>();

        for (ModalBean modalBean : saleOrderBean.getModals()) {
            SaleOrderItem item = new SaleOrderItem();
            item.setMemo(modalBean.getMemo());
            item.setQuantity(new BigDecimal(modalBean.getQuantity()));   // use int type
            item.setSerialNumber(i++);
            String _unitPrice = modalBean.getUnitPrice();
            if (_unitPrice.equals("")) {
                item.setUnitPrice(null);
                item.setSum(null);
            } else {
                BigDecimal unitPrice = new BigDecimal(_unitPrice);
                item.setUnitPrice(unitPrice);                   //
                item.setSum(item.getQuantity().multiply(unitPrice));  //
             }
            ProductModal productModal = new ProductModalHome().getWithProperties(modalBean.getId());
            item.setProductModal(productModal);
            item.setProductCategoryText(productModal.getProductCategory().getName());
            item.setProductModalText(productModal.getName());
            item.setProductUnitsText(productModal.getProductUnits().getCnName());

            item.setSaleOrder(saleOrder);   //
            items.add(item);
            //重设前台传过来的内容用于显示
            modalBean.setModalName(productModal.getName());
            modalBean.setCategoryName(productModal.getProductCategory().getName());
            modalBean.setUnitsName(productModal.getProductUnits().getCnName());
            //totalSum += item.getSum();
            // add totalsum
            if (i>2)
                contentThumbnailBuilder.append("<br />");

            contentThumbnailBuilder.append("[").append(productModal.getName())
                    .append(",").append(productModal.getProductCategory().getName())
                    .append(",").append(item.getQuantity().toString())
                    .append(productModal.getProductUnits().getCnName());
            if (item.getUnitPrice()!=null && item.getSum() != null) {
                contentThumbnailBuilder.append("*").append(item.getUnitPrice().toString()).append("元/")
                        .append(productModal.getProductUnits().getCnName())
                        .append("=").append(item.getSum().toString()).append("元");
            }
            contentThumbnailBuilder.append("]");

        }
        saleOrder.setSaleOrderItems(new HashSet(items));
        if ("".equals(saleOrderBean.getTotalSum())) {
            saleOrder.setTotalSum(null);
        } else {
            saleOrder.setTotalSum(new BigDecimal(saleOrderBean.getTotalSum()));
        }


        saleOrder.setContentThumbnail(contentThumbnailBuilder.toString());
        saleOrderHome.saveOrUpdate(saleOrder);

        //

    } else {
        // 列表查询中的详情
        int saleOrderId = saleOrderBean.getSaleOrderId();
        SaleOrder saleOrder = saleOrderHome.findById1(saleOrderId);

        // sale_order_number
        saleOrderBean.setSaleOrderNumber(saleOrder.getOrderNumber());
        // total_sum
        saleOrderBean.setTotalSum(WebUtils.nullToBlank(saleOrder.getTotalSum()));
        saleOrderBean.setSaleDate(saleOrder.getDateText());
        saleOrderBean.setFlag(saleOrder.getFlag());

        //saleOrderBean.setCreateUser(saleOrder.getKisUser());
        KisUser cUser = new KisUser();
        cUser.setName(saleOrder.getKisUser().getName());
        saleOrderBean.setCreateUser(cUser);


        //saleOrderBean.setCustomer(saleOrder.getCustomer());
        Customer cCustomer = new Customer();
        cCustomer.setAddress(saleOrder.getCustomerAddress());
        cCustomer.setName(saleOrder.getCustomerName());
        cCustomer.setPrincipal(saleOrder.getCustomerPrincipal());
        cCustomer.setTelNumber(saleOrder.getCustomerContactNumber());
        saleOrderBean.setCustomer(cCustomer);

        saleOrderBean.setCompany(saleOrder.getCustomer().getCompany());


        List<ModalBean> modals = new ArrayList<ModalBean>();
        for (SaleOrderItem item : saleOrder.getSaleOrderItems()) {
            ModalBean modal = new ModalBean();
           // modal.setId(item.getProductModal().getId());    //
            modal.setModalName(item.getProductModalText());
            modal.setCategoryName(item.getProductCategoryText());
            modal.setUnitsName(item.getProductUnitsText());
            modal.setMemo(item.getMemo());    // blank or null.
            modal.setQuantity(item.getQuantity().intValue());
            BigDecimal unitPrice = item.getUnitPrice();
            modal.setUnitPrice(unitPrice == null ? "" : unitPrice.toString());
            BigDecimal sum = item.getSum();
            modal.setSum(sum==null?"":sum.toString());
            modals.add(modal);
        }
        saleOrderBean.setModals(modals);
    }


    //DecimalFormat nf = new DecimalFormat("¥#,###.##");
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
    String totalSum = StringUtils.isBlank(saleOrderBean.getTotalSum()) ? "" : nf.format(new BigDecimal(saleOrderBean.getTotalSum()));   // if null
%>
<html>
<head>
    <title>打印预览</title>
    <style type="text/css">
        * {
            font: 12pt/1.5 \5b8b\4f53, Arial, sans-serif;
        }

        body {
            text-align: center;
            background-color: #FFF;

        }

        .print_content {
            padding: 0;
            background-color: #fff;
            overflow: auto;
            text-align: center;
            width:100%;
        }

        .print_content td {
            height: 20pt;
            line-height:20pt;
            text-align: left;
            border:none;
            font: 12pt/1.5 \5b8b\4f53, Arial, sans-serif;
        }

        table.print_bill_product_table {
            width: 100%;
            font: 12pt/1.5 \5b8b\4f53, Arial, sans-serif;
            border-collapse: collapse;
            table-layout: fixed;
        }

        table.print_bill_product_table .tbh  td {
            height:22pt;
            line-height: 20pt;
            font-weight: bold;
        }

        table.print_bill_product_table td {
            border: 1px solid black;
            text-align: center;
            height:20pt;
            line-height: 20pt;
        }


        td.print_bill_intro {
            vertical-align: middle;
            text-align: center;
            padding: 0;
            border: none;
        }

        td.print_bill_intro div {
            margin: 2pt auto;
            font-size:11pt;
        }

        table.print_sign_table {

            width: 84%;
            margin: 15pt auto 0pt;
            table-layout: fixed;
            padding: 0px;
        }
    </style>
</head>
<body>
<div class="print_content">
        <table style="border-collapse: collapse; width: 100%; border: none;table-layout: fixed; margin: 0pt auto; text-align: left;">
            <tr>
                <td colspan="8" style="text-align: center; height:26pt; line-height: 26pt; font-size:18pt; padding:0pt;">
                    <%=saleOrderBean.getCompany().getName()%>
                </td>
            </tr>
            <tr>
                <td colspan="8" style="text-align: center; height:20pt; line-height:20pt;font-size:14pt; padding:4pt 0pt;">
                    成品出货单
                </td>
            </tr>
            <tr>
                <td colspan="7" style="padding-left:2pt;">
                    地址：<%=WebUtils.nullToBlank(saleOrderBean.getCompany().getAddress())%>
                </td>
                <td></td>
            </tr>
            <tr>
                <td colspan="3" style="padding-left:2pt;">电话：<%=WebUtils.nullToBlank(saleOrderBean.getCompany().getTelNumber())%></td>
                <td colspan="3" style="">传真：<%=WebUtils.nullToBlank(saleOrderBean.getCompany().getFax())%></td>
                <td colspan="2" style="text-align: left;">出货单编号：<%=WebUtils.nullToBlank(saleOrderBean.getSaleOrderNumber())%></td>

            </tr>
            <tr>
                <td colspan="8" style="height:10pt; line-height: 10pt; border:none; border-top:1px solid black; ">
                </td>
            </tr>

            <tr>
                <td style="padding-left:2pt;" colspan="7">
                    <div>
                        <label style="float:left;height:100%; ">客户名称：</label>
                        <%=WebUtils.nullToBlank(saleOrderBean.getCustomer().getName())%>
                    </div>
                </td>
                <td></td>
            </tr>
            <tr>
                <td colspan="3" style="padding-left:2pt;">客户电话：<span><%=WebUtils.nullToBlank(saleOrderBean.getCustomer().getTelNumber())%></span></td>

                <td style="" colspan="3">
                    联系人：<span><%=WebUtils.nullToBlank(saleOrderBean.getCustomer().getPrincipal())%></span>
                </td>
                <td colspan="2"></td>
            </tr>
            <tr>

                <td style="padding-left:2pt;" colspan="4"
                        >送货地址：<span><%=WebUtils.nullToBlank(saleOrderBean.getCustomer().getAddress())%></span></td>
                <td  colspan="3">
                    <div style="float:right;"><label style="float:left; ">送货日期：</label><span><%=WebUtils.nullToBlank(saleOrderBean.getSaleDate())%></span>
                    </div>
                </td>
                <td></td>

            </tr>
        </table>

    <table class="print_bill_product_table">
        <tr class="tbh">
            <td width="18%">规格型号</td>
            <td width="15%">货物名称</td>
            <td width="8%">单位</td>

            <td width="10%">数量</td>
            <td width="10%">价格</td>
            <td width="14%">金额</td>

            <td width="14%">说明</td>
            <td width="11%" style="border:none;" >
                备注：
            </td>
        </tr>

        <%
            List<ModalBean> modals = saleOrderBean.getModals();
            int size = modals.size();
            for (int i = 0; i < kis.util.Settings.SALE_ORDER_ITEM_NUM; i++) {
                String modalName = null;
                String categoryName = null;
                String units = null;
                Integer quantity = null;
                String unitPrice = null;
                String sum = null;
                String memo = null;
                //BigDecimal totalSum = null;

                if (i<size) {
                    ModalBean modalBean = modals.get(i);
                    //int modalId = modalBean.getId();
                    /*System.out.println("modalId = " +modalId);
                    ProductModal modal = new ProductModalHome().getWithProperties(modalId);
                    modalName = modal.getName();
                    categoryName = modal.getProductCategory().getName();
                    units = modal.getProductUnits().getCnName();
                    quantity = modalBean.getQuantity();
                    unitPrice = modalBean.getUnitPrice();
                    sum = modalBean.getSum();
                    memo = modalBean.getMemo();*/

                    modalName = modalBean.getModalName();        //
                    categoryName = modalBean.getCategoryName();  //
                    units = modalBean.getUnitsName();            //
                    quantity = modalBean.getQuantity();
                    unitPrice = modalBean.getUnitPrice();
                    sum = modalBean.getSum();
                    memo = modalBean.getMemo();
                }


        %>
        <tr class="row">
            <td>
                <span><%=WebUtils.nullToBlank(modalName)%></span>
            </td>
            <td><span><%=WebUtils.nullToBlank(categoryName)%></span></td>
            <td><span><%=WebUtils.nullToBlank(units)%></span></td>
            <td >
                <span><%=quantity == null ? "": quantity%></span>
            </td>
            <td>
                <span><%=WebUtils.nullToBlank(unitPrice)%></span>
            </td>
            <td >
                <span><%=WebUtils.nullToBlank(sum)%></span>
            </td>
            <td>
                <span><%=WebUtils.nullToBlank(memo)%></span>
            </td>
            <%
                if (i==0) {
            %>
            <td rowspan="6" class="print_bill_intro" style="border:none;">
                <div>白联:留存</div>
                <div>红联:客户</div>
                <div>黄联:结算</div>
            </td>
            <%
                }
            %>
        </tr>
        <%
            }
        %>
        <tr class="row">
            <td colspan="7" style="text-align: right;font-weight: bold;">
            <span style="margin-right: 20%;">合计金额：<%=totalSum%></span>
            </td>

        </tr>
    </table>

    <table class="print_sign_table">
            <tr>
                <td style="width:33.3%; text-align: center;">财务：</td>
                <td style="width:33.3%; text-align: center;">制单：<%=WebUtils.nullToBlank(saleOrderBean.getCreateUser().getName())%>
                </td>
                <td style="width:33.3%; text-align: center;">客户签收：</td>
            </tr>
        </table>
    <%
        if (saleOrderBean.getFlag() == 1) {
    %>

    <img style="border:none; width:200px; height:137px; position:absolute; left:320pt; top:20pt;"  src="../images/invalid_w200.png" />
    <%--<div style="border:0px solid green; width:200px; height:137px; position:absolute; left:320pt; top:20pt; background-image:url('../images/invalid_w200.png')" >--%>
    </div>
    <%
        }
    %>

</div>



</body>
</html>
