package praktikum.sesi.sesi11;
import java.util.*;

public class soal1 {
    public static void main (String []args) {
        Scanner input = new Scanner(System.in);

            while (true) {
                try {
                    System.out.print ("Masukkan bilangan pertama: ");
                    int bil1 = input.nextInt();
                    
                    System.out.print ("Masukkan bilangan kedua: ");
                    int bil2 = input.nextInt();
                    
                    System.out.println ("Hasil pembagian nya: " + (bil1 / bil2));
                    break;
                }
                
                catch (ArithmeticException e) {
                    System.out.println ("Error tidak boleh membagi dengan bilangan 0");
                }
                
                catch (InputMismatchException e) {
                    System.out.println ("Error, input tidak boleh huruf");
                    input.nextLine();
                }
                
                finally {
                    System.out.println ("Program nya telah selesai");
                    input.close();
                }
            }
        
    }
}