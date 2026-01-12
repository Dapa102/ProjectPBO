package praktikum.sesi.sesi11;
import java.util.*;

class usernameSalah extends Exception {
    public usernameSalah(String message) {
        super(message);
    }
}

class passwordSalah extends Exception {
    public passwordSalah(String message) {
        super(message);
    }
}

public class soal4 {
    
    public static void login(String username, String password) 
            throws usernameSalah, passwordSalah {
        
        String usernameBenar = "adminn";
        String passwordBenar = "12345";
        
        if (!username.equals(usernameBenar)) {
            throw new usernameSalah("Error: Username tidak ditemukan");
        }
        
        if (!password.equals(passwordBenar)) {
            throw new passwordSalah("Error: Password salah");
        }
        
        System.out.println("Login berhasil! Selamat datang, " + username);
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        try {
            System.out.print("Masukkan username: ");
            String username = input.nextLine();
            
            System.out.print("Masukkan password: ");
            String password = input.nextLine();
            
            login(username, password);
            
        }
        
        catch (usernameSalah e) {
            System.out.println(e.getMessage());
        }
        
        catch (passwordSalah e) {
            System.out.println(e.getMessage());
        }
        
        finally {
            System.out.println("Program selesai");
            input.close();
        }
    }
}
