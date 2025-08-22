import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Table extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private List<Password> data;
    private final String[] columns = {"Name", "Login", "Password", "Category"};

    public Table() {

        data = new ArrayList<>();

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 3;
            }

        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        setViewportView(table);

        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }

    public void add(Password password) {

        data.add(password);
        model.addRow(new Object[]{password.getName(), password.getLogin(), password.getPassword(), password.getCategory()});

    }

    public void remove() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {

            model.removeRow(selectedRow);
            Password toDelete = data.get(selectedRow);
            data.remove(toDelete);

        }

    }

    public void save(String key, String path) {

        String date = "";

        try {

            FileReader fileReader = new FileReader(path);
            Scanner scanner = new Scanner(fileReader);

            if (scanner.hasNextLine()) {
                date = scanner.nextLine();
            }

            fileReader.close();

        } catch (IOException e) {
            System.out.println("Table-save-reader");
        }

        try {

            FileWriter fileWriter = new FileWriter(path, false);

            if (!date.isEmpty()) {
                fileWriter.write(date + '\n');
            } else {
                fileWriter.write('\n');
            }

            for (int row = 0; row < model.getRowCount(); row++) {

                for (int col = 0; col < model.getColumnCount(); col++) {

                    if (col == 0 || col == 3) {
                        fileWriter.write(model.getValueAt(row, col).toString());
                    } else {
                        fileWriter.write(Utilities.encrypt(model.getValueAt(row, col).toString(), key));
                    }

                    fileWriter.write("|");

                }
                fileWriter.write("\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Table-Save-writer");
        }

    }

    public String load(String key, String path) {

        String date = null;
        try {
            FileReader fileReader = new FileReader(path);
            Scanner scanner = new Scanner(fileReader);

            date = scanner.nextLine();

            while (scanner.hasNextLine()) {

                String[] elements = scanner.nextLine().split("\\|");

                for (int i = 1; i < elements.length - 1; i++) {
                    elements[i] = Utilities.decrypt(elements[i], key);
                }

                data.add(new Password(elements[0], elements[1], elements[2], elements[3]));
                model.addRow(elements);
            }

            Utilities.date(path);
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Table-Load-Error");
        }

        return date;
    }

    public void sort(String howSort) {

        if (howSort.equals("asc")) {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        } else if (howSort.equals("des")) {
            sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
        } else {
            sorter.setSortKeys(null);
        }

    }

    public void filterCategory(String category) {

        model.setRowCount(0);

        switch (category) {
            case "All":
                for (Password p : data) {
                    model.addRow(new Object[]{p.getName(), p.getLogin(), p.getPassword(), p.getCategory()});
                }
                break;
            case "Other":
                for (Password p : data) {
                    if (!isKnownCategory(p.getCategory())) {
                        model.addRow(new Object[]{p.getName(), p.getLogin(), p.getPassword(), p.getCategory()});
                    }
                }
                break;
            default:
                for (Password p : data) {
                    if (p.getCategory().equals(category)) {
                        model.addRow(new Object[]{p.getName(), p.getLogin(), p.getPassword(), p.getCategory()});
                    }
                }
                break;
        }

    }

    private boolean isKnownCategory(String category) {
        return category.equals("Personal") || category.equals("Work-related") ||
                category.equals("Educational") || category.equals("Finance");
    }
    public void removeCategory(String category) {

        model.setRowCount(0);

        if (!category.equals("Other")){

            Iterator<Password> iterator = data.iterator();
            while (iterator.hasNext()) {

                Password p = iterator.next();
                if (p.getCategory().equals(category)){
                    iterator.remove();
                } else {
                    model.addRow(new Object[]{p.getName(), p.getLogin(), p.getPassword(), p.getCategory()});
                }

            }
        }else {

            Iterator<Password> iterator = data.iterator();
            while (iterator.hasNext()) {

                Password p = iterator.next();
                if (isKnownCategory(p.getCategory())){
                    model.addRow(new Object[]{p.getName(), p.getLogin(), p.getPassword(), p.getCategory()});
                } else {
                    iterator.remove();

                }
            }

        }

    }


}
