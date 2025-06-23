package entities;

public class Product {
    protected int idPro;
    protected String name;
    protected double price;
    protected int quantity;

    public Product(int idPro,String name, double price, int quantity) {
        try {
            this.idPro = idPro;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        } catch (IllegalArgumentException e) {
            System.err.println("The input Product() values ​​are invalid: "+ e.getMessage());
        }
    }
    
    public Product(String name, double price, int quantity) {
        this(0, name, price, quantity); // Define o idPro como 0 por padrão
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public int getIdPro() {
        return idPro;
    }

    public void exhibitionProductsData() {
        System.out.println("\n--- Ficha do Produto ---");
        System.out.println("Id do prodtuto: " + idPro);
        System.out.println("Nome do produto: " + name);
        System.out.println("Valor R$ : " + price);
        System.out.println("Quantidade: " + quantity);
    }

    

}
