import javax.swing.*;
import java.awt.*;


/* */
public class LoginFrame extends FormFrame {

    /* all fields components of the frame */
    private JTextField field_username;
    private JPasswordField field_password;


    public LoginFrame() {
        setFrameProperties();
        setPanelFields();
        setPanelButtons();
        pack();
    }


    @Override
    protected String getFrameTitle() {
        return "Login - Rubrica (by Giuseppe Trivisano)";
    }


    @Override
    protected void setFrameProperties() {
        /* settings of login frame */
        setTitle(this.getFrameTitle());
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }


    @Override
    protected void setPanelFields() {
        JPanel panel_fields = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        createLabelAndField(panel_fields, gbc, 0, "Username", field_username = new JTextField(FormFrame.FIELD_WIDTH));
        createLabelAndField(panel_fields, gbc, 1, "Password", field_password = new JPasswordField(FormFrame.FIELD_WIDTH));

        /* add panel to frame */
        add(panel_fields, BorderLayout.CENTER);
    }


    @Override
    protected void setPanelButtons() {
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button_login = new JButton("Login");
        buttonLoginActionListener(button_login);
        /* add button to panel */
        panel_buttons.add(button_login);
        /* add panel to frame */
        add(panel_buttons, BorderLayout.SOUTH);
    }


    /**
     * Add the <code>ActionListener</code> to the login button
     * @param button_login <code>JButton</code> instance of login button
     */
    private void buttonLoginActionListener(JButton button_login) {
        button_login.addActionListener(e -> {
            String username = field_username.getText();
            String password = String.valueOf(field_password.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                User user = User.verifyLoginData(username, password);
                if (user != null) {
                    dispose();
                    new RubricaMainFrame(user).setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "I dati di accesso non sono corretti!", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Compila tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}