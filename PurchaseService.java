import java.util.Map;

public class PurchaseService {

    // متدی که CartPanel صدا می‌زند
    public static boolean finalizePurchase(Customer customer) {

        ShoppingCart cart = customer.getCart();
        double total = cart.getTotalPrice();

        // بررسی موجودی
        if (customer.getBalance() < total) {
            return false;
        }

        // کسر موجودی مشتری
        customer.deductBalance(total);

        // کاهش موجودی محصولات
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setStock(p.getStock() - qty);
        }

        // پاک کردن سبد خرید
        cart.clear();

        return true;
    }
}