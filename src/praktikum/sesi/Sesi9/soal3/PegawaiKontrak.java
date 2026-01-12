package praktikum.sesi.Sesi9.soal3;

public class PegawaiKontrak extends Pegawai {
    private double bonusPerProyek;
    private int jumlahProyek;

    public void setBonus(double bonus) {
        this.bonusPerProyek = bonus;
    }

    public void setJumlahProyek(int jumlah) {
        this.jumlahProyek = jumlah;
    }

    @Override
    public double hitungGaji() {
        return gajiPokok + (bonusPerProyek * jumlahProyek);
    }
}
