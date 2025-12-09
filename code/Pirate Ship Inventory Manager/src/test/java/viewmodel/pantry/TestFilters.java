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

class TestFilters {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        Session.setInventory(inventory);
    }

    @AfterEach
    void tearDown() {
        Session.setInventory(null);
    }

    @Test
    void testConstructorInitializesWithOnlyFood() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        // constructor filters to FOOD only
        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Bread", viewModel.getFilteredStock().get(0).getName());
    }

    @Test
    void testApplyFilters_noFilter_noSearch_showsAll() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        viewModel.getSelectedFilterProperty().set(null);
        viewModel.getSearchTextProperty().set("");
        viewModel.applyFilters();

        assertEquals(2, viewModel.getFilteredStock().size());
    }

    @Test
    void testSelectedFilterValidEnum_foodOnly() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        viewModel.getSelectedFilterProperty().set("FOOD");
        viewModel.getSearchTextProperty().set("");
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Bread", viewModel.getFilteredStock().get(0).getName());
    }

    @Test
    void testSelectedFilterInvalidEnum_fallsBackToUnfiltered() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        viewModel.getSelectedFilterProperty().set("INVALID");
        viewModel.getSearchTextProperty().set("");
        viewModel.applyFilters();

        // invalid enum should be ignored and show all
        assertEquals(2, viewModel.getFilteredStock().size());
    }

    @Test
    void testSearchTextNonBlank_matchesName() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        viewModel.getSelectedFilterProperty().set(null);
        viewModel.getSearchTextProperty().set("bread");
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Bread", viewModel.getFilteredStock().get(0).getName());
    }

    @Test
    void testFilterAndSearchTogether() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);

        ViewPantryViewModel viewModel = new ViewPantryViewModel();

        viewModel.getSelectedFilterProperty().set("OTHER");
        viewModel.getSearchTextProperty().set("tool");
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Tools", viewModel.getFilteredStock().get(0).getName());
    }
    @Test
    void testFilterAndSearchTogetherIfFilterEmpty() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);
        
        ViewPantryViewModel viewModel = new ViewPantryViewModel();
viewModel.getSelectedFilterProperty().set("");
        viewModel.getSelectedFilterProperty().set("OTHER");
        viewModel.getSearchTextProperty().set("tool");
        viewModel.applyFilters();

        assertEquals(1, viewModel.getFilteredStock().size());
        assertEquals("Tools", viewModel.getFilteredStock().get(0).getName());
    }
    
    @Test
    void testFilterAndSearchTogetherIfTextIsValid() {
        Compartment noneComp = inventory.getCompartments().get(0);

        Stock food = new Stock("Bread", 3, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.FOOD);
        Stock other = new Stock("Tools", 5, Condition.PERFECT, EnumSet.of(SpecialQuality.NONE), null,
                StockType.OTHER);

        noneComp.addStock(food);
        noneComp.addStock(other);
        
        ViewPantryViewModel viewModel = new ViewPantryViewModel();
       viewModel.getSelectedFilterProperty().set("OTHER");
		viewModel.getSearchTextProperty().set("Tools");
		
		assertEquals(1, viewModel.getFilteredStock().size());
		assertEquals("Tools", viewModel.getFilteredStock().get(0).getName());
viewModel.getSelectedFilterProperty().set("safae");
     
        viewModel.getSearchTextProperty().set("Ficv");
        viewModel.applyFilters();

        assertEquals(0, viewModel.getFilteredStock().size());
       
    

}}