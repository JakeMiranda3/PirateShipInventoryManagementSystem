package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.ActionType;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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

	private ComboBox<ActionType> actionTypeBox;
	@FXML
	private DatePicker startDatePicker;

	@FXML
	private ListView<StockChange> stockChangesList;

	@FXML
	private Spinner<Integer> startHourSpinner;
	@FXML
	private Spinner<Integer> startMinuteSpinner;
	@FXML
	private Spinner<Integer> endHourSpinner;
	@FXML
	private Spinner<Integer> endMinuteSpinner;

	private ViewStockChangesViewModel viewModel;

	/**
	 * Instantiates a new view stock changes code behind.
	 */
	public ViewStockChangesCodeBehind() {
		this.viewModel = new ViewStockChangesViewModel();
	}

	@FXML
	private void initialize() {
		this.startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
		this.startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
		this.endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 23));
		this.endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 59));
		this.stockChangesList.setItems(this.viewModel.getFilteredChangesProperty());

		this.specialQualityBox.getItems().add(null);
		this.specialQualityBox.getItems().addAll(SpecialQuality.values());

		this.specialQualityBox.valueProperty().bindBidirectional(this.viewModel.getSelectedSpecialQuality());

		this.crewmateBox.getItems().add(null);
		this.crewmateBox.getItems().addAll(Session.getInventory().getUsersWhoMadeChanges());

		this.crewmateBox.valueProperty().bindBidirectional(this.viewModel.getSelectedCrewmateProperty());

		this.startDatePicker.valueProperty().bindBidirectional(this.viewModel.startDateProperty());
		this.endDatePicker.valueProperty().bindBidirectional(this.viewModel.endDateProperty());

		this.startHourSpinner.getValueFactory().valueProperty()
				.bindBidirectional(this.viewModel.startHourProperty().asObject());
		this.startMinuteSpinner.getValueFactory().valueProperty()
				.bindBidirectional(this.viewModel.startMinuteProperty().asObject());
		this.endHourSpinner.getValueFactory().valueProperty()
				.bindBidirectional(this.viewModel.endHourProperty().asObject());
		this.endMinuteSpinner.getValueFactory().valueProperty()
				.bindBidirectional(this.viewModel.endMinuteProperty().asObject());

		this.actionTypeBox.getItems().add(null);
		this.actionTypeBox.getItems().addAll(ActionType.values());
		this.actionTypeBox.valueProperty().bindBidirectional(this.viewModel.getSelectedActionType());

	}

	@FXML
	private void handleApplyFilters(ActionEvent event) {
		try {
			this.viewModel.applyFilters();
			this.stockChangesList.refresh();
		} catch (IllegalArgumentException error) {
			new Alert(Alert.AlertType.ERROR, error.getMessage()).showAndWait();
		} catch (Exception error) {
			new Alert(Alert.AlertType.ERROR, "An unexpected error occured, please try again").showAndWait();
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
		this.specialQualityBox.setValue(null);
		this.crewmateBox.setValue(null);
		this.actionTypeBox.setValue(null);
		this.startDatePicker.setValue(null);
		this.endDatePicker.setValue(null);

		this.startHourSpinner.getValueFactory().setValue(0);
		this.startMinuteSpinner.getValueFactory().setValue(0);
		this.endHourSpinner.getValueFactory().setValue(23);
		this.endMinuteSpinner.getValueFactory().setValue(59);

		this.viewModel.clearFilters();
		this.stockChangesList.refresh();
	}

}
