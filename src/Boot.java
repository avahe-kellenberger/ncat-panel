import javax.swing.SwingUtilities;

public class Boot {

    public static void main(final String[] args) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                final GUI gui = new GUI("ncat panel");
                gui.setVisible(true);
            });
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }
    
}
