<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.Cart" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Your Cart</title>
<%@ include file="/include/css.jsp"%>
<style>
    /* Add your custom CSS styles here */
    table {
        width: 100%;
        border-collapse: collapse;
        border: 1px solid #ddd;
        margin-bottom: 20px;
    }
    th, td {
        padding: 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
    tr:hover {
        background-color: #f5f5f5;
    }
    input[type="number"], input[type="submit"] {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f9f9f9;
        transition: all 0.3s ease;
    }
    input[type="number"]:focus, input[type="submit"]:hover {
        border-color: #4CAF50;
        background-color: #4CAF50;
        color: white;
    }
    form {
        display: inline;
    }
</style>
</head>
<body>
<%@ include file="/include/header.jsp"%>
<h2>Your Cart</h2>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart != null) {
        List<Order> items = cart.getItems();
        if (items.size() > 0) {
%>
            <table>
                <tr>
                    <th>Pizza</th>
                    <th>Size</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
                <%
                    for (int i = 0; i < items.size(); i++) {
                        Order item = items.get(i);
                %>
                    <tr>
                        <td><%= item.getPizzaId() %></td>
                        <td><%= item.getSize() %></td>
                     
                       <td><%= item.getQuantity() %></td>

                      
                        <td><%= item.getTotalPrice() %></td>
                        <td>
                         <form action="remove-from-cart.jsp" method="post">
                                <input type="hidden" name="index" value="<%= i %>">
                                <input type="submit" value="Remove" style="background-color: #f44336; color: white;">
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </table>
            <h3>Total: <%= cart.getTotalPrice() %> DT </h3>
            <form action="CheckoutServlet.jsp" method="post">
    			<input type="submit" value="Checkout" style="background-color: #4CAF50; color: white; padding: 10px 20px; border-radius: 5px;">
			</form>
<%
        } else {
            out.println("<p>Your cart is empty.</p>");
        }
    } else {
        out.println("<p>Your cart is empty.</p>");
    }
%>
<%@ include file="/include/footer.jsp"%>
<%@ include file="/include/js.jsp"%>
</body>
</html>
