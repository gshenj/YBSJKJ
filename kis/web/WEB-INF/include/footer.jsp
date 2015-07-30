<%@ page import="kis.entity.Company" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Company company = (Company) session.getAttribute("company");
%>
<div id="footer" class="kis_footer">Copyright ©2015 <%=company.getName()%> &nbsp;&nbsp;KIS系统&nbsp;&nbsp;V1.0Beta</div>