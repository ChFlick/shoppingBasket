package org.warehouse.discount;
import org.warehouse.product.Product;

public class TwoForOneDiscount implements Discount {
	private int productsProcessed = 0;
	
	public void initDiscountCalculation() {
		productsProcessed = 0;
	}

	
	@Override
	public void accept(Product product) {
		if(productsProcessed % 2 != 0) {
			product.setPriceInCentsAfterDiscount(0l);
		}
		
		productsProcessed++;
	}

}
