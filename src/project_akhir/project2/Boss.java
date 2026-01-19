package project_akhir.project2;

public class Boss extends Musuh {

    // Tier 2: Boss (lebih kuat dari musuh biasa)
    public Boss(String nama){
        super(nama);
        setDarah(250);        // Tier 2
        setSerangan(35);      // Tier 2
        setEnergiMaksimal(120);  // Boss punya lebih banyak energi
    }

    @Override
    public void serangan(Player p){
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("Boss sudah mati!");
        }
        if (getEnergi() < 25) {
            throw new IllegalStateException("Energi boss tidak cukup!");
        }
        System.out.println("BOSS " + getNama() + " MENGELUARKAN SERANGAN API!");
        setEnergi(getEnergi() - 25);
        p.kenaSerangan(getSerangan());
    }
}