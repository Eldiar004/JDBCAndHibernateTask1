package peaksoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/homework5";
    private static final String userName = "postgres";
    private static final String password = "1234";
    public static Connection connection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
