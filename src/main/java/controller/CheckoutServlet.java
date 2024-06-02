package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Pizza;
import model.Order;
import test.DBUtil;

/**
 * Servlet implementation class CheckoutServlet
 */
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Pizza pizza =  (Pizza) request.getAttribute("pizza");
        System.out.println("Pizza received: " + pizza);
        if (pizza != null && !pizza.isEmpty()) {
            System.out.println("Cart is not null.");
            // Iterate over the items in the cart and save them as orders
            
			
            // Clear the cart after successfully saving orders
            session.removeAttribute("cart");
            System.out.println("Cart cleared successfully.");
            response.sendRedirect("user/checkout-success.jsp");
        } else {
            System.out.println("Cart is null or empty.");
            // Handle empty cart
            response.sendRedirect("user/cart.jsp");
        }
    }

    // Method to save an order to the database
    private void saveOrderToDatabase(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DBUtil.getConnection();// Implement this method to get a database connection
            String sql = "INSERT INTO orders (customerId, pizzaId, quantity, Size, totalPrice, orderStatus) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getCustomerId());
            statement.setString(2, order.getPizzaId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getSize());
            statement.setDouble(5, order.getTotalPrice());
            statement.setString(6, order.getOrderStatus());
            
            statement.executeUpdate();
            System.out.println("Order saved to the database.");
        } finally {
            // Close resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}
