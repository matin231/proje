import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

// این پنل مسئول نمایش لیست محصولات و جزئیات هر محصول است
// شامل جستجو، انتخاب محصول و نمایش تصویر و توضیحات
public class ProductPanel extends JPanel {

    // لیست کامل محصولات فروشگاه
    private List<Product> allProducts = new ArrayList<>();

    // لیست نمایشی محصولات
    private JList<Product> list = new JList<>();

    // فیلد جستجو
    private JTextField searchField = new JTextField();

    // اجزای نمایش جزئیات محصول
    private JLabel imageLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel priceLabel = new JLabel();
    private JLabel categoryLabel = new JLabel();
    private JTextArea descArea = new JTextArea();

    // سازنده پنل محصولات
    public ProductPanel(ProductManager pm) {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // دریافت تمام محصولات از مدیر محصولات
        allProducts.addAll(pm.getAllProducts());

        // پنل جستجو
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        JLabel searchLabel = new JLabel("Search:");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // لیست سمت چپ محصولات
        list.setListData(allProducts.toArray(new Product[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBorder(new TitledBorder("Products"));
        listScroll.setPreferredSize(new Dimension(220, 0));

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(searchPanel, BorderLayout.NORTH);
        left.add(listScroll, BorderLayout.CENTER);

        add(left, BorderLayout.WEST);

        // کارت نمایش جزئیات محصول
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        // محل نمایش تصویر محصول
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(260, 260));
        imageLabel.setBorder(new LineBorder(Color.GRAY));

        card.add(imageLabel, BorderLayout.NORTH);

        // نمایش اطلاعات اصلی محصول
        JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
        info.setOpaque(false);

        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        priceLabel.setForeground(new Color(0, 128, 0));
        categoryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        info.add(nameLabel);
        info.add(priceLabel);
        info.add(categoryLabel);

        card.add(info, BorderLayout.CENTER);

        // بخش توضیحات محصول
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descArea.setBorder(new TitledBorder("Description"));

        card.add(new JScrollPane(descArea), BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);

        // رویداد انتخاب محصول از لیست
        list.addListSelectionListener(e -> showDetails());

        // رویداد جستجو در محصولات
        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
                }
        );
    }

    // فیلتر کردن محصولات بر اساس نام یا دسته‌بندی
    private void filter() {
        String text = searchField.getText().toLowerCase();

        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(text)
                    || p.getCategory().toLowerCase().contains(text)) {
                filtered.add(p);
            }
        }
        list.setListData(filtered.toArray(new Product[0]));
    }

    // به‌روزرسانی لیست محصولات
    public void refresh(List<Product> products) {
        allProducts.clear();
        allProducts.addAll(products);
        list.setListData(allProducts.toArray(new Product[0]));
    }

    // نمایش جزئیات محصول انتخاب‌شده
    private void showDetails() {
        Product p = list.getSelectedValue();
        if (p == null) return;

        nameLabel.setText(p.getName());
        priceLabel.setText("Price: $" + p.getPrice());
        categoryLabel.setText("Category: " + p.getCategory());
        descArea.setText(p.getDescription());

        imageLabel.setIcon(null);

        // بارگذاری تصویر محصول در صورت وجود
        if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
            File f = new File(p.getImagePath());
            if (f.exists()) {
                ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                Image img = icon.getImage()
                        .getScaledInstance(260, 260, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            }
        }
    }

    // دریافت محصول انتخاب‌شده
    public Product getSelectedProduct() {
        return list.getSelectedValue();
    }
}
