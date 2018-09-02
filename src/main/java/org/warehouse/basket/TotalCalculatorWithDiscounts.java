package org.warehouse.basket;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.warehouse.discount.Discount;
import org.warehouse.discount.DiscountTypes;
import org.warehouse.product.Product;

public class TotalCalculatorWithDiscounts implements TotalCalculator {
	public long calculateTotalPriceInCents(Basket basket) {
		List<Product> products = basket.getProducts();
		products.forEach(Product::initDiscountCalculation);

		Map<String, Set<DiscountTypes>> discountsByProductId = basket.getDiscountsByProductId();

		discountsByProductId.entrySet().stream().forEach(entry -> {
			List<Product> viableProducts = products.stream()
					.filter(p -> p.getId().equals(entry.getKey()))
					.collect(Collectors.toList());
			Set<Discount> discounts = entry.getValue().stream()
					.map(DiscountTypes::getActualDiscount)
					.collect(Collectors.toUnmodifiableSet());
			discounts.forEach(applyDiscountTo(viableProducts));
		});

		return products.stream()
				.mapToLong(Product::getPriceInCentsAfterDiscount)
				.sum();
	}

	private Consumer<? super Discount> applyDiscountTo(List<Product> viableProducts) {
		return discount -> viableProducts.forEach(discount::accept);
	}

}
