package entities;

public class Furniture extends Product {
    private double height;
    private double width;
    private String material;

    public Furniture(int idPro, String name, double price, int quantity, double height, double width, String material) {
        super(idPro, name, price, quantity);
        try{
            this.height = height;
            this.width = width;
            this.material = material;
        }catch(IllegalArgumentException e){
            System.err.println("Input Furniture() values is invalid: " + e.getMessage());
        }
    }

    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public String getMaterial() {
        return material;
    }    @Override
    public void exhibitionProductsData() {
        super.exhibitionProductsData();
        System.out.println("Material: " + getMaterial());
        System.out.println("size:(" + String.format("%.1f", getWidth()) +
                "x" + String.format("%.1f", getHeight()) + ")m");
    }

}
