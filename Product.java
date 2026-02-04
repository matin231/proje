// این کلاس نمایانگر یک محصول در فروشگاه است
// اطلاعات پایه و جزئیات هر محصول را نگهداری می‌کند
public class Product {

    // شناسه یکتای محصول
    private String id;

    // نام محصول
    private String name;

    // دسته‌بندی محصول
    private String category;

    // قیمت محصول
    private double price;

    // تعداد موجودی محصول
    private int stock;

    // توضیحات تکمیلی محصول
    private String description;

    // مسیر تصویر مربوط به محصول
    private String imagePath;

    // سازنده محصول برای مقداردهی تمام اطلاعات اولیه
    public Product(String id, String name, String category,
                   double price, int stock,
                   String description, String imagePath) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imagePath = imagePath;
    }

    // متدهای دریافت اطلاعات محصول
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }

    // متدهای تغییر اطلاعات محصول
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // نحوه نمایش محصول در لیست‌ها (مانند JList)
    @Override
    public String toString() {
        return name + " | " + price;
    }
}
