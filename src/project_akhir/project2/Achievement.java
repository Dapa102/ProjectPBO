package project_akhir.project2;

import java.util.ArrayList;
import java.util.List;

public class Achievement {
    private String name;
    private String description;
    private boolean unlocked;
    private String icon;
    
    public Achievement(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.unlocked = false;
        this.icon = icon;
    }
    
    public void unlock() {
        this.unlocked = true;
    }
    
    public boolean isUnlocked() {
        return unlocked;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getIcon() {
        return icon;
    }
    
    @Override
    public String toString() {
        return icon + " " + name + (unlocked ? " ✓" : " ✗");
    }
    
    // Achievement Manager
    public static class AchievementManager {
        private List<Achievement> achievements;
        
        public AchievementManager() {
            achievements = new ArrayList<>();
            initializeAchievements();
        }
        
        private void initializeAchievements() {
            achievements.add(new Achievement("First Blood", "Kalahkan musuh pertama", "⚔️"));
            achievements.add(new Achievement("Boss Slayer", "Kalahkan Boss", "👑"));
            achievements.add(new Achievement("Immortal", "Menang tanpa healing", "🛡️"));
            achievements.add(new Achievement("Combo Master", "Combo 5x dalam satu battle", "🔥"));
            achievements.add(new Achievement("Critical Strike", "Dapatkan critical hit", "💥"));
            achievements.add(new Achievement("Ultimate Power", "Kalahkan Super Boss", "⭐"));
        }
        
        public void checkAndUnlock(String achievementName) {
            for (Achievement achievement : achievements) {
                if (achievement.getName().equals(achievementName)) {
                    achievement.unlock();
                }
            }
        }
        
        public List<Achievement> getAchievements() {
            return achievements;
        }
        
        public int getUnlockedCount() {
            return (int) achievements.stream().filter(Achievement::isUnlocked).count();
        }
    }
}
