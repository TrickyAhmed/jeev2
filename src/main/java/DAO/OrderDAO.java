package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaQuery;
import model.Order;
import util.HibernateUtil;

public class OrderDAO {
	private final Connection connection;

	public OrderDAO(Connection connection) {
		this.connection = connection;
	}
    @SuppressWarnings("deprecation")
    public void saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, pizza_id, quantity, total_price, order_status , size) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getCustomerId());
            statement.setString(2, order.getPizzaId());
            statement.setInt(3, order.getQuantity());
            statement.setDouble(4, order.getTotalPrice());
            statement.setString(5, order.getOrderStatus());
            statement.setString(6, order.getSize());
            statement.executeUpdate();
        }
    }

    
    public List<Order> getAllOrders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Create CriteriaQuery for Order entity
            CriteriaQuery<Order> criteriaQuery = session.getCriteriaBuilder().createQuery(Order.class);
            criteriaQuery.from(Order.class);
            // Execute query and return results
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void updateOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                session.delete(order);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public Order getOrderById(int orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Order.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Order> getOrdersEnCours() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE order_status = 'En Cours'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("customer_id"),
                    resultSet.getString("pizza_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("size"),
                    resultSet.getDouble("total_price"),
                    resultSet.getString("order_status")
                );
                orders.add(order);
            }
        }
        return orders;
    }
    public List<Order> getOrdersPret() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE order_status = 'pret'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("customer_id"),
                    resultSet.getString("pizza_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("size"),
                    resultSet.getDouble("total_price"),
                    resultSet.getString("order_status")
                );
                orders.add(order);
            }
        }
        return orders;
    }
    public void updateOrderStatus(int customerId, String pizzaId, String newStatus) throws SQLException {
        String sql = "UPDATE orders SET order_status = ? WHERE customer_id = ? AND pizza_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setInt(2, customerId);
            statement.setString(3, pizzaId);
            statement.executeUpdate();
        }
    }
    
    public void updateOrderStatusToDelivre(int customerId, String pizzaId, String newStatus) throws SQLException {
        String sql = "UPDATE orders SET order_status = ? WHERE customer_id = ? AND pizza_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus);
            statement.setInt(2, customerId);
            statement.setString(3, pizzaId);
            statement.executeUpdate();
        }
    }

    // Add other necessary methods for CRUD operations
}
