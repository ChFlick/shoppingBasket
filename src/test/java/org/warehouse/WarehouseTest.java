package org.warehouse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.warehouse.basket.Basket;
import org.warehouse.basket.TotalCalculator;
import org.warehouse.basket.TotalCalculatorWithDiscounts;
import org.warehouse.discount.DiscountTypes;
import org.warehouse.inventory.ExternInitialisedInventory;
import org.warehouse.inventory.Inventory;
import org.warehouse.product.ProductType;

import com.google.common.collect.Sets;

public class WarehouseTest {

	@Test
	public void singleProductTypeCanBeScanned() {
		ProductType a1 = new ProductType("A001", 100);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(100L));
	}
	
	@Test
	public void multipleProductTypesCanBeScanned() {
		ProductType a1 = new ProductType("A001", 100);
		ProductType a2 = new ProductType("A002", 250);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1, a2));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		basket.scan("A002");
		
		assertThat(basket.total(), is(450L));
	}
	
	@Test
	public void singleDiscountCanBeApplied() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(90L));
	}
	
	@Test
	public void singleDiscountCanBeAppliedOnMultipleProductTypes() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(180L));
	}
	
	@Test
	public void singleDiscountOnDifferentProductTypes() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		
		ProductType a2 = new ProductType("A002", 50);
		a2.addDiscount(DiscountTypes.TEN_PERCENT);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1, a2));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A002");
		
		assertThat(basket.total(), is(90L + 45L));
	}
	
	@Test
	public void twoForOneDiscountCanBeAppliedWithTwoProductTypes() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TWO_FOR_ONE);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(100L));
	}
	
	@Test
	public void twoForOneDiscountCanBeAppliedWithThreeProductTypes() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TWO_FOR_ONE);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(200L));
	}
	
	@Test
	public void multipleDiscountsCanBeApplied() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TWO_FOR_ONE);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(180L));
	}
	
	@Test
	public void theTotalSumCanBeCalculatedIntermediate() {
		ProductType a1 = new ProductType("A001", 100);
		a1.addDiscount(DiscountTypes.TWO_FOR_ONE);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(180L));
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(180L));
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(270L));
	}

}
