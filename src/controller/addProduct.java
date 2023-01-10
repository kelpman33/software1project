package controller;
/**
 * addProduct class
 */
/**
 * @author James Badke
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.*;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addProduct implements Initializable {
    public TextField idText;
    public TextField nameText;
    public TextField invText;
    public TextField priceText;
    public TextField maxText;
    public TextField minText;
    public TextField searchText;
    public Button addBtn;
    public Button removeBtn;
    public Button saveBtn;
    public Button cancelBtn;
    public TableView partsTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInvColumn;
    public TableColumn partPriceColumn;
    public TableView assPartsTable;
    public TableColumn assPartIDColumn;
    public TableColumn assPartNameColumn;
    public TableColumn assPartInvColumn;
    public TableColumn assPartPriceColumn;

    /**
     * retrieves inventory from main and creates list of associated parts for products
     */
    Inventory inv = Main.inv;
    public ObservableList<Part> assPartsList = FXCollections.observableArrayList();

    @Override
    /**
     * method runs immediately after screen initialization, creates and populates tables
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autoGenID();
        partsTable.setItems(inv.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        assPartsTable.setItems(assPartsList);
        assPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * search function
     * @param actionEvent
     */
    public void onSearch(ActionEvent actionEvent) {
        String q = searchText.getText();
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
     * adds part to current product's list of associated parts
     * @param actionEvent add button click
     */
    public void onAdd(ActionEvent actionEvent) {
        Part newPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        assPartsList.add(newPart);
    }

    /**
     * removes part from current product's list of associated parts
     * @param actionEvent
     */
    public void onRemove(ActionEvent actionEvent) {
        Part removePart = (Part) assPartsTable.getSelectionModel().getSelectedItem();
        if (assPartsList.isEmpty()) {
            errorWindow(1);
            return;
        }
        if (!assPartsList.isEmpty() && removePart == null) {
            errorWindow(2);
            return;
        } else {
            boolean confirm = confirmationWindow(removePart.getName());
            if (!confirm) {
                return;
            }
            assPartsList.remove(removePart);
            assPartsTable.refresh();
        }
    }

    /**
     * adds product to inventory unless error cases arise
     * @param actionEvent save button click
     * @throws Exception
     */
    public void onSave(ActionEvent actionEvent) throws Exception {
        int id = Integer.parseInt(idText.getText());
        String name = nameText.getText();
        double price = Double.parseDouble(priceText.getText());
        int stock = Integer.parseInt(invText.getText());
        int min = Integer.parseInt(minText.getText());
        int max = Integer.parseInt(maxText.getText());
        if (min > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Values");
            alert.setContentText("Min must be less than or equal to max.");
            alert.showAndWait();
        } else if (stock > max || stock < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Values");
            alert.setContentText("Stock must be between min and max.");
            alert.showAndWait();
        } else {
            Product newProd = new Product(id,name,price,stock,min,max);
            newProd.setAssociatedParts(assPartsList);
            inv.addProduct(newProd);
            goToMainScreen();
        }
    }

    /**
     * goes back to main screen
     * @param actionEvent cancel button click
     * @throws Exception
     */
    public void onCancel(ActionEvent actionEvent) throws Exception {
        goToMainScreen();
    }

    /**
     * automatically assigns sequential ID for part to add
     */
    public void autoGenID(){
        int newProductID = inv.productListSize() + 1;
        idText.setText(String.valueOf(newProductID));
        idText.setEditable(false);
        idText.setDisable(true);
    }

    /**
     * goes to main screen
     * @throws Exception
     */
    public void goToMainScreen() throws Exception {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * windows for error cases
     * @param code which error window to display
     */
    private void errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory");
            alert.setContentText("List is empty.");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("No item selected.");
            alert.showAndWait();
        }
    }

    /**
     * method for confirming user action
     * @param name name of part to remove from associated parts list
     * @return confirm or decline
     */
    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove");
        alert.setHeaderText("Are you sure you want to remove " + name + " from associated parts?");
        alert.setContentText("Click ok to confirm.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
