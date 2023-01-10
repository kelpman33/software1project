package main;
/**
 * Main class
 * Javadoc files located in Soft1proj/src/Javadoc
 */
/**
 * @author James Badke
 */
/**
 * FUTURE ENHANCEMENT
 * create table of products that share associated part with each other, with applicable search, add, and remove methods as necessary
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import model.InHouse;
import model.OutSourced;
import model.Inventory;
import model.Part;
import model.Product;

public class Main extends Application {

    public static Inventory inv = new Inventory();

    @Override
    public void start(Stage stage) throws Exception {
        addInv(inv);

        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * function for adding test data
     * @param inv inventory with lists of parts and products
     */
    public void addInv(Inventory inv){
        Part part1 = new InHouse(1, "part1", 4.99, 5, 1, 10, 10);
        Part part2 = new OutSourced(2, "part2", 4.99, 5, 1, 10, "Company 2");
        inv.addPart(part1);
        inv.addPart(part2);
        Product product1 = new Product(1,"product1",9.99,5,1,10);
        Product product2 = new Product(2,"product2",9.99,5,1,10);
        inv.addProduct(product1);
        inv.addProduct(product2);
    }

    public static void main(String[] args){
        launch(args);
    }
}