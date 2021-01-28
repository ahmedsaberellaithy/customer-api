package io.jumia.task.customer;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Total 20 customers to be tested on the following test cases
	 * customers with ids [0,1,2,3,4,5,6] are numbers from Morocco
	 * customers with ids [1,4,5,6] are valid numbers from Morocco
	 * empty list of customers at the case country = morocc //wrong input
	 * empty list of customers at the case valid = rvalid //wrong input
	 * list of 20 customers when no validation is passed
	 */
	private List<Customer> customers = new ArrayList<Customer>(
			Arrays.asList(
					//country=morocco
					new Customer(0,"Walid Hammadi","(212) 6007989253"),
					//country=morocco&valid=valid
					new Customer(1,"Yosaf Karrouch","(212) 698054317"),
					//country=morocco
					new Customer(2,"Younes Boutikyad","(212) 6546545369"),
					//country=morocco
					new Customer(3,"Houda Houda","(212) 6617344445"),
					//country=morocco&valid=valid
					new Customer(4,"Chouf Malo","(212) 691933626"),
					//country=morocco&valid=valid
					new Customer(5,"soufiane fritisse ","(212) 633963130"),
					//country=morocco&valid=valid
					new Customer(6,"Nada Sofie","(212) 654642448"),
					new Customer(7,"Edunildo Gomes Alberto ","(258) 847651504"),
					new Customer(8,"Walla's Singz Junior","(258) 846565883"),
					new Customer(9,"sevilton sylvestre","(258) 849181828"),
					//country=mozambique&valid=inValid
					new Customer(10,"Tanvi Sachdeva","(258) 84330678235"),
					new Customer(11,"Florencio Samuel","(258) 847602609"),
					//country=mozambique&valid=inValid
					new Customer(12,"Solo Dolo","(258) 042423566"),
					new Customer(13,"Pedro B 173","(258) 823747618"),
					new Customer(14,"Ezequiel Fenias","(258) 848826725"),
					new Customer(15,"JACKSON NELLY","(256) 775069443"),
					new Customer(16,"Kiwanuka Budallah","(256) 7503O6263"),
					new Customer(17,"VINEET SETH","(256) 704244430"),
					new Customer(18,"Jokkene Richard","(256) 7734127498"),
					new Customer(19,"Ogwal David","(256) 7771031454")
					)
			);

	@Test
	void testFilterCustomers() {
		List<Customer> allCustomers = customerService.filterCustomers(customers,null,null);
		assertThat(allCustomers.size(), is(20));
		List<Customer> emptyListCustomers = customerService.filterCustomers(customers,"morocca","valid");
		assertThat(emptyListCustomers.size(), is(0));
//		fail("Not yet implemented");
	}

}
