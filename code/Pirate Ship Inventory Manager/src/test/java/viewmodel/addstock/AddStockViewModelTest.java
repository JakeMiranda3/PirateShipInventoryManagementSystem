package viewmodel.addstock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.AddStockViewModel;

public class AddStockViewModelTest {

	@Test
	public void testConstructorInitializesProperties() {
		AddStockViewModel viewModel = new AddStockViewModel();

		assertEquals("", viewModel.nameProperty().get(), "Checks name property");
		assertEquals(1, viewModel.quantityProperty().get(), "Checks quantity property");
		assertNull(viewModel.conditionProperty().get(), "Checks condition property");
		assertFalse(viewModel.noneSelectedProperty().get(), "Checks the none selected property");
		assertFalse(viewModel.flammableSelectedProperty().get(), "Checks the flammable selected property");
		assertFalse(viewModel.liquidSelectedProperty().get(), "Checks the liquid selected property");
		assertFalse(viewModel.perishableSelectedProperty().get(), "Checks the perishable selected property");
		assertFalse(viewModel.expirationDateEnabledProperty().get(), "Checks the expiration date enabled property");
		assertNull(viewModel.expirationDateProperty().get(), "Checks the expiration date property");
		assertNull(viewModel.selectedCompartmentProperty().get(), "Checks the selected compartment property");
	}

	@Test
	public void testSelectingNoneClearsOtherQualities() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.flammableSelectedProperty().set(true);
		viewModel.liquidSelectedProperty().set(true);
		viewModel.perishableSelectedProperty().set(true);

		viewModel.noneSelectedProperty().set(true);

		assertTrue(viewModel.noneSelectedProperty().get(), "Checks the none selected property");
		assertFalse(viewModel.flammableSelectedProperty().get(), "Checks the flammable selected property");
		assertFalse(viewModel.liquidSelectedProperty().get(), "Checks the liquid selected property");
		assertFalse(viewModel.perishableSelectedProperty().get(), "Checks the perishable selected property");
	}

	@Test
	public void testSelectingOtherQualitiesClearsNone() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.noneSelectedProperty().set(true);
		viewModel.flammableSelectedProperty().set(true);

		assertFalse(viewModel.noneSelectedProperty().get(), "Checks the boolean value of the none selected property");
	}

	@Test
	public void testPerishableEnablesExpirationDate() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.perishableSelectedProperty().set(true);

		assertTrue(viewModel.expirationDateEnabledProperty().get(),
				"Checks the boolean value of the expiration date enabled property");
	}

	@Test
	public void testUnselectingPerishableClearsExpirationDate() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.perishableSelectedProperty().set(true);
		viewModel.expirationDateProperty().set(LocalDate.now());

		viewModel.perishableSelectedProperty().set(false);

		assertNull(viewModel.expirationDateProperty().get(), "Checks the value of the expiration date property");
		assertFalse(viewModel.expirationDateEnabledProperty().get(),
				"Checks the boolean value of expiration date enabled property");
	}

	@Test
	public void testBuildSpecialQualitiesWithNone() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.noneSelectedProperty().set(true);

		Set<SpecialQuality> results = viewModel.buildSpecialQualities();
		assertTrue(results.contains(SpecialQuality.NONE), "Checks if the special quality contains NONE");
		assertEquals(1, results.size(), "Checks the size of the selected qualities when only none is selected");
	}

	@Test
	public void testBuildSpecialQualitiesMultiple() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.flammableSelectedProperty().set(true);
		viewModel.liquidSelectedProperty().set(true);

		Set<SpecialQuality> results = viewModel.buildSpecialQualities();
		assertTrue(results.contains(SpecialQuality.FLAMMABLE), "Ensures that flammable is selected");
		assertTrue(results.contains(SpecialQuality.LIQUID), "Ensures that liquid is selected");
		assertEquals(2, results.size(), "Checks the amount of selected qualities");
	}

	@Test
	public void testValidateInputFailsWhenNameEmpty() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("");
		viewModel.quantityProperty().set(10);
		viewModel.conditionProperty().set(Condition.PERFECT);
		viewModel.noneSelectedProperty().set(true);

		assertEquals("Stock name cannot be empty.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenNameIsNull() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set(null);
		viewModel.quantityProperty().set(10);
		viewModel.conditionProperty().set(Condition.PERFECT);
		viewModel.noneSelectedProperty().set(true);

		assertEquals("Stock name cannot be empty.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenQuantityZero() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Gold");
		viewModel.quantityProperty().set(0);
		viewModel.conditionProperty().set(Condition.USABLE);
		viewModel.noneSelectedProperty().set(true);

		assertEquals("Quantity must be greater than zero.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenConditionMissing() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Gold");
		viewModel.quantityProperty().set(5);
		viewModel.noneSelectedProperty().set(true);

		assertEquals("Please select a condition.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenNoSpecialQualitySelected() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Gold");
		viewModel.quantityProperty().set(5);
		viewModel.conditionProperty().set(Condition.UNUSABLE);

		assertEquals("Please select at least one special quality.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenPerishableMissingExpiration() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Food");
		viewModel.quantityProperty().set(10);
		viewModel.conditionProperty().set(Condition.PERFECT);
		viewModel.perishableSelectedProperty().set(true);

		assertEquals("Perishable items must have an expiration date.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputFailsWhenNonPerishableHasExpiration() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Gold");
		viewModel.quantityProperty().set(10);
		viewModel.conditionProperty().set(Condition.UNUSABLE);
		viewModel.noneSelectedProperty().set(true);
		viewModel.expirationDateProperty().set(LocalDate.now());

		assertEquals("Only perishable items can have an expiration date.", viewModel.validateInput(),
				"Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputSucceedsWithValidNoneQuality() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Gold");
		viewModel.quantityProperty().set(2);
		viewModel.conditionProperty().set(Condition.USABLE);
		viewModel.noneSelectedProperty().set(true);

		assertNull(viewModel.validateInput(), "Ensures the validation returns the correct response");
	}

	@Test
	public void testValidateInputSucceedsWithValidPerishable() {
		AddStockViewModel viewModel = new AddStockViewModel();

		viewModel.nameProperty().set("Food");
		viewModel.quantityProperty().set(5);
		viewModel.conditionProperty().set(Condition.PERFECT);
		viewModel.perishableSelectedProperty().set(true);
		viewModel.expirationDateProperty().set(LocalDate.now());

		assertNull(viewModel.validateInput(), "Ensures the validation returns the correct response");
	}

}
