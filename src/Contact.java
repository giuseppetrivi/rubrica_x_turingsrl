import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;


/**
 * Class to represent contact data
 */
public class Contact {

    /* contact attributes */
    private User user = null;
    private Integer id = null;
    private String name = null;
    private String surname = null;
    private String address = null;
    private String phone = null;
    private Integer age = null;


    /**
     * Construct <code>Contact</code> instance passing all attributes by arguments
     */
    public Contact(User user, Integer id, String name, String surname, String address, String phone, Integer age) {
        this.setUser(user);
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setAddress(address);
        this.setPhone(phone);
        this.setAge(age);
    }


    /**
     * Construct the <code>Contact</code> instance by filling it with <code>contact_data</code> values
     * @param contact_data <code>Map</code> instance with all attributes inside it
     */
    public Contact(Map<String, Object> contact_data) throws Exception {
        boolean map_object_is_valid = Stream.of("user", "id", "name", "surname", "address", "phone")
            .allMatch(contact_data::containsKey);
        if (map_object_is_valid) {
            this.setUser((User) contact_data.get("user"));
            this.setId((Integer) contact_data.get("id"));
            this.setName((String) contact_data.get("name"));
            this.setSurname((String) contact_data.get("surname"));
            this.setAddress((String) contact_data.get("address"));
            this.setPhone((String) contact_data.get("phone"));
            this.setAge(Integer.parseInt((String) contact_data.get("age")));
        } else {
            throw new Exception("Some necessary key is missing from contact_data");
        }

    }


    /* Getter and setter methods */
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getAge() { return this.age; }
    public void setAge(Integer age) { this.age = age; }


    /**
     * Get the formatted info to be used in a table row
     * @return <code>Object[]</code> containing the row data
     */
    public Object[] getTableRow() {
        return new Object[]{this.getId(), this.getName(), this.getSurname(), this.getPhone()};
    }


    /**
     * Static method to check the format of all <code>Contact</code> fields
     * @param contact_data map containing the data of the contact
     * @throws Exception if a format check is not passing
     */
    public static void checkAllFields(Map<String, Object> contact_data) throws Exception {
        String regex_name = "\\b([A-ZÀ-ÿa-z][-,a-z. ']+[ ]*){2,50}";
        Contact.checkFieldValidity(
            regex_name,
            (String) contact_data.get("name"),
            "Il formato del nome inserito non è valido"
        );
        Contact.checkFieldValidity(
            regex_name,
            (String) contact_data.get("surname"),
            "Il formato del cognome inserito non è valido"
        );
        Contact.checkFieldValidity(
            "^[A-Za-zÀ-ÿ0-9\\s'.,\\-°/]{5,100}$",
            (String) contact_data.get("address"),
            "Il formato dell'indirizzo inserito non è valido"
        );
        Contact.checkFieldValidity(
            "^\\+?\\d{1,4}?[\\s\\-\\.]?\\(?\\d{1,5}\\)?[\\s\\-\\.]?\\d{1,5}[\\s\\-\\.]?\\d{1,6}[\\s\\-\\.]?\\d{0,6}$",
            (String) contact_data.get("phone"),
            "Il formato del numero di telefono inserito non è valido"
        );
        Contact.checkFieldValidity(
            "^(?:1[01][0-9]|150|[1-9]?[0-9])$",
            (String) contact_data.get("age"),
            "L'età inserita non è valida"
        );
    }


    /**
     * General check procedure for each field
     * @param regex regex expression, describing the rules of a correct field
     * @param field the field to verify
     * @param exception_message the message to show if the check fails
     * @throws Exception if a pattern doesn't match
     */
    private static void checkFieldValidity(String regex, String field, String exception_message) throws Exception {
        if (!Pattern.compile(regex).matcher(field).matches()) {
            throw new Exception(exception_message);
        }
    }


    /**
     * Prints all the <code>Contact</code> attributes in pretty format (for debugging)
     */
    public void printPrettyInfo() {
        String format = "%10s : %s\n";
        System.out.printf(format, "ID", this.getId());
        System.out.printf(format, "Nome", this.getName());
        System.out.printf(format, "Cognome", this.getSurname());
        System.out.printf(format, "Indirizzo", this.getAddress());
        System.out.printf(format, "Telefono", this.getPhone());
        System.out.printf(format, "Età", this.getAge());
        System.out.println();
    }

}
