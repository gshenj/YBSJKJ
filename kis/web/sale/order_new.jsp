<%@ page import="org.apache.commons.lang.time.DateFormatUtils" %>
<%@ page import="kis.entity.Company" %>
<%@ page import="kis.util.Settings" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>

    <title>填写送货单</title>

    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/jBox.css">
    <link rel="stylesheet" type="text/css" href="../css/menu.css?1" >


    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/LodopFuncs.js"></script>
    <script type="text/javascript" src="../js/accounting.min.js"></script>
    <script type="text/javascript" src="../js/jBox.js"></script>

    <style type="text/css">
        table.bill_table {
            border-collapse: collapse;
            border-spacing: 0px;
            width: 100%;
            border: none;
            table-layout: fixed;
            margin: 1pt auto;
        }

        table.bill_table td {
            height: 30pt;
            line-height: 30pt;;
            padding: 0pt;
            font-size: 12pt;
            text-align: left;;
        }

        table.bill_table td label {
            font-size: 12pt;
        }

        table.bill_table .tbh td {
            text-align: center;
        }

        table.bill_table td span {
            font-size: 12pt;
        }

        #customer_name_div {
            float: left;
            height: 100%;
        }

        #sale_date_div {
            text-align: left;
        }

        #customer_name_div .textbox .textbox-text {
            font-size: 12pt;
        }

        #sale_date_div .textbox-text {
            font-size: 12pt;
        }

        table.bill_product_table .textbox .textbox-text {
            font-size: 12pt;
        }

        table.bill_product_table {
            width: 100%;
            border:none;
            border-collapse: collapse;
            table-layout: fixed;
        }

        table.bill_product_table td {
            border: 1px solid lightgray;
            text-align: center;
            padding: 0pt 0pt;
            height:32pt;
            line-height: 32pt;;
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

    </style>

    <script type="text/javascript">

        var DO_PRINT_BUTTONS;
        var productData = null;
        var EMP_PRODUCT_ID = -1;
        var EMP_PRODUCT_DATA = {
            selected:true,
            modal_id: EMP_PRODUCT_ID,
            modal_name: '',
            category_name: '',
            quantity: '',
            sum: '',
            memo: '',
            modal_units: '',
            suggest_unit_price: ''
        }
        var ITEM_NUM = <%=Settings.SALE_ORDER_ITEM_NUM%>;

        var myModal;

        $(document).ready(function () {
            DO_PRINT_BUTTONS = $('#print_btn').html();
            $('#print_btn').detach();
            myModal = new jBox('Modal')
            myModal.setWidth('600pt');
        });

        function preparePostData(request_type) {
            var data = {};
            data.requestType = request_type;
            data.customer = {id: $('#customer').combobox('getValue'), name:$('#customer').combobox('getText')};
            //data.createUser = {userId:1};  //应该在session中取
            //data.company = {id:1};         //应该在session中取
            data.saleDate = $('#sale_date').datebox('getValue');
            data.totalSum = THE_TOTAL_SUM;

            var modals = new Array();
            var size = $('.product_id').size();
            for (var i = 0; i < size; i++) {
                var modal_id = $('.product_id').eq(i).combobox('getValue')
                var quantity = $('.product_quantity').eq(i).numberbox('getValue');
                var modal_name = $('.product_id').eq(i).combobox('getText');
                var units = $('.product_unit').eq(i).html();
                var category_name = $('.product_type').eq(i).html();
                if (modal_id > 0 && quantity > 0) {
                    var obj = {};
                    obj.id = modal_id;
                    obj.quantity = quantity;
                    var _unitPrice = $('.product_price').eq(i).numberbox('getValue');
                    obj.unitPrice = _unitPrice//==''?null:parseFloat(_unitPrice);
                    var _sum = $('.product_sum').eq(i).numberbox('getValue');
                    obj.sum = _sum//==''?null:parseFloat(_sum);
                    obj.memo = $('.product_memo').eq(i).textbox('getValue');
                    obj.modalName=modal_name;
                    obj.categoryName = category_name;
                    obj.unitsName = units;
                    //arr.push(obj);
                    modals.push(obj);
                }

            }
            data.modals = modals;
            //var jsonStr = JSON.stringify(data);
            return data;
        }

        function do_preview() {
            // check needed
           if ($('#customer').combobox('getValue')=='') {
               alert('请选择客户名称！');
               return;
           }
            if ($('#sale_date').datebox('getValue') == '') {
                alert('请选择送货日期！');
                return;
            }

            var hasItems = false;
            for (var i=0; i<ITEM_NUM; i++) {
                var modalId = $('.product_id').eq(i).combobox('getValue');
                if (modalId != '' && modalId!='-1' && $('.product_quantity').eq(i).numberbox('getValue') != '') {
                    hasItems = true;
                }
            }
            if (!hasItems) {
                alert('请至少输入一个送货单项目！')
                return;
            }

            myModal.setTitle('').setContent('loading').open();

            //预览请求
            $.post('order_info.jsp', {"sale_order": JSON.stringify(preparePostData(1))}, function (s) {
                myModal.setTitle(DO_PRINT_BUTTONS)
                myModal.setContent(s)
            })
        }

        function do_print() {
            $.post('order_info.jsp', {"sale_order": JSON.stringify(preparePostData(2))}, function (s) {
                previewOrPrint(s);
                myModal.close();
                location.href='order_list.jsp'
                // jump to list view
            });
        }

        function change_customer(newId, oldId) {
            $.getJSON("order_service?method=getCustomerModals", {"customerId": newId}, function (json) {
            //$.getJSON("json_req/customer_modals.jsp?", {"customer_id": newId}, function (json) {
                // alert(JSON.stringify(json));
                var arr = new Array();
                for (var d in json) {
                    var obj = new Object();
                    obj.modal_id = json[d].id;
                    obj.modal_name = json[d].modalName;   // show it
                    obj.category_name = json[d].categoryName;
                    obj.modal_units = json[d].unitsName;
                    obj.suggest_unit_price = json[d].unitPrice;
                    obj.quantity = '';
                    obj.memo = '';
                    obj.sum = '';
                    arr.push(obj);
                }
                arr.push(EMP_PRODUCT_DATA);
                productData = arr;
                after_change_customer(arr);
            });


            var data = $('#customer').combobox('getData');
            for (var d in data) {
                if (data[d].id == newId) {
                    //alert(data[d].address)
                    $('#customer_address').html(data[d].address);
                    $('#customer_principal').html(data[d].principal);
                    $('#customer_phone').html(data[d].mobileNumber);

                    return;
                }
            }
        }

        function after_change_customer(arr) {
            $('.product_id').combobox('clear')
            $('.product_id').combobox('loadData', arr)
            $('.product_id').combobox('setValue', EMP_PRODUCT_ID);
            $('.product_type').empty();    //.html()
            $('.product_unit').empty();
            $('.product_quantity').numberbox('clear');
            $('.product_price').numberbox('clear');
            $('.product_sum').numberbox('clear');
            $('.product_memo').textbox('clear');
        }

        function clear_product_item(idx) {
            $('.product_type').eq(idx).empty();
            $('.product_unit').eq(idx).empty();
            $('.product_quantity').eq(idx).numberbox('clear');
            $('.product_price').eq(idx).numberbox('clear');
            $('.product_sum').eq(idx).numberbox('clear');
            $('.product_memo').eq(idx).textbox('clear');
        }

        function change_product(newId, oldId, idx) {
            if (newId == EMP_PRODUCT_ID) {
                clear_product_item(idx);
                return;
            }

            for (var d in productData) {
                if (productData[d].modal_id == newId) {
                    $('.product_type').eq(idx).html(productData[d].category_name);
                    $('.product_unit').eq(idx).html(productData[d].modal_units);
                    $('.product_price').eq(idx).numberbox('setValue', productData[d].suggest_unit_price);
                    $('.product_quantity').eq(idx).numberbox('clear');
                    $('.product_sum').eq(idx).numberbox('clear');
                    $('.product_memo').eq(idx).textbox('clear');
                    return;
                }
            }
        }

        var THE_TOTAL_SUM = null;

        function fillSumAndTotalSum(quantity, unit_price, idx) {
            var sum, total_sum = 0.00;
            if (quantity==='' || unit_price==='') sum = "";
            else if (quantity == 0 || unit_price==0) sum =0;
            else sum = quantity * unit_price;
            $('.product_sum').eq(idx).numberbox('setValue', sum);

            var size =  $('.product_sum').size();
            for (var i=0; i<size; i++) {
                var item_sum = $('.product_sum').eq(i).numberbox('getValue');
                //alert(item_sum);
                if (item_sum == '') { //选择了产品，但是没有金额的情况
                    if (parseInt($('.product_id').eq(i).combobox('getValue'))!=EMP_PRODUCT_ID) {
                        total_sum = '';
                       // alert('set total_sum to ""')
                        break;
                    }
                } else {
                    total_sum = total_sum + parseFloat(item_sum);
                }
            }

            if (total_sum == '') {
                $('#product_total_sum').html('')
                THE_TOTAL_SUM = total_sum;
            } else {
                //alert(total_sum.toFixed(2))
                THE_TOTAL_SUM = total_sum.toFixed(2) + "";
                $('#product_total_sum').html(accounting.formatMoney(total_sum))
            }

        }

        function change_product_quantity(new_quantity, o, idx) {
            var unit_price = $('.product_price').eq(idx).numberbox('getValue');
            fillSumAndTotalSum(new_quantity, unit_price, idx);
        }

        function change_product_price(new_unit_price, o, idx) {
            var quantity = $('.product_quantity').eq(idx).numberbox('getValue');
            fillSumAndTotalSum(quantity, new_unit_price, idx);
        }

    </script>

</head>
<body>
<div class="kis_wrapper">
    <jsp:include page="/WEB-INF/include/header_and_menu.jsp"    />

    <div id="content" class="kis_content" style=" text-align: center;">
        <div style="margin:0px auto; width:100%;text-align:center;">
            <div style="width:100%; margin:0 auto; text-align: left;  border-left:0px dashed black;">
                <div style="text-align: right; width:100%; padding-right:10px;">
                    <a href="javascript:void(0);" onclick="do_preview()" class="easyui-linkbutton" data-options="iconCls:'icon-print', height:40" style="margin-left: 40px;">打印预览</a>
                </div>
                <table class="bill_table">
                    <tr>
                        <td style="padding-left:2pt" colspan="7">
                            <div id="customer_name_div">
                                <label style="float:left; height:30pt; line-height: 30pt; ">客户名称：</label>
                                <input id="customer" name="customer" class="easyui-combobox"
                                       data-options="required: false,valueField: 'id',textField: 'name', groupField: 'cityName',editable:true, panelHeight:280,url:'order_service?method=getCustomers',groupFormatter: gFormatItem,formatter: formatCustomerItem,onChange: change_customer"
                                       style="float:left; height:30pt; line-height:30pt;padding:0; width:300pt;"/>

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-left:2pt; overflow: visible;" colspan="2">
                            客户电话：<span id="customer_phone"></span>
                        </td>
                        <td colspan="3" style="">联系人：<span id="customer_principal"></span></td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                        <td style="padding-left:2pt;" colspan="4"
                                >送货地址：<span id="customer_address"></span></td>
                        <td id="sale_date_div" colspan="3" style="">
                            <div style="float:right;display: inline-block;"><label
                                    style="float:left; height:30pt; line-height: 30pt; ">送货日期：</label><input
                                    id="sale_date" class="easyui-datebox" data-options="editable:false"
                                    style="float:left; height:30pt; line-height:30pt;width:120pt;" value="<%=DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd")%>"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="7">
                            <table class="bill_product_table">
                                <tr class="tbh">
                                    <td width="20%">规格型号</td>
                                    <td width="18%">货物名称</td>
                                    <td width="10%">单位</td>
                                    <td width="10%">数量</td>
                                    <td width="9%">价格</td>
                                    <td width="13%">金额</td>
                                    <td width="20%">备注</td>
                                </tr>

                                <%
                                    for (int i = 0; i < kis.util.Settings.SALE_ORDER_ITEM_NUM; i++) {
                                %>
                                <tr class="row">
                                    <td>
                                        <input idx="<%=i%>" class="easyui-combobox product_id"
                                               data-options="valueField: 'modal_id', textField: 'modal_name',editable: false,formatter: formatModalItem,onChange:function(n,o) {change_product(n, o, $(this).attr('idx'));}"
                                               style="width:98%; height:30pt">
                                    </td>
                                    <td><span class="product_type"></span></td>
                                    <td><span class="product_unit"></span></td>
                                    <td>
                                        <input idx="<%=i%>" class="easyui-numberbox product_quantity" data-options="min:0,precision:0, onChange:function (n,o) {change_product_quantity(n, o, $(this).attr('idx'));}" value=""
                                               style="width:98%;height:30pt"/>
                                    </td>
                                    <td>
                                        <input idx="<%=i%>" class="easyui-numberbox product_price" data-options="min:0,precision:2, onChange:function(n,o) {change_product_price(n, o, $(this).attr('idx'));}" value=""
                                               style="width:98%;height:30pt"/>
                                    </td>
                                    <td>
                                        <input idx="<%=i%>" class="easyui-numberbox product_sum" data-options="editable:false,readonly:true,min:0,precision:2" value=""
                                               style="width:98%;height:30pt"/>
                                    </td>
                                    <td><input idx="<%=i%>" class="easyui-textbox product_memo" value=""
                                               style="width:98%;height:30pt"/>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                                <tr class="row">
                                    <td colspan="7" style="text-align: right;font-weight: bold;" >
                                        合计金额：<span id="product_total_sum" style="margin-right: 20%;"></span>
                                    </td>

                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp" />

</div>

<div id="print_btn" style="display: none;">
    <div style="text-align: right; margin-right:6pt;"><a class="easyui-linkbutton" data-options="iconCls:'icon-print'" href="javascript:void(0)" onclick="do_print()" >保存&打印</a> </div>
</div>

<script type="text/javascript" >

    var LODOP  = getLodop(); //声明为全局变量

    function previewOrPrint(html_text) {
        //LODOP = getLodop();
        //LODOP.PRINT_INITA(0, 0, 522, 333, "打印控件功能演示_Lodop功能_自定义纸张2");
        //LODOP.PRINT_INIT("打印任务1");
       // LODOP.SET_PRINTER_INDEX(-1);   //默认打印机
        LODOP.SET_PRINT_PAGESIZE(0, '210mm', '140mm', '');
        //LODOP.SET_PRINT_PAGESIZE(1, 0, 0,"A5");
        //LODOP.SET_PRINT_PAGESIZE(1, 1397, 2410,"A4");
       // LODOP.ADD_PRINT_HTM('5mm', '10mm', '550pt', '420pt', html_text)
        LODOP.SET_SHOW_MODE ('HIDE_SBUTTIN_PREVIEW',true)
        LODOP.SET_SHOW_MODE ('HIDE_QBUTTIN_PREVIEW',true)
        LODOP.SET_SHOW_MODE ('HIDE_PAGE_PERCENT',true)
        LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,"打印预览.开始打印");
        LODOP.ADD_PRINT_HTM('5%', '6%', '93%', '93%', html_text)
        LODOP.PREVIEW();
        //LODOP.PRINT();
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
            symbol : "¥",   // default currency symbol is '$'
            format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
            decimal : ".",  // decimal point separator
            thousand: ",",  // thousands separator
            precision : 2   // decimal places
        },
        number: {
            precision : 0,  // default precision on numbers is 0
            thousand: ",",
            decimal : "."
        }
    }

</script>
</body>
</html>
