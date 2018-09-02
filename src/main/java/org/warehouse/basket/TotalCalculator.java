package org.warehouse.basket;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.warehouse.discount.Discount;
import org.warehouse.product.Product;

public interface TotalCalculator {
	public long calculateTotalPriceInCents(Collection<Product> products, 
			Map<String, Set<Discount>> discountsByProductId);
}