import javax.swing.*;
import java.awt.*;

/**
 * Abstract class to define the base properties of label-fields-button frames
 */
abstract public class FormFrame extends JFrame {

    /* constants to be used in the label and field settings */
    protected static final int FIELD_WIDTH = 30;
    protected static final double LEFT_SIDE = 0.15;
    protected static final double RIGHT_SIDE = 0.85;


    /**
     * Get the frame title to set it dynamically for specific subclasses of <code>FormFrame</code>
     * @return dynamic frame title
     */
    abstract protected String getFrameTitle();


    /**
     * Set the base frame properties
     */
    abstract protected void setFrameProperties();


    /**
     * Set the panel containing labels and fields
     */
    abstract protected void setPanelFields();


    /**
     * Create a label-field couple of components, to put it into panel of fields
     * @param panel_fields panel to group the components
     * @param gbc <code>GridBagConstraints</code> instance to specify <code>GridBagLayout</code> properties
     * @param gridy <code>int</code> to specify the label-text pair
     * @param label_name label name
     * @param field <code>JTextField</code> instance
     */
    protected void createLabelAndField(JPanel panel_fields, GridBagConstraints gbc, int gridy, String label_name, JTextField field) {
        gbc.gridy = gridy;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = FormFrame.LEFT_SIDE;
        gbc.fill = GridBagConstraints.NONE;
        panel_fields.add(new JLabel(label_name + ":"), gbc);
        gbc.gridx = 1;
        gbc.weightx = FormFrame.RIGHT_SIDE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel_fields.add(field, gbc);
    }


    /**
     * Set the panel containing the final buttons of the form
     */
    abstract protected void setPanelButtons();

}