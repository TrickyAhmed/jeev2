package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

import java.io.IOException;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'index de l'élément à supprimer
        int index = Integer.parseInt(request.getParameter("index"));

        // Récupérer le panier depuis la session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Supprimer l'élément du panier
        if (cart != null) {
            cart.removeItem(index);
        }

        // Rediriger vers la page du panier
        response.sendRedirect("cart.jsp");
    }
}
