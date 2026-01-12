package praktikum.sesi.Sesi10;
import java.util.*;

public class Main {
    public static void main (String []args) {
        List<Animal> animals = new ArrayList<>();

        animals.add (new Animal());
        animals.add (new Cat());
        animals.add (new Dog());
        animals.add (new Tiger());

        for (Animal a : animals) {
            a.bersuara();
        }

    }
}