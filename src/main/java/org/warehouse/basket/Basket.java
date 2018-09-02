package org.warehouse.basket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.warehouse.discount.DiscountTypes;
import org.warehouse.inventory.Inventory;
import org.warehouse.product.Product;

public class Basket {
	private Inventory inventory;
	private TotalCalculator totalCalculator;

	private List<Product> products = new ArrayList<>();
	private Map<String, Set<DiscountTypes> > discountsByProductId = new HashMap<>();

	public Basket(Inventory inventory, TotalCalculator totalCalculator) {
		this.inventory = inventory;
		this.totalCalculator = totalCalculator;
	}

	public void scan(String productId) {
		Optional<Product> possibleProduct = inventory.getProductById(productId);
		possibleProduct.ifPresent(product -> {
			products.add(product);
			Set<DiscountTypes> discounts = discountsByProductId.getOrDefault(productId, new HashSet<>());
			discounts.addAll(product.getDiscounts());
			getDiscountsByProductId().put(productId, discounts);
		});
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public long total() {
		return totalCalculator.calculateTotalPriceInCents(this);
	}

	public Map<String, Set<DiscountTypes> > getDiscountsByProductId() {
		return discountsByProductId;
	}

}
