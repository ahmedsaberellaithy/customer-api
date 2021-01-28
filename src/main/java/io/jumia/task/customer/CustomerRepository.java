package io.jumia.task.customer;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public Customer findCustomerById(Integer Id);
	
}
