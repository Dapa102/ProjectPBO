package praktikum.sesi.Sesi9.soal4;

public class Pakaian extends Produk {
    private String ukuran;

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    @Override
    public void infoProduk() {
        super.infoProduk();
        System.out.println("Ukuran: " + ukuran);
    }
}
