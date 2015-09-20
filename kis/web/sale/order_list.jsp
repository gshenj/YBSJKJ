<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            font-size: 16px;
            font-weight: bold;
            color: #0E2D5F;
            height: 18pt;
            line-height: 18pt;
        }

    </style>

    <script type="text/javascript">
        function gFormatItem(group) {
            return '<div style="font-weight:bold; height:12pt; line-height: 12pt; color:grey; font-size:10pt; padding:0px;">' + group + '</div>'
        }

        function formatCustomerItem(row) {
            return '<div style="height:12pt; line-height: 12pt; font-size:11pt; padding:1px;">' + row.name + '</div>';
        }


        var DO_PRINT_BUTTONS;
        var CURRENT_SALE_ORDER_ID;
        var myModal;

        function stylerTd(value, row, index) {
            return 'padding: 4pt 0pt; ';
        }


        function dblClickRow(idx,row) {
            do_preview(row)
        }


         function rowStylerFun(index,row){
            if (row.flag==1){
                return 'color:#DC143C';
            }
        }

        $(document).ready(function () {

            DO_PRINT_BUTTONS = $('#print_btn').html();
            $('#print_tbn').detach();
            myModal = new jBox('Modal')
            myModal.setWidth('600pt');
        });


        function change_customer(newId, oldId) {
        }

        var bPopup;
        function do_preview(row) {
            myModal.setTitle('').setContent('loading').open();
            CURRENT_SALE_ORDER_ID = row.id;
            //预览请求
            $.post('order_info.jsp', {
                "sale_order": JSON.stringify({
                    requestType: 3,
                    saleOrderId: CURRENT_SALE_ORDER_ID
                })
            }, function (s) {
                if (row.flag!=1)
                    myModal.setTitle(DO_PRINT_BUTTONS)
                myModal.setContent(s)
            })
        }


        function do_print() {
            $.post('order_info.jsp', {
                "sale_order": JSON.stringify({
                    requestType: 3,
                    saleOrderId: CURRENT_SALE_ORDER_ID
                })
            }, function (s) {
                previewOrPrint(s);
                // myModal.close();
            });
        }

        function do_invalid() {
            var r = confirm("确定要作废吗？");
                if (r) {
                    $.post('order_service?method=invalidSaleOrder', {saleOrderId: CURRENT_SALE_ORDER_ID}, function (s) {
                        myModal.close();
                        $('#dg').datagrid('reload');
                    });
                }

        }

        function list_sale_orders() {
            var customer = $('#customer').combobox('getValue');
            var flag = $('#flag').combo('getValue');
            var beginDate = $('#sale_date_begin').datebox('getValue');
            var endDate = $('#sale_date_end').datebox('getValue');

            $('#dg').datagrid('load', {customerId: customer, flag:flag, beginDate: beginDate, endDate: endDate});
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
                                   editable="false" data-options="panelHeight:280,url:'order_service?method=getCustomers&addall=1',value:'-1', groupFormatter:gFormatItem,formatter: formatCustomerItem,onChange: change_customer "
                                   style="float:left; height:30pt; line-height:30pt;padding:0; width:300pt;"/>
                         </span>
                         <span style="float:left; margin-left:40pt; ">
                            <label style="float:left; height:30pt; line-height: 30pt; margin:0pt 0 0 10pt; ">作废标志：</label>
                            <select id="flag" name="flag" class="easyui-combobox" data-options=""
                                style="float:left; height:30pt; line-height:30pt;width:120pt;">
                                <option value="-1" selected>全部</option>
                                <option value="0">正常</option>
                                <option value="1">作废</option>
                                </select>
                        </span>
                </div>

                <div style="clear:both;">
                        <span style="float:left;">
                    <label style="float:left;  height:30pt; line-height: 30pt; ">日期：</label>
                    <input
                            id="sale_date_begin" class="easyui-datebox" data-options="editable:true"
                            style="float:left; height:30pt; line-height:30pt;width:120pt;" value=""/>
                        </span>
                        <span style="float:left;">
                    <label style="float:left; height:30pt; line-height: 30pt; margin:0pt 10pt; ">到</label>
                    <input
                            id="sale_date_end" class="easyui-datebox" data-options="editable:true"
                            style="float:left; height:30pt; line-height:30pt;width:120pt;" value=""/>
                    </span>


                </div>
                <a href="#" class="easyui-linkbutton" onclick="list_sale_orders()"
                   style="float:left; margin:0pt 0pt 0pt 70pt;"><span>查询</span></a>
                <a href="#" class="easyui-linkbutton" onclick="export_sale_orders()"
                   style="float:left; margin:0pt 0pt 0pt 30pt;"><span>导出</span></a>
            </div>

            <table id="dg" style="width:100%;" title="出库单列表" class="easyui-datagrid"
                   rownumbers="true" fitColumns="true"
             url="order_service?method=getSaleOrders",
            pagination="true",
            pagePosition="bottom",
            pageNumber="1",
            pageSize="10",
            loadMsg="加载中...",
            singleSelect="true",
            striped="true",
            pageList="[10,20]",
            autoRowHeight="true"
            data-options="onDblClickRow:dblClickRow, rowStyler:rowStylerFun">

                <thead>
                <tr>
                 <%--   <th field="cityId" hidden="true"></th>--%>
                    <th field="order_number" width="6%">单号</th>
                    <th field="sale_date" width="9%">送货日期</th>
                    <th field="customer" width="20%">客户</th>
                    <th field="content_thumbnail" width="38%">内容</th>
                    <th field="create_user" width="6%">制单人</th>
                    <th field="create_date" width="13%">录入时间</th>
                    <th field="flag" width="5%" data-options="formatter:function(value,row,index){if (row.flag==1) return '作废'; else return '正常';}">标志</th>

                </tr>
            </table>

        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp"/>

</div>

<div id="print_btn" style="display:none;">
    <div style="text-align: right; margin-right:6pt;">
        <a class="easyui-linkbutton" id="btn_invalid" data-options="iconCls:'icon-cancel'"
           style="color:red; margin-right:30pt;"
           href="javascript:void(0)" onclick="do_invalid()">作废</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-print'" id="btn_print" href="javascript:void(0)"  onclick="do_print()">打印</a>
    </div>
</div>

<script type="text/javascript">

    var LODOP = getLodop();; //声明为全局变量
    function previewOrPrint(html_text) {
        //LODOP = getLodop();
        LODOP.SET_PRINT_PAGESIZE(0, '210mm', '140mm', '');
        LODOP.SET_SHOW_MODE ('HIDE_SBUTTIN_PREVIEW',true)
        LODOP.SET_SHOW_MODE ('HIDE_QBUTTIN_PREVIEW',true)
        LODOP.SET_SHOW_MODE ('HIDE_PAGE_PERCENT',true)
        LODOP.SET_SHOW_MODE('NP_NO_RESULT', true)
        LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,"打印预览.开始打印");
        LODOP.ADD_PRINT_HTM('5%', '6%', '93%', '93%', html_text)
        LODOP.PREVIEW();
    }

    function gFormatItem(group) {
        return '<div style="font-weight:bold; height:12pt; line-height: 12pt; color:grey; font-size:10pt; padding:0px;">' + group + '</div>'
    }

    function formatModalItem(row) {
        return '<div style="height:12pt; line-height: 12pt; font-size:11pt; padding:1px;">' + row.modal_name + '</div>';
    }

    function formatCustomerItem(row) {
        return '<div style="height:12pt; line-height: 12pt; font-size:11pt; padding:1px;">' + row.name + '</div>';
    }

    accounting.settings = {
        currency: {
            symbol: "¥",   // default currency symbol is '$'
            format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
            decimal: ".",  // decimal point separator
            thousand: ",",  // thousands separator
            precision: 2   // decimal places
        },
        number: {
            precision: 0,  // default precision on numbers is 0
            thousand: ",",
            decimal: "."
        }
    }

</script>
</body>
</html>
