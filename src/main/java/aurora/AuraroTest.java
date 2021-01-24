package aurora;

import java.sql.*;
import java.util.UUID;

class AuroraTest {
    private final Connection conn;
    private Statement statement = null;

    public AuroraTest(String jdbc_url, String dbUsername, String dbPassword) throws SQLException, ClassNotFoundException {
        String dbName = "test-database";
        String jdbcUrl = "jdbc:postgresql://" + jdbc_url + ":9999/" + dbName + "?user=" + dbUsername + "&password=" + dbPassword;
        Class.forName("org.postgresql.Driver");
        this.conn = DriverManager.getConnection(jdbcUrl);
    }

    public void createDB () throws SQLException {
        statement = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS AuroraTest (id SERIAL PRIMARY KEY, content VARCHAR(80))";
        statement.executeUpdate(sql);

        System.out.println("DB table AuroraTest is created.");
    }

    public void insertData() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO AuroraTest (content) VALUES (?)");
        String content = "" + UUID.randomUUID();
        preparedStatement.setString(1, content);
        preparedStatement.executeUpdate();

        System.out.println("data is insert to table AuroraTest.");
    }

    public void selectData() throws SQLException {
        String sql = "SELECT  count(*) as count FROM jdbc_test";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String count = resultSet.getString("count");
            System.out.println("Total Records: " + count);
        }

        System.out.println("select result is done.");
    }

}