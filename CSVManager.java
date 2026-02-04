import java.io.*;
import java.util.*;

// این کلاس مسئول ذخیره‌سازی و بازیابی اطلاعات پروژه با استفاده از فایل CSV است
public class CSVManager {
    // ذخیره لیست محصولات در فایل products.csv
    public static void saveProducts(List<Product> products) {
        try (PrintWriter pw = new PrintWriter("products.csv")) {
            for (Product p : products) {
                pw.println(
                        p.getId() + "," +
                        p.getName() + "," +
                        p.getCategory() + "," +
                        p.getPrice() + "," +
                        p.getStock() + "," +
                        p.getDescription().replace(",", " ") + "," +
                        p.getImagePath()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // بارگذاری محصولات از فایل products.csv
    public static List<Product> loadProducts() {
        List<Product> list = new ArrayList<>();
        File file = new File("products.csv");
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 7) continue;

                list.add(new Product(
                        d[0],
                        d[1],
                        d[2],
                        Double.parseDouble(d[3]),
                        Integer.parseInt(d[4]),
                        d[5],
                        d[6]
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // ذخیره سبد خرید هر کاربر در فایل جداگانه
    public static void saveCart(ShoppingCart cart, String username) {
        try (PrintWriter pw = new PrintWriter("cart_" + username + ".csv")) {
            for (Map.Entry<Product, Integer> e : cart.getItems().entrySet()) {
                pw.println(e.getKey().getId() + "," + e.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // بارگذاری سبد خرید کاربر از فایل CSV
    public static void loadCart(ShoppingCart cart, List<Product> products, String username) {
        File file = new File("cart_" + username + ".csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                String id = d[0];
                int qty = Integer.parseInt(d[1]);

                // تطبیق شناسه محصول با لیست محصولات موجود
                for (Product p : products) {
                    if (p.getId().equals(id)) {
                        for (int i = 0; i < qty; i++) {
                            cart.addProduct(p);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // بارگذاری کاربر و احراز هویت از فایل users.csv
    public static User loadUser(String username, String password) {

        File file = new File("users.csv");

        // ایجاد ادمین پیش‌فرض در صورت نبود فایل
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("admin,admin,ADMIN,0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // بررسی اطلاعات ورود
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 4) continue;

                if (d[0].equals(username) && d[1].equals(password)) {

                    if (d[2].equalsIgnoreCase("ADMIN")) {
                        return new Admin(d[0], d[1]);
                    } else {
                        return new Customer(
                                d[0],
                                d[1],
                                Double.parseDouble(d[3])
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ثبت‌نام مشتری جدید
    public static boolean registerCustomer(
            String username,
            String password,
            double balance
    ) {
        File file = new File("users.csv");

        // ایجاد فایل و ادمین پیش‌فرض در صورت نبود
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("admin,admin,ADMIN,0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // بررسی تکراری نبودن نام کاربری
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d[0].equalsIgnoreCase(username)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ذخیره اطلاعات مشتری جدید
        try (FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(username + "," + password + ",CUSTOMER," + balance);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
