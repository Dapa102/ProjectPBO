package praktikum.sesi.Sesi9.soal2;

public class Main {
    public static void main(String[] args) {

        Kucing k = new Kucing();
        k.setNama("Anggora");
        k.setUsia(2);

        System.out.println("Nama: " + k.getNama());
        System.out.println("Usia: " + k.getUsia() + "Tahun");
        k.bersuara();
    }
}
