package praktikum.sesi.Sesi9.soal3;

public class Main {
    public static void main(String[] args) {

        PegawaiTetap tetap = new PegawaiTetap();
        tetap.setNama("mulyono");
        tetap.setGajiPokok(3000000);
        tetap.setTunjangan(1000000);

        PegawaiKontrak kontrak = new PegawaiKontrak();
        kontrak.setNama("budi");
        kontrak.setGajiPokok(2500000);
        kontrak.setBonus(500000);
        kontrak.setJumlahProyek(2);

        System.out.println("Pegawai Tetap: " + tetap.getNama());
        System.out.println("Gaji: " + tetap.hitungGaji());

        System.out.println("\nPegawai Kontrak: " + kontrak.getNama());
        System.out.println("Gaji: " + kontrak.hitungGaji());
    }
}
