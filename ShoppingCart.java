import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// این کلاس نمایانگر سبد خرید کاربر است
// اطلاعات محصولات انتخاب‌شده و تعداد آن‌ها را نگهداری می‌کند
public class ShoppingCart implements Serializable {

    // نگهداری محصولات و تعداد هر محصول در سبد خرید
    private Map<Product, Integer> items = new HashMap<>();

    // افزودن محصول به سبد خرید
    // در صورتی که موجودی محصول بیشتر از صفر باشد
    public void addProduct(Product p) {
        if (p.getStock() > 0) {
            items.put(p, items.getOrDefault(p, 0) + 1);
        }
    }

    // حذف کامل یک محصول از سبد خرید
    public void removeProduct(Product p) {
        items.remove(p);
    }

    // محاسبه قیمت کل سبد خرید
    public double getTotalPrice() {
        double sum = 0;

        // پیمایش تمام محصولات و محاسبه مجموع قیمت‌ها
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            sum += e.getKey().getPrice() * e.getValue();
        }

        return sum;
    }

    // دریافت لیست محصولات موجود در سبد خرید
    public Map<Product, Integer> getItems() {
        return items;
    }

    // خالی کردن کامل سبد خرید
    public void clear() {
        items.clear();
    }
}
