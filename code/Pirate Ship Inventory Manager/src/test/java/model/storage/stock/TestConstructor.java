package model.storage.stock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory_manager.enums.Condition;
import edu.westga.cs3211.pirate_ship_inventory_manager.enums.SpecialQuality;
import edu.westga.cs3211.pirate_ship_inventory_manager.model.storage.Stock;

 public class TestConstructor {

	@Test
	public void testWhenStockNameIsNull() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock(null, 30, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenStockNameIsBlank() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock(" ", 30, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenStockNameIsEmpty() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("", 30, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenQuantityIsZero() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", 0, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenQuantityIsLessThanZero() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", -5, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenConditionIsNull() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", 30, null, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenQualitiesIsNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", 30, Condition.USABLE, null, null);
		});
	}
	
	@Test
	public void testWhenQualitiesContainsNoneAndAnotherQuality() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		specialQualities.add(SpecialQuality.LIQUID);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", 30, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testWhenStockIsPerishableAndNoDateIsSet() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		assertThrows(IllegalArgumentException.class, () -> {
			new Stock("Gold", 30, Condition.USABLE, specialQualities, null);
		});
	}
	
	@Test
	public void testValidStockWithPerfectCondtion() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.PERFECT, specialQualities, null);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.PERFECT, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(null, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}
	
	@Test
	public void testValidStockWithUsableCondtion() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.USABLE, specialQualities, null);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.USABLE, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(null, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}
	
	@Test
	public void testValidStockWithUnUsableCondtion() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, null);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.UNUSABLE, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(null, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}
	
	@Test
	public void testValidStockWithOneSpecialQuality() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.NONE);
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, null);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.UNUSABLE, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(null, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}
	
	@Test
	public void testValidStockWithMultipleSpecialQualities() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.LIQUID);
		specialQualities.add(SpecialQuality.FLAMMABLE);
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, null);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.UNUSABLE, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(null, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}
	
	@Test
	public void testValidStockWithPerishableSpecialQuality() {
		Set<SpecialQuality> specialQualities = new HashSet<SpecialQuality>();
		specialQualities.add(SpecialQuality.PERISHABLE);
		LocalDate date = LocalDate.parse("2025-11-16");
		Stock stockItem = new Stock("Gold", 30, Condition.UNUSABLE, specialQualities, date);
		assertEquals("Gold", stockItem.getName(), "Checks the stock's name");
		assertEquals(30, stockItem.getQuantity(), "Checks the amount of the stock");
		assertEquals(Condition.UNUSABLE, stockItem.getCondition(), "Checks the condition of the stock");
		assertEquals(specialQualities, stockItem.getQualities(), "Checks the qualities of the stock");
		assertEquals(date, stockItem.getExpirationDate(), "Defaults to null since stock isn't perishable");
	}

}
