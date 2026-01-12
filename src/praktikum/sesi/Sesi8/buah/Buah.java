package praktikum.sesi.Sesi8.buah;

class Fruits {
    public void print_namaBuah () {
        System.out.println ("ini adalah class Fruits");
    }
}

class Mangga extends Fruits {
    public void print_mangga () {
        System.out.println ("Mangga subclass dari class Fruits");
    }
}

class Anggur extends Fruits {
    public void print_anggur () {
        System.out.println ("Anggur subclass dari class Fruits");
    }
}

class Jeruk extends Fruits {
    public void print_jeruk () {
        System.out.println ("Jeruk subclass dari class Fruits");
    }
}

class Buah {
    public static void main (String []args) {

        Mangga m = new Mangga();
        m.print_namaBuah();
        m.print_mangga();

        Anggur a = new Anggur();
        a.print_namaBuah();
        a.print_anggur();

        Jeruk j = new Jeruk();
        j.print_namaBuah();
        j.print_jeruk();
    }
}