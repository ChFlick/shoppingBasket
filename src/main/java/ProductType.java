
public class ProductType {
	private final String id;
	private final long priceInCents;

	public ProductType(String id, long priceInCents) {
		super();
		this.id = id;
		this.priceInCents = priceInCents;
	}

	public String getId() {
		return id;
	}

	public long getPriceInCents() {
		return priceInCents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
