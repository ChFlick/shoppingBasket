
public class Product {
	public static Product createByType(ProductType productType) {
		return new Product(productType.getId(), productType.getPriceInCents());
	}
	
	private String productId;
	private long priceInCents;
	
	private Product(String productId, long priceInCents) {
		this.productId = productId;
		this.priceInCents = priceInCents;
	}

	public String getProductId() {
		return productId;
	}

	public long getPriceInCents() {
		return priceInCents;
	}
	
}
