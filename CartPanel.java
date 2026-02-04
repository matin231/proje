import javax.swing.*;
import java.awt.*;
import java.util.Map;

// این پنل وظیفه نمایش سبد خرید مشتری را بر عهده دارد
// کاربر می‌تواند محصولات انتخاب‌شده را مشاهده، حذف یا خرید نهایی کند
public class CartPanel extends JPanel {

    // مشتری‌ای که این سبد خرید متعلق به اوست
    private Customer customer;

    // مدل لیست برای نمایش آیتم‌های سبد خرید
    private DefaultListModel<String> cartModel = new DefaultListModel<>();
    private JList<String> cartList = new JList<>(cartModel);

    // نمایش مبلغ کل و موجودی حساب مشتری
    private JLabel totalLabel = new JLabel();

    // سازنده پنل سبد خرید
    public CartPanel(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout());

        // بارگذاری اولیه اطلاعات سبد خرید
        refresh();

        // دکمه حذف محصول انتخاب‌شده
        JButton removeBtn = new JButton("Remove Selected");

        // دکمه نهایی‌سازی خرید
        JButton buyBtn = new JButton("Finalize Purchase");

        removeBtn.addActionListener(e -> removeSelected());
        buyBtn.addActionListener(e -> finalizePurchase());

        // پنل پایینی برای دکمه‌ها
        JPanel bottom = new JPanel();
        bottom.add(removeBtn);
        bottom.add(buyBtn);

        add(totalLabel, BorderLayout.NORTH);
        add(new JScrollPane(cartList), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // به‌روزرسانی لیست سبد خرید و اطلاعات مالی
    private void refresh() {
        cartModel.clear();

        // پیمایش محصولات موجود در سبد خرید
        for (Map.Entry<Product, Integer> e :
                customer.getCart().getItems().entrySet()) {

            Product p = e.getKey();
            int qty = e.getValue();

            // نمایش نام محصول، تعداد و قیمت کل آن
            cartModel.addElement(
                    p.getName() + " x" + qty +
                    " = " + (p.getPrice() * qty)
            );
        }

        // نمایش مجموع قیمت و موجودی حساب مشتری
        totalLabel.setText(
                "Total: " + customer.getCart().getTotalPrice() +
                " | Balance: " + customer.getBalance()
        );

        // ذخیره وضعیت سبد خرید در فایل CSV
        CSVManager.saveCart(
                customer.getCart(),
                customer.getUsername()
        );
    }

    // حذف محصول انتخاب‌شده از سبد خرید
    private void removeSelected() {
        int index = cartList.getSelectedIndex();
        if (index >= 0) {

            // پیدا کردن محصول متناظر با آیتم انتخاب‌شده
            Product p = (Product)
                    customer.getCart().getItems()
                            .keySet().toArray()[index];

            customer.getCart().removeProduct(p);
            refresh();
        }
    }

    // نهایی‌سازی فرآیند خرید
    private void finalizePurchase() {

        // بررسی موفق بودن خرید
        boolean ok = PurchaseService.finalizePurchase(customer);

        // نمایش پیام مناسب به کاربر
        JOptionPane.showMessageDialog(
                this,
                ok ? "Purchase completed" : "Not enough balance"
        );

        refresh();
    }
}
