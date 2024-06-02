<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Success</title>
    <%@ include file="/include/css.jsp" %>
</head>
<body>
    <%@ include file="/include/header.jsp" %>

    <div class="container">
        <h2>Thank you for your order!</h2>
        <p>Your order has been successfully placed.</p>
        
        
        <a href="PizzaController" class="btn btn-primary">Order More</a>
    </div>

    <%@ include file="/include/footer.jsp" %>
    <%@ include file="/include/js.jsp" %>
</body>
</html>
