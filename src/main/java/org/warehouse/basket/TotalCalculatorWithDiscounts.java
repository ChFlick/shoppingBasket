package org.warehouse.basket;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.warehouse.discount.Discount;
import org.warehouse.product.Product;

public class TotalCalculatorWithDiscounts implements TotalCalculator {
	public long calculateTotalPriceInCents(Collection<Product> products, 
			Map<String, Set<Discount>> discountsByProductId) {
		products.forEach(Product::initDiscountCalculation);

		if(discountsByProductId != null) {
			discountsByProductId.values().stream()
				.forEach(discounts -> discounts.forEach(Discount::initDiscountCalculation));
			discountProducts(products, discountsByProductId);
		}

		return products.stream()
				.mapToLong(Product::getPriceInCentsAfterDiscount)
				.sum();
	}

	private void discountProducts(Collection<Product> products, Map<String, Set<Discount>> discountsByProductId) {
		discountsByProductId.entrySet().stream().forEach(entry -> {
			List<Product> viableProducts = getProductsWithProductId(products, entry.getKey());
			applyDiscountsOnProducts(entry.getValue(), viableProducts);
		});
	}

	private List<Product> getProductsWithProductId(Collection<Product> products, String productId) {
		return products.stream()
				.filter(p -> p.getId().equals(productId))
				.collect(toList());
	}
	
	private void applyDiscountsOnProducts(Set<Discount> discounts, List<Product> viableProducts) {
		discounts.forEach(applyDiscountTo(viableProducts));
	}

	private Consumer<? super Discount> applyDiscountTo(List<Product> viableProducts) {
		return discount -> viableProducts.forEach(discount::accept);
	}

}
