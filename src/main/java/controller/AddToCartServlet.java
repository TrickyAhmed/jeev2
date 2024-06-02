package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Order;
import model.Pizza;
import DAO.PizzaDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import test.DBUtil;

/**
 * Servlet implementation class AddToCartServlet
 */

public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection connection;
    private PizzaDAO pizzaDAO;

    public void init() throws ServletException {
        try {
            connection = DBUtil.getConnection();
            pizzaDAO = new PizzaDAO(connection);
            System.out.println("AddToCartServlet initialized with database connection.");
        } catch (SQLException e) {
            System.out.println("Error initializing AddToCartServlet: " + e.getMessage());
            throw new ServletException("Error initializing servlet", e);
        }
    }

    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed in AddToCartServlet.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection in AddToCartServlet: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("AddToCartServlet destroyed.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside AddToCartServlet doPost method.");

        // Récupérer les valeurs des paramètres du formulaire
        String pizzaName = request.getParameter("pizzaName");
        String size = request.getParameter("size");
        String quantityStr = request.getParameter("quantity");
        String Price = request.getParameter("price");
        System.out.println(pizzaName);
        System.out.println(size);
        System.out.println("Type of quantityStr: " + quantityStr);
        System.out.println(Price);
        // Assurez-vous que les valeurs ne sont pas nulles ou vides
        if (pizzaName == null || pizzaName.isEmpty() || size == null || size.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {
            System.out.println("Missing parameters in the request.");
            response.sendRedirect("pizzas.jsp");
            return;
        }

        try {
            // Convertir la quantité en entier
        	int quantity = Integer.parseInt(quantityStr);
        	double price = Double.parseDouble(Price);
            // Utilisez les données récupérées pour créer la commande
            Order order = new Order(1, pizzaName, quantity, size, price,"En Cours");
            System.out.println("Order created: " + order.getPizzaId());

            // Enregistrer la commande dans la session
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
                System.out.println("New cart created and added to session.");
            }
            cart.addItem(order);
            System.out.println("Order added to cart.");
            
            // Rediriger vers la page pizzas.jsp avec un indicateur d'ajout réussi
            response.sendRedirect("cart.jsp?addedToCart=true");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing quantity: " + e.getMessage());
            response.sendRedirect("pizzas.jsp");
        }
    }

    private double calculateTotalPrice(double unitPrice, int quantity) {
        return unitPrice * quantity;
    }
}
