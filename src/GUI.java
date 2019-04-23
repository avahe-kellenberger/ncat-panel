import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SpringLayout;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
   
    private final JButton btnWhoAmI, btnSystemInfo, btnSchTasks;

    /**
     * 
     * @param title
     */
    public GUI(final String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        final Container contentPane = this.getContentPane();
        final SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        // Top row
        this.btnWhoAmI = new JButton("whoami");
        this.btnWhoAmI.addActionListener(this::whoAmI);
        layout.putConstraint(SpringLayout.WEST, this.btnWhoAmI, 4, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, this.btnWhoAmI, 4, SpringLayout.NORTH, contentPane);
        contentPane.add(this.btnWhoAmI);
        
        this.btnSystemInfo = new JButton("systeminfo");
        this.btnSystemInfo.addActionListener(this::systemInfo);
        layout.putConstraint(SpringLayout.WEST, this.btnSystemInfo, 4, SpringLayout.EAST, this.btnWhoAmI);
        layout.putConstraint(SpringLayout.NORTH, this.btnSystemInfo, 4, SpringLayout.NORTH, contentPane);
        contentPane.add(this.btnSystemInfo);

        this.btnSchTasks = new JButton("schtasks");
        this.btnSchTasks.addActionListener(this::schTasks);
        layout.putConstraint(SpringLayout.WEST, this.btnSchTasks, 4, SpringLayout.EAST, this.btnSystemInfo);
        layout.putConstraint(SpringLayout.NORTH, this.btnSchTasks, 4, SpringLayout.NORTH, contentPane);
        contentPane.add(this.btnSchTasks);

    }

    private void whoAmI(final ActionEvent e) {
        System.out.println("Test");
    }
    
    private void systemInfo(final ActionEvent e) {
        System.out.println("Test");
    }
    
    private void schTasks(final ActionEvent e) {
        System.out.println("Test");
    }
    
}