package praktikum.sesi.sesi11;
import java.util.*;

public class soal2 {
    public static void main (String []args) {
        Scanner input = new Scanner (System.in);
        int dataArray[] = {5,4,3,2,1};
        int index = 0;

        while (true) {
            try {
                System.out.print ("Masukkan satu index array: ");
                index = input.nextInt();

                System.out.println ("Data ditemukan pada index ke " + index + "data tersebut adalah: " + dataArray[index]);
                break;
            }

            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println ("Data array pada index " + index + " tidak ditemukan");

            }

            finally {
                System.out.println ("Program selesai");
                input.close();
            }

        }

    }
}