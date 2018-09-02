package org.warehouse.product;

import java.util.Set;

import org.warehouse.discount.Discount;

public class Product {
	private ProductType productType;
	private long priceInCentsAfterDiscount;

	Product(ProductType productType) {
		this.productType = productType;
		this.priceInCentsAfterDiscount = productType.getPriceInCents();
	}

	public void initDiscountCalculation() {
		this.priceInCentsAfterDiscount = productType.getPriceInCents();
	}

	public Set<Discount> getDiscounts() {
		return productType.getDiscounts();
	}

	public String getId() {
		return productType.getId();
	}

	public long getPriceInCents() {
		return productType.getPriceInCents();
	}

	public void setPriceInCentsAfterDiscount(long value) {
		this.priceInCentsAfterDiscount = value;
	}

	public long getPriceInCentsAfterDiscount() {
		return this.priceInCentsAfterDiscount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (priceInCentsAfterDiscount ^ (priceInCentsAfterDiscount >>> 32));
		result = prime * result + ((productType == null) ? 0 : productType.hashCode());
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
		Product other = (Product) obj;
		if (priceInCentsAfterDiscount != other.priceInCentsAfterDiscount)
			return false;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		return true;
	}

}
