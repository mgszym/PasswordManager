import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    //Master Key
    private String key;

    //Path
    private String path;

    //Components
    private Panel panel;
    private Table table;

    //Colors
    private final Color GREY = new Color(192, 192, 192);
    private final Color YELLOW = new Color(255, 234, 0);

    public Frame(boolean isLoading, String key, String path) {
        super("Passwords Manager");
        setLayout(new BorderLayout());
        setKey(key);
        setPath(path);


        //Panel
        panel = new Panel();
        add(panel, BorderLayout.SOUTH);
        panel.setAddListener(new AddListener() {

            public void add(AddEvent event) {

                Password p = event.getPassword();
                table.add(p);

            }

        });
        panel.setDelListener(new DeleteListener() {
            @Override
            public void delete() {
                table.remove();
            }
        });

        //Table
        table = new Table();
        if (isLoading) {
            String date = table.load(this.key, path);

            if (date.isEmpty()){
                date = "The file has not been decrypted yet";
            }

            JOptionPane.showMessageDialog(null, date, "Date of encrypted", JOptionPane.INFORMATION_MESSAGE);
        }
        add(table, BorderLayout.CENTER);
        table.setBackground(YELLOW);


        setJMenuBar(createMenu());
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar createMenu() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(GREY);
        menuBar.setBorder(BorderFactory.createLineBorder(YELLOW));

        //Options:
        JMenu optionsMenu = new JMenu("Options:");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (key == null) {
                    String tmp  = (String) JOptionPane.showInputDialog("Set master password");

                    if (tmp.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Incorrect master password", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        setKey(tmp);
                    }

                }

                table.save(key, path);

            }

        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        //Sort:
        JMenu sortMenu = new JMenu("Sort:");
        JMenuItem asc = new JMenuItem("Name-Ascending");
        JMenuItem des = new JMenuItem("Name-Descending");
        JMenuItem def = new JMenuItem("Default");
        asc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table.sort("asc");

            }

        });

        des.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table.sort("des");
            }

        });

        def.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table.sort("def");

            }

        });

        //Show:
        JMenu showMenu = new JMenu("Show:");
        JMenuItem personalFilter = new JMenuItem("Personal");
        JMenuItem workFilter = new JMenuItem("Work-related");
        JMenuItem educationalFilter = new JMenuItem("Educational");
        JMenuItem financeFilter = new JMenuItem("Finance");
        JMenuItem otherFilter = new JMenuItem("Other");
        JMenuItem allFilter = new JMenuItem("All");

        personalFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table.filterCategory("Personal");

            }

        });
        workFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table.filterCategory("Work-related");

            }

        });
        educationalFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.filterCategory("Educational");
            }
        });
        financeFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.filterCategory("Finance");
            }
        });
        otherFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.filterCategory("Other");
            }
        });
        allFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.filterCategory("All");
            }
        });

        //Remove:
        JMenu removeMenu = new JMenu("Remove:");
        JMenuItem personalRemove= new JMenuItem("Personal");
        JMenuItem workRemove= new JMenuItem("Work-related");
        JMenuItem educationalRemove = new JMenuItem("Educational");
        JMenuItem financeRemove = new JMenuItem("Finance");
        JMenuItem otherRemove= new JMenuItem("Other");

        personalRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeCategory("Personal");
            }

        });

        workRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeCategory("Work-related");
            }
        });

        educationalRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeCategory("Educational");
            }
        });

        financeRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeCategory("Finance");
            }
        });

        otherRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeCategory("Other");
            }
        });


        //Add
        optionsMenu.add(save);
        optionsMenu.addSeparator();
        optionsMenu.add(exit);

        sortMenu.add(asc);
        sortMenu.add(des);
        sortMenu.add(def);

        showMenu.add(personalFilter);
        showMenu.add(workFilter);
        showMenu.add(educationalFilter);
        showMenu.add(financeFilter);
        showMenu.add(otherFilter);
        showMenu.add(allFilter);

        removeMenu.add(personalRemove);
        removeMenu.add(workRemove);
        removeMenu.add(educationalRemove);
        removeMenu.add(financeRemove);
        removeMenu.add(otherRemove);

        menuBar.add(optionsMenu);
        menuBar.add(sortMenu);
        menuBar.add(showMenu);
        menuBar.add(removeMenu);

        return menuBar;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
