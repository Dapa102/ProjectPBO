package praktikum.sesi.Sesi9.soal4;

public class Elektronik extends Produk {
    private int garansi;

    public void setGaransi(int garansi) {
        this.garansi = garansi;
    }

    @Override
    public void infoProduk() {
        super.infoProduk();
        System.out.println("Garansi: " + garansi + " tahun");
    }
}
