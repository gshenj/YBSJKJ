<%@ page import="org.apache.commons.lang.time.DateUtils" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    List<Map> datas = new ArrayList<Map>();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    // 打印

    String today = format.format(calendar.getTime());
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    String curMonthBegin = format.format(calendar.getTime());


    System.out.println("当月第一天：" + format.format(calendar.getTime()));

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    String curMonthEnd = format.format(calendar.getTime());
    System.out.println("当月最后一天：" + format.format(calendar.getTime()));

    //calendar.set(Calendar.DATE, calendar.(Calendar.DATE));

    calendar.add(Calendar.MONTH, -1);
    // Date strDateTo = calendar.getTime();

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    Date strDateTo = calendar.getTime();
    String preMonthBegin = format.format(calendar.getTime());

    System.out.println("上月第一天：" + format.format(strDateTo));

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    strDateTo = calendar.getTime();
    String preMonthEnd = format.format(calendar.getTime());

    System.out.println("上月最后天：" + format.format(strDateTo));



    calendar.setTime(new Date());

    //calendar.add(Calendar.YEAR, -3);
    // Date strDateTo = calendar.getTime();

    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
    strDateTo = calendar.getTime();
    String curYearBegin = format.format(calendar.getTime());

    System.out.println("当年第一天：" + format.format(strDateTo));

    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    strDateTo = calendar.getTime();
    String curYearEnd = format.format(calendar.getTime());

    System.out.println("当年最后天：" + format.format(strDateTo));


    calendar.setTime(new Date());
    calendar.add(Calendar.YEAR, -1);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
    strDateTo = calendar.getTime();
    String preYearBegin = format.format(calendar.getTime());

    System.out.println("去年第一天：" + format.format(strDateTo));

    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    strDateTo = calendar.getTime();
    String preYearEnd = format.format(calendar.getTime());

    System.out.println("去年最后天：" + format.format(strDateTo));


    Map<String, String> data = new HashMap();
    data.put("name", "本月度");
    data.put("value", curMonthBegin + "_" + curMonthEnd);
    datas.add(data);
    data = new HashMap<String, String>();
    data.put("name", "上月度");
    data.put("value", preMonthBegin + "_" + preMonthEnd);
    datas.add(data);

    data = new HashMap<String, String>();
    data.put("name", "本年度");
    data.put("value", preYearBegin + "_" + preYearEnd);
    datas.add(data);

    data = new HashMap<String, String>();
    data.put("name", "上年度");
    data.put("value", preYearBegin + "_" + preYearEnd);
    datas.add(data);

    String dataJson = JSON.toJSONString(datas);
%>

