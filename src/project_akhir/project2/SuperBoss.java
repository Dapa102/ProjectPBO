package project_akhir.project2;

public class SuperBoss extends Boss {

    // Tier 3: SuperBoss (musuh terkuat)
    public SuperBoss(String nama) {
        super(nama);
        setDarah(400);        // Tier 3
        setSerangan(60);      // Tier 3
        setEnergiMaksimal(150);  // SuperBoss punya energi paling banyak
    }

    @Override
    public void serangan(Player p) {
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("SuperBoss sudah mati!");
        }
        if (getEnergi() < 50) {
            throw new IllegalStateException("Energi SuperBoss tidak cukup!");
        }
        System.out.println("SUPERBOSS " + getNama() + " menyerang dengan ULTIMATE!");
        setEnergi(getEnergi() - 50);  // Ultimate costs more energy
        p.kenaSerangan(getSerangan() * 2);
    }
}
