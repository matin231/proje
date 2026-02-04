import javax.swing.*;
import java.awt.*;

// پنل مخصوص مدیر سیستم
// این پنل امکانات اضافه و ویرایش محصولات را در اختیار Admin قرار می‌دهد
public class AdminPanel extends JPanel {

    // مدیریت محصولات فروشگاه
    private ProductManager productManager;

    // پنل نمایش لیست محصولات
    private ProductPanel productPanel;

    // سازنده پنل مدیر که ProductManager را دریافت می‌کند
    public AdminPanel(ProductManager pm) {
        this.productManager = pm;
        setLayout(new BorderLayout());

        // ایجاد و اضافه کردن پنل محصولات به مرکز صفحه
        productPanel = new ProductPanel(pm);
        add(productPanel, BorderLayout.CENTER);

        // دکمه‌های افزودن و ویرایش محصول
        JButton addBtn = new JButton("Add Product");
        JButton editBtn = new JButton("Edit Product");

        // اتصال دکمه‌ها به متدهای مربوطه
        addBtn.addActionListener(e -> addProduct());
        editBtn.addActionListener(e -> editProduct());

        // پنل پایین صفحه برای دکمه‌ها
        JPanel bottom = new JPanel();
        bottom.add(addBtn);
        bottom.add(editBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    // باز کردن پنجره انتخاب تصویر برای محصول
    // مسیر فایل تصویر انتخاب‌شده برگردانده می‌شود
    private String chooseImage() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png"
        ));

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    // افزودن محصول جدید توسط مدیر
    private void addProduct() {

        // فیلدهای ورود اطلاعات محصول
        JTextField name = new JTextField();
        JTextField cat = new JTextField();
        JTextField price = new JTextField();
        JTextField stock = new JTextField();
        JTextArea desc = new JTextArea(3, 15);

        // نمایش مسیر تصویر انتخاب‌شده
        JLabel imgPath = new JLabel("No image selected");
        JButton imgBtn = new JButton("Choose Image");

        // انتخاب تصویر محصول
        imgBtn.addActionListener(e -> {
            String path = chooseImage();
            if (!path.isEmpty())
                imgPath.setText(path);
        });

        // پیام ورودی برای پنجره افزودن محصول
        Object[] msg = {
                "Name:", name,
                "Category:", cat,
                "Price:", price,
                "Stock:", stock,
                "Description:", new JScrollPane(desc),
                imgBtn, imgPath
        };

        // اگر مدیر تأیید کرد، محصول ساخته و ذخیره می‌شود
        if (JOptionPane.showConfirmDialog(this, msg,
                "Add Product", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {

            Product p = new Product(
                    String.valueOf(System.currentTimeMillis()),
                    name.getText(),
                    cat.getText(),
                    Double.parseDouble(price.getText()),
                    Integer.parseInt(stock.getText()),
                    desc.getText(),
                    imgPath.getText()
            );

            // افزودن محصول به سیستم
            productManager.addProduct(p);

            // ذخیره محصولات در فایل CSV
            CSVManager.saveProducts(productManager.getAllProducts());

            // به‌روزرسانی لیست محصولات در پنل
            productPanel.refresh(productManager.getAllProducts());
        }
    }

    // ویرایش محصول انتخاب‌شده
    private void editProduct() {

        // دریافت محصول انتخاب‌شده از لیست
        Product p = productPanel.getSelectedProduct();
        if (p == null) return;

        // مقداردهی اولیه فیلدها با اطلاعات فعلی محصول
        JTextField name = new JTextField(p.getName());
        JTextField cat = new JTextField(p.getCategory());
        JTextField price = new JTextField("" + p.getPrice());
        JTextField stock = new JTextField("" + p.getStock());
        JTextArea desc = new JTextArea(p.getDescription(), 3, 15);

        JLabel imgPath = new JLabel(p.getImagePath());
        JButton imgBtn = new JButton("Change Image");

        // تغییر تصویر محصول
        imgBtn.addActionListener(e -> {
            String path = chooseImage();
            if (!path.isEmpty())
                imgPath.setText(path);
        });

        Object[] msg = {
                "Name:", name,
                "Category:", cat,
                "Price:", price,
                "Stock:", stock,
                "Description:", new JScrollPane(desc),
                imgBtn, imgPath
        };

        // اعمال تغییرات در صورت تأیید مدیر
        if (JOptionPane.showConfirmDialog(this, msg,
                "Edit Product", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {

            p.setName(name.getText());
            p.setCategory(cat.getText());
            p.setPrice(Double.parseDouble(price.getText()));
            p.setStock(Integer.parseInt(stock.getText()));
            p.setDescription(desc.getText());
            p.setImagePath(imgPath.getText());

            // ذخیره تغییرات در فایل
            CSVManager.saveProducts(productManager.getAllProducts());

            // رفرش پنل نمایش محصولات
            productPanel.refresh(productManager.getAllProducts());
        }
    }
}
