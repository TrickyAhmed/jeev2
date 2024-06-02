<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Livreur Orders</title>
<%@ include file="/include/css.jsp"%>
</head>
<body>
    <%@ include file="/include/header.jsp"%>

    <div class="container">
        <h2>Orders Pret</h2>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Customer ID</th>
                    <th>Pizza ID</th>
                    <th>Quantity</th>
                    <th>Size</th>
                    <th>Total Price</th>
                    <th>Order Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Order> orders = (List<Order>) request.getAttribute("orders");
                    if (orders != null && !orders.isEmpty()) {
                        for (Order order : orders) {
                %>
                <tr>
                    <td><%= order.getCustomerId() %></td>
                    <td><%= order.getPizzaId() %></td>
                    <td><%= order.getQuantity() %></td>
                    <td><%= order.getSize() %></td>
                    <td>$<%= order.getTotalPrice() %></td>
                    <td><%= order.getOrderStatus() %></td>
                    <td>
                        <form action="OrderListController" method="post">
                            <input type="hidden" name="customerId" value="<%= order.getCustomerId() %>">
                            <input type="hidden" name="pizzaId" value="<%= order.getPizzaId() %>">
                            <button type="submit" class="btn btn-primary">Mark as pret</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No orders found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <%@ include file="/include/footer.jsp"%>
    <%@ include file="/include/js.jsp"%>
</body>
</html>
