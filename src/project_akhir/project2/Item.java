package project_akhir.project2;

public class Item {
    private String name;
    private ItemType type;
    private int value;
    private String description;
    
    public enum ItemType {
        WEAPON, ARMOR, POTION
    }
    
    public Item(String name, ItemType type, int value, String description) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public ItemType getType() {
        return type;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ", +" + value + ")";
    }
}
