<%@ page import="kis.entity.Company" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Company company = (Company)session.getAttribute("company");
%>
<!DOCTYPE html>
<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>

    <title>订单列表</title>

    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/menu.css?1">


    <style type="text/css">
        .left_td {width:25%; text-align: right;  padding:4pt 4pt 4pt 0pt;}
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

    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/LodopFuncs.js"></script>
    <script type="text/javascript" src="../js/accounting.min.js"></script>

    <script type="text/javascript">

        var SEL_CUSTOMER;
        var SEL_CATEGORY;

        $(document).ready(function () {
        });

        function clickCategoryRow(idx, row) {
            SEL_CATEGORY = row;// $('#dg_category').datagrid('getSelected');
            loadModals();
        }

        function clickCustomerRow(idx, row) {
            SEL_CUSTOMER = row;// $('#dg_customer').datagrid('getSelected');
            loadModals();
        }

        function loadModals() {
            if (SEL_CUSTOMER == null || SEL_CATEGORY == null) {
                return false;
            }
            $('#dg_modal').datagrid('load', {customerId:SEL_CUSTOMER.id,categoryId:SEL_CATEGORY.id})
        }

    </script>

</head>
<body>
<div class="kis_wrapper">
    <jsp:include page="/WEB-INF/include/header_and_menu.jsp"/>

    <div class="kis_content" style=" text-align: center;">
        <div style=" margin:0 auto; text-align:left; width:100%;">

            <div style="width:100%;">
                <table id="dg_customer" title="客户信息" class="easyui-datagrid" style="width:100%; height:400px;"
                       url="order_service?method=getCustomers"
                       toolbar="#toolbar_customer"
                       rownumbers="true" fitColumns="true" singleSelect="true" data-options="onClickRow:clickCustomerRow">
                    <thead>
                    <tr>
                        <th field="cityId" hidden="true"></th>
                        <th field="name" width="22%">名称</th>
                        <th field="cityName" width="6%">城市</th>
                        <th field="principal" width="6%">联系人</th>
                        <th field="telNumber" width="9%">电话</th>
                        <th field="mobileNumber" width="9%">手机</th>
                        <th field="address" width="26%">地址</th>
                        <th field="saleBillInfo" width="20%">开票资料</th>
                    </tr>
                    </thead>
                </table>
                <div id="toolbar_customer">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCustomer()">
                        新增</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCustomer()">
                        编辑</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCustomer()">
                        删除</a>
                </div>
            </div>

            <div style="margin:8pt; "></div>

            <div style="width:30%; float:left; ">
                <table id="dg_category" title="产品类目" class="easyui-datagrid" style="width:100%;height:250px"
                       url="order_service?method=getCategories"
                       toolbar="#toolbar"
                       rownumbers="true" fitColumns="true" singleSelect="true" data-options="onClickRow:clickCategoryRow">
                    <thead>
                    <tr>
                        <th field="name" width="50">类型名称</th>
                        <th field="description" width="50">类型描述</th>
                    </tr>
                    </thead>
                </table>
                <div id="toolbar">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
                </div>
            </div>

            <div style="width:68%; float:right; ">
                <table id="dg_modal" title="产品型号" class="easyui-datagrid" style="width:100%;height:250px"
                       url="order_service?method=getModals"
                       toolbar="#toolbar_modal"
                       rownumbers="true" fitColumns="true" singleSelect="true">
                    <thead>
                    <tr>
                        <th field="name" width="20%">型号名称</th>
                        <th field="description" width="20%">型号描述</th>
                        <th field="productUnitsName" width="8%">单位</th>
                        <th field="suggestUnitPrice" width="8%">单价</th>
                        <%--<th field="productCategory" width="8%">类别</th>
                        <th field="customerName" width="40%">客户</th>--%>
                    </tr>
                    </thead>
                </table>
                <div id="toolbar_modal">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newModal()">
                        新增</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editModal()">
                        编辑</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteModal()">
                        删除</a>
                </div>
            </div>

        </div>
    </div>

    <jsp:include page="/WEB-INF/include/footer.jsp" />

