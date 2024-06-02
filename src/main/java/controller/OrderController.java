package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO(connection);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "list":
                    listOrders(request, response);
                    break;
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "create":
                    createOrder(request, response);
                    break;
                case "update":
                    updateOrder(request, response);
                    break;
                default:
                    doGet(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("order-list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("order-form.jsp").forward(request, response);
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String pizzaId = request.getParameter("pizzaId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        String orderStatus = request.getParameter("orderStatus");

        Order newOrder = new Order(customerId, pizzaId, quantity,orderStatus, totalPrice, orderStatus);
        orderDAO.saveOrder(newOrder);
        response.sendRedirect("OrderController?action=list");
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order existingOrder = orderDAO.getOrderById(orderId);
        request.setAttribute("order", existingOrder);
        request.getRequestDispatcher("order-form.jsp").forward(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String pizzaId = request.getParameter("pizzaId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        String orderStatus = request.getParameter("orderStatus");
        String Size = "Medium"; 

        Order order = new Order(customerId, pizzaId, quantity,Size, totalPrice, orderStatus);
        orderDAO.updateOrder(order);
        response.sendRedirect("OrderController?action=list");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        orderDAO.deleteOrder(orderId);
        response.sendRedirect("OrderController?action=list");
    }
}
