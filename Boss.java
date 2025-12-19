package project_akhir.project2;

public class Boss extends Musuh{

    public Boss(String nama){
        super(nama);
        try {
            setDarah(150);
            setSerangan(30);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR saat inisialisasi Boss: " + e.getMessage());
        }
    }

    @Override
    public void serangan(Player p){
        try {
            if (p == null) {
                throw new IllegalArgumentException("Target player tidak valid!");
            }
            if (this.isMati()) {
                throw new IllegalStateException("Boss sudah mati, tidak bisa menyerang!");
            }
            System.out.println("BOSS " + getNama() + " MENGELUARKAN SERANGAN API!");
            p.kenaSerangan(getSerangan());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