</div>
</div>

<script type="text/javascript">

    function check0() {
        SEL_CUSTOMER = $('#dg_customer').datagrid('getSelected');
        SEL_CATEGORY = $('#dg_category').datagrid('getSelected');
    }
    function check1() {
       // check0();
        if (SEL_CATEGORY == null || SEL_CATEGORY == null) {
            return false;
        }
        return true;
    }

    /*产品类型curd操作*/
    function newModal() {

        if (!check1()) {
            alert('请先选择客户和产品类型！')
            return;
        }


        $('#dlg_modal').dialog('open').dialog('setTitle', '新增产品型号');
        $('#fm_modal').form('clear');
        url_modal = 'order_service?method=saveOrUpdateModal&customerId='+SEL_CUSTOMER.id+"&categoryId="+SEL_CATEGORY.id;
    }

    function editModal() {
        var row = $('#dg_modal').datagrid('getSelected');
        if (row) {
            $('#dlg_modal').dialog('open').dialog('setTitle', '编辑产品型号');
            $('#fm_modal').form('load', row);
            url_modal = 'order_service?method=saveOrUpdateModal&id=' + row.id;
        }
    }


    function saveModal() {
        //alert(url_customer)
        $('#fm_modal').form('submit', {
            url: url_modal,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.errorMsg) {
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#dlg_modal').dialog('close');        // close the dialog
                    $('#dg_modal').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function deleteModal() {
        var row = $('#dg_modal').datagrid('getSelected');
        if (row) {
            $.messager.confirm('删除确认', '确定要删除选择的项吗？', function (r) {
                if (r) {
                    $.post('order_service?method=deleteModal', {id: row.id}, function (result) {
                        if (result.success) {
                            $('#dg_modal').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    }, 'json');
                }
            });
        }
    }
</script>
<div id="dlg_modal" class="easyui-dialog" modal="true" shadow="true" closed="true" buttons="#dlg_modal_buttons" style="width:400px;padding:10px 20px">
    <form id="fm_modal" method="post" novalidate>
        <table>
            <tr><td class="left_td">名称:</td><td> <input name="name" class="easyui-textbox"  style="width:250px;"  required="true"></td></tr>
            <tr><td class="left_td">描述:</td><td>
                <input name="description" class="easyui-textbox"  style="width:250px;" >
            </td></tr>
            <tr><td class="left_td">单位:</td><td>
                <input name="productUnitsId"  class="easyui-combobox"
                       style="width:250px;" editable="false" required="true" textField="cnName" valueField="id"  url="order_service?method=getProductUnits" />
            </td></tr>
            <tr><td class="left_td">单价:</td><td> <input name="suggestUnitPrice"  style="width:250px;"  class="easyui-numberbox" data-options="min:0,precision:2"></td></tr>
        </table>
    </form>
</div>
<div id="dlg_modal_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveModal()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg_modal').dialog('close')" style="width:90px">取消</a>
</div>



<script type="text/javascript">
    /*产品类型curd操作*/
    function newCustomer() {
        $('#dlg_customer').dialog('open').dialog('setTitle', '新增客户');
        $('#fm_customer').form('clear');
        url_customer = 'order_service?method=saveOrUpdateCustomer';
    }

    function editCustomer() {
        var row = $('#dg_customer').datagrid('getSelected');
        if (row) {
            $('#dlg_customer').dialog('open').dialog('setTitle', '编辑客户');
            $('#fm_customer').form('load', row);
            url_customer = 'order_service?method=saveOrUpdateCustomer&id=' + row.id;
        }
    }


    function saveCustomer() {
        // alert(url_customer)
        $('#fm_customer').form('submit', {
            url: url_customer,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.errorMsg) {
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#dlg_customer').dialog('close');        // close the dialog
                    $('#dg_customer').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function deleteCustomer() {
        var row = $('#dg_customer').datagrid('getSelected');
        if (row) {
            $.messager.confirm('删除确认', '确定要删除选择的项吗？', function (r) {
                if (r) {
                    $.post('order_service?method=deleteCustomer', {id: row.id}, function (result) {
                        if (result.success) {
                            $('#dg_customer').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    }, 'json');
                }
            });
        }
    }
</script>
<div id="dlg_customer" class="easyui-dialog" modal="true" shadow="true" closed="true" buttons="#dlg_customer_buttons" style="width:400px;padding:10px 20px">
    <form id="fm_customer" method="post" novalidate>
        <table>
            <tr><td class="left_td">名称:</td><td> <input name="name" style="width:250px;"  class="easyui-textbox" required="true"></td></tr>
            <tr><td class="left_td">城市:</td><td>
                <input name="cityId"  style="width:250px;"  class="easyui-combobox"
                       editable="false" required="true" textField="name" valueField="id" url="order_service?method=getCities"
                        />
            </td></tr>
            <tr><td class="left_td">联系人:</td><td>
                <input name="principal"  style="width:250px;"  class="easyui-textbox" required="true" />

            </td></tr>
            <tr><td class="left_td">电话:</td><td> <input name="telNumber"  style="width:250px;"  class="easyui-textbox"/></td></tr>
            <tr><td class="left_td">手机:</td><td> <input name="mobileNumber"  style="width:250px;"  class="easyui-textbox"/></td></tr>
            <tr><td class="left_td">地址:</td><td>  <input name="address" style="width:250px;" class="easyui-textbox" required="true"/></td></tr>
            <tr><td class="left_td">开票资料:</td><td><input name="saleBillInfo" class="easyui-textbox" data-options="multiline:true" style="width:250px;height: 150px;" /></td></tr>
        </table>
    </form>
</div>
<div id="dlg_customer_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveCustomer()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_customer').dialog('close')" style="width:90px">取消</a>
</div>






<script type="text/javascript">
    /*产品类型curd操作*/
    function newUser() {
        $('#dlg').dialog('open').dialog('setTitle', '新增产品类目');
        $('#fm').form('clear');
        url = 'order_service?method=saveOrUpdateCategory';
    }

    function editUser() {
        var row = $('#dg_category').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', '编辑产品类目');
            $('#fm').form('load', row);
            url = 'order_service?method=saveOrUpdateCategory&id=' + row.id;
        }
    }


    function saveUser() {
        $('#fm').form('submit', {
            url: url,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.errorMsg) {
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg_category').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function destroyUser() {
        var row = $('#dg_category').datagrid('getSelected');
        if (row) {
            $.messager.confirm('删除确认', '确定要删除选择的项吗？', function (r) {
                if (r) {
                    $.post('order_service?method=deleteCategory', {id: row.id}, function (result) {
                        if (result.success) {
                            $('#dg_category').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    }, 'json');
                }
            });
        }
    }
</script>
<div id="dlg" class="easyui-dialog" modal="true" shadow="true" closed="true" buttons="#dlg-buttons"
     style="width:400px;padding:10px 20px">
    <form id="fm" method="post" novalidate>
        <table>
            <tr><td class="left_td">类型名称:</td><td><input name="name" class="easyui-textbox"  style="width:250px;"  required="true" /></td></tr>
            <tr><td class="left_td">备注描述:</td><td><input name="description" class="easyui-textbox"  style="width:250px;"  /></td></tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()"  style="width:90px;" >Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')"   style="width:90px;" >Cancel</a>
</div>


<script type="text/javascript">

    var LODOP; //声明为全局变量
    function previewOrPrint(html_text) {
        LODOP = getLodop();
        LODOP.PRINT_INITA(0, 0, 522, 333, "打印控件功能演示_Lodop功能_自定义纸张2");
        LODOP.SET_PRINTER_INDEX(-1);
        LODOP.SET_PRINT_PAGESIZE(1, 2410, 1397, "");
        LODOP.ADD_PRINT_HTM('5mm', '10mm', '550pt', '420pt', html_text)
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
