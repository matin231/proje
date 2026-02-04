import java.util.*;
import java.util.stream.Collectors;

// این کلاس مسئول مدیریت محصولات فروشگاه است
// شامل افزودن، حذف، جستجو و مرتب‌سازی محصولات
public class ProductManager {

    // لیست اصلی محصولات
    private List<Product> products = new ArrayList<>();

    // افزودن محصول جدید
    public void addProduct(Product p) {
        products.add(p);
    }

    // حذف یک محصول
    public void removeProduct(Product p) {
        products.remove(p);
    }

    // به‌روزرسانی اطلاعات محصول (در حال حاضر فقط موجودی)
    public void updateProduct(Product p, String name, String category, double price, int stock) {
        p.setStock(stock);
    }

    // دریافت لیست کامل محصولات
    public List<Product> getAllProducts() {
        return products;
    }

    // جستجوی محصول بر اساس نام
    public List<Product> searchByName(String name) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // جستجوی محصول بر اساس دسته‌بندی
    public List<Product> searchByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // مرتب‌سازی محصولات بر اساس نام
    public List<Product> sortByName() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    // مرتب‌سازی محصولات بر اساس دسته‌بندی
    public List<Product> sortByCategory() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getCategory))
                .collect(Collectors.toList());
    }

    // مرتب‌سازی محصولات بر اساس قیمت
    public List<Product> sortByPrice() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }
}
