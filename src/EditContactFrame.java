import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EditContactFrame extends ContactFrame {

    protected Person person_to_edit;


    public EditContactFrame(RubricaMainFrame parent_frame, DefaultTableModel table_model, User user, Person person) {
        super(parent_frame, table_model, user);
        this.person_to_edit = person;
        setFrameProperties();
        setPanelFields();
        setFieldsValues(); /* fill the fields with person to edit data */
        setPanelButtons();
        pack();
    }


    @Override
    protected String getFrameTitle() {
        return "Modifica contatto - Rubrica (by Giuseppe Trivisano)";
    }


    private void setFieldsValues() {
        if (person_to_edit != null) {
            field_name.setText(person_to_edit.getName());
            field_surname.setText(person_to_edit.getSurname());
            field_address.setText(person_to_edit.getAddress());
            field_phone.setText(person_to_edit.getPhone());
            field_age.setText(Integer.toString(person_to_edit.getAge()));
        }
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
                JOptionPane.showMessageDialog(EditContactFrame.this, exc.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Person edited_person = this.user.editPersonById(person_to_edit.getId(), person_data);
            if (edited_person != null) {
                int selected_row = parent_frame.getTable().getSelectedRow();
                table_model.setValueAt(edited_person.getId(), selected_row, 0);
                table_model.setValueAt(edited_person.getName(), selected_row, 1);
                table_model.setValueAt(edited_person.getSurname(), selected_row, 2);
                table_model.setValueAt(edited_person.getAddress(), selected_row, 3);

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
