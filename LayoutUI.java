import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.stream.Stream;

public class LayoutUI extends JFrame {

    private DefaultTableModel productListModel;
    private DefaultTableModel tasksModel;
    private DefaultTableModel commentsModel;

    // Kolom untuk Tasks & Comments dipakai berulang di beberapa method
    private static final String[] TASK_COLUMNS = {
            "State", "Task", "Assigner", "Executer", "Creation Date", "Estimated Date", "Executed Date"
    };

    private static final String[] COMMENT_COLUMNS = {
            "Date", "Time", "User", "Comment"
    };

    public LayoutUI() {
        setTitle("Detail Proyek RNV-JKT-AXG-001");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(1000, 750));

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

        // 1. HEADER (Client, Information, Additional Info)
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        mainContentPanel.add(headerPanel);

        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(new JSeparator());
        mainContentPanel.add(Box.createVerticalStrut(10));

        // 2. PRODUCT LIST
        JPanel productListPanel = createProductListPanel();
        productListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        mainContentPanel.add(productListPanel);
        setupProductListListeners(productListPanel);

        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(new JSeparator());
        mainContentPanel.add(Box.createVerticalStrut(10));

        // 3. TASKS
        JPanel tasksPanel = createTasksPanel();
        tasksPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        mainContentPanel.add(tasksPanel);
        setupTasksListeners(tasksPanel);

        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(new JSeparator());
        mainContentPanel.add(Box.createVerticalStrut(10));

        // 4. COMMENTS
        JPanel commentsPanel = createCommentsPanel();
        commentsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        mainContentPanel.add(commentsPanel);
        setupCommentsListeners(commentsPanel);

        add(mainContentPanel, BorderLayout.CENTER);

