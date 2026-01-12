package project_akhir.project1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        MenuService menuService = new MenuService();
        PaymentService paymentService = new PaymentService();
        DiscountService discountService = new DiscountService();

        double total = 0;
        boolean repeat = true;

        System.out.println("=== Selamat Datang di Kantin ===");

        while (repeat) {
            total += menuService.showMenuAndChoose(input);

            System.out.print("\nApakah ingin memilih menu lagi? (y/n): ");
            String pilih = input.next();

            if (!pilih.equalsIgnoreCase("y")) {
                repeat = false;
            }
        }

        System.out.println("\nMenampilkan ringkasan keranjang sebelum pembayaran...");
        menuService.printCartSummary();

        // Terapkan diskon sebelum pembayaran
        total = discountService.applyDiscount(total, input);

        paymentService.processPayment(total, input);
    }
}
