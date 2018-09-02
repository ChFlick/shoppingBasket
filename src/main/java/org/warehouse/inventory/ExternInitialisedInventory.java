package org.warehouse.inventory;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.warehouse.product.Product;
import org.warehouse.product.ProductType;

public class ExternInitialisedInventory implements Inventory {
	private Map<String, ProductType> productTypesByProductId;

	public ExternInitialisedInventory(Set<ProductType> productTypes) {
		if (productTypes == null) {
			productTypesByProductId = Collections.emptyMap();
		} else {
			this.productTypesByProductId = productTypes.stream()
					.collect(Collectors.toUnmodifiableMap(ProductType::getId, p -> p));
		}
	}

	@Override
	public Optional<Product> getProductById(String id) {
		return Optional.ofNullable(productTypesByProductId.get(id)).map(ProductType::createProduct);
	}

}
