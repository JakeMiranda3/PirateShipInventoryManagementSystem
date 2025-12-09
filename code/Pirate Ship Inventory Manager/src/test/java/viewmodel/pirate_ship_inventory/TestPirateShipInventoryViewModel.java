package viewmodel.pirate_ship_inventory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Role;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.Session;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.User;
import edu.westga.cs3211.pirate_ship_inventory_manager.viewmodel.PirateShipInventoryViewModel;

public class TestPirateShipInventoryViewModel {

	@Test
	public void testUserWithQuarterMasterRole() {
		User jane = new User("Jane", "Doe");
		jane.addRole(Role.QUARTERMASTER);
		Session.setCurrentuser(jane);
		
		PirateShipInventoryViewModel viewModel = new PirateShipInventoryViewModel();
		
		assertTrue(viewModel.isQuarterMasterProperty().get(), "Checks if user has the Quartermaster role");
	}
	@Test
	public void testUserWithChefRole() {
		User jane = new User("Jane", "Doe");
		jane.addRole(Role.CHEF);
		Session.setCurrentuser(jane);
		
		PirateShipInventoryViewModel viewModel = new PirateShipInventoryViewModel();
		
		assertTrue(viewModel.isChefProperty().get(), "Checks if user has the Chef role");
	}
	
	@Test
	public void testUserWithoutQuarterMasterRole() {
		User jane = new User("Jane", "Doe");
		Session.setCurrentuser(jane);
		
		PirateShipInventoryViewModel viewModel = new PirateShipInventoryViewModel();
		
		assertFalse(viewModel.isQuarterMasterProperty().get(), "Checks if user has the Quartermaster role");
	}

}
