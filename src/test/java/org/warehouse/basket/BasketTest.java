package org.warehouse.basket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.warehouse.discount.Discount;
import org.warehouse.inventory.ExternInitialisedInventory;
import org.warehouse.inventory.Inventory;
import org.warehouse.product.Product;
import org.warehouse.product.ProductType;

import com.google.common.collect.Sets;

public class BasketTest {
	private Inventory inventory;
	private Basket basket;
	
	@Test
	public void notAvailableScannedProductWontBeAdded() throws Exception {
		inventory = new ExternInitialisedInventory(Collections.emptySet());
		basket = new Basket(inventory, null);
		
		basket.scan("A1");
		
		assertThat(basket.getProducts().size(), is(0));
	}
	
	@Test
	public void availableScannedProductGetsAdded() throws Exception {
		ProductType testProduct = new ProductType("A1", 100);
		inventory = new ExternInitialisedInventory(Sets.newHashSet(testProduct));
		basket = new Basket(inventory, null);
		
		basket.scan("A1");
		
		assertThat(basket.getProducts().size(), is(1));
		assertThat(basket.getProducts().get(0), is(equalTo(testProduct.createProduct())));
	}
	
	@Test
	public void availableScannedProductGetsAddedMultipleTimes() throws Exception {
		ProductType testProduct = new ProductType("A1", 100);
		inventory = new ExternInitialisedInventory(Sets.newHashSet(testProduct));
		basket = new Basket(inventory, null);
		
		basket.scan("A1");
		basket.scan("A1");
		
		assertThat(basket.getProducts().size(), is(2));
		assertThat(basket.getProducts().get(0), is(equalTo(testProduct.createProduct())));
		assertThat(basket.getProducts().get(1), is(equalTo(testProduct.createProduct())));
	}
	
	@Test
	public void basketUsesTotalCalculatorForCalculations() throws Exception {
		long totalValue = 1000L;
		TotalCalculator calc = new TotalCalculator() {
			@Override
			public long calculateTotalPriceInCents(Collection<Product> products,
					Map<String, Set<Discount>> discountsByProductId) {
				return totalValue;
			}
		};
		basket = new Basket(null, calc);
		
		assertThat(basket.total(), is(totalValue));
	}
}
