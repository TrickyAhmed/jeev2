package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.OrderDAO;
import DAO.PizzaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;
import test.DBUtil;

public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderDAO orderDAO;
	private Connection connection;

	public void init() throws ServletException {
		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Initialize PizzaDAO and ChefDAO, and retrieve database connection
			connection = DBUtil.getConnection();
			if (connection != null) {
				System.out.println("Database connection established successfully.");
			} else {
				System.out.println("Failed to establish database connection.");
			}
			orderDAO = new OrderDAO(connection);
		} catch (ClassNotFoundException | SQLException e) {
			throw new ServletException("Error initializing servlet", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Retrieve list of orders from DAO
			List<Order> orders = orderDAO.getOrdersEnCours();
			// Set orders as request attributes
			request.setAttribute("orders", orders);
			// Forward request to orderList.jsp
			request.getRequestDispatcher("orderList.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("Error retrieving orders", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Retrieve parameters for updating order status
			int customerId = Integer.parseInt(request.getParameter("customerId"));
			String pizzaId = request.getParameter("pizzaId");
			// Update the order status to "pret"
			orderDAO.updateOrderStatus(customerId, pizzaId, "pret");
			// Redirect to the same page to refresh the list
			response.sendRedirect("OrderListController");
		} catch (SQLException e) {
			throw new ServletException("Error updating order status", e);
		}
	
	}
}
