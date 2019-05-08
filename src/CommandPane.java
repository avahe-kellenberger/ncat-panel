import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
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

    private final CommandController controller;

    private final JTextField txtRemoteIPAddress, txtLocalFilePath, txtRemoteFilePath;
    private final JTextArea txtCommandOutput;

    public CommandPane(final CommandController controller) {
        super();
        this.controller = controller;

        final SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        this.txtRemoteIPAddress = new JTextField(16);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(0, 500));

        final JButton btnWhoAmI = new JButton("whoami /all");
        btnWhoAmI.addActionListener(e -> this.controller.whoAmI());
        buttonsPanel.add(btnWhoAmI);

        final JButton btnSystemInfo = new JButton("systeminfo");
        btnSystemInfo.addActionListener(e -> this.controller.systemInfo());
        buttonsPanel.add(btnSystemInfo);

        final JButton btnSchTasks = new JButton("schtasks");
        btnSchTasks.addActionListener(e -> this.controller.schTasks());
        buttonsPanel.add(btnSchTasks);

        final JButton btnNetconfig = new JButton("net config workstation");
        btnNetconfig.addActionListener(e -> this.controller.netconfig());
        buttonsPanel.add(btnNetconfig);

        final JButton btnTaskList = new JButton("tasklist");
        btnTaskList.addActionListener(e -> this.controller.taskList());
        buttonsPanel.add(btnTaskList);

        final JButton btnIpconfig = new JButton("ipconfig /all");
        btnIpconfig.addActionListener(e -> this.controller.ipconfig());
        buttonsPanel.add(btnIpconfig);

        final JButton btnNetstat = new JButton("netstat -ano");
        btnNetstat.addActionListener(e -> this.controller.netstat());
        buttonsPanel.add(btnNetstat);

        layout.putConstraint(SpringLayout.NORTH, buttonsPanel, 4, SpringLayout.SOUTH, this.txtRemoteIPAddress);
        layout.putConstraint(SpringLayout.SOUTH, buttonsPanel, 72, SpringLayout.SOUTH, btnNetstat);
        layout.putConstraint(SpringLayout.WEST, buttonsPanel, 4, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, buttonsPanel, -4, SpringLayout.EAST, this);
        this.add(buttonsPanel);

        final JLabel lblIPAddress = new JLabel("Remote IP:");
        layout.putConstraint(SpringLayout.NORTH, lblIPAddress, 4, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, lblIPAddress, 4, SpringLayout.WEST, this);
        this.add(lblIPAddress);

        layout.putConstraint(SpringLayout.NORTH, this.txtRemoteIPAddress, 4, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, this.txtRemoteIPAddress, 4, SpringLayout.EAST, lblIPAddress);
        this.add(this.txtRemoteIPAddress);
        {
            final SpringLayout fileTransferLayout = new SpringLayout();
            final JPanel fileTransferPanel = new JPanel(fileTransferLayout);
            fileTransferPanel.setPreferredSize(new Dimension(0, 85));

            final JButton btnUpload = new JButton("Upload");
            this.txtLocalFilePath = new JTextField(24);
            this.txtLocalFilePath.setPreferredSize(new Dimension(0, 24));
            final JButton btnSelectLocalFile = new JButton("Select local file...");
            final JButton btnDownload = new JButton("Download");
            this.txtRemoteFilePath = new JTextField(24);
            this.txtRemoteFilePath.setPreferredSize(new Dimension(0, 24));

            // btnUpload
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnUpload, 0, SpringLayout.WEST, fileTransferPanel);
            fileTransferLayout.putConstraint(SpringLayout.EAST, btnUpload, 0, SpringLayout.EAST, this.txtLocalFilePath);
            btnUpload.addActionListener(e -> this.controller.upload());
            fileTransferPanel.add(btnUpload);

            // txtLocalFilePath
            fileTransferLayout.putConstraint(SpringLayout.WEST, this.txtLocalFilePath, 0, SpringLayout.WEST, btnUpload);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, this.txtLocalFilePath, 4, SpringLayout.SOUTH, btnUpload);
            fileTransferPanel.add(this.txtLocalFilePath);

            // btnSelectLocalFile
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnSelectLocalFile, 4, SpringLayout.EAST, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, btnSelectLocalFile, 0, SpringLayout.NORTH, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.SOUTH, btnSelectLocalFile, 0, SpringLayout.SOUTH, this.txtLocalFilePath);
            btnSelectLocalFile.addActionListener(e -> this.selectLocalFile());
            fileTransferPanel.add(btnSelectLocalFile);

            // btnDownload
            fileTransferLayout.putConstraint(SpringLayout.NORTH, btnDownload, 0, SpringLayout.NORTH, btnUpload);
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnDownload, 0, SpringLayout.WEST, btnSelectLocalFile);
            fileTransferLayout.putConstraint(SpringLayout.EAST, btnDownload, 0, SpringLayout.EAST, btnSelectLocalFile);
            btnDownload.addActionListener(e -> this.controller.download());
            fileTransferPanel.add(btnDownload);

            // txtRemoteFilePath
            fileTransferLayout.putConstraint(SpringLayout.WEST, this.txtRemoteFilePath, 0, SpringLayout.WEST, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, this.txtRemoteFilePath, 4, SpringLayout.SOUTH, this.txtLocalFilePath);
            fileTransferPanel.add(this.txtRemoteFilePath);

            // txtCommandOutput
            txtCommandOutput = new JTextArea();
            txtCommandOutput.setBackground(new Color(18, 18, 18));
            txtCommandOutput.setEnabled(false);
            txtCommandOutput.setText("Command Output Panel (2019):\r\n\r\n>\r\n");
            layout.putConstraint(SpringLayout.WEST, txtCommandOutput, 0, SpringLayout.WEST, buttonsPanel);
            layout.putConstraint(SpringLayout.EAST, txtCommandOutput, 0, SpringLayout.EAST, buttonsPanel);
            layout.putConstraint(SpringLayout.NORTH, txtCommandOutput, 4, SpringLayout.SOUTH, fileTransferPanel);
            layout.putConstraint(SpringLayout.SOUTH, txtCommandOutput, -4, SpringLayout.SOUTH, this);
            this.add(txtCommandOutput);

            // fileTransferPanel
            layout.putConstraint(SpringLayout.NORTH, fileTransferPanel, 4, SpringLayout.SOUTH, buttonsPanel);
            layout.putConstraint(SpringLayout.WEST, fileTransferPanel, 0, SpringLayout.WEST, buttonsPanel);
            layout.putConstraint(SpringLayout.EAST, fileTransferPanel, 0, SpringLayout.EAST, buttonsPanel);
            this.add(fileTransferPanel);
        }
    }

    private void selectLocalFile() {
        this.log("select local file");
    }

    private void log(final String text) {
        this.txtCommandOutput.setText(this.txtCommandOutput.getText() + text + "\r\n>\r\n");
    }

}
