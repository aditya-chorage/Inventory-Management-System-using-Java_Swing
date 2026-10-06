package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class InventoryGUI extends JFrame {

    private final ArrayList<Product> products = new ArrayList<>();

    private DefaultTableModel tableModel;
    private JTable table;

    private final JLabel totalProductsLabel = new JLabel("0");
    private final JLabel totalQuantityLabel = new JLabel("0");
    private final JLabel lowStockLabel = new JLabel("0");
    private final JLabel inventoryValueLabel = new JLabel("₹0.00");

    private final JLabel pageTitle = new JLabel("Dashboard");

    // Colors
    private static final Color SIDEBAR = new Color(31, 41, 55);
    private static final Color ACCENT = new Color(37, 99, 235);
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);

    public InventoryGUI() {

        setTitle("Inventory Management System");

        setSize(1150, 700);

        setMinimumSize(new Dimension(950, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // Load existing/sample products
        loadSampleData();

        buildUI();

        refreshTable();

        setVisible(true);
    }

    // =========================
    // MAIN GUI
    // =========================

    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout());

        root.setBackground(BACKGROUND);

        setContentPane(root);

        root.add(createSidebar(), BorderLayout.WEST);

        root.add(createMainPanel(), BorderLayout.CENTER);
    }

    // =========================
    // SIDEBAR
    // =========================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());

        sidebar.setPreferredSize(new Dimension(210, 700));

        sidebar.setBackground(SIDEBAR);

        sidebar.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 14, 18, 14
                )
        );

        // Logo / Brand

        JPanel brand = new JPanel(new BorderLayout());

        brand.setOpaque(false);

        JLabel icon = new JLabel("▣", SwingConstants.CENTER);

        icon.setFont(new Font("SansSerif", Font.BOLD, 30));

        icon.setForeground(Color.WHITE);

        icon.setPreferredSize(new Dimension(45, 45));

        JLabel brandText = new JLabel(
                "<html><b>INVENTORY</b><br>" +
                "<font size='2'>Management System</font></html>"
        );

        brandText.setForeground(Color.WHITE);

        brandText.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        brand.add(icon, BorderLayout.WEST);

        brand.add(brandText, BorderLayout.CENTER);

        // Menu

        JPanel menu = new JPanel(
                new GridLayout(0, 1, 0, 10)
        );

        menu.setOpaque(false);

        menu.setBorder(
                BorderFactory.createEmptyBorder(
                        35, 0, 0, 0
                )
        );

        JButton dashboardButton =
                sidebarButton("▦   Dashboard");

        JButton productsButton =
                sidebarButton("▣   Products");

        JButton searchButton =
                sidebarButton("⌕   Search");

        JButton lowStockButton =
                sidebarButton("!   Low Stock");

        JButton exitButton =
                sidebarButton("×   Exit");

        // Dashboard

        dashboardButton.addActionListener(e -> {

            pageTitle.setText("Dashboard");

            refreshTable();
        });

        // Products

        productsButton.addActionListener(e -> {

            pageTitle.setText("All Products");

            refreshTable();
        });

        // Search

        searchButton.addActionListener(e -> {

            searchProduct();
        });

        // Low Stock

        lowStockButton.addActionListener(e -> {

            showLowStock();
        });

        // Exit

        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        });

        menu.add(dashboardButton);
        menu.add(productsButton);
        menu.add(searchButton);
        menu.add(lowStockButton);
        menu.add(exitButton);

        sidebar.add(brand, BorderLayout.NORTH);

        sidebar.add(menu, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "<html><center>" +
                "Java Swing Project<br>" +
                "Inventory Management" +
                "</center></html>",
                SwingConstants.CENTER
        );

        footer.setForeground(
                new Color(156, 163, 175)
        );

        footer.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        sidebar.add(footer, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton sidebarButton(String text) {

        JButton button = new JButton(text);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setForeground(
                new Color(229, 231, 235)
        );

        button.setBackground(SIDEBAR);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 12, 12, 8
                )
        );

        button.setOpaque(true);

        return button;
    }

    // =========================
    // MAIN PANEL
    // =========================

    private JPanel createMainPanel() {

        JPanel main = new JPanel(
                new BorderLayout(0, 18)
        );

        main.setBackground(BACKGROUND);

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        22, 25, 22, 25
                )
        );

        // Header

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);

        pageTitle.setFont(
                new Font("SansSerif", Font.BOLD, 26)
        );

        pageTitle.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage products, stock levels and inventory value"
        );

        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        subtitle.setForeground(MUTED);

        JPanel heading = new JPanel(
                new GridLayout(2, 1)
        );

        heading.setOpaque(false);

        heading.add(pageTitle);

        heading.add(subtitle);

        JButton addButton =
                createPrimaryButton("+  Add Product");

        addButton.addActionListener(e -> {

            addProduct();
        });

        header.add(heading, BorderLayout.WEST);

        header.add(addButton, BorderLayout.EAST);

        // Center

        JPanel center = new JPanel(
                new BorderLayout(0, 18)
        );

        center.setOpaque(false);

        center.add(createCards(), BorderLayout.NORTH);

        center.add(createTablePanel(), BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);

        main.add(center, BorderLayout.CENTER);

        return main;
    }

    // =========================
    // DASHBOARD CARDS
    // =========================

    private JPanel createCards() {

        JPanel cards = new JPanel(
                new GridLayout(1, 4, 14, 0)
        );

        cards.setOpaque(false);

        cards.add(
                createCard(
                        "TOTAL PRODUCTS",
                        totalProductsLabel,
                        "Items in inventory"
                )
        );

        cards.add(
                createCard(
                        "TOTAL QUANTITY",
                        totalQuantityLabel,
                        "Units available"
                )
        );

        cards.add(
                createCard(
                        "LOW STOCK",
                        lowStockLabel,
                        "Quantity below 5"
                )
        );

        cards.add(
                createCard(
                        "INVENTORY VALUE",
                        inventoryValueLabel,
                        "Current stock value"
                )
        );

        return cards;
    }

    private JPanel createCard(
            String title,
            JLabel value,
            String description
    ) {

        JPanel card = new JPanel(
                new BorderLayout(0, 5)
        );

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(229, 231, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 17, 15, 17
                        )
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 11)
        );

        titleLabel.setForeground(MUTED);

        value.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        value.setForeground(ACCENT);

        JLabel desc = new JLabel(description);

        desc.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        desc.setForeground(MUTED);

        card.add(titleLabel, BorderLayout.NORTH);

        card.add(value, BorderLayout.CENTER);

        card.add(desc, BorderLayout.SOUTH);

        return card;
    }

    // =========================
    // TABLE
    // =========================

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(
                new BorderLayout(0, 10)
        );

        panel.setBackground(CARD);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(229, 231, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JPanel tableHeader = new JPanel(
                new BorderLayout()
        );

        tableHeader.setOpaque(false);

        JLabel label = new JLabel(
                "Product Inventory"
        );

        label.setFont(
                new Font("SansSerif", Font.BOLD, 17)
        );

        label.setForeground(TEXT);

        JPanel actions = new JPanel(
                new GridLayout(1, 3, 7, 0)
        );

        actions.setOpaque(false);

        JButton updateButton =
                smallButton("Edit");

        JButton deleteButton =
                smallButton("Delete");

        JButton refreshButton =
                smallButton("Refresh");

        updateButton.addActionListener(e -> {

            updateSelectedProduct();
        });

        deleteButton.addActionListener(e -> {

            deleteSelectedProduct();
        });

        refreshButton.addActionListener(e -> {

            pageTitle.setText("Dashboard");

            refreshTable();
        });

        actions.add(updateButton);
        actions.add(deleteButton);
        actions.add(refreshButton);

        tableHeader.add(
                label,
                BorderLayout.WEST
        );

        tableHeader.add(
                actions,
                BorderLayout.EAST
        );

        // Table columns

        String[] columns = {
                "ID",
                "Product Name",
                "Category",
                "Price",
                "Quantity",
                "Status"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {

                return false;
            }
        };

        table = new JTable(tableModel);

        table.setRowHeight(32);

        table.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        table.setForeground(TEXT);

        table.setGridColor(
                new Color(235, 238, 242)
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setSelectionBackground(
                new Color(219, 234, 254)
        );

        table.setSelectionForeground(TEXT);

        table.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        table.getTableHeader().setForeground(TEXT);

        table.getTableHeader().setBackground(
                new Color(243, 244, 246)
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 35)
        );

        // Center some columns

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(5)
                .setCellRenderer(center);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(229, 231, 235)
                )
        );

        panel.add(
                tableHeader,
                BorderLayout.NORTH
        );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================
    // BUTTONS
    // =========================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setBackground(ACCENT);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 18, 12, 18
                )
        );

        return button;
    }

    private JButton smallButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setBackground(Color.WHITE);

        button.setForeground(TEXT);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(209, 213, 219)
                )
        );

        return button;
    }

    // =========================
    // SAMPLE DATA
    // =========================

    private void loadSampleData() {

        products.add(
                new Product(
                        101,
                        "Arduino Uno",
                        "Electronics",
                        550,
                        15
                )
        );

        products.add(
                new Product(
                        102,
                        "HC-SR04 Sensor",
                        "Sensors",
                        120,
                        8
                )
        );

        products.add(
                new Product(
                        103,
                        "Jumper Wires",
                        "Accessories",
                        80,
                        25
                )
        );

        products.add(
                new Product(
                        104,
                        "Servo Motor",
                        "Motors",
                        180,
                        3
                )
        );

        products.add(
                new Product(
                        105,
                        "L293D Motor Driver",
                        "Electronics",
                        150,
                        4
                )
        );
    }

    // =========================
    // REFRESH TABLE
    // =========================

    private void refreshTable() {

        tableModel.setRowCount(0);

        int totalQuantity = 0;

        int lowStock = 0;

        double totalValue = 0;

        for (Product p : products) {

            String status =
                    p.getQuantity() < 5
                    ? "LOW STOCK"
                    : "IN STOCK";

            tableModel.addRow(
                    new Object[] {

                            p.getId(),

                            p.getName(),

                            p.getCategory(),

                            String.format(
                                    "₹%.2f",
                                    p.getPrice()
                            ),

                            p.getQuantity(),

                            status
                    }
            );

            totalQuantity +=
                    p.getQuantity();

            if (p.getQuantity() < 5) {

                lowStock++;
            }

            totalValue +=
                    p.getPrice()
                    * p.getQuantity();
        }

        totalProductsLabel.setText(
                String.valueOf(
                        products.size()
                )
        );

        totalQuantityLabel.setText(
                String.valueOf(
                        totalQuantity
                )
        );

        lowStockLabel.setText(
                String.valueOf(
                        lowStock
                )
        );

        inventoryValueLabel.setText(
                String.format(
                        "₹%.2f",
                        totalValue
                )
        );
    }

    // =========================
    // ADD PRODUCT
    // =========================

    private void addProduct() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Product ID:"
                );

        if (idText == null) {
            return;
        }

        int id;

        try {

            id = Integer.parseInt(
                    idText.trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid ID."
            );

            return;
        }

        // Check duplicate ID

        for (Product p : products) {

            if (p.getId() == id) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product ID already exists!"
                );

                return;
            }
        }

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Product Name:"
                );

        if (name == null ||
                name.trim().isEmpty()) {

            return;
        }

        String category =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Category:"
                );

        if (category == null ||
                category.trim().isEmpty()) {

            return;
        }

        String priceText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Price:"
                );

        if (priceText == null) {
            return;
        }

        double price;

        try {

            price = Double.parseDouble(
                    priceText.trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid price."
            );

            return;
        }

        if (price < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price cannot be negative."
            );

            return;
        }

        String quantityText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Quantity:"
                );

        if (quantityText == null) {
            return;
        }

        int quantity;

        try {

            quantity = Integer.parseInt(
                    quantityText.trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid quantity."
            );

            return;
        }

        if (quantity < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity cannot be negative."
            );

            return;
        }

        Product product =
                new Product(
                        id,
                        name.trim(),
                        category.trim(),
                        price,
                        quantity
                );

        products.add(product);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Product added successfully!"
        );
    }

    // =========================
    // GET SELECTED PRODUCT
    // =========================

    private Product getSelectedProduct() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first."
            );

            return null;
        }

        int id =
                Integer.parseInt(
                        tableModel
                                .getValueAt(row, 0)
                                .toString()
                );

        for (Product p : products) {

            if (p.getId() == id) {

                return p;
            }
        }

        return null;
    }

    // =========================
    // UPDATE
    // =========================

    private void updateSelectedProduct() {

        Product p =
                getSelectedProduct();

        if (p == null) {
            return;
        }

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new name:",
                        p.getName()
                );

        if (name == null ||
                name.trim().isEmpty()) {

            return;
        }

        String category =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new category:",
                        p.getCategory()
                );

        if (category == null ||
                category.trim().isEmpty()) {

            return;
        }

        String priceText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new price:",
                        p.getPrice()
                );

        if (priceText == null) {
            return;
        }

        double price;

        try {

            price =
                    Double.parseDouble(
                            priceText.trim()
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid price."
            );

            return;
        }

        if (price < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price cannot be negative."
            );

            return;
        }

        String quantityText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new quantity:",
                        p.getQuantity()
                );

        if (quantityText == null) {
            return;
        }

        int quantity;

        try {

            quantity =
                    Integer.parseInt(
                            quantityText.trim()
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid quantity."
            );

            return;
        }

        if (quantity < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity cannot be negative."
            );

            return;
        }

        p.setName(name.trim());

        p.setCategory(category.trim());

        p.setPrice(price);

        p.setQuantity(quantity);

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Product updated successfully!"
        );
    }

    // =========================
    // DELETE
    // =========================

    private void deleteSelectedProduct() {

        Product p =
                getSelectedProduct();

        if (p == null) {
            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete \""
                        + p.getName()
                        + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            products.remove(p);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Product deleted successfully!"
            );
        }
    }

    // =========================
    // SEARCH
    // =========================

    private void searchProduct() {

        String search =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Product ID or Product Name:"
                );

        if (search == null ||
                search.trim().isEmpty()) {

            return;
        }

        search =
                search.trim().toLowerCase();

        tableModel.setRowCount(0);

        boolean found = false;

        for (Product p : products) {

            if (
                String.valueOf(
                        p.getId()
                ).equals(search)

                ||

                p.getName()
                        .toLowerCase()
                        .contains(search)
            ) {

                String status =
                        p.getQuantity() < 5
                        ? "LOW STOCK"
                        : "IN STOCK";

                tableModel.addRow(
                        new Object[] {

                                p.getId(),

                                p.getName(),

                                p.getCategory(),

                                String.format(
                                        "₹%.2f",
                                        p.getPrice()
                                ),

                                p.getQuantity(),

                                status
                        }
                );

                found = true;
            }
        }

        pageTitle.setText(
                "Search Results"
        );

        if (!found) {

            JOptionPane.showMessageDialog(
                    this,
                    "No product found."
            );

            refreshTable();

            pageTitle.setText(
                    "Dashboard"
            );
        }
    }

    // =========================
    // LOW STOCK
    // =========================

    private void showLowStock() {

        tableModel.setRowCount(0);

        boolean found = false;

        for (Product p : products) {

            if (p.getQuantity() < 5) {

                tableModel.addRow(
                        new Object[] {

                                p.getId(),

                                p.getName(),

                                p.getCategory(),

                                String.format(
                                        "₹%.2f",
                                        p.getPrice()
                                ),

                                p.getQuantity(),

                                "LOW STOCK"
                        }
                );

                found = true;
            }
        }

        pageTitle.setText(
                "Low Stock Products"
        );

        if (!found) {

            JOptionPane.showMessageDialog(
                    this,
                    "No low-stock products."
            );

            refreshTable();

            pageTitle.setText(
                    "Dashboard"
            );
        }
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new InventoryGUI()
        );
    }
}