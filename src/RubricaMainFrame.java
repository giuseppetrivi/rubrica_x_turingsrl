import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;


/*  */
public class RubricaMainFrame extends JFrame {

    /* useful components of the frame */
    private final JTable table;
    private final DefaultTableModel table_model;

    /* User reference */
    private final User user;


    public RubricaMainFrame(User user) {
        this.user = user;

        /* settings of main frame */
        setTitle("Homepage - Rubrica (by Giuseppe Trivisano)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        /* creation of the contacts table */
        String[] columns = {"Id", "Nome", "Cognome", "Telefono"};
        table_model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(table_model);

        /* insert list of contacts in the table */
        Vector<Person> list_of_contacts = this.user.getAllUserContacts();
        for (Person p : list_of_contacts) {
            table_model.addRow(p.getTableRow());
        }

        /* hide the first column (id) */
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        /* add table to scroll pane */
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        /* sets button panel with 3 buttons inside it */
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button_add = new JButton("Aggiungi");
        buttonAddActionListener(button_add);
        JButton button_edit = new JButton("Modifica");
        buttonEditActionListener(button_edit);
        JButton button_delete = new JButton("Elimina");
        buttonDeleteActionListener(button_delete);

        /* add 3 buttons to panel */
        panel_buttons.add(button_add);
        panel_buttons.add(button_edit);
        panel_buttons.add(button_delete);

        /* add panel to frame */
        add(panel_buttons, BorderLayout.SOUTH);
    }


    /* Sets the ActionListener of the button_add */
    private void buttonAddActionListener(JButton button_add) {
        button_add.addActionListener(e -> {
            setVisible(false);

            FormFrame add_contact_frame = new AddContactFrame(RubricaMainFrame.this, table_model, this.user);
            add_contact_frame.setVisible(true);
        });
    }


    /* Sets the ActionListener of the button_edit */
    private void buttonEditActionListener(JButton button_edit) {
        button_edit.addActionListener(e -> {
            int selected_row = table.getSelectedRow();
            if (selected_row != -1) {
                setVisible(false);

                int person_id = (int) table.getValueAt(selected_row, 0);
                Person person_to_edit = this.user.getPersonById(person_id);
                person_to_edit.printPrettyInfo();

                FormFrame edit_contact_frame = new EditContactFrame(RubricaMainFrame.this, table_model, this.user, person_to_edit);
                edit_contact_frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(RubricaMainFrame.this, "Seleziona una contatto da modificare.", "Avviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }


    /* Sets the ActionListener of the button_delete */
    private void buttonDeleteActionListener(JButton button_delete) {
        // TODO: aggiungere messaggio di conferma
        button_delete.addActionListener(e -> {
            int selected_row = table.getSelectedRow();
            if (selected_row != -1) {
                int person_id = (int) table.getValueAt(selected_row, 0);
                Person person = this.user.getPersonById(person_id);
                if (person == null) {
                    //throw new Exception("Error in the retrieval of the person with id=" + person_id);
                    JOptionPane.showMessageDialog(RubricaMainFrame.this, "Errore nella ricerca del contatto. Riprova.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showOptionDialog(
                    null,
                    "Sei sicuro di voler eliminare il contatto di " + person.getName() + " " + person.getSurname() +"?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[] {"Si", "No"},
                    "No"
                );

                if (choice == JOptionPane.YES_OPTION) {
                    boolean person_is_deleted = this.user.deletePersonById(person_id);
                    if (person_is_deleted) {
                        table_model.removeRow(selected_row);
                        JOptionPane.showMessageDialog(RubricaMainFrame.this, "Contatto eliminato con successo!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(RubricaMainFrame.this, "Seleziona un contatto da eliminare.", "Avviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }


    /* Getter method to use JTable properties outside the frame */
    public JTable getTable() {
        return this.table;
    }
}