import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.Sets;

public class WarehouseTest {

	@Test
	public void singleProductCanBeScanned() {
		ProductType a1 = new ProductType("A001", 100);
		Inventory inventory = new Inventory(Sets.newHashSet(a1));
		Basket basket = new Basket(inventory);
		
		basket.scan("A001");
		
		assertThat(basket.total(), is(100L));
	}
	
	@Test
	public void multipleProductsCanBeScanned() {
		ProductType a1 = new ProductType("A001", 100);
		ProductType a2 = new ProductType("A002", 250);
		Inventory inventory = new Inventory(Sets.newHashSet(a1, a2));
		Basket basket = new Basket(inventory);
		
		basket.scan("A001");
		basket.scan("A001");
		basket.scan("A002");
		
		assertThat(basket.total(), is(450L));
	}

}
