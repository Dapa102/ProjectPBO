package praktikum.sesi.sesi11;
import java.util.*;

public class soal3 {
    public static int bacaAngka() throws NumberFormatException {
        Scanner input = new Scanner (System.in);

        System.out.println ("Masukkan angka: ");
        String strAngka = input.nextLine();

        input.close();
        return Integer.parseInt(strAngka);
    }

    public static void main (String []args) {
        try {
            while (true) {
                try {
                    int angka = bacaAngka();
                    System.out.println ("Angka yang dimasukkan" + angka);
                    break;
                }

                catch (NumberFormatException e) {
                    System.out.println ("Error, gaboleh huruf");
                }
            }
        }
        finally {
            System.out.println ("program selesai");
        }

    }
}