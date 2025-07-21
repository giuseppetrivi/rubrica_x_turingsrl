import java.sql.*;
import java.util.Map;
import java.util.Vector;

// TODO: controllare tutte le varie eccezioni e messaggi di errore

public class User {

    private final String username;


    public User(String username) {
        this.username = username;
    }


    /**
     * Verify login data and return the resulted User (or null if login data are not correct)
     * @param username username (unique id) of the user
     * @param password password of the user
     * @return <code>User</code> instance if login data are correct, <code>null</code> instance if not
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
     * Get <code>Vector</code> of all people related to the user
     */
    public Vector<Person> getAllUserContacts() {
        String sql_query = "SELECT p.* FROM users as u JOIN people as p WHERE u.username=p.username_fk AND username=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            ResultSet result = stm.executeQuery();
            int row_count = DBHandler.getRowCount(result);

            if (row_count > 0) {
                Vector<Person> list_of_contacts = new Vector<>();
                while (result.next()) {
                    Person person = new Person(
                        this,
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("surname"),
                        result.getString("address"),
                        result.getString("phone"),
                        result.getInt("age")
                    );
                    list_of_contacts.add(person);
                }
                return list_of_contacts;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get person data (related to the current user) by its id
     * @param person_id id of the person to get
     * @return <code>Person</code> instance of found person, or <code>null</code> if no person is found
     */
    public Person getPersonById(Integer person_id) {
        String sql_query = "SELECT * FROM people WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            stm.setInt(2, person_id);
            ResultSet result = stm.executeQuery();
            int row_count = DBHandler.getRowCount(result);

            if (row_count == 1 && result.first()) {
                return new Person(
                    this,
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
     * Insert new person (related to the current user)
     * @param person_data <code>Map</code> instance containing the person data to create new person
     * @return <code>Person</code> instance of inserted person, or <code>null</code> if no person has been inserted due to some error
     */
    public Person insertNewPerson(Map<String, Object> person_data) {
        String sql_query = "INSERT INTO people(username_fk, name, surname, address, phone, age) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            Statement.RETURN_GENERATED_KEYS
        )) {
            stm.setString(1, this.username);
            stm.setString(2, (String) person_data.get("name"));
            stm.setString(3, (String) person_data.get("surname"));
            stm.setString(4, (String) person_data.get("address"));
            stm.setString(5, (String) person_data.get("phone"));
            stm.setInt(6, Integer.parseInt((String) person_data.get("age")));
            int row_count = stm.executeUpdate();

            if (row_count == 1) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int person_id = rs.getInt(1);
                    person_data.put("id", person_id);
                    person_data.put("user", this);
                    return new Person(person_data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Edit existent person (related to the current user)
     * @param person_id id of the person to edit
     * @param person_data <code>Map</code> instance containing the person data to edit the person
     * @return <code>Person</code> instance of edited person, or <code>null</code> if no person has been edited due to some error
     */
    public Person editPersonById(Integer person_id, Map<String, Object> person_data) {
        String sql_query = "UPDATE people SET name=?, surname=?, address=?, phone=?, age=? WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            Statement.RETURN_GENERATED_KEYS
        )) {
            stm.setString(1, (String) person_data.get("name"));
            stm.setString(2, (String) person_data.get("surname"));
            stm.setString(3, (String) person_data.get("address"));
            stm.setString(4, (String) person_data.get("phone"));
            stm.setInt(5, Integer.parseInt((String) person_data.get("age")));
            stm.setString(6, this.username);
            stm.setInt(7, person_id);
            int row_count = stm.executeUpdate();

            if (row_count == 1) {
                person_data.put("id", person_id);
                person_data.put("user", this);
                return new Person(person_data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Delete existent person (related to the current user)
     * @param person_id id of the person to delete
     * @return <code>true</code> if person has been deleted, <code>false</code> if not
     */
    public boolean deletePersonById(Integer person_id) {
        String sql_query = "DELETE FROM people WHERE username_fk=? AND id=?";
        try (PreparedStatement stm = DBHandler.getInstance().prepareStatement(
            sql_query,
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        )) {
            stm.setString(1, this.username);
            stm.setInt(2, person_id);
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
