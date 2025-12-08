package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewInventoryViewModel;

import java.io.IOException;
import java.util.stream.Collectors;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

/**
 * The Class ViewInventoryCodeBehind.
 * 
 * @author fmensah1
 * @version fall 2025
 */
public class ViewInventoryCodeBehind {

    /** The compartment combo box. */
    @FXML
    private ComboBox<String> compartmentComboBox;

    /** The inventory list view. */
    @FXML
    private ListView<Stock> inventoryListView;

    /** The root pane. */
    @FXML
    private AnchorPane rootPane;

    /** The special quality combo box. */
    @FXML
    private ComboBox<SpecialQuality> specialQualityComboBox;

    /** The stock type combo box. */
    @FXML
    private ComboBox<StockType> stockTypeComboBox;

    /** The view model. */
    private ViewInventoryViewModel viewModel;
    
    /**
     * Initialize.
     */
    @FXML
    public void initialize() {

        this.viewModel = new ViewInventoryViewModel();
        
        this.inventoryListView.setItems(this.viewModel.getFilteredStock());

        this.compartmentComboBox.getItems().addAll(
            Session.getInventory().getCompartments()
                .stream()
                .map(Compartment::getCompartmentName)
                .collect(Collectors.toList())
        );
        
        this.specialQualityComboBox.getItems().addAll(SpecialQuality.values()); 
        this.stockTypeComboBox.getItems().addAll(StockType.values());

        this.compartmentComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedCompartmentProperty());
        this.specialQualityComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedQualityProperty());
        this.stockTypeComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedStockTypeProperty());
        this.compartmentComboBox.setOnAction(e -> this.viewModel.applyFilters());
        this.specialQualityComboBox.setOnAction(e -> this.viewModel.applyFilters());
        this.stockTypeComboBox.setOnAction(e -> this.viewModel.applyFilters());
    }

    /**
     * Handle home.
     */
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
