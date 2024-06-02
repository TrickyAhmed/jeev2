package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import DAO.PizzaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pizza;
import test.DBUtil;

/**
 * Servlet implementation class PizzaDetailController
 */
public class PizzaDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PizzaDAO pizzaDAO;

	public void init() throws ServletException {
		System.out.println("Initializing PizzaDetailController...");
		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC Driver loaded.");

			// Initialize PizzaDAO and retrieve database connection
			connection = DBUtil.getConnection();
			if (connection != null) {
				System.out.println("Database connection established successfully.");
			} else {
				System.out.println("Failed to establish database connection.");
			}
			pizzaDAO = new PizzaDAO(connection);
			System.out.println("PizzaDAO initialized.");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC Driver not found.");
			throw new ServletException("Error initializing servlet: Driver not found", e);
		} catch (SQLException e) {
			System.err.println("Error establishing database connection.");
			throw new ServletException("Error initializing servlet: Database connection error", e);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PizzaDetailController() {
		super();
		System.out.println("PizzaDetailController instantiated.");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Handling GET request...");
		try {
			// Retrieve pizza ID from request parameter
			String pizzaIdParam = request.getParameter("pizzaId");
			System.out.println("Request parameter 'pizzaId': " + pizzaIdParam);
			int pizzaId = Integer.parseInt(pizzaIdParam);
			System.out.println("Parsed pizza ID: " + pizzaId);

			// Retrieve pizza by ID from database using DAO
			Pizza pizza = pizzaDAO.getPizzaById(pizzaId);
			System.out.println("Pizza retrieved from database: " + pizza);

			// Set pizza as a request attribute
			request.setAttribute("pizza", pizza);
			System.out.println("Pizza set as request attribute.");

			// Forward request to pizzadetail.jsp
			request.getRequestDispatcher("pizzas.jsp").forward(request, response);
			System.out.println("Request forwarded to pizzas.jsp.");
		} catch (NumberFormatException e) {
			System.err.println("Invalid pizza ID format: " + e.getMessage());
			// Handle if pizzaId parameter is not provided or invalid
			throw new ServletException("Invalid pizza ID", e);
		} catch (SQLException e) {
			System.err.println("SQL error while retrieving pizza: " + e.getMessage());
			// Handle database errors
			throw new ServletException("Error retrieving pizza from database", e);
		}
	}

	public void destroy() {
		System.out.println("Destroying PizzaDetailController...");
		// Close database connection
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Database connection closed.");
			}
		} catch (SQLException e) {
			System.err.println("Error closing database connection: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
