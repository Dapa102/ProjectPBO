package project_akhir.project2;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<Musuh> daftarMusuh = new ArrayList<>();
        ArrayList<String> battleLog = new ArrayList<>();

        try {
            System.out.println("===== RPG FANTASY GAME =====");
            System.out.print("Masukkan nama hero: ");
            String namaPlayer = input.nextLine();

            if (namaPlayer == null || namaPlayer.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama hero tidak boleh kosong!");
            }

            Player player = new Player(namaPlayer);
            player.tampilkanSkill(); // HASHSET DIPAKAI

            daftarMusuh.add(new Musuh("Kroco (Goblin)"));
            daftarMusuh.add(new Boss("Boss Naga Kuwad"));
            daftarMusuh.add(new SuperBoss("Super Boss Raja Iblis"));

            System.out.println("\n=== Pilih Musuh ===");
            for (int i = 0; i < daftarMusuh.size(); i++) {
                System.out.println((i + 1) + ". " + daftarMusuh.get(i).getNama());
            }

            System.out.print("\nPilih: ");
            int pilihMusuh = input.nextInt();

            if (pilihMusuh < 1 || pilihMusuh > daftarMusuh.size()) {
                throw new IllegalArgumentException("Pilihan musuh tidak valid!");
            }

            Musuh musuh = daftarMusuh.get(pilihMusuh - 1);

            System.out.println("\nPertarungan dimulai melawan " + musuh.getNama() + "!\n");

            while (!musuh.isMati() && player.getDarah() > 0) {

                System.out.println("\n=== Giliran " + player.getNama() + " ===");
                System.out.println("HP Kamu : " + player.getDarah());
                System.out.println("HP Musuh: " + musuh.getDarah());
                System.out.println("1. Serangan");
                System.out.println("2. Penyembuhan");
                System.out.println("3. Serangan Ultimate");
                System.out.print("\nPilih aksi: ");

                int menu = input.nextInt();

                switch(menu) {
                    case 1:
                        player.serangMusuh(musuh);
                        battleLog.add(player.getNama() + " menyerang " + musuh.getNama());
                        break;
                    case 2:
                        player.penyembuhan();
                        battleLog.add(player.getNama() + " melakukan penyembuhan");
                        break;
                    case 3:
                        player.seranganBurst(musuh);
                        battleLog.add(player.getNama() + " menggunakan ultimate");
                        break;
                    default:
                        throw new IllegalArgumentException("Pilihan aksi tidak valid!");
                }

                if (!musuh.isMati()) {
                    System.out.println("\n=== Giliran Musuh ===");
                    musuh.serangan(player);
                    battleLog.add(musuh.getNama() + " menyerang " + player.getNama());
                }
            }

            System.out.println("\n=== RIWAYAT PERTARUNGAN ===");
            for (String log : battleLog) {
                System.out.println("- " + log);
            }

            if (player.getDarah() > 0) {
                System.out.println("\nYOU WIN!");
            } else {
                System.out.println("\nGAME OVER...");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {
            input.close();
            System.out.println("\nTerima kasih telah bermain!");
        }
    }
}
