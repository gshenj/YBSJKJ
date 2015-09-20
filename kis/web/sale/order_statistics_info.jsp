<%@ page import="kis.service.StatisticsOrderResult" %>
<%@ page import="java.util.List" %>
<%@ page import="kis.service.StatisticsItemResult" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  User: jim
  Date: 2015/3/5
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String beginDate = (String) request.getAttribute("beginDate");
    String endDate = (String)request.getAttribute("endDate");
    List<StatisticsOrderResult> results = (List) request.getAttribute("statistics");
    request.removeAttribute("statistics");
%>
    <div style="text-align: left; font-weight: bold; ">统计时间为：<%=beginDate%> 至 <%=endDate%></div>
    <table id="statistics_tb">
        <thead>
        <tr>
            <th width="40%">客户</th>
            <th width="15%">产品类型</th>
            <th width="15%">产品型号</th>
            <th width="15%">数量</th>
            <th width="15%">金额</th>
        </tr>
        </thead>
        <tbody>
        <%
            boolean first = true;

            BigDecimal quantityTotalSum = new BigDecimal(0);
            BigDecimal moneyTotalSum = new BigDecimal(0);
            for (StatisticsOrderResult r : results) {
                BigDecimal quantitySum = new BigDecimal(0);
                BigDecimal moneySum = new BigDecimal(0);
                first = true;

                List<StatisticsItemResult> items = r.getItemResults();
                int size = items.size();
                for (StatisticsItemResult item : items) {

        %>
                <tr>
                    <%
                        if (first) {
                    %>
                    <td rowspan="<%=size+1%>"><%=r.getCustomerName()%></td>
                    <%
                            first = false;
                        }
                    %>
                    <td><%=item.getCategoryName()%>
                    </td>
                    <td><%=item.getModalName()%>
                    </td>
                    <td><%=item.getQuantity()%>
                    </td>
                    <td><%=(item.getSum()==null)?"":item.getSum()%>
                    </td>
                </tr>
            <%
                        if (item.getQuantity()!=null)
                    quantitySum = quantitySum.add(item.getQuantity());
                    if (item.getSum()!=null)
                    moneySum = moneySum.add(item.getSum());
                }
            %>
        <tr style="background-color:#eeeeff;">
            <td class="sum xiaoji" colspan="2">小计</td>
            <td class="sum"><%=quantitySum%></td>
            <td class="sum"><%=moneySum%></td>
        </tr>

        <%
                quantityTotalSum = quantityTotalSum.add(quantitySum);
                 moneyTotalSum = moneyTotalSum.add(moneySum);
            }
        %>
        <tr style="background-color:#ddddff;">
            <td class="total_sum" colspan="3" style="text-align: center; ">总计</td>
            <td class="total_sum"><%=quantityTotalSum%></td>
            <td class="total_sum"><%=moneyTotalSum%></td>
        </tr>
        </tbody>
    </table>
