package project_akhir.project1;

public class Food extends MenuItem {
    public Food(String name, double price) {
        super(name, price);
    }

    @Override
    
    public void display() {
        System.out.println("[Makanan] " + getName() + " - Rp" + getPrice());
    }
}
