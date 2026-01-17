package project_akhir.project2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerDAO - Data Access Object untuk operasi database Player
 * Disesuaikan dengan struktur tabel di database turnbaserpg
 */
public class PlayerDAO {
    
    /**
     * Model untuk data player dari database
     */
    public static class PlayerData {
        public int id;
        public String name;
        public int totalMenang;
        public int totalKalah;
        public int damageTertinggi;
        public Timestamp createdAt;
        public Timestamp lastPlayed;
        
        @Override
        public String toString() {
            return String.format("%s - Menang:%d Kalah:%d (DMG Tertinggi: %d)", 
                name, totalMenang, totalKalah, damageTertinggi);
        }
    }
    
    /**
     * Menyimpan atau mengupdate data player
     */
    public static int savePlayer(String name) {
        String selectSQL = "SELECT id FROM players WHERE name = ?";
        String insertSQL = "INSERT INTO players (name, total_menang, total_kalah) VALUES (?, 0, 0)";
        String updateSQL = "UPDATE players SET last_played = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection()) {
            
            // Cek apakah player sudah ada
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                selectStmt.setString(1, name);
                ResultSet rs = selectStmt.executeQuery();
                
                if (rs.next()) {
                    int playerId = rs.getInt("id");
                    // Update last_played
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                        updateStmt.setInt(1, playerId);
                        updateStmt.executeUpdate();
                    }
                    System.out.println("✓ Player ditemukan: " + name + " (ID: " + playerId + ")");
                    return playerId;
                }
            }
            
            // Jika tidak ada, buat player baru
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, name);
                insertStmt.executeUpdate();
                
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int playerId = generatedKeys.getInt(1);
                    System.out.println("✓ Player baru dibuat: " + name + " (ID: " + playerId + ")");
                    return playerId;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat menyimpan player: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Mendapatkan data player berdasarkan ID
     */
    public static PlayerData getPlayer(int playerId) {
        String sql = "SELECT * FROM players WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                PlayerData data = new PlayerData();
                data.id = rs.getInt("id");
                data.name = rs.getString("name");
                data.totalMenang = rs.getInt("total_menang");
                data.totalKalah = rs.getInt("total_kalah");
                data.damageTertinggi = rs.getInt("damage_tertinggi");
                data.createdAt = rs.getTimestamp("created_at");
                data.lastPlayed = rs.getTimestamp("last_played");
                return data;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data player: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Update statistik player setelah battle
     */
    public static void updatePlayerStats(int playerId, boolean menang, int damageDealt) {
        String sql = """
            UPDATE players 
            SET total_menang = total_menang + ?,
                total_kalah = total_kalah + ?,
                damage_tertinggi = CASE WHEN ? > damage_tertinggi THEN ? ELSE damage_tertinggi END,
                last_played = CURRENT_TIMESTAMP
            WHERE id = ?
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, menang ? 1 : 0);
            stmt.setInt(2, menang ? 0 : 1);
            stmt.setInt(3, damageDealt);
            stmt.setInt(4, damageDealt);
            stmt.setInt(5, playerId);
            
            stmt.executeUpdate();
            System.out.println("✓ Statistik player berhasil diupdate");
            
        } catch (SQLException e) {
            System.err.println("Error saat update statistik: " + e.getMessage());
        }
    }
    
    /**
     * Menyimpan riwayat battle
     */
    public static void saveBattleHistory(int playerId, String namaMusuh, int damageDealt, boolean menang) {
        String sql = """
            INSERT INTO battle_history 
            (player_id, nama_musuh, damage_dihasilkan, hasil)
            VALUES (?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            stmt.setString(2, namaMusuh);
            stmt.setInt(3, damageDealt);
            stmt.setString(4, menang ? "MENANG" : "KALAH");
            
            stmt.executeUpdate();
            System.out.println("✓ Battle history berhasil disimpan");
            
        } catch (SQLException e) {
            System.err.println("Error saat menyimpan battle history: " + e.getMessage());
        }
    }
    
    /**
     * Mendapatkan leaderboard (top players)
     */
    public static List<PlayerData> getLeaderboard(int limit) {
        List<PlayerData> leaderboard = new ArrayList<>();
        String sql = """
            SELECT * FROM players 
            ORDER BY total_menang DESC, damage_tertinggi DESC 
            LIMIT ?
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PlayerData data = new PlayerData();
                data.id = rs.getInt("id");
                data.name = rs.getString("name");
                data.totalMenang = rs.getInt("total_menang");
                data.totalKalah = rs.getInt("total_kalah");
                data.damageTertinggi = rs.getInt("damage_tertinggi");
                data.createdAt = rs.getTimestamp("created_at");
                data.lastPlayed = rs.getTimestamp("last_played");
                leaderboard.add(data);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil leaderboard: " + e.getMessage());
        }
        
        return leaderboard;
    }
    
    /**
     * Mendapatkan riwayat battle player
     */
    public static List<String> getBattleHistory(int playerId, int limit) {
        List<String> history = new ArrayList<>();
        String sql = """
            SELECT nama_musuh, hasil, damage_dihasilkan, tanggal_pertempuran
            FROM battle_history 
            WHERE player_id = ? 
            ORDER BY tanggal_pertempuran DESC 
            LIMIT ?
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String entry = String.format("[%s] %s vs %s - Damage: %d",
                    rs.getString("hasil"),
                    rs.getTimestamp("tanggal_pertempuran"),
                    rs.getString("nama_musuh"),
                    rs.getInt("damage_dihasilkan")
                );
                history.add(entry);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil battle history: " + e.getMessage());
        }
        
        return history;
    }
}
