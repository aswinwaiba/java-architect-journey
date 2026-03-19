public class Main {
    public static void main (String[] args) {
        PhysicalProduct p1 = new PhysicalProduct("1","Glass",100.0, 0.5);
        DigitalProduct d1 = new DigitalProduct("2","ClaudeCode", 300.0,"URL://asdfasdf");

        System.out.println(describeProduct(p1));
        System.out.println(describeProduct(d1));
    }

    public static String describeProduct(ProductType productType) {
        return switch (productType) {
            case PhysicalProduct p -> "Ships in a box. Weight: " + p.unitWeight() + "kg. Price: $" + p.price();
            case DigitalProduct d -> "Download at: " + d.downloadUrl() + ". Price: $" + d.price();
        };
    }
}
