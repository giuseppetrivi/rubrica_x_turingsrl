import java.io.*;
import java.util.Properties;

/**/
public class DBProperties extends Properties {

    private final static String DB_PROP_FILENAME = "db.properties";
    private static Properties prop = null;


    /**
     * Custom constructor to automatically load the database properties file
     */
    private DBProperties() {
        super();
        String cwd = System.getProperty("user.dir");
        File file = new File(cwd, DBProperties.DB_PROP_FILENAME);

        try (InputStream is = new FileInputStream(file)) {
            this.load(is);
            // usa props...
        } catch (IOException e) {
            e.printStackTrace();
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
