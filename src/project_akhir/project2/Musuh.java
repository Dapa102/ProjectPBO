package project_akhir.project2;

public class Musuh {

    private String nama;
    private int darah;
    private int serangan;
    private int energi;
    private int energiMaksimal;
    private int cooldownSerangan = 0;

    // Tier 1: Musuh biasa (Goblin, Orc, dll)
    public Musuh(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            this.nama = "Musuh tidak diketahui";
        } else {
            this.nama = nama;
        }
        this.darah = 80;      // Tier 1
        this.serangan = 12;   // Tier 1
        this.energi = 80;
        this.energiMaksimal = 80;
    }

    public String getNama() {
        return nama;
    }

    public int getDarah() {
        return darah;
    }

    public int getSerangan() {
        return serangan;
    }

    public int getEnergi() {
        return energi;
    }

    public int getEnergiMaksimal() {
        return energiMaksimal;
    }

    protected void setDarah(int darah) {
        if (darah < 0) {
            throw new IllegalArgumentException("Darah tidak boleh negatif!");
        }
        this.darah = darah;
    }

    protected void setSerangan(int serangan) {
        if (serangan < 0) {
            throw new IllegalArgumentException("Serangan tidak boleh negatif!");
        }
        this.serangan = serangan;
    }

    protected void setEnergiMaksimal(int energi) {
        this.energiMaksimal = energi;
        this.energi = energi;
    }

    public void setEnergi(int energy) {
        this.energi = Math.max(0, Math.min(energy, energiMaksimal));
    }

    public void serangan(Player p) {
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("Musuh sudah mati!");
        }
        if (energi < 15) {
            throw new IllegalStateException("Energi musuh tidak cukup!");
        }
        System.out.println(nama + " menyerang kamu!");
        energi -= 15;
        p.kenaSerangan(serangan);
    }

    public void kenaSerangan(int dmg) {
        if (dmg < 0) {
            throw new IllegalArgumentException("Damage tidak boleh negatif!");
        }
        darah -= dmg;
        if (darah < 0) darah = 0;
        System.out.println(nama + " HP: " + darah);
    }

    public void regenerasiEnergi() {
        energi += 10;
        if (energi > energiMaksimal) {
            energi = energiMaksimal;
        }
    }

    public int getCooldownSerangan() {
        return cooldownSerangan;
    }

    public void kurangiCooldown() {
        if (cooldownSerangan > 0) cooldownSerangan--;
    }

    public boolean isMati(){
        return darah <= 0;
    }
}
