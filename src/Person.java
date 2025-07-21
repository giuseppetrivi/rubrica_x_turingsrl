import java.util.Map;
import java.util.regex.Pattern;

public class Person {

    /* Person attributes */
    private User user = null;
    private Integer id = null;
    private String name = null;
    private String surname = null;
    private String address = null;
    private String phone = null;
    private Integer age = null;


    /**
     * Construct <code>Person</code> instance passing all attributes by arguments
     */
    public Person(User user, Integer id, String name, String surname, String address, String phone, Integer age) {
        this.setUser(user);
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setAddress(address);
        this.setPhone(phone);
        this.setAge(age);
    }


    /**
     * Construct the <code>Person</code> instance by filling it with <code>person_data</code> values
     * @param person_data <code>Map</code> instance with all attributes inside it
     */
    public Person(Map<String, Object> person_data) {
        this.setUser((User) person_data.get("user"));
        this.setId((Integer) person_data.get("id"));
        this.setName((String) person_data.get("name"));
        this.setSurname((String) person_data.get("surname"));
        this.setAddress((String) person_data.get("address"));
        this.setPhone((String) person_data.get("phone"));
        this.setAge(Integer.parseInt((String) person_data.get("age")));
    }


    /* Getter and setter methods */
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return this.age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }


    /**
     * Get the formatted info to be used in a table row
     * @return <code>Object[]</code> containing the row data
     */
    public Object[] getTableRow() {
        return new Object[]{this.getId(), this.getName(), this.getSurname(), this.getPhone()};
    }


    /**
     * Static method to check the format of all <code>Person</code> fields
     * @param person_data map containing the data of the person
     * @throws Exception if a format check is not passing
     */
    public static void checkAllFields(Map<String, Object> person_data) throws Exception {
        String regex_name = "\\b([A-ZÀ-ÿa-z][-,a-z. ']+[ ]*){2,50}";
        Person.checkFieldValidity(
            regex_name,
            (String) person_data.get("name"),
            "Il formato del nome inserito non è valido"
        );
        Person.checkFieldValidity(
            regex_name,
            (String) person_data.get("surname"),
            "Il formato del cognome inserito non è valido"
        );
        Person.checkFieldValidity(
            "^[A-Za-zÀ-ÿ0-9\\s'.,\\-°/]{5,100}$",
            (String) person_data.get("address"),
            "Il formato dell'indirizzo inserito non è valido"
        );
        Person.checkFieldValidity(
            "^\\+?\\d{1,4}?[\\s\\-\\.]?\\(?\\d{1,5}\\)?[\\s\\-\\.]?\\d{1,5}[\\s\\-\\.]?\\d{1,6}[\\s\\-\\.]?\\d{0,6}$",
            (String) person_data.get("phone"),
            "Il formato del numero di telefono inserito non è valido"
        );
        Person.checkFieldValidity(
            "^(?:1[01][0-9]|150|[1-9]?[0-9])$",
            (String) person_data.get("age"),
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
     * Print all the <code>Person</code> attributes in pretty format (for debugging)
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
