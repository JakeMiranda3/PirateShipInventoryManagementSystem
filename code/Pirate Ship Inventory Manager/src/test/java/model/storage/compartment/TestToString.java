package model.storage.compartment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Compartment;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

class TestToString {

	@Test
	void testToStringFormat() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.FLAMMABLE);
		Stock stock1 = new Stock("Gas", 30, Condition.PERFECT, specialQualities, null);

		Compartment compartment = new Compartment("Regular Compartment", 100, SpecialQuality.FLAMMABLE);
		compartment.addStock(stock1);

		String expected = "FLAMMABLE Compartment (30/100)";
		assertEquals(expected, compartment.toString(), "Checks toString formatting");

	}

}
