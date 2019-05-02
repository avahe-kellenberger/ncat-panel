import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Boot {

    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            SwingUtilities.invokeAndWait(() -> {
                final GUI gui = new GUI("ncat panel");
                gui.setVisible(true);
            });
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }
    
}
