package org.warehouse.inventory;

import java.util.Optional;

import org.warehouse.product.Product;

public interface Inventory {
	Optional<Product> getProductById(String id);
}