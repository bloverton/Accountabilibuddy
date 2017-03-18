package accountabilibuddy.util;

import java.sql.*;

/**
 * Created by overt on 2/21/2017.
 */
public class DatabaseConnection {

    private Connection connection;
    private String url;

    /**
     * Sets the URL of the database
     * @param url - Location of the database
     */
    public DatabaseConnection(String url){
        this.url = url;
    }

    /**
     * Connects to the database
     */
    public void connect(){
        connection = null;
        try{
            //DB parameters
            connection = DriverManager.getConnection(url);
        }catch(SQLException SQLError){
            SQLError.printStackTrace();
        }
    }

    /**
     * Creates a new database file in the database
     * @param fileName - Name of new database file
     */
    public void createNewDatabase(String fileName){
        try(Connection connection = DriverManager.getConnection(url + fileName)){
            if(connection != null){
                DatabaseMetaData metadata = connection.getMetaData();
            }
        }catch(SQLException SQLError){
            SQLError.printStackTrace();
        }
    }

    /**
     * Inserts the user into the database
     * @param username - Username used for user's login
     * @param password - Password used for user's login
     * @param tableName - Name of table you want to inject data to
     */
    public void insertUser(String tableName, String username, String password){
        //SQL command to insert username and password in table
        String sqlInsert = "INSERT INTO " + tableName + "(username,password) VALUES(?,?)";

        //Exception handling for prepared statement
        try(PreparedStatement sqlCommand = connection.prepareStatement(sqlInsert)){
            sqlCommand.setString(1, username);
            sqlCommand.setString(2, password);
            sqlCommand.execute();
        }catch(SQLException SQLError){
            SQLError.printStackTrace();
        }
    }

    /**
     * Checks for duplicate strings in a column at a specified table name. Returns true there is no duplicates
     * Returns false if there are duplicate strings.
     * @param tableName - Literally the name of the table
     * @param columnName - Literally the name of the column
     * @param stringQuery - Literally the name of the string
     * @return
     */
    public boolean isValidUsername(String tableName, String columnName, String stringQuery){
        //SQL command to check for string at a table column in a database table
        String sqlSelectUsers = "SELECT " + columnName + " FROM " + tableName;
        //Exception handling for connection statement and statement execution

        try(Statement connStatement = connection.createStatement();
            ResultSet resultStatement = connStatement.executeQuery(sqlSelectUsers)){

            while(resultStatement.next()){
                if(resultStatement.getString(columnName).equals(stringQuery))
                    return false;
            }
        }catch(SQLException SQLError){
            SQLError.printStackTrace();
        }
        return true;
    }

    /**
     * Closes connection to database
     */
    public void closeConnection(){
        try {
            connection.close();
        }catch(SQLException SQLError){
            SQLError.printStackTrace();
        }
    }

    /**
     * Sets a new URL location and sets a new connection to the database specified at the new URL location
     *  Note: Does not implicitly call connect method. "connect" method must be called explicitly.
     * @param url - Location of the database
     */
    public void setURL(String url){
        this.url = url;
    }
}