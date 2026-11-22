import java.util.ArrayList;
import java.util.List;

/**
 * Лаб5, задание 2.
 * Класс Payment с внутренним классом Product, с помощью объектов
 * которого формируется покупка из нескольких товаров.
 * Также демонстрируется использование анонимного класса.
 */
public class Payment {

    private final String customer;
    private final List<Product> products = new ArrayList<>();

    public Payment(String customer) {
        this.customer = customer;
    }

    /** Внутренний класс: один товар в покупке. */
    public class Product {
        private final String name;
        private final double price;
        private final int quantity;

        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public double getTotal() {
            return price * quantity;
        }

        @Override
        public String toString() {
            return String.format("%-12s %8.2f x %d = %9.2f", name, price, quantity, getTotal());
        }
    }

    /** Добавление товара в покупку (создаётся объект внутреннего класса). */
    public void addProduct(String name, double price, int quantity) {
        products.add(new Product(name, price, quantity));
    }

    /** Общая стоимость покупки. */
    public double getTotal() {
        double sum = 0;
        for (Product p : products) {
            sum += p.getTotal();
        }
        return sum;
    }

    /** Вывод чека. */
    public void printReceipt() {
        System.out.println("Покупатель: " + customer);
        System.out.println("--------------------------------------------");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("--------------------------------------------");
        System.out.printf("Итого: %.2f%n", getTotal());
    }

    /** Интерфейс для демонстрации анонимного класса. */
    interface Discount {
        double apply(double total);
    }

    public static void main(String[] args) {
        Payment payment = new Payment("Иванов И.И.");
        payment.addProduct("Хлеб", 2.50, 2);
        payment.addProduct("Молоко", 3.20, 3);
        payment.addProduct("Сыр", 12.80, 1);
        payment.addProduct("Яблоки", 4.00, 5);

        payment.printReceipt();

        // Объект внутреннего класса можно создать и вне Payment
        Payment.Product extra = payment.new Product("Шоколад", 5.00, 2);
        System.out.println("\nОтдельный товар: " + extra);

        // Анонимный класс: скидка 10% при сумме больше 40
        Discount discount = new Discount() {
            @Override
            public double apply(double total) {
                return total > 40 ? total * 0.9 : total;
            }
        };

        double total = payment.getTotal();
        System.out.printf("%nСумма без скидки: %.2f%n", total);
        System.out.printf("Сумма со скидкой:  %.2f%n", discount.apply(total));
    }
}
