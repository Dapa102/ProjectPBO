package project_akhir.project2;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class PlayerEnhanced {
    private String nama;
    private int darah = 100;
    private int maxDarah = 150;
    private int serangan = 15;
    private int level = 1;
    private int exp = 0;
    private int gold = 0;
    private int combo = 0;
    private boolean usedHealing = false;
    private Random random = new Random();
    
    // Equipment
    private Item weapon;
    private Item armor;
    
    // Stats tracking
    private int totalDamageDealt = 0;
    private int criticalHits = 0;
    private int enemiesDefeated = 0;
    
    private Set<String> skillSet = new HashSet<>();
    
    public PlayerEnhanced(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama player tidak boleh kosong!");
        }
        this.nama = nama;
        skillSet.add("Serangan");
        skillSet.add("Penyembuhan");
        skillSet.add("Ultimate");
    }
    
    public int serangMusuh(Musuh m) {
        if (m == null || m.isMati()) {
            throw new IllegalStateException("Target tidak valid!");
        }
        
        int damage = calculateDamage();
        boolean isCritical = random.nextDouble() < 0.2; // 20% critical chance
        
        if (isCritical) {
            damage = (int)(damage * 1.5);
            criticalHits++;
        }
        
        m.kenaSerangan(damage);
        totalDamageDealt += damage;
        combo++;
        
        return isCritical ? -damage : damage; // negative = critical
    }
    
    public int seranganBurst(Musuh m) {
        if (!skillSet.contains("Ultimate")) {
            throw new IllegalStateException("Skill Ultimate tidak tersedia!");
        }
        if (m == null || m.isMati()) {
            throw new IllegalStateException("Target tidak valid!");
        }
        
        int specialDamage = calculateDamage() * 3;
        m.kenaSerangan(specialDamage);
        totalDamageDealt += specialDamage;
        combo = 0; // Reset combo after ultimate
        
        return specialDamage;
    }
    
    public void penyembuhan() {
        if (!skillSet.contains("Penyembuhan")) {
            throw new IllegalStateException("Skill penyembuhan tidak tersedia!");
        }
        if (darah >= maxDarah) {
            throw new IllegalStateException("HP sudah maksimal!");
        }
        
        usedHealing = true;
        darah += 30;
        if (darah > maxDarah) {
            darah = maxDarah;
        }
        combo = 0; // Reset combo after healing
    }
    
    public void kenaSerangan(int dmg) {
        if (dmg < 0) {
            throw new IllegalArgumentException("Damage tidak boleh negatif!");
        }
        
        // Armor reduces damage
        int actualDamage = dmg;
        if (armor != null) {
            actualDamage = Math.max(1, dmg - armor.getValue());
        }
        
        darah -= actualDamage;
        if (darah < 0) darah = 0;
        combo = 0; // Reset combo when hit
    }
    
    public void gainExp(int amount) {
        exp += amount;
        checkLevelUp();
    }
    
    private void checkLevelUp() {
        int expNeeded = level * 100;
        if (exp >= expNeeded) {
            level++;
            exp -= expNeeded;
            maxDarah += 20;
            serangan += 3;
            darah = maxDarah; // Full heal on level up
        }
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    public void equipWeapon(Item weapon) {
        if (weapon.getType() == Item.ItemType.WEAPON) {
            this.weapon = weapon;
        }
    }
    
    public void equipArmor(Item armor) {
        if (armor.getType() == Item.ItemType.ARMOR) {
            this.armor = armor;
        }
    }
    
    public void onEnemyDefeated() {
        enemiesDefeated++;
        combo = 0;
    }
    
    private int calculateDamage() {
        int baseDamage = serangan;
        if (weapon != null) {
            baseDamage += weapon.getValue();
        }
        return baseDamage;
    }
    
    // Getters
    public int getDarah() { return darah; }
    public int getMaxDarah() { return maxDarah; }
    public String getNama() { return nama; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getGold() { return gold; }
    public int getCombo() { return combo; }
    public int getTotalDamage() { return totalDamageDealt; }
    public int getCriticalHits() { return criticalHits; }
    public int getEnemiesDefeated() { return enemiesDefeated; }
    public boolean hasUsedHealing() { return usedHealing; }
    public Item getWeapon() { return weapon; }
    public Item getArmor() { return armor; }
    
    public int getExpForNextLevel() {
        return level * 100;
    }
}
