package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewStockChangesViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * View Stock Changes Code Behind class
 * 
 * @author Jake Miranda
 * @version Fall 2025
 */
public class ViewStockChangesCodeBehind {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private ComboBox<User> crewmateBox;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private ComboBox<SpecialQuality> specialQualityBox;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private ListView<StockChange> stockChangesList;

	private ViewStockChangesViewModel viewModel;

	/**
	 * Instantiates a new view stock changes code behind.
	 */
	public ViewStockChangesCodeBehind() {
		this.viewModel = new ViewStockChangesViewModel();
	}

	@FXML
	private void initialize() {
		this.stockChangesList.setItems(this.viewModel.getFilteredChangesProperty());

		this.specialQualityBox.getItems().add(null);
		this.specialQualityBox.getItems().addAll(SpecialQuality.values());

		this.specialQualityBox.valueProperty().bindBidirectional(this.viewModel.getSelectedSpecialQuality());

		this.crewmateBox.getItems().add(null);
		this.crewmateBox.getItems().addAll(Session.getInventory().getUsersWhoMadeChanges());

		this.crewmateBox.valueProperty().bindBidirectional(this.viewModel.getSelectedCrewmateProperty());
		this.startDatePicker.valueProperty().bindBidirectional(this.viewModel.startDateProperty());
		this.endDatePicker.valueProperty().bindBidirectional(this.viewModel.endDateProperty());
	}

	@FXML
	private void handleApplyFilters(ActionEvent event) {
		try {
			this.viewModel.applyFilters();
		} catch (IllegalArgumentException error) {
			new Alert(Alert.AlertType.ERROR, error.getMessage()).showAndWait();
		}

	}

	@FXML
	private void handleHome(ActionEvent event) {
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

	@FXML
	private void handleResetFilters(ActionEvent event) {
		this.viewModel.clearFilters();
		this.specialQualityBox.setValue(null);
		this.crewmateBox.setValue(null);
		this.startDatePicker.setValue(null);
		this.endDatePicker.setValue(null);
	}

}
