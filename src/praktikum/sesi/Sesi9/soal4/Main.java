package praktikum.sesi.Sesi9.soal4;

public class Main {
    public static void main(String[] args) {

        Makanan mie = new Makanan();
        mie.setNama("Indomie");
        mie.setHarga(3000);
        mie.setStok(10);
        mie.setKadaluarsa(false);

        mie.infoProduk();
        System.out.println("Bisa dibeli? " + mie.bisaDibeli());
    }
}