<!DOCTYPE html>
<head>
    <link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>

    <title>订单列表</title>

    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link href="../css/jBox.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="../css/menu.css?1">



    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/LodopFuncs.js"></script>
    <script type="text/javascript" src="../js/accounting.min.js"></script>
    <script type="text/javascript" src="../js/jBox.js"></script>


    <style type="text/css">

        #statistics_result {
            clear:both;
            margin:16pt 0 0;
            text-align: center;;
            min-height: 250pt;
        }
        #statistics_tb {
            width:100%;
            margin:0 auto;
            border: 0px solid black;
            text-align:left;;

        }

        #statistics_tb td, #statistics_tb th {
            border: 1px solid #95bbe7;
            padding: 2pt 6pt;
            font-size:11pt;

        }

        #statistics_tb th {
            padding: 2pt 0;
            font-weight: bold;
            text-align: center;
            background-color: #e7f0ff;
        }
        #statistics_tb td.sum{
            font-weight: bold;
            text-align: left;
            padding-left:8pt;
        }

        #statistics_tb td.total_sum{
            font-weight: bold;
            text-align: left;
            padding-left:16pt;
        }

        #statistics_tb td.xiaoji {
           text-align: center;
        }

        #list_condition {
            font-size: 12pt;
        }

        #list_condition .textbox .textbox-text {
            font-size: 12pt;
        }

        #list_condition .textbox-text {
            font-size: 12pt;
        }

        /*重设按钮样式*/
        .l-btn-text {
            display: inline-block;
            vertical-align: top;
            width: auto;
            line-height: 24px;
            font-size: 16px;
            padding: 2px 10px;
            margin: 0 4px;
        }

        .datagrid-cell,
        .datagrid-cell-group,
        .datagrid-header-rownumber,
        .datagrid-cell-rownumber {
            margin: 0;
            padding: 6px 4px;
            white-space: nowrap;
            word-wrap: normal;
            overflow: hidden;
            height: 24px;
            line-height: 24px;
            font-size: 14px;
        }

        .datagrid-header .datagrid-cell {
            height: auto;
        }
        .datagrid-header .datagrid-cell span {
            font-size: 14px;
        }

        .panel-title {
            font-size: 14px;
            font-weight: bold;
            color: #0E2D5F;
            height: 18px;
            line-height: 18px;
        }

    </style>

    <script type="text/javascript">

        function gFormatItem(group) {
            return '<div style="font-weight:bold; height:12pt; line-height: 12pt; color:grey; font-size:10pt; padding:0px;">' + group + '</div>'
        }

        function formatCustomerItem(row) {
            return '<div style="height:12pt; line-height: 12pt; font-size:11pt; padding:1px;">' + row.name + '</div>';
        }

        function list_sale_orders() {
            var customer = $('#customer').combobox('getValue');
            var beginDate = $('#sale_date_begin').datebox('getValue');
            var endDate = $('#sale_date_end').datebox('getValue');

            $.post('order_service?method=statisticSaleOrders', {customerId:customer, beginDate:beginDate, endDate:endDate}, function (s) {
                $('#statistics_result').html(s);
            });
        }


        function export_sale_orders() {
            var customer = $('#customer').combobox('getValue');
            var flag = $('#flag').combo('getValue');
            var beginDate = $('#sale_date_begin').datebox('getValue');
            var endDate = $('#sale_date_end').datebox('getValue');

            window.open("order_service?method=getSaleOrders&customerId="+customer+"&flag="+flag+"&beginDate="+beginDate+"&endDate="+endDate,"下载","");
        }
    </script>

</head>
<body>
<div class="kis_wrapper">
    <jsp:include page="/WEB-INF/include/header_and_menu.jsp"/>

    <div class="kis_content" style=" text-align: center;">
        <div style=" margin:0 auto; text-align:left; width:100%;">

            <div id="list_condition" style=" padding:10pt 10pt;">
                <div>
                         <span style="float:left;">
                            <label style="float:left; height:30pt; line-height: 30pt; ">客户：</label>
                            <input id="customer" name="customer" class="easyui-combobox"
                                   required="true" valueField="id" textField="name" groupField="cityName"
                                   editable="false" data-options="panelHeight:280,url:'order_service?method=getCustomers&addall=1',value:'-1', groupFormatter:gFormatItem,formatter: formatCustomerItem "
                                   style="float:left; height:30pt; line-height:30pt;padding:0; width:300pt;"/>
                         </span>
                    <span style="float:left; margin-left:30pt;">
                    <label style="float:left;  height:30pt; line-height: 30pt; ">日期：</label>
                    <input
                            id="sale_date_begin" class="easyui-datebox" data-options="editable:false"
                            style="float:left; height:30pt; line-height:30pt;width:120pt;" value="<%=curMonthBegin%>"/>
                        </span>
                        <span style="float:left;">
                    <label style="float:left; height:30pt; line-height: 30pt; margin:0pt 10pt; ">到</label>
                    <input
                            id="sale_date_end" class="easyui-datebox" data-options="editable:false"
                            style="float:left; height:30pt; line-height:30pt;width:120pt;" value="<%=today%>"/>
                    </span>
                    <a href="#" class="easyui-linkbutton" onclick="list_sale_orders()"
                       style="float:left; margin:0pt 0pt 0pt 70pt;"><span>查询</span></a>
                    <a href="#" class="easyui-linkbutton" onclick="export_sale_orders()"
                       style="float:left; margin:0pt 0pt 0pt 30pt;"><span>导出</span></a>
                </div>

               <%-- <div style="clear:both;">
                    <span style="float:left;">
                        <input id="date_span" name="date_span" class="easyui-combobox" valueField="value" textField="name"
                               editable="false" data-options="panelHeight:280,data:dateData,formatter: formatCustomerItem,onChange: change_customer "
                               style="float:left; height:30pt; line-height:30pt;padding:0; width:300pt;"/>
                    </span>



                </div>--%>

            </div>

            <div id="statistics_result"></div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp"/>

</div>

</body>
</html>
