package model;
/**
 * Product class
 */
/**
 * @author James Badke
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    /**
     * getters and setters
     */
    public ObservableList getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * functions for adding associated part to product
     * @param part given part
     */
    public void addAssociatedPart(Part part) {associatedParts.add(part);}
    /**
     * functions for removing associated part to product
     * @param part given part
     */
    public void removeAssociatedPart(Part part) {associatedParts.remove(part);}

    /**
     * gets size of part list
     * @return size
     */
    public int getPartsListSize() {return associatedParts.size();}
}