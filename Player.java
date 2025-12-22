package project_akhir.project2;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String nama;
    private int darah = 100;
    private int serangan = 15;
    private static final int MAX_DARAH = 150;

    // ===== HASHSET UNTUK SKILL =====
    private Set<String> skillSet = new HashSet<>();

    public Player(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama player tidak boleh kosong!");
        }
        this.nama = nama;

        // Skill default (unik)
        skillSet.add("Serangan");
        skillSet.add("Penyembuhan");
        skillSet.add("Ultimate");
    }

    public void serangMusuh(Musuh m) {
        if (m == null) {
            throw new IllegalArgumentException("Target musuh tidak valid!");
        }
        if (m.isMati()) {
            throw new IllegalStateException("Musuh sudah mati!");
        }
        System.out.println("\n" + nama + " menyerang musuh!");
        m.kenaSerangan(serangan);
    }

    public void seranganBurst(Musuh m) {
        if (!skillSet.contains("Ultimate")) {
            throw new IllegalStateException("Skill Ultimate tidak tersedia!");
        }
        if (m == null) {
            throw new IllegalArgumentException("Target musuh tidak valid!");
        }
        if (m.isMati()) {
            throw new IllegalStateException("Musuh sudah mati!");
        }
        int specialDamage = serangan * 8;
        System.out.println("\n" + nama + " menggunakan SERANGAN ULTIMATE!");
        m.kenaSerangan(specialDamage);
    }

    public void penyembuhan() {
        if (!skillSet.contains("Penyembuhan")) {
            throw new IllegalStateException("Skill penyembuhan tidak tersedia!");
        }
        if (darah >= MAX_DARAH) {
            throw new IllegalStateException("HP sudah maksimal!");
        }
        darah += 10;
        if (darah > MAX_DARAH) {
            darah = MAX_DARAH;
        }
        System.out.println("HP bertambah! HP: " + darah);
    }

    public void tampilkanSkill() {
        System.out.println("Skill " + nama + ": " + skillSet);
    }

    public void kenaSerangan(int dmg) {
        if (dmg < 0) {
            throw new IllegalArgumentException("Damage tidak boleh negatif!");
        }
        darah -= dmg;
        if (darah < 0) darah = 0;
        System.out.println("Kamu terkena serangan! HP: " + darah);
    }

    public int getDarah(){ 
        return darah; 
    }

    public String getNama(){ 
        return nama; 
    }
}
