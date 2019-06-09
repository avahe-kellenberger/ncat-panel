import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * CommandPane is a panel with buttons to invoke ncat commands, with an output
 * console.
 */
public class CommandPane extends JPanel {
    private static final long serialVersionUID = -5550674321804283838L;

    private Thread readThread = null;

    // TODO: Double check path
    private static final String ncatPath = "C:\\Program Files (x86)\\Nmap\\ncat.exe";

    private final CommandController controller;

    private final JTextField txtRemoteIPAddress, txtRemotePort, txtLocalFilePath, txtRemoteFilePath;
    private final JButton btnConnect, btnDisconnect, btnWhoAmI, btnSystemInfo, btnSchTasks, btnNetconfig, btnTaskList, btnIpconfig, btnNetstat, btnUpload, btnSelectLocalFile, btnDownload;

    private final JScrollPane commandOutputScrollPane;
    private final JTextArea txtCommandOutput;

    private Process process = null;
    private OutputStream processOutputStream = null;

    public CommandPane(final CommandController controller) {
        super();
        this.controller = controller;

        final SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        this.txtRemoteIPAddress = new JTextField(16);
        this.txtRemotePort = new JTextField(6);
        this.btnConnect = new JButton("Connect");
        this.btnConnect.addActionListener(e -> this.connect());

        this.btnDisconnect = new JButton("Disconnect");
        this.btnDisconnect.setEnabled(false);
        this.btnDisconnect.addActionListener(e -> this.disconnect());

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(0, 500));

        this.btnWhoAmI = new JButton(this.controller.whoAmI());
        this.btnWhoAmI.addActionListener(e -> this.runCommand(this.controller.whoAmI()));
        buttonsPanel.add(btnWhoAmI);

        this.btnSystemInfo = new JButton(this.controller.systemInfo());
        this.btnSystemInfo.addActionListener(e -> this.runCommand(this.controller.systemInfo()));
        buttonsPanel.add(btnSystemInfo);

        this.btnSchTasks = new JButton(this.controller.schTasks());
        this.btnSchTasks.addActionListener(e -> this.runCommand(this.controller.schTasks()));
        buttonsPanel.add(btnSchTasks);

        this.btnNetconfig = new JButton(this.controller.netconfig());
        this.btnNetconfig.addActionListener(e -> this.runCommand(this.controller.netconfig()));
        buttonsPanel.add(btnNetconfig);

        this.btnTaskList = new JButton(this.controller.taskList());
        this.btnTaskList.addActionListener(e -> this.runCommand(this.controller.taskList()));
        buttonsPanel.add(btnTaskList);

        this.btnIpconfig = new JButton(this.controller.ipconfig());
        this.btnIpconfig.addActionListener(e -> this.runCommand(this.controller.ipconfig()));
        buttonsPanel.add(btnIpconfig);

        this.btnNetstat = new JButton(this.controller.netstat());
        this.btnNetstat.addActionListener(e -> this.runCommand(this.controller.netstat()));
        buttonsPanel.add(btnNetstat);

        layout.putConstraint(SpringLayout.NORTH, buttonsPanel, 4, SpringLayout.SOUTH, this.txtRemoteIPAddress);
        layout.putConstraint(SpringLayout.SOUTH, buttonsPanel, 74, SpringLayout.SOUTH, btnNetstat);
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

        final JLabel lblPort = new JLabel("Port");
        layout.putConstraint(SpringLayout.NORTH, lblPort, 0, SpringLayout.NORTH, lblIPAddress);
        layout.putConstraint(SpringLayout.SOUTH, lblPort, 0, SpringLayout.SOUTH, lblIPAddress);
        layout.putConstraint(SpringLayout.WEST, lblPort, 8, SpringLayout.EAST, this.txtRemoteIPAddress);
        this.add(lblPort);

        layout.putConstraint(SpringLayout.NORTH, this.txtRemotePort, 0, SpringLayout.NORTH, this.txtRemoteIPAddress);
        layout.putConstraint(SpringLayout.SOUTH, this.txtRemotePort, 0, SpringLayout.SOUTH, this.txtRemoteIPAddress);
        layout.putConstraint(SpringLayout.WEST, this.txtRemotePort, 4, SpringLayout.EAST, lblPort);
        this.add(this.txtRemotePort);

