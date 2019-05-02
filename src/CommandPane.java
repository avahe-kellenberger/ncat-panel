import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * CommandPane is a panel with buttons to invoke ncat commands, with an output
 * console.
 */
public class CommandPane extends JPanel {
    private static final long serialVersionUID = -5550674321804283838L;

    private final JTextField txtLocalFilePath, txtRemoteFilePath;

    public CommandPane() {
        super();
        final SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(0, 400));

        final JButton btnWhoAmI = new JButton("whoami /all");
        btnWhoAmI.addActionListener(this::whoAmI);
        buttonsPanel.add(btnWhoAmI);

        final JButton btnSystemInfo = new JButton("systeminfo");
        btnSystemInfo.addActionListener(this::systemInfo);
        buttonsPanel.add(btnSystemInfo);

        final JButton btnSchTasks = new JButton("schtasks");
        btnSchTasks.addActionListener(this::schTasks);
        buttonsPanel.add(btnSchTasks);

        final JButton btnNetconfig = new JButton("net config workstation");
        btnNetconfig.addActionListener(this::netconfig);
        buttonsPanel.add(btnNetconfig);

        final JButton btnTaskList = new JButton("tasklist");
        btnTaskList.addActionListener(this::taskList);
        buttonsPanel.add(btnTaskList);

        final JButton btnIpconfig = new JButton("ipconfig /all");
        btnIpconfig.addActionListener(this::ipconfig);
        buttonsPanel.add(btnIpconfig);

        final JButton btnNetstat = new JButton("netstat -ano");
        btnNetstat.addActionListener(this::netstat);
        buttonsPanel.add(btnNetstat);

        layout.putConstraint(SpringLayout.NORTH, buttonsPanel, 4, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, buttonsPanel, 4, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, buttonsPanel, -4, SpringLayout.EAST, this);
        this.add(buttonsPanel);

        {
            final SpringLayout fileTransferLayout = new SpringLayout();
            final JPanel fileTransferPanel = new JPanel(fileTransferLayout);
            final JButton btnUpload = new JButton("Upload");
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnUpload, 4, SpringLayout.WEST, fileTransferPanel);
            btnUpload.addActionListener(this::upload);
            fileTransferPanel.add(btnUpload);

            final JButton btnDownload = new JButton("Download");
            btnDownload.addActionListener(this::download);
            fileTransferPanel.add(btnDownload);

            final JButton btnSelectLocalFile = new JButton("Select local file...");
            btnSelectLocalFile.addActionListener(this::selectLocalFile);
            fileTransferPanel.add(btnSelectLocalFile);

            this.txtLocalFilePath = new JTextField();
            fileTransferPanel.add(txtLocalFilePath);

            this.txtRemoteFilePath = new JTextField();
            fileTransferPanel.add(txtRemoteFilePath);

            layout.putConstraint(SpringLayout.NORTH, fileTransferPanel, 4, SpringLayout.SOUTH, buttonsPanel);
            this.add(fileTransferPanel);
        }

        // Bottom
        final JTextArea txtArea = new JTextArea();
        txtArea.setBackground(Color.GRAY);
        txtArea.setEnabled(false);
        txtArea.setText("Command Output Panel (2019):\n\n>\n");
        this.add(txtArea);
    }

    private void whoAmI(final ActionEvent e) {
        System.out.println("Test");
    }

    private void systemInfo(final ActionEvent e) {
        System.out.println("Test");
    }

    private void netconfig(final ActionEvent e) {
        System.out.println("Test");
    }

    private void schTasks(final ActionEvent e) {
        System.out.println("Test");
    }

    private void taskList(final ActionEvent e) {
        System.out.println("Test");
    }

    private void ipconfig(final ActionEvent e) {
        System.out.println("Test");
    }

    private void netstat(final ActionEvent e) {
        System.out.println("Test");
    }

    private void upload(final ActionEvent e) {
        System.out.println("Test");
    }

    private void download(final ActionEvent e) {
        System.out.println("Test");
    }

    private void selectLocalFile(final ActionEvent e) {
        System.out.println("Test");
    }

}
