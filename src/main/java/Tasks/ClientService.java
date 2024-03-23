package Tasks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/Test_DB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public long create(String name) throws SQLException {
        validateName(name);

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO client (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        }
    }

    public String getById(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM client WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new SQLException("Client not found with id: " + id);
                }
            }
        }
    }

    public void setName(long id, String name) throws SQLException {
        validateName(name);

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating client failed, no rows affected.");
            }
        }
    }

    public void deleteById(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String deleteProjectWorkerQuery = "DELETE FROM project_worker WHERE project_id = ?";
            try (PreparedStatement deleteProjectWorkerStatement = connection.prepareStatement(deleteProjectWorkerQuery)) {
                deleteProjectWorkerStatement.setLong(1, id);
                deleteProjectWorkerStatement.executeUpdate();
            }


            String deleteProjectQuery = "DELETE FROM project WHERE id = ?";
            try (PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectQuery)) {
                deleteProjectStatement.setLong(1, id);
                int affectedRows = deleteProjectStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting project failed, no rows affected.");
                }
            }
        }
    }

    public List<Client> listAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name FROM client")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                clients.add(new Client(id, name));
            }
        }
        return clients;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }
}