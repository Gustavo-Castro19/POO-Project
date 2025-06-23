package entities;

import java.util.Date;

public class Electronics extends Product {

    private String mark;
    private String fabricator;
    private String model;
    private Date yearLaunch;

    public Electronics(int idPro, String name, double price, int quantity, String mark, String fabricator, String model, Date yearLaunch) {
        super(idPro, name, price, quantity);
        try{
            this.mark = mark;
            this.fabricator = fabricator;
            this.model = model;
            this.yearLaunch = yearLaunch;
        }catch(IllegalArgumentException e){
            System.err.println("Input Electronics() values is invalid: " + e.getMessage());
        }
    }

    public String getMark() {
        return mark;
    }
    public String getFabricator() {
        return fabricator;
    }
    public String getModel() {
        return model;
    }
    public Date getYearLaunch() {
        return yearLaunch;
    }

    public java.sql.Date getSqlYearLaunch () {
        java.sql.Date date = sqlDate(this.yearLaunch.getTime());
        return date;
    }

    private java.sql.Date sqlDate(long time) {
        return new java.sql.Date(time);
    }


    @Override
    public void exhibitionProductsData() {
        super.exhibitionProductsData();

        System.out.println("Marca: " + mark);
        System.out.println("Fabricante: " + fabricator);
        System.out.println("Modelo: " + model);
        System.out.println("Ano de lançamento: " + yearLaunch);
    }
}
