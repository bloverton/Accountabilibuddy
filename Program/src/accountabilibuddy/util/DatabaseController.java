import java.sql.*;
import java.util.Scanner;

public class DatabaseController {

    private static String url = "jdbc:postgresql://accountabilibuddydbinstance.clskvjtu76zv.us-west-1.rds.amazonaws.com:5432/accountabilibuddydb" +
            "?user=masterUsername&password=masterPassword";

    // Returns a Connection object that establishes a connection to the AWS PostgreSQL database.
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database failed to connect.");
            e.printStackTrace();
        }
        return conn;
    }

    // Checks if the username and password are a valid record in the database.
    public static boolean checkUser(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "' AND password = '" + password + "'");
        return userFound.next();
    }

    //
    public static void signUp() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        String passwordCheck = "";
        System.out.println(" --- SignUp --- ");
        System.out.println("What username would you like?");
        username = scanner.nextLine();
        System.out.println("What password would you like?");
        password = scanner.nextLine();
        System.out.println("Please re-enter your password.");
        passwordCheck = scanner.nextLine();
        if (password.equals(passwordCheck)) {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "'");
            if (userFound.next()) {
                System.out.println("Sorry, username already taken.");
            } else
                st.execute("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "');");
            st.close();
        }
    }

    public static void returnUsers() throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT username FROM users");
        int count = 1;
        while (rs.next()) {
            System.out.println("Username " + count + ": " + rs.getString(1));
            count++;
        }
        rs.close();
        st.close();
    }
}
