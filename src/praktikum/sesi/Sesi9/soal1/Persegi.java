package praktikum.sesi.Sesi9.soal1;

public class Persegi extends bangunDatar {
    private double sisi;

    public void setSisi(double sisi) {
        this.sisi = sisi;
    }

    @Override
    public double luas() {
        return sisi * sisi;
    }
}
