package edu.westga.cs3211.pirate_ship_inventory_manager.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.StockChange;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.AddStockViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Add Stock Code Behind
 * 
 * @author Jake miranda
 * @version Fall 2025
 */
public class AddStockCodeBehind {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private ComboBox<Condition> conditionBox;

	@FXML
	private ComboBox<StockType> stockTypeBox;

	@FXML
	private CheckBox flammableCheck;

	@FXML
	private CheckBox liquidCheck;

	@FXML
	private TextField nameField;

	@FXML
	private CheckBox noneCheck;

	@FXML
	private CheckBox perishableCheck;

	@FXML
	private Spinner<Integer> quantitySpinner;

	@FXML
	private DatePicker expirationDatePicker;

	@FXML
	private ComboBox<Compartment> compartmentBox;

	private AddStockViewModel viewModel;

	/**
	 * Instantiates a new adds the stock code behind.
	 */
	public AddStockCodeBehind() {
		this.viewModel = new AddStockViewModel();

	}

	@FXML
	private void handleHome() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPirateShipInventoryWindow.fxml"));

			Parent mainPage = loader.load();

			Scene scene = new Scene(mainPage);
			Stage stage = (Stage) this.rootPane.getScene().getWindow();

			stage.setScene(scene);
			stage.show();
		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	@FXML
	private void handleAddStockToCompartment() {
		String validationError = this.viewModel.validateInput();

		if (validationError != null) {
			Alert alert = new Alert(Alert.AlertType.ERROR, validationError);
			alert.showAndWait();
			return;
		}

		Stock stock = new Stock(this.viewModel.nameProperty().get(), this.viewModel.quantityProperty().get(),
				this.viewModel.conditionProperty().get(), this.viewModel.buildSpecialQualities(),
				this.viewModel.expirationDateProperty().get(), this.viewModel.stockTypeProperty().get());

		Compartment selectedCompartment = this.compartmentBox.getValue();

		if (selectedCompartment == null) {
			new Alert(Alert.AlertType.ERROR, "Please select a compartment.").showAndWait();
			return;
		}

		if (stock.getQuantity() > selectedCompartment.getRemainingCapacity()) {
			new Alert(Alert.AlertType.ERROR, "This compartment only has " + selectedCompartment.getRemainingCapacity()
					+ " units of remaining capacity.").showAndWait();
			return;
		}

		if (!selectedCompartment.canStoreStock(stock)) {
			new Alert(Alert.AlertType.ERROR, "This compartment cannot store that type of stock").showAndWait();
			return;
		}

		selectedCompartment.addStock(stock);
		Session.getInventory().addStockChange(new StockChange(stock, Session.getCurrentUser()));

		int index = this.compartmentBox.getItems().indexOf(selectedCompartment);
		this.compartmentBox.getItems().set(index, selectedCompartment);
		new Alert(Alert.AlertType.INFORMATION, "Stock added successfully!").showAndWait();

	}

	@FXML
	private void initialize() {

		this.quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));

		this.conditionBox.getItems().addAll(Condition.values());

		this.stockTypeBox.getItems().addAll(StockType.values());

		this.nameField.textProperty().bindBidirectional(this.viewModel.nameProperty());
		this.quantitySpinner.getValueFactory().valueProperty()
				.bindBidirectional(this.viewModel.quantityProperty().asObject());
		this.conditionBox.valueProperty().bindBidirectional(this.viewModel.conditionProperty());
		this.stockTypeBox.valueProperty().bindBidirectional(this.viewModel.stockTypeProperty());

		this.noneCheck.selectedProperty().bindBidirectional(this.viewModel.noneSelectedProperty());
		this.flammableCheck.selectedProperty().bindBidirectional(this.viewModel.flammableSelectedProperty());
		this.liquidCheck.selectedProperty().bindBidirectional(this.viewModel.liquidSelectedProperty());
		this.perishableCheck.selectedProperty().bindBidirectional(this.viewModel.perishableSelectedProperty());

		this.expirationDatePicker.valueProperty().bindBidirectional(this.viewModel.expirationDateProperty());
		this.expirationDatePicker.disableProperty().bind(this.viewModel.expirationDateEnabledProperty().not());

		this.compartmentBox.getItems().addAll(Session.getInventory().getCompartments());
		this.compartmentBox.valueProperty().bindBidirectional(this.viewModel.selectedCompartmentProperty());

	}

}
