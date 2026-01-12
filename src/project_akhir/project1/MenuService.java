package project_akhir.project1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuService {

    private List<MenuItem> foodList = new ArrayList<>();
    private List<MenuItem> drinkList = new ArrayList<>();
    private Map<MenuItem, Integer> cart = new LinkedHashMap<>();

    public MenuService() {
        foodList.add(new Food("Nasi Goreng", 15000));
        foodList.add(new Food("Mie Goreng", 12000));
        foodList.add(new Food("Ayam Geprek", 18000));

        drinkList.add(new Drink("Es Teh", 5000));
        drinkList.add(new Drink("Es Jeruk", 7000));
        drinkList.add(new Drink("Kopi", 10000));
    }

    public double showMenuAndChoose(Scanner input) {
        System.out.println("\n=== Pilih Kategori ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Edit Harga Item");
        System.out.println("4. Tambah Item Baru");
        System.out.println("5. Hapus Item");
        System.out.println("6. Lihat Ringkasan Keranjang");
        System.out.print("Pilih: ");
        int pilih = input.nextInt();

        if (pilih == 1) {
            return chooseItem(foodList, input);
        } else if (pilih == 2) {
            return chooseItem(drinkList, input);
        } else if (pilih == 3) {
            editPriceFlow(input);
            return 0; // tidak menambah total belanja
        } else if (pilih == 4) {
            addItemFlow(input);
            return 0;
        } else if (pilih == 5) {
            removeItemFlow(input);
            return 0;
        } else if (pilih == 6) {
            printCartSummary();
            return 0;
        } else {
            System.out.println("Input salah!");
            return 0;
        }
    }

    private double chooseItem(List<MenuItem> list, Scanner input) {
        System.out.println("\n=== Daftar Menu ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.print((i + 1) + ". ");
            list.get(i).display();
        }

        System.out.print("Pilih menu (1-" + list.size() + "): ");
        int pilih = input.nextInt();

        if (pilih >= 1 && pilih <= list.size()) {
            MenuItem item = list.get(pilih - 1);
            System.out.print("Masukkan quantity: ");
            int qty = input.nextInt();
            if (qty <= 0) {
                System.out.println("Quantity harus > 0");
                return 0;
            }
            addToCart(item, qty);
            double subtotal = item.getPrice() * qty;
            System.out.println("Anda memilih: " + item.getName() + " x" + qty + " = Rp" + subtotal);
            return subtotal;
        } else {
            System.out.println("Menu tidak tersedia!");
            return 0;
        }
    }

    private void editPriceFlow(Scanner input) {
        System.out.println("\n=== Edit Harga ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.print("Pilih kategori: ");
        int kategori = input.nextInt();

        List<MenuItem> target;
        if (kategori == 1) {
            target = foodList;
        } else if (kategori == 2) {
            target = drinkList;
        } else {
            System.out.println("Kategori tidak valid");
            return;
        }

        System.out.println("\n=== Item dalam kategori ===");
        for (int i = 0; i < target.size(); i++) {
            System.out.print((i + 1) + ". ");
            target.get(i).display();
        }
        System.out.print("Pilih nomor item yang ingin diubah: ");
        int idx = input.nextInt();
        if (idx < 1 || idx > target.size()) {
            System.out.println("Nomor item tidak valid");
            return;
        }
        MenuItem item = target.get(idx - 1);
        System.out.print("Masukkan harga baru untuk '" + item.getName() + "': Rp");
        double newPrice = input.nextDouble();
        if (newPrice <= 0) {
            System.out.println("Harga harus lebih besar dari 0");
            return;
        }
        item.setPrice(newPrice);
        System.out.println("Harga berhasil diubah. Data terbaru:");
        item.display();
    }

    private void addItemFlow(Scanner input) {
        System.out.println("\n=== Tambah Item Baru ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.print("Pilih kategori: ");
        int kategori = input.nextInt();
        input.nextLine(); // consume newline
        System.out.print("Masukkan nama item: ");
        String nama = input.nextLine();
        System.out.print("Masukkan harga (Rp): ");
        double harga = input.nextDouble();
        if (harga <= 0) {
            System.out.println("Harga harus > 0");
            return;
        }
        MenuItem baru;
        if (kategori == 1) {
            baru = new Food(nama, harga);
            foodList.add(baru);
        } else if (kategori == 2) {
            baru = new Drink(nama, harga);
            drinkList.add(baru);
        } else {
            System.out.println("Kategori tidak valid");
            return;
        }
        System.out.println("Item berhasil ditambahkan:");
        baru.display();
    }

    private void removeItemFlow(Scanner input) {
        System.out.println("\n=== Hapus Item ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.print("Pilih kategori: ");
        int kategori = input.nextInt();
        List<MenuItem> target;
        if (kategori == 1) {
            target = foodList;
        } else if (kategori == 2) {
            target = drinkList;
        } else {
            System.out.println("Kategori tidak valid");
            return;
        }
        if (target.isEmpty()) {
            System.out.println("Daftar kosong");
            return;
        }
        System.out.println("\n=== Daftar Item ===");
        for (int i = 0; i < target.size(); i++) {
            System.out.print((i + 1) + ". ");
            target.get(i).display();
        }
        System.out.print("Pilih nomor yang dihapus: ");
        int idx = input.nextInt();
        if (idx < 1 || idx > target.size()) {
            System.out.println("Nomor tidak valid");
            return;
        }
        MenuItem removed = target.remove(idx - 1);
        System.out.println("Item dihapus: " + removed.getName());
    }

    private void addToCart(MenuItem item, int qty) {
        cart.put(item, cart.getOrDefault(item, 0) + qty);
    }

    public void printCartSummary() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        System.out.println("\n=== Ringkasan Keranjang ===");
        double total = 0;
        for (Map.Entry<MenuItem, Integer> e : cart.entrySet()) {
            double line = e.getKey().getPrice() * e.getValue();
            System.out.println(e.getKey().getName() + " x" + e.getValue() + " = Rp" + line);
            total += line;
        }
        System.out.println("Total sementara: Rp" + total);
    }
}
