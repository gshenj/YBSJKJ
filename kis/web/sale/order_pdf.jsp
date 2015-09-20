<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<script type="text/javascript">
    function doPdf() {
        var orderNumber = document.getElementById("orderNumber").value;
        window.location.href=orderNumber+".pdf";

    }
</script>
    <label>Order Number:</label> <input type="text" id="orderNumber" name="orderNumber" />
<button onclick="doPdf();">获取</button>
</body>
</html>
