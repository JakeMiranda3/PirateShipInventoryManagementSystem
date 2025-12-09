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
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
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
    
    @Test
    void testTakeFoodAmountWhenNull() {
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
		viewModel.takeFood(null);
		assertEquals(0, viewModel.getFilteredStock().size());
		assertDoesNotThrow(() -> { viewModel.takeFood(null); });
    }
    @Test
    void testTakeFoodAmountWhenNotNull() {
    	Stock stockItem = new Stock("Apple", 10, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
		viewModel.takeFood(stockItem);
		assertEquals(0, viewModel.getFilteredStock().size());
		assertDoesNotThrow(() -> { viewModel.takeFood(null); });
    }
    @Test
    void testTakeFoodAmountWhenAmountIsZero() {
    	Stock stockItem = new Stock("Apple", 10, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    	
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
    			viewModel.getAmountToTakeProperty().set(0);
		var actual = viewModel.takeFood(stockItem);
		
		assertEquals("Amount must be at least 1." , actual);
		
    } 
    @Test
    void testTakeFoodAmountWhenNotEnoughQuantity() {
    	Stock stockItem = new Stock("Apple", 10, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    	
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
    			
    			viewModel.getAmountToTakeProperty().set(15);
		var actual = viewModel.takeFood(stockItem);
		
		assertEquals("Selected item no longer exists in inventory." , actual);
		
    }
    @Test
    void testTakeFoodAmountWhenExistInInventory() {
    	Stock stockItem = new Stock("Apple", 1, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    	
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
    			Inventory inventory = Session.getInventory();
				Compartment regular = inventory.getCompartments().get(0);
				regular.addStock(stockItem);
    			
    			viewModel.getAmountToTakeProperty().set(5);
		var actual = viewModel.takeFood(stockItem);
		
		assertEquals("Not enough quantity available." , actual);
		
    }  @Test
    void testTakeFoodAmountWhenAmountExceedsQuantity() {
    	Stock stockItem = new Stock("Apple", 1, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    	
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
    			Inventory inventory = Session.getInventory();
    			Session.setInventory(inventory);
    			User testUser = new User("TestUser", "password");
    			Session.setCurrentuser(testUser);
    			viewModel.getAmountToTakeProperty().set(5);
				Compartment regular = inventory.getCompartments().get(0);
				regular.addStock(stockItem);
    			
    			
		var actual = viewModel.takeFood(stockItem);
		
		assertEquals("Not enough quantity available." , actual);
		
    } @Test
    void testTakeFoodAmountWhenAmountEqualsQuantity() {
    	Stock stockItem = new Stock("Apple",5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
				StockType.FOOD);
    	
    			ViewPantryViewModel viewModel = new ViewPantryViewModel();
    			Inventory inventory = Session.getInventory();
    			Session.setInventory(inventory);
    			User testUser = new User("TestUser", "password");
    			Session.setCurrentuser(testUser);
    			viewModel.getAmountToTakeProperty().set(5);
				Compartment regular = inventory.getCompartments().get(0);
				regular.addStock(stockItem);
    			
    			
		var actual = viewModel.takeFood(stockItem);
		
		assertEquals(null , actual);
		
    }  
    @Test

    void testTakeFoodAmountWhenAmountDoesntEqualsQuantity() {

        Stock stockItem = new Stock("Apple", 10, Condition.PERFECT, 

                                    EnumSet.of(SpecialQuality.NONE), null, StockType.FOOD);

        

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        Inventory inventory = Session.getInventory();

        Session.setInventory(inventory);

        User testUser = new User("TestUser", "password");

        Session.setCurrentuser(testUser);

        

        // Set amount to 3 (less than quantity 10) to hit the else statement

        viewModel.getAmountToTakeProperty().set(3);

        

        Compartment regular = inventory.getCompartments().get(0);

        regular.addStock(stockItem);

        

        var actual = viewModel.takeFood(stockItem);

        assertEquals(null, actual);

        

        // Verify the stock was replaced with reduced quantity stock

        // Original quantity: 10, amount taken: 3, new quantity should be: 7

        Stock remainingStock = regular.getStoredStock().get(0);

        assertEquals(7, remainingStock.getQuantity());

        assertEquals("Apple", remainingStock.getName());

        assertEquals(Condition.PERFECT, remainingStock.getCondition());

    }

}