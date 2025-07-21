import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * [Singleton class]
 */
public class DBHandler {

    private static Connection conn = null;


    /* Empty constructor because this class cannot be instantiated */
    private DBHandler() {}


    /**
     * [<i>Singleton method</i>] Create the Connection instance if it is not already been created
     * @return <code>Connection</code> instance to connect with the mysql database
     */
    public static Connection getInstance() {
        if (DBHandler.conn == null) {
            Properties db_prop = DBProperties.getInstance();
            try {
                DBHandler.conn = DriverManager.getConnection(
                    "jdbc:mysql://" + db_prop.getProperty("host") + ":" + db_prop.getProperty("port") + "/" + db_prop.getProperty("db_name"),
                    db_prop.getProperty("user"),
                    db_prop.getProperty("password")
                );
                System.out.println("Connessione riuscita!");
            } catch (SQLException e) {
                System.out.println("Errore di connessione: " + e.getMessage());
            }
        }
        return DBHandler.conn;
    }


    /**
     * Utility function that counts the number of rows in the ResultSet of an SQL statement.<br>
     * Go to the last row, get the index of the last row and then return back to the starting point, to
     * make the result iterable with the <code>next()</code> method.
     *
     * @param result <code>ResultSet</code> instance, which contains all rows returned by the SQL statement
     * @return the number of rows in the result set
     */
    public static int getRowCount(ResultSet result) throws SQLException {
        result.last();
        int row_count = result.getRow();
        result.beforeFirst();
        return row_count;
    }
}
