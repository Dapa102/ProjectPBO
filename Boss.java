package project_akhir.project2;

public class Boss extends Musuh {

    public Boss(String nama){
        super(nama);
        setDarah(150);
        setSerangan(30);
    }

    @Override
    public void serangan(Player p){
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("Boss sudah mati!");
        }
        System.out.println("BOSS " + getNama() + " MENGELUARKAN SERANGAN API!");
        p.kenaSerangan(getSerangan());
    }
}