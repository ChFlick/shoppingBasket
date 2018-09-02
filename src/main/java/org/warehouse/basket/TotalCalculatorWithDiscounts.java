package org.warehouse.basket;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.warehouse.discount.Discount;
import org.warehouse.discount.DiscountTypes;
import org.warehouse.product.Product;

public class TotalCalculatorWithDiscounts implements TotalCalculator {
	public long calculateTotalPriceInCents(Basket basket) {
		List<Product> products = basket.getProducts();
		products.forEach(Product::initDiscountCalculation);

		Map<String, Set<DiscountTypes>> discountsByProductId = basket.getDiscountsByProductId();

		discountsByProductId.entrySet().stream().forEach(entry -> {
			List<Product> viableProducts = getProductsWithProductId(products, entry.getKey());
			Set<Discount> discounts = getDiscountsForDiscountTypes(entry.getValue());
			applyDiscountsOnProducts(discounts, viableProducts);
		});

		return products.stream()
				.mapToLong(Product::getPriceInCentsAfterDiscount)
				.sum();
	}

	private Set<Discount> getDiscountsForDiscountTypes(Set<DiscountTypes> discuntTypes) {
		return discuntTypes.stream()
				.map(DiscountTypes::getActualDiscount)
				.collect(toUnmodifiableSet());
	}

	private List<Product> getProductsWithProductId(List<Product> products, String productId) {
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
