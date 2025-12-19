package project_akhir.project2;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        try {
            System.out.println("===== RPG FANTASY GAME =====");
            System.out.print("Masukkan nama hero: ");
            String namaPlayer = input.nextLine();
            
            // Validasi nama tidak kosong
            if (namaPlayer == null || namaPlayer.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama hero tidak boleh kosong!");
            }
            
            Player player = new Player(namaPlayer);

            System.out.println("\n=== Pilih Musuh ===");
            System.out.println("1. Kroco (Goblin)");
            System.out.println("2. Boss Naga Kuwad");
            System.out.println("3. Super Boss Raja Iblis");
            System.out.print("\nPilih: ");
            
            int pilihMusuh;
            try {
                pilihMusuh = input.nextInt();
            } catch (InputMismatchException e) {
                throw new IllegalArgumentException("Input harus berupa angka!");
            }

            Musuh musuh;

            if (pilihMusuh == 2) {
                musuh = new Boss("Boss Naga Kuwad");
            } 
            else if (pilihMusuh == 3) {
                musuh = new SuperBoss("Super Boss Raja Iblis");
            } 
            else if (pilihMusuh == 1) {
                musuh = new Musuh("Kroco (Goblin)");
            } 
            else {
                throw new IllegalArgumentException("Pilihan musuh tidak valid! Pilih 1-3.");
            }
     
            System.out.println("\nPertarungan dimulai melawan " + musuh.getNama() + "!\n");

            // LOOP GAME
            while (!musuh.isMati() && player.getDarah() > 0) {

                try {
                    System.out.println("\n=== Giliran " + player.getNama() + " ===");
                    System.out.println("HP Kamu : " + player.getDarah());
                    System.out.println("HP Musuh: " + musuh.getDarah());
                    System.out.println("\n1. Serangan");
                    System.out.println("2. Penyembuhan");
                    System.out.println("3. Serangan Ultimate");
                    System.out.print("\nPilih aksi: ");
                    
                    int menu;
                    try {
                        menu = input.nextInt();
                    } catch (InputMismatchException e) {
                        input.nextLine(); // Clear buffer
                        throw new IllegalArgumentException("Input harus berupa angka!");
                    }

                    switch(menu) {
                        case 1:
                            player.serangMusuh(musuh);
                            break;
                        case 2:
                            player.penyembuhan();
                            break;
                        case 3:
                            player.seranganBurst(musuh);
                            break;
                        default:
                            throw new IllegalArgumentException("Pilihan aksi tidak valid! Pilih 1-3.");
                    }

                    // MUSUH MENYERANG BALIK
                    if (!musuh.isMati()) {
                        System.out.println("\n=== Giliran Musuh ===");
                        musuh.serangan(player);
                    }
                    
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("ERROR: " + e.getMessage());
                    System.out.println("Giliran kamu dilewati!\n");
                    // Musuh tetap menyerang
                    if (!musuh.isMati()) {
                        System.out.println("\n=== Giliran Musuh ===");
                        musuh.serangan(player);
                    }
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan tidak terduga: " + e.getMessage());
                }
            }

            // HASIL GAME
            if (player.getDarah() > 0) {
                System.out.println("\nYOU WIN! " + player.getNama() + " mengalahkan " + musuh.getNama() + "!" + " yeyyyyy");
            } else {
                System.out.println("\nGAME OVER... Kamu kalah melawan " + musuh.getNama() + " wuuuuuuuu");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR FATAL: " + e.getMessage());
            System.out.println("Game tidak dapat dimulai!");
        } catch (Exception e) {
            System.out.println("ERROR FATAL: Terjadi kesalahan tidak terduga.");
            System.out.println("Detail: " + e.getMessage());
            e.printStackTrace();
        } finally {
            input.close();
            System.out.println("\nTerima kasih telah bermain!");
        }
    }
}
