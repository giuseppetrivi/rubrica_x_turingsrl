import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AddContactFrame extends ContactFrame {


    public AddContactFrame(RubricaMainFrame parent_frame, DefaultTableModel table_model, User user) {
        super(parent_frame, table_model, user);
        setFrameProperties();
        setPanelFields();
        setPanelButtons();
        pack();
    }


    @Override
    protected String getFrameTitle() {
        return "Aggiungi contatto - Rubrica (by Giuseppe Trivisano)";
    }


    @Override
    protected void buttonSaveActionListener(JButton button_save) {
        button_save.addActionListener(e -> {
            Map<String, Object> person_data = new HashMap<>();
            person_data.put("name", field_name.getText());
            person_data.put("surname", field_surname.getText());
            person_data.put("address", field_address.getText());
            person_data.put("phone", field_phone.getText());
            person_data.put("age", field_age.getText());

            /* preliminary check of all fields */
            try {
                Person.checkAllFields(person_data);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(AddContactFrame.this, exc.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Person new_person = this.user.insertNewPerson(person_data);
            if (new_person != null) {
                table_model.addRow(new_person.getTableRow());
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
