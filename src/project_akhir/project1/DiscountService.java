package project_akhir.project1;

import java.util.Scanner;

public class DiscountService {
    private double automaticDiscount = 0.0; // nilai potongan (rupiah)
    private double couponDiscount = 0.0;    // nilai potongan (rupiah)

    public double applyDiscount(double originalTotal, Scanner input) {
        automaticDiscount = 0.0;
        couponDiscount = 0.0;

        System.out.println("\n=== Diskon ===");
        System.out.println("Total awal: Rp" + originalTotal);

        // Diskon otomatis jika mencapai ambang
        if (originalTotal >= 50000) {
            automaticDiscount = originalTotal * 0.10; // 10%
            System.out.println("Diskon otomatis 10%: -Rp" + automaticDiscount);
        } else {
            System.out.println("Belum mencapai Rp50000 untuk diskon otomatis (10%).");
        }

        // Kupon
        System.out.println("Kode kupon tersedia: HEMAT10 (10%), MAKAN5 (Rp5000), SUPER20 (20% jika total>=100000)");
        System.out.print("Masukkan kode kupon (atau '-' untuk lewati): ");
        String code = input.next();

        if (!code.equalsIgnoreCase("-")) {
            couponDiscount = evaluateCoupon(code, originalTotal - automaticDiscount);
            if (couponDiscount > 0) {
                System.out.println("Kupon valid: -Rp" + couponDiscount);
            } else {
                System.out.println("Kupon tidak valid atau syarat tidak terpenuhi.");
            }
        }

        double finalTotal = originalTotal - automaticDiscount - couponDiscount;
        if (finalTotal < 0) finalTotal = 0; // keamanan

        System.out.println("Total setelah diskon: Rp" + finalTotal);
        return finalTotal;
    }

    private double evaluateCoupon(String code, double baseAmount) {
        switch (code.toUpperCase()) {
            case "HEMAT10":
                return baseAmount * 0.10; // 10% dari sisa setelah diskon otomatis
            case "MAKAN5":
                return 5000;
            case "SUPER20":
                if (baseAmount >= 100000) {
                    return baseAmount * 0.20; // 20%
                } else {
                    return 0;
                }
            default:
                return 0;
        }
    }

    public double getAutomaticDiscount() {
        return automaticDiscount;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }
}
