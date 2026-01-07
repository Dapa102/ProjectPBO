package project_akhir.project2;

public class Musuh {

    private String nama;
    private int darah;
    private int serangan;

    public Musuh(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            this.nama = "Musuh tidak diketahui";
        } else {
            this.nama = nama;
        }
        this.darah = 50;
        this.serangan = 10;
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

    public void serangan(Player p) {
        if (p == null) {
            throw new IllegalArgumentException("Target player tidak valid!");
        }
        if (this.isMati()) {
            throw new IllegalStateException("Musuh sudah mati!");
        }
        System.out.println(nama + " menyerang kamu!");
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

    public boolean isMati(){
        return darah <= 0;
    }
}
