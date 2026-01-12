package praktikum.sesi.Sesi9.soal1;

public class Segitiga extends bangunDatar {
    private double alas, tinggi;

    public void setAlas(double alas) {
        this.alas = alas;
    }

    public void setTinggi(double tinggi) {
        this.tinggi = tinggi;
    }

    @Override
    public double luas() {
        return 0.5 * alas * tinggi;
    }
}
