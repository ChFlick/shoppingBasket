package org.warehouse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.warehouse.basket.Basket;
import org.warehouse.basket.TotalCalculator;
import org.warehouse.basket.TotalCalculatorWithDiscounts;
import org.warehouse.discount.DiscountTypes;
import org.warehouse.inventory.ExternInitialisedInventory;
import org.warehouse.inventory.Inventory;
import org.warehouse.product.Product;

import com.google.common.collect.Sets;

public class WarehouseTest {

	@Test
	public void singleProductCanBeScanned() {
		Product a1 = new Product("A001", 100);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(100L));
	}
	
	@Test
	public void multipleProductsCanBeScanned() {
		Product a1 = new Product("A001", 100);
		Product a2 = new Product("A002", 250);
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
		Product a1 = new Product("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(90L));
	}
	
	@Test
	public void singleDiscountCanBeAppliedOnMultipleProducts() {
		Product a1 = new Product("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(180L));
	}
	
	@Test
	public void singleDiscountOnDifferentProducts() {
		Product a1 = new Product("A001", 100);
		a1.addDiscount(DiscountTypes.TEN_PERCENT);
		
		Product a2 = new Product("A002", 50);
		a2.addDiscount(DiscountTypes.TEN_PERCENT);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1, a2));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A002");
		
		assertThat(basket.total(), is(90L + 45L));
	}
	
	@Test
	public void twoForOneDiscountCanBeAppliedWithTwoProducts() {
		Product a1 = new Product("A001", 100);
		a1.addDiscount(DiscountTypes.TWO_FOR_ONE);
		
		Inventory inventory = new ExternInitialisedInventory(Sets.newHashSet(a1));
		TotalCalculator totalCalculator = new TotalCalculatorWithDiscounts();
		Basket basket = new Basket(inventory, totalCalculator);
		
		basket.scan("A001");
		basket.scan("A001");
		
		assertThat(basket.total(), is(100L));
	}
	
	@Test
	public void twoForOneDiscountCanBeAppliedWithThreeProducts() {
		Product a1 = new Product("A001", 100);
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
		Product a1 = new Product("A001", 100);
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
		Product a1 = new Product("A001", 100);
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
