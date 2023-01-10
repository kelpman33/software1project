package controller;
/**
 * mainController class
 */
/**
 * @author James Badke
 */

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Inventory;
import model.Part;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Product;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    public TableView productsTable;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInvColumn;
    public TableColumn productPriceColumn;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInvColumn;
    public TableColumn partPriceColumn;
    public Button addButton1;
    public Button modifyButton1;
    public Button deleteButton1;
    public Button modifyButton2;
    public Button deleteButton2;
    public Button addButton2;
    public TableView partsTable;
    public Button exitButton;
    public TextField partsSearch;
    public TextField productsSearch;

    /**
     * retrieves inv from main
     */
    Inventory inv = Main.inv;

    @Override
    /**
     * function called immediately upon program starting, creates and populates tables with parts and products from main
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create and populate tables
        partsTable.setItems(inv.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(inv.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Function for transitioning to the "add part" screen
     * @param actionEvent click add button
     * @throws Exception
     */
    public void onAdd1(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) addButton1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * Goes to modify screen, unless error cases occur
     * @param actionEvent modify button press
     * RUNTIME ERROR
     * Error occurred whereupon clicking modify, screen wouldn't change. Fixed by moving "modifyPart.sendPart(selected)"
     * to before "Stage stage = (Stage) modifyButton1.getScene().getWindow()". Was originally after "stage.show();".
     */
    public void onModify1(ActionEvent actionEvent) {
        try {
            Part selected = (Part) partsTable.getSelectionModel().getSelectedItem();
            if (inv.getAllParts().isEmpty()) {
                errorWindow(1);
            } else if (!inv.getAllParts().isEmpty() && selected == null) {
                errorWindow(2);
            } else {
                //pass selected part to modify screen
                modifyPart.sendPart(selected);
                //go to modify screen
                Stage stage = (Stage) modifyButton1.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
            }
        } catch (IOException e) {
        }
    }

    /**
     * method for removing part from inventory
     * @param actionEvent delete button press
     */
    public void onDelete1(ActionEvent actionEvent) {
        Part removePart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (inv.getAllParts().isEmpty()) {
            errorWindow(1);
        } else if (!inv.getAllParts().isEmpty() && removePart == null) {
            errorWindow(2);
        } else {
            boolean confirm = confirmationWindow(removePart.getName());
            if (!confirm) {
                return;
            }
            inv.deletePart(removePart);
            partsTable.refresh();
        }
    }
    /**
     * Goes to modify screen, unless error cases occur
     * @param actionEvent modify button press
     */
    public void onModify2(ActionEvent actionEvent) {
        try {
            Product selected = (Product) productsTable.getSelectionModel().getSelectedItem();
            if (inv.getAllProducts().isEmpty()) {
                errorWindow(1);
            } else if (!inv.getAllProducts().isEmpty() && selected == null) {
                errorWindow(2);
            } else {
                //pass selected part to modify screen
                modifyProduct.sendProduct(selected, selected.getAssociatedParts());
                //go to modify screen
                Stage stage = (Stage) modifyButton2.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
            }
        } catch (IOException e) {
        }
    }

    /**
     * method for removing product from inventory
     * @param actionEvent delete button press
     */
    public void onDelete2(ActionEvent actionEvent) {
        Product removeProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (inv.getAllProducts().isEmpty()) {
            errorWindow(1);
        } else if (!inv.getAllProducts().isEmpty() && removeProduct == null) {
            errorWindow(2);
        } else if (!removeProduct.getAssociatedParts().isEmpty()){
            errorWindow(3);
        } else {
            boolean confirm = confirmationWindow(removeProduct.getName());
            if (!confirm) {
                return;
            }
            inv.deleteProduct(removeProduct);
            productsTable.refresh();
        }
    }

    public void onAdd2(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) addButton2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
    /**
     * method for retrieving search results
     * @param actionEvent search enter press
     */
    public void onSearch1(ActionEvent actionEvent) {
        String q = partsSearch.getText();
        ObservableList<Part> parts = inv.lookUpPart(q);
        partsTable.setItems(parts);
        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part p = inv.lookUpPart(id);
                if (p != null) {
                    parts.add(p);
                }
            } catch (NumberFormatException e){

            }
        }
    }
    /**
     * method for retrieving search results
     * @param actionEvent search enter press
     */
    public void onSearch2(ActionEvent actionEvent) {
        String q = productsSearch.getText();
        ObservableList<Product> products = inv.lookUpProduct(q);
        productsTable.setItems(products);
        if (products.size() == 0){
            int id =  Integer.parseInt(q);
            Product p = inv.lookUpProduct(id);
            if (p != null) {
                products.add(p);
            }
        }
    }
    /**
     * Closes the program
     * @param actionEvent exit button press
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * methods for each error case
     * @param code determines which error message to show
     */
    private void errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory");
            alert.setContentText("List is empty.");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("No item selected.");
            alert.showAndWait();
        }
        if (code == 3) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("Can not delete product with associated parts.");
            alert.showAndWait();
        }
    }

    /**
     * method for confirmation of user deletion action
     * @param name name of part/product to delete
     * @return confirms or declines
     */
    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete " + name + "?");
        alert.setContentText("Click ok to confirm deletion.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}