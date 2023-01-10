package controller;
/**
 * addPart class
 */
/**
 * @author James Badke
 */

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;
import model.InHouse;
import model.OutSourced;
import model.Part;
import model.Inventory;

import java.net.URL;
import java.util.ResourceBundle;

public class addPart implements Initializable{
    public RadioButton inHouseBtn;
    public ToggleGroup buttonTG;
    public RadioButton outsourceBtn;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField idText;
    public TextField nameText;
    public TextField invText;
    public TextField priceText;
    public TextField maxText;
    public TextField machineIDText;
    public TextField minText;
    public AnchorPane addPartWindow;
    public Label machineIDLabel;

    /**
     * retrieves inventory from main
     */
    Inventory inv = Main.inv;

    @Override
    /**
     * method called upon screen initialization
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
    autoGenID();
    inHouseBtn.setSelected(true);
    }

    /**
     * adds part to inventory, unless error cases arise
     * @param actionEvent save button press
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
        } else if (inHouseBtn.isSelected() == true){
            int machineID = Integer.parseInt(machineIDText.getText());
            Part newPart = new InHouse(id, name, price, stock, min, max, machineID);
            inv.addPart(newPart);
            goToMainScreen();
        } else if (outsourceBtn.isSelected() == true){
            String companyName = machineIDText.getText();
            Part newPart = new OutSourced(id, name, price, stock, min, max, companyName);
            inv.addPart(newPart);
            goToMainScreen();
        }
    }

    /**
     * goes back to main screen
     * @param actionEvent cancel button press
     * @throws Exception
     */
    public void onCancel(ActionEvent actionEvent) throws Exception {
        goToMainScreen();
    }
    public void autoGenID(){
        int newPartID = inv.partListSize() + 1;
        idText.setText(String.valueOf(newPartID));
        idText.setEditable(false);
        idText.setDisable(true);
    }

    /**
     * methods for changing text depending on radio button
     * @param actionEvent radio button press
     */
    public void selectInHouse(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
    }

    public void selectOutsourced(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name");
    }

    /**
     * goes back to main screen
     * @throws Exception
     */
    public void goToMainScreen() throws Exception {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
