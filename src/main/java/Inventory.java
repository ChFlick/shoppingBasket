import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Inventory {
	private Map<String, ProductType> productTypesById;

	public Inventory(Set<ProductType> productTypes) {
		if (productTypes == null) {
			productTypesById = Collections.emptyMap();
		} else {
			this.productTypesById = productTypes.stream()
					.collect(Collectors.toUnmodifiableMap(ProductType::getId, p -> p));
		}
	}

	public Optional<Product> getProductById(String id) {
		ProductType productType = productTypesById.get(id);

		if (productType == null) {
			return Optional.empty();
		}

		return Optional.of(Product.createByType(productType));
	}

}
