import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

public class MenuFrame extends JFrame {

    //Components
    private JLabel title;
    private JButton newFile;
    private JButton loadFile;
    private JPanel panel;

    //Colors
    private final Color GREY = new Color(192, 192, 192);
    private final Color YELLOW = new Color(255, 234, 0);

    public MenuFrame() {

        super("File Chooser");
        panel = new JPanel();
        panel.setBackground(GREY);
        title = new JLabel("Password Manager");

        //New File
        newFile = new JButton("New File");
        newFile.setBackground(YELLOW);
        newFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String filePath = null;

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt" );
                fileChooser.setFileFilter(filter);

                fileChooser.setDialogTitle("Specify a file to save");
                if (fileChooser.showOpenDialog(MenuFrame.this) == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();

                    if (selectedFile.length() <= 0) {
                        dispose();
                        Frame frame = new Frame(false, null, filePath);
                    } else {
                        JOptionPane.showMessageDialog(null, "File must be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }


        });

        //Load File
        loadFile = new JButton("Load File");
        loadFile.setBackground(YELLOW);
        loadFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String filePath = null;

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt" );
                fileChooser.setFileFilter(filter);

                fileChooser.setDialogTitle("Specify a file to load");
                if (fileChooser.showOpenDialog(MenuFrame.this) == JFileChooser.APPROVE_OPTION) {

                    String key = null;
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();

                    if (selectedFile.length() <= 0) {
                        JOptionPane.showMessageDialog(null, "Incorrect file", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        key = (String) JOptionPane.showInputDialog("Enter master password");
                    }

                    if (key != null && key.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Incorrect master password", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        dispose();
                        Frame frame = new Frame(true, key, filePath);
                    }
                }

            }

        });


        setLayout();
        setVisible(true);
        setSize(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setLayout() {

        setLayout(new BorderLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(newFile, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loadFile, gbc);

        this.add(panel, BorderLayout.CENTER);

    }


}
