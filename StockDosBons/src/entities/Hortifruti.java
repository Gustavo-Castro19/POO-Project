package entities;

public class Hortifruti extends Product {
    private double MiddleWeight;
    private double TotalWeight;

    public Hortifruti(int idPro ,String name, double price, int quantity, double MiddleWeight) {
        super(idPro, name, price, quantity);
        this.MiddleWeight = MiddleWeight;
        if(MiddleWeight != (int)MiddleWeight){
            throw new IllegalArgumentException("Wight value is invalid.");
        }
    }

    public double getMiddleWeight() {
        return MiddleWeight;
    }
    public double getTotalWeight() {
        return TotalWeight;
    }
    public void updateTotalValue() {
        TotalWeight = (MiddleWeight * quantity) / 1000;
    }

    public void exhibitionHortifruti() {
        updateTotalValue();

        System.out.println("\n--- Ficha do Produto ---");
        System.out.println("Nome do produto: " + name);
        System.out.println("Valor por kg R$ : " + price);
        System.out.println("Peso total: " + TotalWeight + "kg");
        System.out.printf("Valor total R$: %.2f\n", (TotalWeight * price));
        // System.out.println("Valor total R$: " + (TotalWeight * price));
    }
}
