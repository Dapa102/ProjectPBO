package project_akhir.project2;

public class SuperBoss extends Boss {

    public SuperBoss(String nama) {
        super(nama);
        try {
            setDarah(300);
            setSerangan(50);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR saat inisialisasi SuperBoss: " + e.getMessage());
        }
    }

    @Override
    public void serangan(Player p) {
        try {
            if (p == null) {
                throw new IllegalArgumentException("Target player tidak valid!");
            }
            if (this.isMati()) {
                throw new IllegalStateException("SuperBoss sudah mati, tidak bisa menyerang!");
            }
            System.out.println("SUPERBOSS " + getNama() + " menyerang dengan ULTIMATE!");
            p.kenaSerangan(getSerangan() * 2);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
