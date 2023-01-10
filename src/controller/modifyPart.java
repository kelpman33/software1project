package controller;
/**
 * modifyPart class
 */
/**
 * @author James Badke
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;

public class modifyPart implements Initializable {
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
     * retrieves inventory from main and part values from part selected at main screen
     */
    Inventory inv = Main.inv;
    private static Part currentPart;

    @Override
    /**
     * immediately runs, sets radio button value depending on part type, sets text field values
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (currentPart instanceof InHouse) {
            inHouseBtn.setSelected(true);
        } else if (currentPart instanceof OutSourced){
            outsourceBtn.setSelected(true);
        }
        setFields();
    }

    /**
     * updates selected part's values, unless error cases arise
     * @param actionEvent
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
            inv.updatePart(currentPart.getId(), newPart);
            goToMainScreen();
        } else if (outsourceBtn.isSelected() == true){
            String companyName = machineIDText.getText();
            Part newPart = new OutSourced(id, name, price, stock, min, max, companyName);
            inv.updatePart(currentPart.getId(), newPart);
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

    /**
     * sets the values of the text fields to the values of the selected part
     */
    public void setFields(){
        if (currentPart instanceof InHouse) {
            idText.setText(Integer.toString(currentPart.getId()));
            nameText.setText(currentPart.getName());
            priceText.setText(Double.toString(currentPart.getPrice()));
            invText.setText(Integer.toString(currentPart.getStock()));
            minText.setText(Integer.toString(currentPart.getMin()));
            maxText.setText(Integer.toString(currentPart.getMax()));
            machineIDText.setText(Integer.toString(((InHouse) currentPart).getMachineID()));
        } else
        if (currentPart instanceof OutSourced) {
            idText.setText(Integer.toString(currentPart.getId()));
            nameText.setText(currentPart.getName());
            priceText.setText(Double.toString(currentPart.getPrice()));
            invText.setText(Integer.toString(currentPart.getStock()));
            minText.setText(Integer.toString(currentPart.getMin()));
            maxText.setText(Integer.toString(currentPart.getMax()));
            machineIDText.setText(((OutSourced) currentPart).getCompanyName());
        }

        idText.setEditable(false);
        idText.setDisable(true);
    }

    /**
     * methods that change label text depending on radio button
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

    /**
     * retrieves part from main screen
     * @param part
     */
    public static void sendPart(Part part){
        currentPart = part;
    }
}