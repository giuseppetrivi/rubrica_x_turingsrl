import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

abstract public class ContactFrame extends FormFrame {

    /* all fields components of the frame */
    protected JTextField field_name;
    protected JTextField field_surname;
    protected JTextField field_address;
    protected JTextField field_phone;
    protected JTextField field_age;

    /* reference to the parent frame and its table model */
    protected RubricaMainFrame parent_frame;
    protected DefaultTableModel table_model;
    protected User user;


    public ContactFrame(RubricaMainFrame parent_frame, DefaultTableModel table_model, User user) {
        this.parent_frame = parent_frame;
        this.table_model = table_model;
        this.user = user;
    }


    @Override
    protected void setFrameProperties() {
        setTitle(this.getFrameTitle());
        setSize(600, 400);
        setLocationRelativeTo(parent_frame);
        setResizable(false);
        /* when this window closes, make visible again the main window */
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent_frame.setVisible(true);
            }
        });
        setLayout(new BorderLayout());
    }


    @Override
    protected void setPanelFields() {
        JPanel panel_fields = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        createLabelAndField(panel_fields, gbc, 0, "Nome", field_name = new JTextField(FormFrame.FIELD_WIDTH));
        createLabelAndField(panel_fields, gbc, 1, "Cognome", field_surname = new JTextField(FormFrame.FIELD_WIDTH));
        createLabelAndField(panel_fields, gbc, 2, "Indirizzo", field_address = new JTextField(FormFrame.FIELD_WIDTH));
        createLabelAndField(panel_fields, gbc, 3, "Telefono", field_phone = new JTextField(FormFrame.FIELD_WIDTH));
        createLabelAndField(panel_fields, gbc, 4, "Età", field_age = new JTextField(FormFrame.FIELD_WIDTH));

        /* add panel to frame */
        add(panel_fields, BorderLayout.CENTER);
    }


    @Override
    protected void setPanelButtons() {
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button_save = new JButton("Salva");
        buttonSaveActionListener(button_save);
        JButton button_cancel = new JButton("Annulla");
        buttonCancelActionListener(button_cancel);
        /* add 2 buttons to panel */
        panel_buttons.add(button_save);
        panel_buttons.add(button_cancel);
        /* add panel to frame */
        add(panel_buttons, BorderLayout.SOUTH);
    }


    /**
     * Add the <code>ActionListener</code> to the save button
     * @param button_save <code>JButton</code> instance of save button
     */
    abstract protected void buttonSaveActionListener(JButton button_save);


    /**
     * Add the <code>ActionListener</code> to the cancel button
     * @param button_cancel <code>JButton</code> instance of cancel button
     */
    protected void buttonCancelActionListener(JButton button_cancel) {
        button_cancel.addActionListener(e -> {
            dispose();
            parent_frame.setVisible(true);
        });
    }

}
