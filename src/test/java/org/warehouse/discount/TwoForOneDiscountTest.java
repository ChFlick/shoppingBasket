package org.warehouse.discount;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.warehouse.discount.TwoForOneDiscount;
import org.warehouse.product.Product;
import org.warehouse.product.ProductType;

public class TwoForOneDiscountTest {


	private TwoForOneDiscount twoForOneDiscount;
	private Product testProduct;
	private Product testProduct2;
	private Product testProduct3;

	
	@Before
	public void setUp() {
		twoForOneDiscount = new TwoForOneDiscount();
		testProduct = new ProductType("", 100).createProduct();
		testProduct2 = new ProductType("", 100).createProduct();
		testProduct3 = new ProductType("", 100).createProduct();
	}
	
	@Test
	public void twoForOneDiscountDoesNothingForOneProduct() throws Exception {
		twoForOneDiscount.accept(testProduct);
		
		assertThat(testProduct.getPriceInCentsAfterDiscount(), is(100L));
	}
	
	@Test
	public void twoForOneDiscountDiscountsSecondsProduct() throws Exception {
		twoForOneDiscount.accept(testProduct);
		twoForOneDiscount.accept(testProduct2);

		assertThat(testProduct.getPriceInCentsAfterDiscount(), is(100L));
		assertThat(testProduct2.getPriceInCentsAfterDiscount(), is(0L));

	}
	
	@Test
	public void twoForOneDiscountWithThreeProductsDiscountsSecondProduct() throws Exception {
		twoForOneDiscount.accept(testProduct);
		twoForOneDiscount.accept(testProduct2);
		twoForOneDiscount.accept(testProduct3);

		assertThat(testProduct.getPriceInCentsAfterDiscount(), is(100L));
		assertThat(testProduct2.getPriceInCentsAfterDiscount(), is(0L));
		assertThat(testProduct3.getPriceInCentsAfterDiscount(), is(100L));

	}

}
