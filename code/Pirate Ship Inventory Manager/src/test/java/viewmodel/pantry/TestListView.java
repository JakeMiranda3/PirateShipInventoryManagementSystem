package viewmodel.pantry;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.StockType;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Inventory;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.ViewPantryViewModel;

class TestListView {

    @BeforeEach
    void setUp() {
        Session.setInventory(new Inventory());
    }

    @AfterEach
    void tearDown() {
        Session.setInventory(null);
    }

    @Test
    void testEmptyInventoryShowsEmptyList() {
        ViewPantryViewModel viewModel = new ViewPantryViewModel();
        assertEquals(0, viewModel.getFilteredStock().size());
    }

    @Test
    void testOnlyFoodPopulated() {
        Inventory inventory = Session.getInventory();
        Compartment regular = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.USABLE, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        regular.addStock(food);
        regular.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();
        // pantry list should only contain FOOD items
        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Bread", viewModel.getFilteredStock().get(0).getName());
    }

    @Test
    void testSearchFiltersFoodList() {
        Inventory inventory = Session.getInventory();
        Compartment regular = inventory.getCompartments().get(0);

        Stock apple = new Stock("Apple", 4, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock bread = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock hammer = new Stock("Hammer", 2, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        regular.addStock(apple);
        regular.addStock(bread);
        regular.addStock(hammer);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        // search for "app" should match "Apple" only
        viewModel.getSearchTextProperty().set("app");
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Apple", viewModel.getFilteredStock().get(0).getName());
    }

    @Test
    void testSelectedFilterStringFiltersByStockType() {
        Inventory inventory = Session.getInventory();
        Compartment regular = inventory.getCompartments().get(0);

        Stock apple = new Stock("Apple", 4, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock wrench = new Stock("Wrench", 1, Condition.USABLE, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        regular.addStock(apple);
        regular.addStock(wrench);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        // initially pantry view should show only food
        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Apple", viewModel.getFilteredStock().get(0).getName());

        // set selectedFilter to OTHER to switch to other-type items
        viewModel.getSelectedFilterProperty().set(StockType.OTHER.name());
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Wrench", viewModel.getFilteredStock().get(0).getName());
    }

}