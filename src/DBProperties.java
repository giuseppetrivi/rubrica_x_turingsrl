import java.io.FileReader;
import java.util.Properties;

/**/
public class DBProperties extends Properties {

    private final static String DB_PROP_FILENAME = "configuration/db.properties";
    private static Properties prop = null;


    /**
     * Custom constructor to automatically load the database properties file
     */
    private DBProperties() {
        super();
        try {
            FileReader file = new FileReader(DBProperties.DB_PROP_FILENAME);
            this.load(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * [<i>Singleton method</i>] Create the <code>DBProperties</code> instance if it is not already been created
     * @return <code>DBProperties</code> instance with the <code>db.properties</code> already loaded
     */
    public static Properties getInstance() {
        if (DBProperties.prop == null) {
            DBProperties.prop = new DBProperties();
        }
        return DBProperties.prop;
    }
}