        // BOTTOM BUTTONS
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton exportButton = new JButton("Export");
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(90, 48, 168));
        saveButton.setForeground(Color.WHITE);

        addActionListeners(exportButton, saveButton);

        buttonPanel.add(exportButton);
        buttonPanel.add(saveButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    // ================= HEADER / INFO =================

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        headerPanel.add(createClientPanel());
        headerPanel.add(createInformationPanel());
        headerPanel.add(createAdditionalInfoPanel());
        return headerPanel;
    }

    private JPanel createClientPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Client"));

        panel.add(new JLabel("Client ID: 101"));
        panel.add(new JLabel("Name: Bapak Alex Gunawan"));
        panel.add(new JLabel("Phone: (+62) 8123456789"));
        panel.add(new JLabel("Registration No: RNV-JKT-AXG-001"));
        panel.add(Box.createVerticalStrut(15));

        JButton detailsButton = new JButton("Details");
        detailsButton.setBackground(new Color(90, 48, 168));
        detailsButton.setForeground(Color.WHITE);
        detailsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Details Button Clicked!"));
        panel.add(detailsButton);

        return panel;
    }

    private JPanel createInformationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 3, 5, 3);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField buyerField = createEditableField("Bapak Alex Gunawan");
        JTextField sellerField = createEditableField("PT Bangun Jaya Abadi");
        JTextField addressField = createEditableField("Jl. Raya Lohbener Baru, 778A");
        JTextField reserveField = createEditableField("0 of 30");
        JTextField creditField = createEditableField("AAA");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0; gbc.gridwidth = 1;
        panel.add(new JLabel("Reserve days:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        reserveField.setPreferredSize(new Dimension(80, reserveField.getPreferredSize().height));
        panel.add(reserveField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        JButton addDaysButton = new JButton("Add Days");
        addDaysButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add Days Button Clicked!"));
        panel.add(addDaysButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Buyer:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panel.add(buyerField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Seller:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panel.add(sellerField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Credit rating:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        creditField.setPreferredSize(new Dimension(80, creditField.getPreferredSize().height));
        panel.add(creditField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        JButton sbpButton = new JButton("S&P Update");
        sbpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "S&P Update Button Clicked!"));
        panel.add(sbpButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; gbc.weightx = 1.0;
        JCheckBox approvedCheckBox = new JCheckBox("Approved: Proyek renovasi telah disetujui, siap dimulai");
        approvedCheckBox.setSelected(true);
        panel.add(approvedCheckBox, gbc);

        return panel;
    }

    private JTextField createEditableField(String text) {
        JTextField field = new JTextField(text);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        field.setBackground(Color.WHITE);
        return field;
    }

    // ================= ADDITIONAL INFORMATION =================

private JPanel createAdditionalInfoPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createTitledBorder("Additional Information"));

    // Menyiapkan data awal
    String[] labels = {"Estimated close:", "Creation date:", "Created by:", "Last edit date:", "Last edited by:",
     "Closed date:", "Closed by:"};
    String[] initialValues = {
        "2025-12-15", "2025-10-15", "Admin", "2025-11-16", "Warnoto", "null", "null"
    };

    // Mengatur panel untuk baris pertama (Estimated close)
    JPanel rowPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST; // Menjaga elemen ke kiri
    gbc.insets = new Insets(0, 0, 0, 0); // Tidak ada jarak antar elemen

    // Membuat JTextField untuk Estimated Close dan tombol Edit di sampingnya
    JTextField estimatedCloseField = new JTextField(initialValues[0]);
    estimatedCloseField.setEditable(false);  // Tidak dapat diedit secara langsung

    JButton editButton = new JButton("Edit");
    editButton.setBackground(new Color(90, 48, 168));
    editButton.setForeground(Color.WHITE);
    editButton.addActionListener(e -> toggleEditMode(new JTextField[]{estimatedCloseField}, editButton));

    // Menempatkan label, field, dan tombol dalam GridBagLayout
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.0; // Label berada di kiri
    rowPanel.add(new JLabel(labels[0]), gbc);  // Label "Estimated Close"

    gbc.gridx = 1;
    gbc.weightx = 1.0; // Field mengambil lebih banyak ruang
    rowPanel.add(estimatedCloseField, gbc);  // JTextField untuk tanggal

    gbc.gridx = 2;
    gbc.weightx = 0.0; // Tombol Edit
    rowPanel.add(editButton, gbc);  // Tombol Edit di sebelah kanan

    panel.add(rowPanel);

    // Menambahkan baris lainnya untuk informasi lainnya dengan jarak yang lebih kecil
    for (int i = 1; i < labels.length; i++) {
        rowPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weightx = 0.0; // Label tetap di kiri
        rowPanel.add(new JLabel(labels[i]), gbc);

        JTextField field = new JTextField(initialValues[i]);
        field.setEditable(false);  // Field lainnya tidak dapat diedit
        gbc.gridx = 1;
        gbc.weightx = 1.0; // Field mengambil lebih banyak ruang
        rowPanel.add(field, gbc);
        panel.add(rowPanel);
    }

    return panel;
}

private void toggleEditMode(JTextField[] fields, JButton editButton) {
    boolean isEditable = fields[0].isEditable();
    
    // Ubah status editable dari semua field
    for (JTextField field : fields) {
        field.setEditable(!isEditable);
    }

    // Ubah teks tombol tergantung status editable
    if (isEditable) {
        editButton.setText("Edit");
    } else {
        editButton.setText("Save");
    }
}



    // ================= PRODUCT LIST =================

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Product List"));

        String[] columnNames = {"Renovation", "Description", "Part #", "Quantity", "List Price", "Discount", 
        "Price", "Wholesale Discount", "Wholesaler Price"};
        Object[][] data = {
                {"Dapur", "Keramik Dinding Putih", "KW-PT-DLX-01", 50, 150000, 0, 7500000, 5, 7125000},
                {"Dapur", "Lem Keramik Instan", "LMI-GRY-STD", 5, 50000, 0, 250000, 10, 225000},
                {"Dapur", "Pipa PVC 3 inch", "PVC-3IN-STD", 12, 35000, 0, 420000, 10, 378000}
        };

        productListModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(productListModel);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton addProdButton = new JButton("Add");
        JButton editProdButton = new JButton("Edit");
        JButton deleteProdButton = new JButton("Delete");
        addActionListeners(addProdButton, editProdButton, deleteProdButton);
        buttonPanel.add(addProdButton);
        buttonPanel.add(editProdButton);
        buttonPanel.add(deleteProdButton);
        panel.add(buttonPanel, BorderLayout.EAST);

        // Panel total
        JPanel totalWrapperPanel = new JPanel(new BorderLayout());
        JPanel totalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.5;
        totalPanel.add(new JLabel("Subtotal list price:"), gbc);
        gbc.gridy = 1;
        totalPanel.add(new JLabel("Total retailer price:"), gbc);
        gbc.gridy = 2;
        totalPanel.add(new JLabel("Total wholesaler price:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;
        totalPanel.add(new JLabel("Rp 15.570.000"), gbc);
        gbc.gridy = 1;
        totalPanel.add(new JLabel("Rp 15.182.500"), gbc);
        gbc.gridy = 2;
        totalPanel.add(new JLabel("Rp 14.249.625"), gbc);

        totalWrapperPanel.add(totalPanel, BorderLayout.EAST);
        panel.add(totalWrapperPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void setupProductListListeners(JPanel productListPanel) {
        JPanel buttonPanel = (JPanel) productListPanel.getComponent(1);
        JButton addProdButton = (JButton) buttonPanel.getComponent(0);
        JButton editProdButton = (JButton) buttonPanel.getComponent(1);
        JButton deleteProdButton = (JButton) buttonPanel.getComponent(2);
        JTable table = ((JTable) ((JScrollPane) productListPanel.getComponent(0)).getViewport().getView());

        // Hapus listener placeholder
        Stream.of(addProdButton, editProdButton, deleteProdButton)
                .forEach(b -> {
                    for (ActionListener al : b.getActionListeners()) {
                        b.removeActionListener(al);
                    }
                });

        // ADD
        addProdButton.addActionListener(e -> addProduct(this, productListModel));

        // EDIT
        editProdButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                editProduct(this, productListModel, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
        });

        // DELETE
        deleteProdButton.addActionListener(e -> deleteData(this, productListModel, table.getSelectedRow()));
    }

    // --- LOGIKA ADD PRODUCT ---

    private void addProduct(JFrame frame, DefaultTableModel model) {
        String[] columnNames = {"Renovation", "Description", "Part #", "Quantity", "List Price", "Discount", 
        "Price", "Wholesale Discount", "Wholesaler Price"};

        ProductDialog dialog = new ProductDialog(frame, "Add New Product", columnNames, null);
        dialog.setVisible(true);

        String[] newValues = dialog.getValues();

        if (newValues != null) {
            Vector<Object> newRow = new Vector<>();
            if (Stream.of(newValues).anyMatch(String::isEmpty)) {
                JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Error Input", 
                JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                for (int i = 0; i < newValues.length; i++) {
                    if (i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) {
                        newRow.add(Double.parseDouble(newValues[i]));
                    } else {
                        newRow.add(newValues[i]);
                    }
                }

                model.addRow(newRow);
                JOptionPane.showMessageDialog(frame, "Product added successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Pastikan Quantity, Price, dan Discount adalah 
                angka yang valid.", "Error Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- LOGIKA EDIT PRODUCT ---

    private void editProduct(JFrame frame, DefaultTableModel model, int selectedRow) {
        String[] columnNames = {"Renovation", "Description", "Part #", "Quantity", "List Price", "Discount", "Price", "Wholesale Discount", "Wholesaler Price"};

        String[] currentValues = new String[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            Object value = model.getValueAt(selectedRow, i);
            currentValues[i] = (value == null) ? "" : value.toString();
        }

        ProductDialog dialog = new ProductDialog(frame, "Edit Product", columnNames, currentValues);
        dialog.setVisible(true);

        String[] newValues = dialog.getValues();

        if (newValues != null) {
            if (Stream.of(newValues).anyMatch(String::isEmpty)) {
                JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                for (int i = 0; i < newValues.length; i++) {
                    if (i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) {
                        model.setValueAt(Double.parseDouble(newValues[i]), selectedRow, i);
                    } else {
                        model.setValueAt(newValues[i], selectedRow, i);
                    }
                }
                JOptionPane.showMessageDialog(frame, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Pastikan Quantity, Price, dan Discount adalah angka yang valid.", "Error Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ================= TASKS =================

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        Object[][] data = {
                {"Completed", "Pemasangan pipa PVC di area garasi", "Warnoto", "Goang", "2025-10-20", "2025-10-21", "2025-10-21"},
                {"Completed", "Pengecatan ulang ruang tamu", "Warnoto", "Toni", "2025-10-25", "2025-10-28", "2025-10-27"},
                {"Delayed", "Pemasangan keramik dinding dapur", "Warnoto", "Toni", "2025-11-01", "2025-11-04", null}
        };
        tasksModel = new DefaultTableModel(data, TASK_COLUMNS);
        JTable table = new JTable(tasksModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton addTaskButton = new JButton("Add");
        JButton editTaskButton = new JButton("Edit");
        JButton deleteTaskButton = new JButton("Delete");
        addActionListeners(addTaskButton, editTaskButton, deleteTaskButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(editTaskButton);
        buttonPanel.add(deleteTaskButton);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void setupTasksListeners(JPanel tasksPanel) {
        JPanel buttonPanel = (JPanel) tasksPanel.getComponent(1);
        JButton addTaskButton = (JButton) buttonPanel.getComponent(0);
        JButton editTaskButton = (JButton) buttonPanel.getComponent(1);
        JButton deleteTaskButton = (JButton) buttonPanel.getComponent(2);
        JTable table = ((JTable) ((JScrollPane) tasksPanel.getComponent(0)).getViewport().getView());

        // Hapus listener placeholder
        Stream.of(addTaskButton, editTaskButton, deleteTaskButton)
                .forEach(b -> {
                    for (ActionListener al : b.getActionListeners()) {
                        b.removeActionListener(al);
                    }
                });

        // ADD TASK pakai dialog isi semua kolom
        addTaskButton.addActionListener(e -> addTask(this, tasksModel));

        // EDIT TASK pakai dialog isi semua kolom
        editTaskButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                editTask(this, tasksModel, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // DELETE TASK (pakai metode umum)
        deleteTaskButton.addActionListener(e -> deleteData(this, tasksModel, table.getSelectedRow()));
    }

    // ADD TASK – semua field diisi lewat dialog
    private void addTask(JFrame frame, DefaultTableModel model) {
        // Reuse ProductDialog sebagai dialog umum (label = kolom task)
        ProductDialog dialog = new ProductDialog(frame, "Add Task", TASK_COLUMNS, null);
        dialog.setVisible(true);

        String[] newValues = dialog.getValues();

        if (newValues != null) {
            // Validasi: semua field diisi
            if (Stream.of(newValues).anyMatch(v -> v.trim().isEmpty())) {
                JOptionPane.showMessageDialog(frame, "Semua field Task harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vector<Object> newRow = new Vector<>();
            for (String v : newValues) {
                newRow.add(v); // semua kolom Task kita simpan sebagai String
            }
            model.addRow(newRow);
            JOptionPane.showMessageDialog(frame, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EDIT TASK – semua field bisa diedit
    private void editTask(JFrame frame, DefaultTableModel model, int selectedRow) {
        String[] currentValues = new String[TASK_COLUMNS.length];
        for (int i = 0; i < TASK_COLUMNS.length; i++) {
            Object value = model.getValueAt(selectedRow, i);
            currentValues[i] = (value == null) ? "" : value.toString(); // hindari NullPointerException
        }

        ProductDialog dialog = new ProductDialog(frame, "Edit Task", TASK_COLUMNS, currentValues);
        dialog.setVisible(true);

        String[] newValues = dialog.getValues();

        if (newValues != null) {
            if (Stream.of(newValues).anyMatch(v -> v.trim().isEmpty())) {
                JOptionPane.showMessageDialog(frame, "Semua field Task harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < newValues.length; i++) {
                model.setValueAt(newValues[i], selectedRow, i);
            }
            JOptionPane.showMessageDialog(frame, "Task updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ================= COMMENTS =================

private JPanel createCommentsPanel() {
    JPanel panel = new JPanel(new BorderLayout(5, 5));
    panel.setBorder(BorderFactory.createTitledBorder("Comments"));

    Object[][] data = {
            {"2025-10-26", "Toni", "Cat tembok sudah diolesi lapisan pertama. Menunggu kering sebelum lapisan kedua."},
            {"2025-11-03", "Toni", "Cuaca hujan deras selama 2 hari, area kerja sedikit basah. Pemasangan ditunda besok pagi."},
            {"2025-11-15", "Goang", "Barang sudah sampai di lokasi. Mulai proses pembongkaran closet lama sore ini."}
    };
    commentsModel = new DefaultTableModel(data, new String[]{"Date", "User", "Comment"});
    JTable table = new JTable(commentsModel);
    panel.add(new JScrollPane(table), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    JButton addCommentButton = new JButton("Add");
    JButton editCommentButton = new JButton("Edit");
    JButton deleteCommentButton = new JButton("Delete");
    addActionListeners(addCommentButton, editCommentButton, deleteCommentButton);
    buttonPanel.add(addCommentButton);
    buttonPanel.add(editCommentButton);
    buttonPanel.add(deleteCommentButton);
    panel.add(buttonPanel, BorderLayout.EAST);

    return panel;
}

private void setupCommentsListeners(JPanel commentsPanel) {
    JPanel buttonPanel = (JPanel) commentsPanel.getComponent(1);
    JButton addCommentButton = (JButton) buttonPanel.getComponent(0);
    JButton editCommentButton = (JButton) buttonPanel.getComponent(1);
    JButton deleteCommentButton = (JButton) buttonPanel.getComponent(2);
    JTable table = ((JTable) ((JScrollPane) commentsPanel.getComponent(0)).getViewport().getView());

    // Hapus listener placeholder
    Stream.of(addCommentButton, editCommentButton, deleteCommentButton)
            .forEach(b -> {
                for (ActionListener al : b.getActionListeners()) {
                    b.removeActionListener(al);
                }
            });

    // ADD COMMENT pakai dialog lengkap
    addCommentButton.addActionListener(e -> addComment(this, commentsModel));

    // EDIT COMMENT pakai dialog lengkap
    editCommentButton.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            editComment(this, commentsModel, selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // DELETE COMMENT
    deleteCommentButton.addActionListener(e -> deleteData(this, commentsModel, table.getSelectedRow()));
}

// ADD COMMENT – semua field diisi lewat dialog (Date, User, Comment)
private void addComment(JFrame frame, DefaultTableModel model) {
    ProductDialog dialog = new ProductDialog(frame, "Add Comment", new String[]{"Date", "User", "Comment"}, null);
    dialog.setVisible(true);

    String[] newValues = dialog.getValues();

    if (newValues != null) {
        if (Stream.of(newValues).anyMatch(v -> v.trim().isEmpty())) {
            JOptionPane.showMessageDialog(frame, "Semua field Comment harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Vector<Object> newRow = new Vector<>();
        for (String v : newValues) {
            newRow.add(v);
        }
        model.addRow(newRow);
        JOptionPane.showMessageDialog(frame, "Comment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

// EDIT COMMENT – semua field bisa diedit
private void editComment(JFrame frame, DefaultTableModel model, int selectedRow) {
    String[] currentValues = new String[]{"", "", ""};
    for (int i = 0; i < 3; i++) {
        Object value = model.getValueAt(selectedRow, i);
        currentValues[i] = (value == null) ? "" : value.toString();
    }

    ProductDialog dialog = new ProductDialog(frame, "Edit Comment", new String[]{"Date", "User", "Comment"}, currentValues);
    dialog.setVisible(true);

    String[] newValues = dialog.getValues();

    if (newValues != null) {
        if (Stream.of(newValues).anyMatch(v -> v.trim().isEmpty())) {
            JOptionPane.showMessageDialog(frame, "Semua field Comment harus diisi!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < newValues.length; i++) {
            model.setValueAt(newValues[i], selectedRow, i);
        }
        JOptionPane.showMessageDialog(frame, "Comment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}


    // ================= METODE UMUM =================

    private void deleteData(JFrame frame, DefaultTableModel model, int selectedRow) {
        if (selectedRow >= 0) {
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(frame, "Row deleted successfully.", "Delete Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Masih ada metode editData umum (kalau mau dipakai di tempat lain)
    private void editData(JFrame frame, DefaultTableModel model, int selectedRow, String title) {
        Object currentValue = model.getValueAt(selectedRow, 0);

        Object input = JOptionPane.showInputDialog(
                frame,
                "Edit the value for '" + model.getColumnName(0) + "' in row " + (selectedRow + 1) + ":",
                "Edit " + title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                currentValue
        );

        if (input != null) {
            model.setValueAt(input.toString(), selectedRow, 0);
            JOptionPane.showMessageDialog(frame, "Row updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addActionListeners(JButton... buttons) {
        // Listener placeholder (nanti dihapus di setup*Listeners)
        ActionListener listener = e -> {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(this, source.getText() + " Button Clicked!", "Action Placeholder", JOptionPane.INFORMATION_MESSAGE);
        };

        Stream.of(buttons).forEach(button -> button.addActionListener(listener));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LayoutUI().setVisible(true));
    }
}


// ================= JDIALOG KUSTOM (UMUM) =================

class ProductDialog extends JDialog {
    private String[] values;
    private JTextField[] fields;
    private boolean saved = false;

    public ProductDialog(JFrame parent, String title, String[] labels, String[] initialValues) {
        super(parent, title, true); // modal=true
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel formPanel = new JPanel(new GridLayout(labels.length, 2, 10, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField(20);
            if (initialValues != null) {
                fields[i].setText(initialValues[i]);
            }
            formPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Jika Save ditekan, ambil semua isi text field
        saveButton.addActionListener(e -> {
            values = new String[labels.length];
            for (int i = 0; i < labels.length; i++) {
                values[i] = fields[i].getText();
            }
            saved = true;
            dispose();
        });

        // Cancel -> tidak simpan apa-apa
        cancelButton.addActionListener(e -> {
            values = null;
            dispose();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public String[] getValues() {
        return saved ? values : null;
    }
}
