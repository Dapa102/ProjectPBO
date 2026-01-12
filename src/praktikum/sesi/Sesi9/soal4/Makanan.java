package praktikum.sesi.Sesi9.soal4;

public class Makanan extends Produk {
    private boolean kadaluarsa;

    public void setKadaluarsa(boolean status) {
        this.kadaluarsa = status;
    }

    @Override
    public boolean bisaDibeli() {
        return !kadaluarsa && stok > 0;
    }

    @Override
    public void infoProduk() {
        super.infoProduk();
        System.out.println("Kadaluarsa: " + (kadaluarsa ? "YA" : "TIDAK"));
    }
}