        layout.putConstraint(SpringLayout.NORTH, this.btnConnect, 0, SpringLayout.NORTH, this.txtRemotePort);
        layout.putConstraint(SpringLayout.SOUTH, this.btnConnect, 0, SpringLayout.SOUTH, this.txtRemotePort);
        layout.putConstraint(SpringLayout.WEST, this.btnConnect, 8, SpringLayout.EAST, this.txtRemotePort);
        this.add(this.btnConnect);

        layout.putConstraint(SpringLayout.NORTH, this.btnDisconnect, 0, SpringLayout.NORTH, this.btnConnect);
        layout.putConstraint(SpringLayout.SOUTH, this.btnDisconnect, 0, SpringLayout.SOUTH, this.btnConnect);
        layout.putConstraint(SpringLayout.WEST, this.btnDisconnect, 8, SpringLayout.EAST, this.btnConnect);
        this.add(this.btnDisconnect);

        {
            final SpringLayout fileTransferLayout = new SpringLayout();
            final JPanel fileTransferPanel = new JPanel(fileTransferLayout);
            fileTransferPanel.setPreferredSize(new Dimension(0, 85));

            this.btnUpload = new JButton("Upload");
            this.txtLocalFilePath = new JTextField(24);
            this.txtLocalFilePath.setPreferredSize(new Dimension(0, 24));
            this.btnSelectLocalFile = new JButton("Select local file...");
            this.btnDownload = new JButton("Download");
            this.txtRemoteFilePath = new JTextField(24);
            this.txtRemoteFilePath.setPreferredSize(new Dimension(0, 24));

            // btnUpload
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnUpload, 0, SpringLayout.WEST, fileTransferPanel);
            fileTransferLayout.putConstraint(SpringLayout.EAST, btnUpload, 0, SpringLayout.EAST, this.txtLocalFilePath);
            this.btnUpload.addActionListener(e -> this.runCommand(this.controller.upload()));
            fileTransferPanel.add(btnUpload);

