package org.warehouse.basket;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.warehouse.discount.Discount;
import org.warehouse.product.Product;
import org.warehouse.product.ProductType;

public class TotalCalculatorWithDiscountsTest {
	private static final Discount MINUS_ONE_DISCOUNT = new Discount() {
		@Override
		public void accept(Product t) {
			t.setPriceInCentsAfterDiscount(t.getPriceInCentsAfterDiscount() - 1);
		}
	};

	private static final Discount MINUS_FIVE_DISCOUNT = new Discount() {
		@Override
		public void accept(Product t) {
			t.setPriceInCentsAfterDiscount(t.getPriceInCentsAfterDiscount() - 5);
		}
	};
	
	private static final Discount DISCOUNT_RESET = new Discount() {
		@Override
		public void accept(Product t) {}
		
		@Override
		public void initDiscountCalculation() {
			resetCalls++;
		}
	};

	private static int resetCalls = 0;	
	private Map<String, Set<Discount>> discountsByProductId;
	private Set<Discount> testDiscounts;

	@Before
	public void setUp() {
		discountsByProductId = new HashMap<>();
		testDiscounts = new HashSet<>();
		resetCalls = 0;
	}

	@Test
	public void canCalculateTotalFromOneProductWithoutDiscounts() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		Product testProduct = new ProductType("12", 100L).createProduct();

		long totalPriceInCents = totalCalculator.calculateTotalPriceInCents(Arrays.asList(testProduct), null);

		assertThat(totalPriceInCents, is(100L));
	}

	@Test
	public void canCalculateTotalFromMultipleProductWithoutDiscounts() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		ProductType testProductType = new ProductType("12", 100L);
		Product testProduct = testProductType.createProduct();
		Product testProduct2 = testProductType.createProduct();
		Product testProduct3 = testProductType.createProduct();
		Product testProduct4 = testProductType.createProduct();

		long totalPriceInCents = totalCalculator
				.calculateTotalPriceInCents(Arrays.asList(testProduct, testProduct2, testProduct3, testProduct4), null);

		assertThat(totalPriceInCents, is(400L));
	}

	@Test
	public void ignoreDiscountsForOtherProductIds() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		Product testProduct = new ProductType("12", 100L).createProduct();
		testDiscounts.add(MINUS_ONE_DISCOUNT);
		discountsByProductId.put("Not12", testDiscounts);

		long totalPriceInCents = totalCalculator.calculateTotalPriceInCents(Arrays.asList(testProduct),
				discountsByProductId);

		assertThat(totalPriceInCents, is(100L));
	}

	@Test
	public void useDiscountForCorrectProductId() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		Product testProduct = new ProductType("12", 100L).createProduct();
		testDiscounts.add(MINUS_ONE_DISCOUNT);
		discountsByProductId.put("12", testDiscounts);

		long totalPriceInCents = totalCalculator.calculateTotalPriceInCents(Arrays.asList(testProduct),
				discountsByProductId);

		assertThat(totalPriceInCents, is(99L));
	}

	@Test
	public void usesMultipleDiscounts() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		Product testProduct = new ProductType("12", 100L).createProduct();
		testDiscounts.add(MINUS_ONE_DISCOUNT);
		testDiscounts.add(MINUS_FIVE_DISCOUNT);
		discountsByProductId.put("12", testDiscounts);

		long totalPriceInCents = totalCalculator.calculateTotalPriceInCents(Arrays.asList(testProduct),
				discountsByProductId);

		assertThat(totalPriceInCents, is(94L));
	}

	@Test
	public void usesMultipleDiscountsOnMultipleProducts() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		ProductType testProductType = new ProductType("12", 100L);
		Product testProduct = testProductType.createProduct();
		Product testProduct2 = testProductType.createProduct();

		testDiscounts.add(MINUS_ONE_DISCOUNT);
		testDiscounts.add(MINUS_FIVE_DISCOUNT);
		discountsByProductId.put("12", testDiscounts);

		long totalPriceInCents = totalCalculator.calculateTotalPriceInCents(
				Arrays.asList(testProduct, testProduct2),
				discountsByProductId);

		assertThat(totalPriceInCents, is(188L));
	}
	
	@Test
	public void discountsGetResettedOnCalculation() throws Exception {
		TotalCalculatorWithDiscounts totalCalculator = new TotalCalculatorWithDiscounts();
		ProductType testProductType = new ProductType("12", 100L);
		Product testProduct = testProductType.createProduct();

		testDiscounts.add(DISCOUNT_RESET);
		discountsByProductId.put("12", testDiscounts);

		totalCalculator.calculateTotalPriceInCents(
				Arrays.asList(testProduct),
				discountsByProductId);
		
		totalCalculator.calculateTotalPriceInCents(
				Arrays.asList(testProduct),
				discountsByProductId);
		
		totalCalculator.calculateTotalPriceInCents(
				Arrays.asList(testProduct),
				discountsByProductId);

		assertThat(resetCalls, is(3));
	}

}
