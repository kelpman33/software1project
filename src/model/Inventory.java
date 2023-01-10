package model;
/**
 * Inventory class
 */
/**
 * @author James Badke
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    /**
     * creates lists of parts and products for the inventory
     */
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to inventory
     * @param part selected part
     */
    public void addPart(Part part){
        if (part != null) {
            allParts.add(part);
        }
    }

    /**
     * Adds product to inventory
     * @param product
     */
    public void addProduct(Product product) {
        if (product != null) {
            this.allProducts.add(product);
        }
    }

    /**
     * looks up part with given ID
     * @param partId id parameter of part
     * @return
     */
    public Part lookUpPart(int partId){
        ObservableList<Part> partsInv = getAllParts();

        for(Part p : partsInv){
            if (p.getId() == partId){
                return p;
            }
        }
        return null;
    }
    /**
     * looks up product with given ID
     * @param productId id parameter of product
     * @return
     */
    public Product lookUpProduct(int productId) {
        ObservableList<Product> productsInv = getAllProducts();

        for(Product p : productsInv){
            if (p.getId() == productId){
                return p;
            }
        }
        return null;
    }
    /**
     * looks up part with given string
     * @param partName string for comparing current item to search item
     * @return parts with full or partial match
     */
    public ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> partsInv = getAllParts();
        for(Part p : partsInv){
            if (p.getName().contains(partName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }
    /**
     * looks up product with given string
     * @param productName string for comparing current item to search item
     * @return products with full or partial match
     */
    public ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> partsInv = getAllProducts();
        for(Product p : partsInv){
            if (p.getName().contains(productName)){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /**
     * updates part with given index
     * @param index ID of which part to update
     * @param part
     */
    public void updatePart(int index, Part part) {
            if (allParts.get(index - 1).getId() == part.getId()) {
                allParts.set(index - 1, part);
            }
    }
    /**
     * updates product with given index
     * @param index ID of which product to update
     * @param product
     */
    public void updateProduct(int index, Product product) {
            if (allProducts.get(index - 1).getId() == product.getId()) {
                allProducts.set(index - 1, product);
            }
    }
    /**
     * method for deleting part from inventory
     * @param selectedPart part to delete
     * @return
     */
    public boolean deletePart(Part selectedPart) {
        getAllParts().remove(selectedPart);
        return true;
    }
    /**
     * method for deleting product from inventory
     * @param selectedProduct product to delete
     * @return
     */
    public boolean deleteProduct(Product selectedProduct) {
        getAllProducts().remove(selectedProduct);
        return true;
    }
    /**
     * methods for geting lists and sizes
     * @return
     */
    public ObservableList<Product> getAllProducts() {return allProducts;}

    public ObservableList<Part> getAllParts() {return allParts;}

    public int partListSize() {return allParts.size();}

    public int productListSize() {return allProducts.size();}
}