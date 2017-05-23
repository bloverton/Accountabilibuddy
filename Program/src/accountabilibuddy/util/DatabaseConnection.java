package accountabilibuddy.util;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by overt on 5/13/2017.
 */
public class DatabaseConnection {

    private static String url = "jdbc:postgresql://accountabilibuddy.cbozwwsumqif.us-east-1.rds.amazonaws.com:5432/accountabilibuddyDB" +
            "?user=masterUsername&password=masterPassword";

    /**
     * @return A Connection object that establishes a connection to the AWS PostgreSQL database.
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /** Checks if the username and password combination are a valid record in the database.
     * @param username
     * @param password
     * @return If a user is found, return true. Otherwise, return false.
     * @throws SQLException
     */
    public static boolean checkUser(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "' AND password = '" + password + "'");
        return userFound.next();
    }

    /** Checks if username has already been created, if not, then will create a new record
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void signUp(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet userFound = st.executeQuery("SELECT 1 FROM users WHERE username = '" + username + "'");
        if (!userFound.next()) {
            st.execute("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "');");
        }
        st.close();
    }

    /** Saves and adds the stock into the users Favorites column
     * @param username
     * @param password
     * @param stock
     * @throws SQLException
     */
    public static void saveStock(String username, String password, String stock) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        boolean stockInserted = st.execute("UPDATE users SET favorites = array_cat(favorites, '{" + stock + "}')" +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
    }

    /** Removes stock from users Favorites column
     * @param username
     * @param password
     * @param stock
     * @throws SQLException
     */
    public static void removeStock(String username, String password, String stock) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        boolean stockInserted = st.execute("UPDATE users SET favorites = array_remove(favorite, '" + stock + "')" +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
    }

    /** Grabs all favorites from the Favorites column of a specific user and returns an ArrayList<String>
     * @param username
     * @param password
     * @return An arraylist with all the user's favorites
     * @throws SQLException
     */
    public static ArrayList<String> getFavorites(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet favoritesFound = st.executeQuery("SELECT favorites FROM users " +
                "WHERE username = '" + username + "' AND password = '" + password + "';");
        ArrayList<String> favorites = new ArrayList<>();
        while (favoritesFound.next() && favoritesFound != null) {
            Array a = favoritesFound.getArray("favorites");
            String[] fav = (String[])a.getArray();
            if(fav == null){
                return new ArrayList<>();
            }
            for (int i = 0; i < fav.length; i++) {
                favorites.add(fav[i]);
            }
        }
        return favorites;
    }

    /**
     * Clears the favorites entry in the database specified by the username and password
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void clearFavorites(String username, String password) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        boolean stocksCleared = st.execute("UPDATE users SET favorites = NULL WHERE username = '" + username + "' " +
                "AND password = '" + password + "';");
    }
}
