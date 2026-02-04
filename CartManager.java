// این کلاس به عنوان واسط بین رابط کاربری و سبد خرید عمل می‌کند
// و عملیات اصلی مربوط به سبد خرید را مدیریت می‌کند
public class CartManager {

    // نمونه‌ای از سبد خرید که عملیات روی آن انجام می‌شود
    private ShoppingCart cart;

    // سازنده کلاس که سبد خرید را دریافت و مقداردهی می‌کند
    public CartManager(ShoppingCart cart) {
        this.cart = cart;
    }

    // اضافه کردن یک محصول به سبد خرید
    public void add(Product product) {
        cart.addProduct(product);
    }

    // حذف یک محصول از سبد خرید
    public void remove(Product product) {
        cart.removeProduct(product);
    }

    // محاسبه و برگرداندن مبلغ کل سبد خرید
    public double getTotal() {
        return cart.getTotalPrice();
    }
}
