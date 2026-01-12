package project_akhir.project1;

import java.util.Scanner;

public class PaymentService {

    public void processPayment(double total, Scanner input) {
        System.out.println("\n=== Pembayaran ===");
        System.out.println("Total belanja: Rp" + total);
        System.out.println("1. Tunai");
        System.out.println("2. Non Tunai");
        System.out.print("Pilih metode pembayaran: ");
        int pilih = input.nextInt();

        if (pilih == 2) {
            System.out.println("Pembayaran non-tunai berhasil! Terima kasih.");
            return;
        }

        System.out.print("Masukkan uang Anda: Rp");
        double uang = input.nextDouble();

        if (uang < total) {
            System.out.println("Uang Anda kurang: Rp. " + (total - uang));
        } else {
            System.out.println("Kembalian Anda: Rp. " + (uang - total));
        }

        System.out.println("Terima kasih telah membeli!");
    }
}
