package project_akhir.project2;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String nama;
    private int darah = 150;  // Disesuaikan dengan tier system (Tier 1 musuh)
    private int serangan = 15;
    private int energi = 100;  // Energy system
    private int energiMaksimal = 100;
    private int cooldownPenyembuhan = 0;  // Cooldown untuk penyembuhan (turn-based)
    private int cooldownUltimate = 0;  // Cooldown untuk ultimate
    private static final int MAX_DARAH = 150;
    private static final int COOLDOWN_PENYEMBUHAN = 2;  // 2 turn cooldown
    private static final int COOLDOWN_ULTIMATE = 3;  // 3 turn cooldown

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
        if (energi < 20) {
            throw new IllegalStateException("Energi tidak cukup untuk menyerang!");
        }
        System.out.println("\n" + nama + " menyerang musuh!");
        energi -= 20;
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
        if (cooldownUltimate > 0) {
            throw new IllegalStateException("Ultimate masih cooldown! Tunggu " + cooldownUltimate + " turn.");
        }
        if (energi < 50) {
            throw new IllegalStateException("Energi tidak cukup untuk ultimate!");
        }
        int specialDamage = serangan * 8;
        System.out.println("\n" + nama + " menggunakan SERANGAN ULTIMATE!");
        energi -= 50;
        cooldownUltimate = COOLDOWN_ULTIMATE;
        m.kenaSerangan(specialDamage);
    }

    public void penyembuhan() {
        if (!skillSet.contains("Penyembuhan")) {
            throw new IllegalStateException("Skill penyembuhan tidak tersedia!");
        }
        if (cooldownPenyembuhan > 0) {
            throw new IllegalStateException("Penyembuhan masih cooldown! Tunggu " + cooldownPenyembuhan + " turn.");
        }
        if (darah >= MAX_DARAH) {
            throw new IllegalStateException("HP sudah maksimal!");
        }
        if (energi < 30) {
            throw new IllegalStateException("Energi tidak cukup untuk penyembuhan!");
        }
        darah += 10;
        if (darah > MAX_DARAH) {
            darah = MAX_DARAH;
        }
        energi -= 30;
        cooldownPenyembuhan = COOLDOWN_PENYEMBUHAN;
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

    public int getSerangan() {
        return serangan;
    }

    public String getNama(){ 
        return nama; 
    }

    public int getEnergi() {
        return energi;
    }

    public int getEnergiMaksimal() {
        return energiMaksimal;
    }

    public void setEnergi(int energi) {
        this.energi = Math.max(0, Math.min(energi, energiMaksimal));
    }

    public void regenerasiEnergi() {
        energi += 15;
        if (energi > energiMaksimal) {
            energi = energiMaksimal;
        }
    }

    public int getCooldownPenyembuhan() {
        return cooldownPenyembuhan;
    }

    public int getCooldownUltimate() {
        return cooldownUltimate;
    }

    public void kurangiCooldown() {
        if (cooldownPenyembuhan > 0) cooldownPenyembuhan--;
        if (cooldownUltimate > 0) cooldownUltimate--;
    }

    public boolean isPenyelembuhanReady() {
        return cooldownPenyembuhan == 0;
    }

    public boolean isUltimateReady() {
        return cooldownUltimate == 0;
    }
}
