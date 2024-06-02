<%@ page import="DAO.OrderDAO" %> <!-- Import the OrderDAO class -->
<%@ page import="model.Pizza" %>
<%@ page import="model.Order" %>
<%@ page import="model.Cart" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="test.DBUtil" %>
<%@ page import="java.sql.Connection" %>

<%
    Connection connection = null;

    Cart cart = (Cart) session.getAttribute("cart");
    if (cart != null) {
        List<Order> items = cart.getItems();
        connection = DBUtil.getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        for (Order item : items) {
            orderDAO.saveOrder(item);
        }
        session.removeAttribute("cart");
        System.out.println("Cart cleared successfully.");
        response.sendRedirect("checkout-success.jsp");
    } else {
        // Handle empty cart
        response.sendRedirect("EMPTY .jsp");
    }
%>
