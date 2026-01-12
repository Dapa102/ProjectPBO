package praktikum.sesi.Sesi9.soal4;

public class Produk {
    protected String nama;
    protected double harga;
    protected int stok;

    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }

    public void infoProduk() {
        System.out.println("Nama  : " + nama);
        System.out.println("Harga : " + harga);
        System.out.println("Stok  : " + stok);
    }

    public boolean bisaDibeli() {
        return stok > 0;
    }
}
