package org.warehouse.inventory;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.warehouse.product.Product;

public class ExternInitialisedInventory implements Inventory {
	private Map<String, Product> productByProductId;

	public ExternInitialisedInventory(Set<Product> products) {
		if (products == null) {
			productByProductId = Collections.emptyMap();
		} else {
			this.productByProductId = products.stream()
					.collect(Collectors.toUnmodifiableMap(Product::getId, p -> p));
		}
	}

	/* (non-Javadoc)
	 * @see org.warehouse.inventory.Inventory#getProductById(java.lang.String)
	 */
	@Override
	public Optional<Product> getProductById(String id) {
		return Optional.ofNullable(productByProductId.get(id)).map(Product::clone);
	}

}
