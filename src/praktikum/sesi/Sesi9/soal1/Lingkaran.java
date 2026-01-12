package praktikum.sesi.Sesi9.soal1;

public class Lingkaran extends bangunDatar {
    private double r;

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public double luas() {
        return Math.PI * r * r;
    }
}
