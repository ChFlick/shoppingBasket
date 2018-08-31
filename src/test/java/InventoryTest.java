import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import com.google.common.collect.Sets;

public class InventoryTest {
	@Test
	public void canBeCreatedEmpty() throws Exception {
		new Inventory(null);
	}
	
	@Test
	public void canBeCreatedWithProductTypes() throws Exception {
		ProductType pType = new ProductType("t1",  100);
		
		new Inventory(Sets.newHashSet(pType));
	}
	
	@Test
	public void idNotAvailableNoProduct() throws Exception {
		ProductType pType = new ProductType("t1",  100);
		
		Inventory inventory = new Inventory(Sets.newHashSet(pType));
		
		Optional<Product> product = inventory.getProductById("t2");
		
		assertThat(product.isPresent(), is(false));
	}
	
	@Test
	public void idAvailableGetsProduct() throws Exception {
		ProductType pType = new ProductType("t1",  100);
		
		Inventory inventory = new Inventory(Sets.newHashSet(pType));
		
		Optional<Product> product = inventory.getProductById("t1");
		
		assertThat(product.isPresent(), is(true));
	}
}
