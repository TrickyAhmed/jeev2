package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.ChefDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chef;
import test.DBUtil;

/**
 * Servlet implementation class ChefController
 */
public class ChefController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private ChefDAO chefDAO;

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
			chefDAO = new ChefDAO(connection);
		} catch (ClassNotFoundException | SQLException e) {
			throw new ServletException("Error initializing servlet", e);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChefController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Retrieve list of pizzas from DAO
			List<Chef> chefs = chefDAO.getAllChefs();
			// Retrieve list of chefs from DAO
			// Set pizzas and chefs as request attributes
			request.setAttribute("chefs", chefs);
			// Forward request to index.jsp
			request.getRequestDispatcher("about.jsp").forward(request, response);
		} catch (SQLException e) {
			throw new ServletException("Error retrieving pizzas or chefs", e);
		}
	}

	public void destroy() {
		// Close database connection
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Database connection closed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
