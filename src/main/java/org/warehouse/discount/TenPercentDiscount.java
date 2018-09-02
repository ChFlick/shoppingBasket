package org.warehouse.discount;
import org.warehouse.product.Product;

public class TenPercentDiscount implements Discount {

	@Override
	public void accept(Product product) {
		product.setPriceInCentsAfterDiscount(
				Math.round(product.getPriceInCentsAfterDiscount() * 0.9));
	}

}
