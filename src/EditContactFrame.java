import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

public class EditContactFrame extends ContactFrame {

    protected Contact contact_to_edit;


    public EditContactFrame(AddressBookMainFrame parent_frame, DefaultTableModel table_model, User user, Contact contact) {
        super(parent_frame, table_model, user);
        this.contact_to_edit = contact;
        setFrameProperties();
        setPanelFields();
        setFieldsValues(); /* fill the fields with data of contact to edit */
        setPanelButtons();
        pack();
    }


    @Override
    protected String getFrameTitle() { return "Modifica contatto - Rubrica (by Giuseppe Trivisano)"; }


    private void setFieldsValues() {
        if (contact_to_edit != null) {
            field_name.setText(contact_to_edit.getName());
            field_surname.setText(contact_to_edit.getSurname());
            field_address.setText(contact_to_edit.getAddress());
            field_phone.setText(contact_to_edit.getPhone());
            field_age.setText(Integer.toString(contact_to_edit.getAge()));
        }
    }


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
                JOptionPane.showMessageDialog(EditContactFrame.this, exc.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Contact edited_contact = this.user.editContactById(contact_to_edit.getId(), contact_data);
            if (edited_contact != null) {
                int selected_row = parent_frame.getTable().getSelectedRow();
                table_model.setValueAt(edited_contact.getTableRow()[0], selected_row, 0);
                table_model.setValueAt(edited_contact.getTableRow()[1], selected_row, 1);
                table_model.setValueAt(edited_contact.getTableRow()[2], selected_row, 2);
                table_model.setValueAt(edited_contact.getTableRow()[3], selected_row, 3);

                JOptionPane.showMessageDialog(EditContactFrame.this, "Contatto modificato!");
                dispose();
                parent_frame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(EditContactFrame.this, "Compila tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
