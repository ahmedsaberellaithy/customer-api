package io.jumia.task.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/")
	 public String checkHealth() {
		return "we are ready...";
	}
	
	@RequestMapping("/customers")
	 public List<Customer> getAllContacts(@RequestParam(value="country",required=false) String country,@RequestParam(value="valid",required=false) String valid) {
		
		List<Customer> customers = customerService.getAllCustomers();
		if(country != null || valid != null) {
			return customerService.filterCustomers(customers,country,valid);
		}else {
			return customers;
		}
	}
	
	@RequestMapping("/customers/{id}")
	 public Customer getContact(@PathVariable Integer id) {
		Customer contact = customerService.getCustomer(id);
		return contact;
	}
}
