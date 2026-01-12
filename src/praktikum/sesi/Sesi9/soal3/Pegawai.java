package praktikum.sesi.Sesi9.soal3;

public class Pegawai {
    private String nama;
    protected double gajiPokok;

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setGajiPokok(double gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public String getNama() {
        return nama;
    }

    public double hitungGaji() {
        return gajiPokok;
    }
}