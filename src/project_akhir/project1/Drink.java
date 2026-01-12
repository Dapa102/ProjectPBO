package project_akhir.project1;

public class Drink extends MenuItem {
    public Drink(String name, double price) {
        super(name, price);
    }

    @Override
    
    public void display() {
        System.out.println("[Minuman] " + getName() + " - Rp" + getPrice());
    }
}
