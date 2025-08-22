import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel {

    private AddListener addListener;
    private DeleteListener delListener;

    //Components
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JLabel loginLabel;
    private JLabel categoryLabel;
    private JTextField nameField;
    private JTextField passwordField;
    private JTextField loginField;
    private JComboBox<String> categoryList;
    private JTextField otherCategory;
    private JButton addButton;
    private JButton passwordGenerator;
    private JButton delButton;

    //Colors
    private final Color GREY = new Color(192, 192, 192);
    private final Color YELLOW = new Color(255, 234, 0);


    Panel() {

        Dimension dimension = new Dimension(0, 150);
        setPreferredSize(dimension);

        //Name
        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(15);

        //Login
        loginLabel = new JLabel("Login:");
        loginField = new JTextField(15);

        //Password
        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(15);

        //Category
        categoryLabel = new JLabel("Category:");
        categoryList = new JComboBox<>(new String[]{"Personal", "Work-related", "Educational", "Finance", "Other"});
        categoryList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (categoryList.getSelectedIndex() == 4){
                    otherCategory.setEnabled(true);
                    otherCategory.setBackground(Color.WHITE);
                }else {
                    otherCategory.setEnabled(false);
                    otherCategory.setBackground(GREY);
                }

            }

        });

        otherCategory = new JTextField(10);
        otherCategory.setEnabled(false);
        otherCategory.setBackground(GREY);

        //Add
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String login = loginField.getText();
                String password = passwordField.getText();

                if (!name.isEmpty() && !login.isEmpty() && ! password.isEmpty()) {
                    String category = (categoryList.getSelectedIndex() == 4) ? otherCategory.getText() : categoryList.getItemAt(categoryList.getSelectedIndex());

                    Password p = new Password(name, login, password, category);
                    AddEvent event = new AddEvent(this, p);

                    nameField.setText("");
                    otherCategory.setText("");
                    loginField.setText("");
                    passwordField.setText("");

                    if (addListener != null) {
                        addListener.add(event);
                    }
                }

            }

        });

        //Generator
        passwordGenerator = new JButton("Generate");
        passwordGenerator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                passwordField.setText(Utilities.generatePassword());

            }

        });

        //Delete
        delButton = new JButton("Delete");
        delButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (delListener != null){
                    delListener.delete();
                }

            }

        });


        setColors();
        setLayout();

    }

    public void setLayout() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        //Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        //Name Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameField, gbc);

        //Login Label
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(loginLabel, gbc);

        //Login Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(loginField, gbc);

        //Password Label
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(passwordLabel, gbc);

        //Password Field
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(passwordField, gbc);

        //Category Label
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(categoryLabel, gbc);

        //Category List
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(categoryList, gbc);

        //Other Category
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(otherCategory, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;

        // Add Button
        gbc.gridx = 4;
        gbc.gridy = 0;
        add(addButton, gbc);

        // Password Generator Button
        gbc.gridx = 4;
        gbc.gridy = 1;
        add(passwordGenerator, gbc);

        // Clear Button
        gbc.gridx = 4;
        gbc.gridy = 2;
        add(delButton, gbc);

    }

    public void setColors() {

        setBackground(GREY);

        nameField.setBorder(BorderFactory.createLineBorder(YELLOW, 2));

        loginField.setBorder(BorderFactory.createLineBorder(YELLOW, 2));

        passwordField.setBorder(BorderFactory.createLineBorder(YELLOW, 2));

        otherCategory.setBorder(BorderFactory.createLineBorder(YELLOW, 2));

        addButton.setBackground(YELLOW);

        passwordGenerator.setBackground(YELLOW);

        delButton.setBackground(YELLOW);

        setBorder(BorderFactory.createLineBorder(YELLOW));
    }

    public void setAddListener(AddListener addListener) {
        this.addListener = addListener;
    }

    public void setDelListener(DeleteListener delListener) {
        this.delListener = delListener;
    }

}
