import javax.swing.*;
import java.awt.*;

// این پنل رابط اصلی مشتری است
// شامل مشاهده محصولات، افزودن به سبد خرید و مشاهده سبد خرید
public class CustomerPanel extends JPanel {

    // مدیر محصولات فروشگاه
    private ProductManager productManager;

    // مشتری واردشده به سیستم
    private Customer customer;

    // پنل نمایش محصولات
    private ProductPanel productPanel;

    // سازنده پنل مشتری
    public CustomerPanel(ProductManager productManager, Customer customer) {
        this.productManager = productManager;
        this.customer = customer;

        setLayout(new BorderLayout());

        // افزودن پنل محصولات به مرکز صفحه
        productPanel = new ProductPanel(productManager);
        add(productPanel, BorderLayout.CENTER);

        // دکمه افزودن محصول به سبد خرید
        JButton addBtn = new JButton("Add to Cart");

        // دکمه مشاهده سبد خرید
        JButton viewCartBtn = new JButton("View Cart");

        // رویداد افزودن محصول انتخاب‌شده به سبد خرید
        addBtn.addActionListener(e -> {
            Product selected = productPanel.getSelectedProduct();
            if (selected != null) {
                customer.getCart().addProduct(selected);

                // ذخیره سبد خرید پس از هر تغییر
                CSVManager.saveCart(
                        customer.getCart(),
                        customer.getUsername()
                );
            }
        });

        // رویداد نمایش پنجره سبد خرید
        viewCartBtn.addActionListener(e -> {
            JFrame f = new JFrame("Shopping Cart");
            f.setSize(400, 300);
            f.add(new CartPanel(customer));
            f.setVisible(true);
        });

        // پنل پایینی برای دکمه‌ها
        JPanel bottom = new JPanel();
        bottom.add(addBtn);
        bottom.add(viewCartBtn);

        add(bottom, BorderLayout.SOUTH);
    }
}
