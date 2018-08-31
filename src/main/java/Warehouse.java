import com.google.common.collect.Sets;

public class Warehouse {
	public Warehouse () {
		ProductType a1 = new ProductType("A001", 100);
		Inventory inventory = new Inventory(Sets.newHashSet(a1));
		
		Basket basket = new Basket(inventory);
		basket.scan("A001");
	}
}
