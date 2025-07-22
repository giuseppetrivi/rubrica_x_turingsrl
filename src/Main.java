import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        /* sets the default look and feel of "modern" Windows */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new LoginFrame().setVisible(true); }
        });
    }
}