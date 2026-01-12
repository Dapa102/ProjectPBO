package praktikum.sesi.Sesi8.gajiKaryawan;

class Karyawan {
    public void print_gaji () {
        System.out.print("Selamat Bekerja");
    }
}

class Gudang extends Karyawan {
    int gajiKaryawan = 5000000;  
}

class Leader extends Gudang {
    int benefit = 2000000;       
}

class Main {
    public static void main(String[] args) {
        Leader l = new Leader();
        System.out.println("Hai");
        System.out.println("gajiKaryawan: " + l.gajiKaryawan + "\nBenefit: " + l.benefit);
    }
}
