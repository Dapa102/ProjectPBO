package praktikum.sesi.coba;

class hewan {
    private String nama;

    public hewan (String nama) {
        this.nama = nama;
    }

    public String getNama () {
        return nama;
    }

    public void setNama (String nama) {
        this.nama = nama;
    }

    public void habitat() {
        System.out.println ("Hewan punya habitat masing masing");
    }

    public void jenisHewan () {
        System.out.println(getNama() + " termasuk hewan.");
    }
}

class gajah extends hewan {

    public gajah(String nama) {
        super(nama);
    }

    @Override
    public void habitat() {
        System.out.println (getNama() + " Hidup di darat");
    }

    @Override
    public void jenisHewan() {
        System.out.println (getNama() + " adalah hewan besar");
    }
}

class mamalia extends hewan {
    public mamalia (String nama) {
        super(nama);
    }

    @Override 
    public void jenisHewan() {
        System.out.println (getNama() + " adalah hewan mamalia");
    }
    
} 

public class latihan1 {
    public static void main (String[] args) {
        hewan h = new hewan ("Hewan");
        h.habitat();
        h.jenisHewan();

        gajah g = new gajah ("Gajah");
        mamalia m = new mamalia("Gajah");

        g.habitat();
        g.jenisHewan();
        m.jenisHewan();
    }
}