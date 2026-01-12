package praktikum.sesi.Sesi9.soal1;

    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            Persegi p = new Persegi();
            Lingkaran l = new Lingkaran();
            Segitiga s = new Segitiga();

            System.out.print("Masukkan sisi persegi: ");
            p.setSisi(input.nextDouble());

            System.out.print("Masukkan jari-jari lingkaran: ");
            l.setR(input.nextDouble());

            System.out.print("Masukkan alas segitiga: ");
            s.setAlas(input.nextDouble());

            System.out.print("Masukkan tinggi segitiga: ");
            s.setTinggi(input.nextDouble());

            System.out.println("\nhasil: ");
            System.out.println("Luas Persegi   : " + p.luas());
            System.out.println("Luas Lingkaran : " + l.luas());
            System.out.println("Luas Segitiga  : " + s.luas());

            input.close(); 
        }
    }
