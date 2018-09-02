package org.warehouse.product;
import java.util.HashSet;
import java.util.Set;

import org.warehouse.discount.DiscountTypes;

public class Product {
	private final String id;
	private final long priceInCents;
	private long priceInCentsAfterDiscount;
	private final Set<DiscountTypes> discounts = new HashSet<>();

	public Product(String id, long priceInCents) {
		super();
		this.id = id;
		this.priceInCents = priceInCents;
		this.priceInCentsAfterDiscount = priceInCents;
	}
	
	private Product(Product product) {
		this.id = product.getId();
		this.priceInCents = product.getPriceInCents();
		this.priceInCentsAfterDiscount = product.getPriceInCentsAfterDiscount();
		this.discounts.addAll(product.getDiscounts());
	}
	
	public void initDiscountCalculation() {
		this.priceInCentsAfterDiscount = priceInCents;
	}
	
	public void addDiscount(DiscountTypes discount) {
		this.discounts.add(discount);
	}
	
	public Set<DiscountTypes> getDiscounts() {
		return discounts;
	}

	public String getId() {
		return id;
	}

	public long getPriceInCents() {
		return priceInCents;
	}
	
	public void setPriceInCentsAfterDiscount(long value) {
		this.priceInCentsAfterDiscount = value;
	}
	
	public long getPriceInCentsAfterDiscount() {
		return this.priceInCentsAfterDiscount;
	}

	@Override
	public Product clone() {
		return new Product(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}