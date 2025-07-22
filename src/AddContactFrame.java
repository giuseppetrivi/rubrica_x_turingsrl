import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;


public class AddContactFrame extends ContactFrame {


    public AddContactFrame(AddressBookMainFrame parent_frame, DefaultTableModel table_model, User user) {
        super(parent_frame, table_model, user);
        setFrameProperties();
        setPanelFields();
        setPanelButtons();
        pack();
    }


    @Override
    protected String getFrameTitle() { return "Aggiungi contatto - Rubrica (by Giuseppe Trivisano)"; }


    @Override
    protected void buttonSaveActionListener(JButton button_save) {
        button_save.addActionListener(e -> {
            Map<String, Object> contact_data = new HashMap<>();
            contact_data.put("name", field_name.getText());
            contact_data.put("surname", field_surname.getText());
            contact_data.put("address", field_address.getText());
            contact_data.put("phone", field_phone.getText());
            contact_data.put("age", field_age.getText());

            /* preliminary check of all fields */
            try {
                Contact.checkAllFields(contact_data);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(AddContactFrame.this, exc.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Contact new_contact = this.user.insertNewContact(contact_data);
            if (new_contact != null) {
                table_model.addRow(new_contact.getTableRow());
                JOptionPane.showMessageDialog(AddContactFrame.this, "Contatto aggiunto!");
                dispose();
                parent_frame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(AddContactFrame.this, "Compila tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
