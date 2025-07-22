import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;


public class AddressBookMainFrame extends JFrame {

    /* useful components of the frame */
    private final JTable table;
    private final DefaultTableModel table_model;

    /* User reference */
    private final LoginFrame login_frame;
    private final User user;


    public AddressBookMainFrame(LoginFrame login_frame, User user) {
        this.login_frame = login_frame;
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
        Vector<Contact> list_of_contacts = this.user.getAllUserContacts();
        for (Contact p : list_of_contacts) {
            table_model.addRow(p.getTableRow());
        }

        /* hide the first column (id) */
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        /* add table to scroll pane */
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        /* sets button panel with 4 buttons inside it */
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button_add = new JButton("Aggiungi");
        buttonAddActionListener(button_add);
        JButton button_edit = new JButton("Modifica");
        buttonEditActionListener(button_edit);
        JButton button_delete = new JButton("Elimina");
        buttonDeleteActionListener(button_delete);
        JButton button_logout = new JButton("Logout");
        buttonLogoutActionListener(button_logout);

        /* add 4 buttons to panel */
        panel_buttons.add(button_add);
        panel_buttons.add(button_edit);
        panel_buttons.add(button_delete);
        panel_buttons.add(button_logout);

        /* add panel to frame */
        add(panel_buttons, BorderLayout.SOUTH);
    }


    /**
     * Adds the <code>ActionListener</code> to the add button:
     * on click opens the AddContactFrame to add a new contact.
     * @param button_add <code>JButton</code> instance of add button
     */
    private void buttonAddActionListener(JButton button_add) {
        button_add.addActionListener(e -> {
            setVisible(false);

            FormFrame add_contact_frame = new AddContactFrame(AddressBookMainFrame.this, table_model, this.user);
            add_contact_frame.setVisible(true);
        });
    }


    /**
     * Adds the <code>ActionListener</code> to the edit button:
     * on click opens the EditContactFrame to edit the selected contact in table.
     * @param button_edit <code>JButton</code> instance of edit button
     */
    private void buttonEditActionListener(JButton button_edit) {
        button_edit.addActionListener(e -> {
            int selected_row = table.getSelectedRow();
            if (selected_row != -1) {
                setVisible(false);

                int contact_id = (int) table.getValueAt(selected_row, 0);
                Contact contact_to_edit = this.user.getContactById(contact_id);
                contact_to_edit.printPrettyInfo();

                FormFrame edit_contact_frame = new EditContactFrame(AddressBookMainFrame.this, table_model, this.user, contact_to_edit);
                edit_contact_frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(AddressBookMainFrame.this, "Seleziona una contatto da modificare.", "Avviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }


    /**
     * Adds the <code>ActionListener</code> to the delete button:
     * on click deletes the selected contact in table.
     * @param button_delete <code>JButton</code> instance of delete button
     */
    private void buttonDeleteActionListener(JButton button_delete) {
        button_delete.addActionListener(e -> {
            int selected_row = table.getSelectedRow();
            if (selected_row != -1) {
                int contact_id = (int) table.getValueAt(selected_row, 0);
                Contact contact = this.user.getContactById(contact_id);
                if (contact == null) {
                    JOptionPane.showMessageDialog(AddressBookMainFrame.this, "Errore nella ricerca del contatto. Riprova.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showOptionDialog(
                    null,
                    "Sei sicuro di voler eliminare il contatto di " + contact.getName() + " " + contact.getSurname() +"?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[] {"Si", "No"},
                    "No"
                );

                if (choice == JOptionPane.YES_OPTION) {
                    boolean contact_is_deleted = this.user.deleteContactById(contact_id);
                    if (contact_is_deleted) {
                        table_model.removeRow(selected_row);
                        JOptionPane.showMessageDialog(AddressBookMainFrame.this, "Contatto eliminato con successo!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(AddressBookMainFrame.this, "Seleziona un contatto da eliminare.", "Avviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }


    /**
     * Adds the <code>ActionListener</code> to the logout button:
     * on click goes to LoginFrame frame, to do again the login.
     * @param button_logout <code>JButton</code> instance of logout button
     */
    protected void buttonLogoutActionListener(JButton button_logout) {
        button_logout.addActionListener(e -> {
            dispose();
            login_frame.setVisible(true);
        });
    }


    /**
     * Getter method to use JTable properties outside the frame
     */
    public JTable getTable() { return this.table; }
}