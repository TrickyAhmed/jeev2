package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pizza;

public class PizzaDAO {
    private final Connection connection;

    public PizzaDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert a new pizza into the database
    public void insertPizza(Pizza pizza) throws SQLException {
        String sql = "INSERT INTO pizza (name, description, price, image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pizza.getName());
            statement.setString(2, pizza.getDescription());
            statement.setDouble(3, pizza.getPrice());
            statement.setString(4, pizza.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception, log or rethrow if necessary
            e.printStackTrace();
            throw e ;
        }}


    public void updatePizza(Pizza pizza) throws SQLException {
        String sql = "UPDATE pizza SET name = ?, description = ?, price = ?, image = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pizza.getName());
            statement.setString(2, pizza.getDescription());
            statement.setDouble(3, pizza.getPrice());
            statement.setString(4, pizza.getImage());
            statement.setInt(5, pizza.getId());
            statement.executeUpdate();
        }
    }

    public void deletePizza(int id) throws SQLException {
        String sql = "DELETE FROM pizza WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all pizzas from the database
    public List<Pizza> getAllPizzas() throws SQLException {
        List<Pizza> pizzas = new ArrayList<>();
        String sql = "SELECT * FROM pizza";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pizza pizza = new Pizza();
                pizza.setId(resultSet.getInt("id"));
                pizza.setName(resultSet.getString("name"));
                pizza.setDescription(resultSet.getString("description"));
                pizza.setPrice(resultSet.getDouble("price"));
                pizza.setImage(resultSet.getString("image"));
                pizzas.add(pizza);
            }
        }
        return pizzas;
    }

    public Pizza getPizzaById(int id) throws SQLException {
        Pizza pizza = null;
        String sql = "SELECT * FROM pizza WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pizza = new Pizza();
                    pizza.setId(resultSet.getInt("id"));
                    pizza.setName(resultSet.getString("name"));
                    pizza.setDescription(resultSet.getString("description"));
                    pizza.setPrice(resultSet.getDouble("price"));
                    pizza.setImage(resultSet.getString("image"));
                }
            }
        }
        return pizza;
    }
}
