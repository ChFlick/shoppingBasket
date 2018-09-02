package org.warehouse.discount;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.warehouse.product.Product;
import org.warehouse.product.ProductType;

public class TenPercentDiscountTest {

	private TenPercentDiscount tenPercentDiscount;
	private Product testProduct;
	
	@Before
	public void setUp() {
		tenPercentDiscount = new TenPercentDiscount();
		testProduct = new ProductType("", 100).createProduct();
	}

	@Test
	public void testTenPercentWithZeroProduct() throws Exception {
		tenPercentDiscount.accept(testProduct);
		
		assertThat(testProduct.getPriceInCentsAfterDiscount(), is(90L));		
	}

}
