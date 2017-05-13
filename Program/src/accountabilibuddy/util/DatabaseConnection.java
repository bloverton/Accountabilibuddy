import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {

    private static String url = "jdbc:postgresql://accountabilibuddydbinstance.clskvjtu76zv.us-west-1.rds.amazonaws.com:5432/accountabilibuddydb" +
            "?user=masterUsername&password=masterPassword";

    // Returns a Connection object that establishes a connection to the AWS PostgreSQL database.
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Checks if the username and password combination are a valid record in the database.
    public static boolean checkUser(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "' AND password = '" + password + "'");
        return userFound.next();
    }

    // Checks if username has already been created, if not, then will create a new record
    public static void signUp(String username, String password, String passwordCheck) throws SQLException {
        if (password.equals(passwordCheck)) {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "'");
            if (!userFound.next()) {
                st.execute("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "');");
            }
            st.close();
        }
    }

    // Saves and adds the stock into the users Favorites column
    public static void saveStock(String username, String password, String stock) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet stockInserted = st.executeQuery("UPDATE users SET favorites = array_cat(favorites, '{" + stock + "}')" +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
    }

    // Removes stock from users Favorites column
    public static void removeStock(String username, String password, String stock) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet stockInserted = st.executeQuery("UPDATE users SET favorites = array_remove(favorite, '" + stock + "')" +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
    }

    // Grabs all favorites from the Favorites column of a specific user and returns an ArrayList<String>
    public static ArrayList<String> getFavorites(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet favoritesFound = st.executeQuery("SELECT favorites FROM users " +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
        ArrayList<String> favorites = new ArrayList<String>();
        while (favoritesFound.next()) {
            Array a = favoritesFound.getArray("favorites");
            String[] fav = (String[])a.getArray();
            for (int i = 0; i < fav.length; i++) {
                favorites.add(fav[i]);
                System.out.println(favorites.get(i));
            }
        }
        return favorites;
    }
}
