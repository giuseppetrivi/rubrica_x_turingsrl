import java.sql.*;
import java.util.Map;
import java.util.Vector;

// TODO: controllare tutte le varie eccezioni e messaggi di errore

/**
 * Class to handle user properties and user contacts CRUD operations
 */
public class User {

    private final String username;


    public User(String username) { this.username = username; }


    /**
     * Verifies login data into `users` database table
     * @param username username (unique id) of the user
     * @param password password of the user
     * @return <code>User</code> instance if login data is correct, <code>null</code> instance if not
     */
    public static User verifyLoginData(String username, String password) {
        String sql_query = "SELECT * FROM users WHERE username=? AND password=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet result = stm.executeQuery();
            int row_count = DBHandler.getRowCount(result);

            if (row_count == 1) {
                return new User(username);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Get <code>Vector</code> of all contacts related to the user
     */
    public Vector<Contact> getAllUserContacts() {
        String sql_query = "SELECT p.* FROM users as u JOIN contacts as p WHERE u.username=p.username_fk AND username=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            ResultSet result = stm.executeQuery();
            int row_count = DBHandler.getRowCount(result);

            if (row_count > 0) {
                Vector<Contact> list_of_contacts = new Vector<>();
                while (result.next()) {
                    Contact contact = new Contact(
                        User.this,
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("surname"),
                        result.getString("address"),
                        result.getString("phone"),
                        result.getInt("age")
                    );
                    list_of_contacts.add(contact);
                }
                return list_of_contacts;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get contact data (related to the current user) by its id
     * @param contact_id id of the contact to get
     * @return <code>Contact</code> instance of found contact, or <code>null</code> if no contact is found
     */
    public Contact getContactById(Integer contact_id) {
        String sql_query = "SELECT * FROM contacts WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            stm.setInt(2, contact_id);
            ResultSet result = stm.executeQuery();
            int row_count = DBHandler.getRowCount(result);

            if (row_count == 1 && result.first()) {
                return new Contact(
                    User.this,
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("surname"),
                    result.getString("address"),
                    result.getString("phone"),
                    result.getInt("age")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Insert new contact (related to the current user)
     * @param contact_data <code>Map</code> instance containing the contact data to create new contact
     * @return <code>Contact</code> instance of inserted contact, or <code>null</code> if no contact has been inserted due to some error
     */
    public Contact insertNewContact(Map<String, Object> contact_data) {
        String sql_query = "INSERT INTO contacts(username_fk, name, surname, address, phone, age) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            Statement.RETURN_GENERATED_KEYS
        )) {
            stm.setString(1, this.username);
            stm.setString(2, (String) contact_data.get("name"));
            stm.setString(3, (String) contact_data.get("surname"));
            stm.setString(4, (String) contact_data.get("address"));
            stm.setString(5, (String) contact_data.get("phone"));
            stm.setInt(6, Integer.parseInt((String) contact_data.get("age")));
            int row_count = stm.executeUpdate();

            if (row_count == 1) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int contact_id = rs.getInt(1);
                    contact_data.put("id", contact_id);
                    contact_data.put("user", User.this);
                    return new Contact(contact_data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Edit existent contact (related to the current user)
     * @param contact_id id of the contact to edit
     * @param contact_data <code>Map</code> instance containing the contact data to edit the contact
     * @return <code>Contact</code> instance of edited contact, or <code>null</code> if no contact has been edited due to some error
     */
    public Contact editContactById(Integer contact_id, Map<String, Object> contact_data) {
        String sql_query = "UPDATE contacts SET name=?, surname=?, address=?, phone=?, age=? WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            Statement.RETURN_GENERATED_KEYS
        )) {
            stm.setString(1, (String) contact_data.get("name"));
            stm.setString(2, (String) contact_data.get("surname"));
            stm.setString(3, (String) contact_data.get("address"));
            stm.setString(4, (String) contact_data.get("phone"));
            stm.setInt(5, Integer.parseInt((String) contact_data.get("age")));
            stm.setString(6, this.username);
            stm.setInt(7, contact_id);
            int row_count = stm.executeUpdate();

            if (row_count == 1) {
                contact_data.put("id", contact_id);
                contact_data.put("user", this);
                return new Contact(contact_data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Delete existent contact (related to the current user)
     * @param contact_id id of the contact to delete
     * @return <code>true</code> if contact has been deleted, <code>false</code> if not
     */
    public boolean deleteContactById(Integer contact_id) {
        String sql_query = "DELETE FROM contacts WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            stm.setInt(2, contact_id);
            int row_count = stm.executeUpdate();

            if (row_count == 1) {
               return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