            // txtLocalFilePath
            fileTransferLayout.putConstraint(SpringLayout.WEST, this.txtLocalFilePath, 0, SpringLayout.WEST, btnUpload);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, this.txtLocalFilePath, 4, SpringLayout.SOUTH, btnUpload);
            fileTransferPanel.add(this.txtLocalFilePath);

            // btnSelectLocalFile
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnSelectLocalFile, 4, SpringLayout.EAST, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, btnSelectLocalFile, 0, SpringLayout.NORTH, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.SOUTH, btnSelectLocalFile, 0, SpringLayout.SOUTH, this.txtLocalFilePath);
            this.btnSelectLocalFile.addActionListener(e -> this.selectLocalFile());
            fileTransferPanel.add(btnSelectLocalFile);

            // btnDownload
            fileTransferLayout.putConstraint(SpringLayout.NORTH, btnDownload, 0, SpringLayout.NORTH, btnUpload);
            fileTransferLayout.putConstraint(SpringLayout.WEST, btnDownload, 0, SpringLayout.WEST, btnSelectLocalFile);
            fileTransferLayout.putConstraint(SpringLayout.EAST, btnDownload, 0, SpringLayout.EAST, btnSelectLocalFile);
            this.btnDownload.addActionListener(e -> this.runCommand(this.controller.download()));
            fileTransferPanel.add(btnDownload);

            // txtRemoteFilePath
            fileTransferLayout.putConstraint(SpringLayout.WEST, this.txtRemoteFilePath, 0, SpringLayout.WEST, this.txtLocalFilePath);
            fileTransferLayout.putConstraint(SpringLayout.NORTH, this.txtRemoteFilePath, 4, SpringLayout.SOUTH, this.txtLocalFilePath);
            fileTransferPanel.add(this.txtRemoteFilePath);

            // txtCommandOutput
            this.txtCommandOutput = new JTextArea();
            this.commandOutputScrollPane = new JScrollPane(this.txtCommandOutput);
            this.txtCommandOutput.setBackground(new Color(18, 18, 18));
            this.txtCommandOutput.setEnabled(false);
            this.txtCommandOutput.setText("Command Output Panel:\r\n\r\n>\r\n");
            layout.putConstraint(SpringLayout.WEST, this.commandOutputScrollPane, 0, SpringLayout.WEST, buttonsPanel);
            layout.putConstraint(SpringLayout.EAST, this.commandOutputScrollPane, 0, SpringLayout.EAST, buttonsPanel);
            layout.putConstraint(SpringLayout.NORTH, this.commandOutputScrollPane, 4, SpringLayout.SOUTH, fileTransferPanel);
            layout.putConstraint(SpringLayout.SOUTH, this.commandOutputScrollPane, -4, SpringLayout.SOUTH, this);
            this.add(this.commandOutputScrollPane);

            // fileTransferPanel
            layout.putConstraint(SpringLayout.NORTH, fileTransferPanel, 4, SpringLayout.SOUTH, buttonsPanel);
            layout.putConstraint(SpringLayout.WEST, fileTransferPanel, 0, SpringLayout.WEST, buttonsPanel);
            layout.putConstraint(SpringLayout.EAST, fileTransferPanel, 0, SpringLayout.EAST, buttonsPanel);
            this.add(fileTransferPanel);
        }

        this.setCommandComponentsEnabled(false);
    }

    /**
     * Enables or disables each component which requires a live netcat connection.
     * @param enabled If the components should be enabled.
     */
    private void setCommandComponentsEnabled(final boolean enabled) {
        this.btnNetstat.setEnabled(enabled);
        this.btnNetconfig.setEnabled(enabled);
        this.btnSchTasks.setEnabled(enabled);
        this.btnTaskList.setEnabled(enabled);
        this.btnUpload.setEnabled(enabled);
        this.btnDownload.setEnabled(enabled);
        this.btnIpconfig.setEnabled(enabled);
        this.btnSystemInfo.setEnabled(enabled);
        this.btnWhoAmI.setEnabled(enabled);
        this.btnSelectLocalFile.setEnabled(enabled);
        this.txtLocalFilePath.setEnabled(enabled);
        this.txtRemoteFilePath.setEnabled(enabled);
    }

    private void connect() {
        try {
            final ProcessBuilder processBuilder = new ProcessBuilder(CommandPane.ncatPath, this.txtRemoteIPAddress.getText(), this.txtRemotePort.getText());
            this.process = processBuilder.start();

            this.setCommandComponentsEnabled(true);
            this.btnConnect.setEnabled(false);
            this.btnDisconnect.setEnabled(true);

            this.processOutputStream = process.getOutputStream();

            this.readThread = new Thread(this::readStream);
            readThread.start();
        } catch (final Exception ex) {
            this.log(ex.getMessage());
            ex.printStackTrace();
        } finally {
            this.disconnect();
        }
    }

    private Runnable readStream() {
        return () -> {
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream()))) {
                this.log(reader.readLine());
            } catch (Exception ex) {
                this.log(ex.getMessage());
            }
        };
    }

    private void disconnect() {
        if (this.readThread != null && this.readThread.isAlive()) {
            this.readThread.interrupt();
            try {
                this.readThread.join();
            } catch (final Exception ignored) {}
        }

        if (this.process != null) {
            this.process.destroy();
            this.process = null;
        }

        if (this.processOutputStream != null) {
            try {
                this.processOutputStream.close();
            } catch (final IOException ignored) {
            }
            this.processOutputStream = null;
        }
        this.setCommandComponentsEnabled(false);
        this.btnConnect.setEnabled(true);
        this.btnDisconnect.setEnabled(false);
    }

    private void runCommand(final String command) {
        if (this.process == null || this.processOutputStream == null) {
            // TODO: Alert that connection is not alive
        } else {
            try {
                this.processOutputStream.write(command.getBytes());
            } catch (final IOException ex) {
                this.log(ex.getMessage());
            }
        }
    }

    private void selectLocalFile() {
        this.log("select local file");
    }

    private void log(final String text) {
        this.txtCommandOutput.setText(this.txtCommandOutput.getText() + text + "\r\n>\r\n");
    }

}
