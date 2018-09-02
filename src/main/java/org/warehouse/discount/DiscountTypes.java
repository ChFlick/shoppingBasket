package org.warehouse.discount;
import java.lang.reflect.InvocationTargetException;

public enum DiscountTypes {
	TEN_PERCENT(TenPercentDiscount.class),
	TWO_FOR_ONE(TwoForOneDiscount.class);
	
	private Class<? extends Discount> discountClass;

	private DiscountTypes(Class<? extends Discount> discountClass) {
		this.discountClass = discountClass;
	}
	
	public Discount getActualDiscount() {
		try {
			return discountClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
