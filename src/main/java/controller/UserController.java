package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import test.DBUtil;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private UserDAO udao;

	public UserController() {
		super();
		System.out.println("UserController: Initializing UserController");
		try {
			connection = DBUtil.getConnection();
			System.out.println("UserController: Database connection established");
			udao = new UserDAO(connection);
			System.out.println("UserController: UserDAO instance created");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("UserController: Error initializing database connection");
			throw new RuntimeException("Failed to initialize database connection", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserController: Handling GET request");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("UserController: GET request served at " + request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserController: Handling POST request");
		try {
			if (request.getParameter("inscription") != null) {
				System.out.println("UserController: Inscription parameter found");
				handleInscription(request, response);
			} else if (request.getParameter("authentification") != null) {
				System.out.println("UserController: Authentification parameter found");
				handleAuthentification(request, response);
			} else {
				System.out.println("UserController: No valid parameter found in POST request");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserController: Exception during POST request handling");
			throw new ServletException("Error during authentication", e);
		}
	}

	private void handleInscription(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		System.out.println("UserController: Handling inscription");
		String l = request.getParameter("login");
		String p = request.getParameter("pass");
		String n = request.getParameter("nom");
		String pr = request.getParameter("prenom");
		String r = request.getParameter("role");

		System.out.println("UserController: Received parameters - login: " + l + ", pass: " + p + ", nom: " + n
				+ ", prenom: " + pr + ", role: " + r);

		User u = new User(l, p, n, pr, r);
		System.out.println("UserController: User object created: " + u);

		udao.create(u);
		System.out.println("UserController: User data inserted into database");

		response.sendRedirect("/application/user/authentification.jsp");
		System.out.println("UserController: Redirecting to authentification.jsp");
	}

	private void handleAuthentification(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("UserController: Handling authentication");
		String l = request.getParameter("login");
		String p = request.getParameter("pass");

		System.out.println("UserController: Received login: " + l + " and password");

		HttpSession session = request.getSession();
		User u = udao.findByLogin(l);

		System.out.println("UserController: Retrieved user: " + (u != null ? u : "null"));

		String message = "";
		if (u != null && u.getPass().equals(p)) {
			System.out.println("UserController: Password matches for user: " + l);
			session.setAttribute("user", u);
			System.out.println("UserController: User session set for user: " + l);

			switch (u.getRole()) {
			case "Administrateur":
				response.sendRedirect("AdminPizzaController");
				System.out.println("UserController: Redirecting Administrateur to AdminPizzaController");
				break;

			case "client":
				response.sendRedirect("PizzaController");
				System.out.println("UserController: Redirecting client to PizzaController");
				break;
			case "chef":
				response.sendRedirect("OrderListController");
				System.out.println("UserController: Redirecting chef to OrderListController");
				break;
			case "livreur":
				response.sendRedirect("LivreurController");
				System.out.println("UserController: Redirecting livreur to LivreurController");
				break;
			default:
				System.out.println("UserController: Unknown user role");
			}
		} else {
			System.out.println("UserController: Authentication failed for user: " + l);
			message = "Erreur d'authentification";
			session.setAttribute("message", message);
			response.sendRedirect(request.getContextPath() + "/user/authentification.jsp");
			System.out.println("UserController: Redirecting to authentification.jsp with error message");
		}
	}

}
