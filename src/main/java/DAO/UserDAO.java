package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(User user) throws SQLException {
        String sql = "INSERT INTO user (login, nom, pass, prenom, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPass());
            statement.setString(3, user.getNom());
            statement.setString(4, user.getPrenom());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
        }
    }

    public User findByLogin(String login) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM user WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setLogin(resultSet.getString("login"));
                    user.setPass(resultSet.getString("nom"));
                    user.setNom(resultSet.getString("pass"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        }
        return user;
    }
}
