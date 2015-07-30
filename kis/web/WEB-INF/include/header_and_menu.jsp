<%@ page import="kis.entity.Company" %>
<%@ page import="kis.entity.KisUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int menu = 0;
    //String uri = request.getRequestURI();
    String uri = request.getServletPath();
    if (uri.endsWith("/order_new.jsp")) {
        menu = 1;
    } else if (uri.endsWith("/order_list.jsp")) {
        menu = 2;
    } else if (uri.endsWith("/order_statistics.jsp")) {
        menu = 3;
    } else if (uri.endsWith("/sys_manage.jsp")) {
        menu = 4;
    } /* else if (uri.equals("/index.jsp")) {
    		menu = 1;
    	}   */
    Company company = (Company) session.getAttribute("company");
    KisUser user = (KisUser) session.getAttribute("user");
%>
<div id="header" style="text-align:center;font-family: Arial, Helvetica, sans-serif; background-color: #36b0b6; height: 60pt;">
    <div style="font-size:28pt; color:#fff; font-weight: bold; padding:10pt 0pt 0pt;">
        <%=company.getName()%><label style="font-size:20pt;font-weight: bold; margin-left:12pt;">KIS系统</label>
<%--
        <label style="font-style:italic; font-size:12pt;margin-left:8pt; color:#fee; ">V1.0Beta</label>
--%>
    </div>
</div>
<div id="cssmenu">
      <ul>
          <li class="<%=(menu == 1) ?"active":""%>"><a href="order_new.jsp"><span>销售开单</span></a></li>
          <li class="<%=(menu == 2) ?"active":""%>"><a href="order_list.jsp"><span>销售清单</span></a></li>
          <li class="<%=(menu == 3) ?"active":""%>"><a href="order_statistics.jsp"><span>销售统计</span></a></li>
          <li class="<%=(menu == 4) ?"active":""%>"><a href="sys_manage.jsp"><span>系统管理</span></a></li>
          <li class="logout"  style="margin-left:300pt;"><a href="javascript:logout();"><span>注销账户：<%=user.getName()%></span></a></li>
      </ul>

</div>

<script type="text/javascript">
    function logout() {
        var r = confirm('确定要注销登录账户吗？');
        if (r) {
            $.post("../login", {logout: 1}, function (result) {
                if (result.success) {
                    window.location.href = '../login.jsp';
                } else {
                    $.messager.show({    // show error message
                        title: 'Error',
                        msg: result.errorMsg
                    });
                }
            }, 'json');
        }
    }
</script>
