package project_akhir.project2;

public class SuperBoss extends Boss {

    // Tier 3: SuperBoss (musuh terkuat)
    public SuperBoss(String nama) {
        super(nama);
        setDarah(400);        // Tier 3
        setSerangan(60);      // Tier 3
    }

    @Override
    public void serangan(Player p) {
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("SuperBoss sudah mati!");
        }
        System.out.println("SUPERBOSS " + getNama() + " menyerang dengan ULTIMATE!");
        p.kenaSerangan(getSerangan() * 2);
    }
}
