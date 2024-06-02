package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 * Servlet implementation class Auth
 */
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String l = request.getParameter("login");
		String p = request.getParameter("pass");
		request.getSession().invalidate();
		ServletContext c = getServletContext();
		String message = "";
		if (c.getAttribute("users") != null) {
			Vector<User> u = (Vector<User>) c.getAttribute("users");
			for (int i = 0; i < u.size(); i++) {
				User us = u.get(i);
				if (us.getLogin().equals(l) && us.getPass().equals(p)) {
					HttpSession s = request.getSession();
					s.setAttribute("user", us);
					response.sendRedirect("index.jsp");
					break;

				}
			}

			if (request.getSession(false) == null) {
				message = "Erreur d'authentification";
				request.setAttribute("message", message);
				RequestDispatcher rd = c.getRequestDispatcher("/PizzaController");
				rd.forward(request, response);
			}

		} else {
			message = "Pas d'utilisateurs inscrit";
			request.setAttribute("message", message);
			RequestDispatcher rd = c.getRequestDispatcher("/authentification.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
