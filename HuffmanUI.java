import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class HuffmanUI extends JFrame {
    private JTextField inputPathField;
    private JTextField outputPathField;
    private JButton browseInputButton;
    private JButton browseOutputButton;
    private JButton compressButton;
    private JButton decompressButton;
    private JLabel statusLabel;
    private Font baseFont = new Font("SansSerif", Font.PLAIN, 16);

    public HuffmanUI() {
        UIManager.put("FileChooser.listFont", new Font("SansSerif", Font.PLAIN, 28));
        UIManager.put("FileChooser.font", new Font("SansSerif", Font.PLAIN, 28));
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 28));
        UIManager.put("Button.font", new Font("SansSerif", Font.PLAIN, 28));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 28));

        setTitle("Huffman Compressor / Decompressor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        inputPathField = new JTextField();
        outputPathField = new JTextField();
        browseInputButton = new JButton("Browse");
        browseOutputButton = new JButton("Browse");
        compressButton = new JButton("Compress");
        decompressButton = new JButton("Decompress");
        statusLabel = new JLabel("Status: Waiting for user...");

        setFontAll(baseFont);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Row 0
        JLabel inputLabel = new JLabel("Input File:");
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // 👈 Larger, bold font
        gbc.gridx = 0; gbc.gridy = 0;
        add(inputLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        add(inputPathField, gbc);
        gbc.gridx = 2; gbc.weightx = 0;
        add(browseInputButton, gbc);

        // Row 1
        JLabel outputLabel = new JLabel("Output File:");
        outputLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // 👈 Adjust as needed
        gbc.gridx = 0; gbc.gridy = 1;
        add(outputLabel, gbc);
        gbc.gridx = 1;
        add(outputPathField, gbc);
        gbc.gridx = 2;
        add(browseOutputButton, gbc);

        // Row 2 - Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(compressButton);
        buttonPanel.add(decompressButton);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3; gbc.weightx = 1;
        add(buttonPanel, gbc);

        // Row 3 - Status
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(statusLabel, gbc);

        // Listeners
        browseInputButton.addActionListener(e -> chooseFile(inputPathField));
        browseOutputButton.addActionListener(e -> chooseSaveFile(outputPathField));
        compressButton.addActionListener(e -> compressFile());
        decompressButton.addActionListener(e -> decompressFile());

        // Dynamic font resize
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int size = Math.max(12, getWidth() / 100);
                setFontAll(new Font("SansSerif", Font.PLAIN, size));
                revalidate();
            }
        });

        setVisible(true);
    }

    private void setFontAll(Font font) {
        inputPathField.setFont(font);
        outputPathField.setFont(font);
        browseInputButton.setFont(font);
        browseOutputButton.setFont(font);
        compressButton.setFont(font);
        decompressButton.setFont(font);
        statusLabel.setFont(font);
    }
    
    private void chooseFile(JTextField targetField) {
    JFileChooser fileChooser = new JFileChooser();
    
    // Create a larger dialog
    JDialog dialog = new JDialog(this, "Select Input File", true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.getContentPane().add(fileChooser);
    dialog.setSize(1000, 700); // 👈 Increase size here
    dialog.setLocationRelativeTo(this);

    fileChooser.addActionListener(e -> {
        if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            targetField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            dialog.dispose();
        } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
            dialog.dispose();
        }
    });

    dialog.setVisible(true);
}


    private void chooseSaveFile(JTextField targetField) {
    JFileChooser fileChooser = new JFileChooser();

    JDialog dialog = new JDialog(this, "Select Output File", true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.getContentPane().add(fileChooser);
    dialog.setSize(1000, 700); // 👈 Larger size
    dialog.setLocationRelativeTo(this);

    fileChooser.addActionListener(e -> {
        if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            targetField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            dialog.dispose();
        } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
            dialog.dispose();
        }
    });

    dialog.setVisible(true);
}

private String readableSize(long bytes) {
    if (bytes >= 1024 * 1024)
        return String.format("%.2f MB", bytes / (1024.0 * 1024));
    else
        return String.format("%.2f KB", bytes / 1024.0);
}


    private void compressFile() {
    String input = inputPathField.getText();
    String output = outputPathField.getText();
    if (input.isEmpty() || output.isEmpty()) {
        showError("Please select input and output files.");
        return;
    }
    try {
        File inputFile = new File(input);
        long originalSize = inputFile.length();

        long startTime = System.currentTimeMillis();

        HuffmanCompressor compressor = new HuffmanCompressor();
        compressor.compress(input, output);

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        File outputFile = new File(output);
        long compressedSize = outputFile.length();

        double ratio = (double) compressedSize / originalSize * 100;

        String originalReadable = readableSize(originalSize);
        String compressedReadable = readableSize(compressedSize);

        String message = String.format(
    "Compression Completed!\n\n" +
    "- Original Size: %s\n" +
    "- Compressed Size: %s\n" +
    "- Compression Ratio: %.2f%%\n" +
    "- Time Taken: %d ms",
    originalReadable,
    compressedReadable,
    ratio,
    timeTaken
);



        statusLabel.setText("Status: Compression successful.");
        JOptionPane.showMessageDialog(this, message, "Compression Report", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        e.printStackTrace();
        showError("Compression failed: " + e.getMessage());
    }
}


    private void decompressFile() {
        String input = inputPathField.getText();
        String output = outputPathField.getText();
        if (input.isEmpty() || output.isEmpty()) {
            showError("Please select input and output files.");
            return;
        }
        try {
            HuffmanDecompressor decompressor = new HuffmanDecompressor();
            decompressor.decompress(input, output);
            statusLabel.setText("Status: Decompression completed.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Decompression failed: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("Status: " + msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HuffmanUI::new);
    }
}
