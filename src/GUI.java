import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JTabbedPane tabbedPane;

    /**
     * 
     * @param title
     */
    public GUI(final String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        final Container contentPane = this.getContentPane();
        this.tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);
        this.addTab(new CommandPane(new WindowsCommandController()));
    }

    private void addTab(final CommandPane tab) {
        this.tabbedPane.addTab("PLACEHOLDER", tab);
    }

}
