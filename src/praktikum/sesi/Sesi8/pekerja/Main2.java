package praktikum.sesi.Sesi8.pekerja;

interface Pekerja {

    public void bekerja();
}

class Manusia {

    public void info(String nama) {
        System.out.println(nama + " adalah seorang manusia.");
    }
}

class Person extends Manusia implements Pekerja {

    String nama = "Budi";

    @Override
    
    public void bekerja() {
        System.out.println(nama + " sedang bekerja sebagai pekerja.");
    }
}

public class Main2 {
    public static void main(String[] args) {

        Person orang = new Person();

        orang.bekerja();

        orang.info(orang.nama);
    }
}
