package project_akhir.project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseManager - Mengelola koneksi database PostgreSQL
 */
public class DatabaseManager {
    
    // Konfigurasi Database PostgreSQL
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/turnbaserpg";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "postgres";
    
    private static Connection connection = null;
    
    /**
     * Mendapatkan koneksi database
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD);
            System.out.println("✓ Koneksi database berhasil! (PostgreSQL)");
        }
        return connection;
    }
    
    /**
     * Menutup koneksi database
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Koneksi database ditutup");
            }
        } catch (SQLException e) {
            System.err.println("Error saat menutup koneksi: " + e.getMessage());
        }
    }
    
    /**
     * Test koneksi database
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Test koneksi gagal: " + e.getMessage());
            return false;
        }
    }
}
