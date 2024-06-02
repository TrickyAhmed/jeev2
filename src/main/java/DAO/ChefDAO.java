package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Chef;

public class ChefDAO {

	private final Connection connection;

    public ChefDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert a new Chef into the database
    public void insertChef(Chef Chef) throws SQLException {
        String sql = "INSERT INTO Chef (name, description, image) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Chef.getName());
            statement.setString(2, Chef.getDescription());
            statement.setString(3, Chef.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception, log or rethrow if necessary
            e.printStackTrace();
            throw e ;
        }}


    public void updateChef(Chef Chef) throws SQLException {
        String sql = "UPDATE Chef SET name = ?, description = ?, image = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Chef.getName());
            statement.setString(2, Chef.getDescription());
            statement.setString(3, Chef.getImage());
            statement.setInt(4, Chef.getId());
            statement.executeUpdate();
        }
    }

    public void deleteChef(int id) throws SQLException {
        String sql = "DELETE FROM Chef WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all Chefs from the database
    public List<Chef> getAllChefs() throws SQLException {
        List<Chef> Chefs = new ArrayList<>();
        String sql = "SELECT * FROM Chef";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Chef Chef = new Chef();
                Chef.setId(resultSet.getInt("id"));
                Chef.setName(resultSet.getString("name"));
                Chef.setDescription(resultSet.getString("description"));
                Chef.setImage(resultSet.getString("image"));
                Chefs.add(Chef);
            }
        }
        return Chefs;
    }

   

	

	public Chef getChefById(int id) throws SQLException {
        Chef Chef = null;
        String sql = "SELECT * FROM Chef WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Chef = new Chef();
                    Chef.setId(resultSet.getInt("id"));
                    Chef.setName(resultSet.getString("name"));
                    Chef.setDescription(resultSet.getString("description"));
                    Chef.setImage(resultSet.getString("image"));
                }
            }
        }
        return Chef;
    }

}
