import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Basket {
	private Inventory inventory;
	
	private List<Product> products = new ArrayList<>();

	public Basket(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void scan(String productId) {
		Optional<Product> product = inventory.getProductById(productId);
		product.ifPresent(products::add);
	}
	
	public long total() {
		return products.stream().mapToLong(Product::getPriceInCents).sum();
	}
}
