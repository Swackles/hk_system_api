package hkSystem.Internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.*;

class Database {
    @Autowired
    private Environment environment;

    private int id;
    private final String url;
    private final String username;
    private final String password;
    private final String database;
    private ResultSet resultSet;
    private int updatedRows;

    Database() {
        this.url = System.getenv("DATABASE_URL");
        this.username = System.getenv("POSTGRES_USER");
        this.password = System.getenv("POSTGRES_PASSWORD");
        this.database = System.getenv("POSTGRES_DB");
    }

    void execute(String query) throws SQLException {
        Connection con = DriverManager.getConnection(url + "/" + database, username, password);
        PreparedStatement prst = con.prepareStatement(query);

        if (prst.execute()) commitQuery(prst);
        else updateQuery(prst);
    }

    long getGeneratedId() throws SQLException{
        if (resultSet.next()) return resultSet.getLong("id");
        else throw new SQLException("Unable to get generated user id");
    }

    private void commitQuery(PreparedStatement prst) throws SQLException {
        resultSet = prst.executeQuery();
    }

    private void updateQuery(PreparedStatement prst) throws SQLException {
        updatedRows = prst.executeUpdate();
        resultSet = prst.getGeneratedKeys();
    }

    ResultSet getResultSet() { return resultSet; }
    int getUpdatedRows() { return updatedRows; }
}
