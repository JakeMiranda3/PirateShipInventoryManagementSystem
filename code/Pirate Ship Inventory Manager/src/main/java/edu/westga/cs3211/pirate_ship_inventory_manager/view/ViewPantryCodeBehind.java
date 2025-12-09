package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;
import java.util.stream.Collectors;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewPantryViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Code-behind for the Pantry view. Binds controls to the ViewPantryViewModel.
 *@version Fall 2025
 * @author Markees Ewers
 */
public class ViewPantryCodeBehind {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Stock> pantryListView;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Spinner<Integer> amountSpinner;

    @FXML
    private Button takeFoodButton;

    private ViewPantryViewModel viewModel;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        this.viewModel = new ViewPantryViewModel();

        this.pantryListView.setItems(this.viewModel.getFilteredStock());

        this.filterComboBox.getItems().addAll(
                java.util.Arrays.stream(StockType.values()).map(Enum::name).collect(Collectors.toList())
        );
        this.searchField.textProperty().bindBidirectional(this.viewModel.getSearchTextProperty());
        
        this.filterComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedFilterProperty());

        // Spinner binding
        this.amountSpinner.setValueFactory(
                new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        this.amountSpinner.getValueFactory().valueProperty().bindBidirectional(this.viewModel.getAmountToTakeProperty().asObject());
        	
        this.takeFoodButton.setOnAction(ex -> this.handleTakeFood());
    }

    private void handleTakeFood() {
        Stock selected = this.pantryListView.getSelectionModel().getSelectedItem();
        String error = this.viewModel.takeFood(selected);
        if (error != null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, error);
            alert.showAndWait();
            return;
        }
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Food taken successfully.");
        alert.showAndWait();
    }

    @FXML
    void handleHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPirateShipInventoryWindow.fxml"));
            Parent main = loader.load();
            Stage stage = (Stage) this.rootPane.getScene().getWindow();
            stage.setScene(new Scene(main));
            stage.show();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

}