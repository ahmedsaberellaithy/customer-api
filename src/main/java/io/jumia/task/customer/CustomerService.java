package io.jumia.task.customer;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	/**
	 * Define the static values of regex for each country using the prefix only
	 * Define the static values of regex for each valid country number
	 */
	public static String cameroonRegex = "^\\(237\\).*$";
	public static String ethiopiaRegex = "^\\(251\\).*$";
	public static String moroccoRegex = "^\\(212\\).*$";
	public static String mozambiqueRegex = "^\\(258\\).*$";
	public static String ugandaRegex = "^\\(256\\).*$";
	public static String cameroonValidPhoneRegex = "\\(237\\)\\ ?[2368]\\d{7,8}$";
	public static String ethiopiaValidPhoneRegex = "\\(251\\)\\ ?[1-59]\\d{8}$";
	public static String moroccoValidPhoneRegex = "\\(212\\)\\ ?[5-9]\\d{8}$";
	public static String mozambiqueValidPhoneRegex = "\\(258\\)\\ ?[28]\\d{7,8}$";
	public static String ugandaValidPhoneRegex = "\\(256\\)\\ ?\\d{9}$";
	
	@Autowired
	private CustomerRepository customerRepository;

	
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	public Customer getCustomer(Integer id){
		return customerRepository.findCustomerById(id);
	}
	
	/**
	 * @param customer
	 * @return is this customer phone number valid in one of the countries
	 */
	private static boolean isValidPhoneNumber(Customer customer) {
		if(customer.getPhone().matches(cameroonValidPhoneRegex)) {
			return true;
		}else if(customer.getPhone().matches(ethiopiaValidPhoneRegex)) {
			return true;
		}else if(customer.getPhone().matches(moroccoValidPhoneRegex)) {
			return true;
		}else if(customer.getPhone().matches(mozambiqueValidPhoneRegex)) {
			return true;
		}else if(customer.getPhone().matches(ugandaValidPhoneRegex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function is overloading isValidPhoneNumber with a country delimiter
	 * @param customer
	 * @param country 
	 * @return is this customer phone number valid in the passed country
	 */
	private static boolean isValidPhoneNumber(Customer customer,int country){
		switch(country) {
			case 1:
				if(customer.getPhone().matches(cameroonValidPhoneRegex)) {
					return true;
				}
				break;
			case 2:
				if(customer.getPhone().matches(ethiopiaValidPhoneRegex)) {
					return true;
				}
				break;
			case 3:
				if(customer.getPhone().matches(moroccoValidPhoneRegex)) {
					return true;
				}
				break;
			case 4:
				if(customer.getPhone().matches(mozambiqueValidPhoneRegex)) {
					return true;
				}
				break;
			case 5:
				if(customer.getPhone().matches(ugandaValidPhoneRegex)) {
					return true;
				}
				break;
			default: return false;
		}
		return false;
	}
	
	/**
	 * to optimize memory usage in comparisons
	 * @param valid String (inValid - valid - null)
	 * @return integer (0:invalid,1:valid,2:all)
	 */
	private static int validFilterToInt(String valid) {
		if(valid != null) {
			if(valid.equals("valid")) {
				return 1;
			}else if(valid.equals("inValid")){
				return 0;
			}
			//case of passing a wrong string
			return -1;
		}else {
			return 2;
		}
	}
	
	/**
	 * to optimize memory usage in comparisons
	 * @param valid String (inValid - valid - null)
	 * @return integer (0:invalid,1:valid,2:all)
	 */
	private static int validCountryToInt(String country) {
		if(country != null){
			switch(country) {
				case "cameroon": return 1;
				case "ethiopia": return 2;
				case "morocco": return 3;
				case "mozambique": return 4;
				case "uganda": return 5;
				default: return -1;
			}
		} else {
			return 0;
		}
	}
	
	private static boolean isThisCustomerEligableForFiltering(Customer customer,int valid) {
		if(valid != 2) {
			boolean isValidPhoneNumber = CustomerService.isValidPhoneNumber(customer);
			if((valid == 1 && isValidPhoneNumber) || (valid == 0 && !isValidPhoneNumber)){
				return true;
			}
		}else { //get all
			return true;
		}
		return false;
	}
	
	private static boolean isThisCustomerEligableForFiltering(Customer customer,int country,int valid) {
		if(valid != 2) {
			boolean isValidPhoneNumber = CustomerService.isValidPhoneNumber(customer,country);
			if((valid == 1 && isValidPhoneNumber)|| (valid == 0 && !isValidPhoneNumber)){
				return true;
			}
		}else {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param customers list of raw customers retrieved from the database
	 * @param country country name - can be passed as null if no company filter passed
	 * @param valid ("valid"-"inValid") - can be passed as null if no valid attr is passed
	 * @return list of customers with same attributes
	 */
	public List<Customer> filterCustomers(List<Customer> customers,String countryStr,String validStr){
		
		List<Customer> filteredCustomers = new ArrayList<Customer>();
		int valid = CustomerService.validFilterToInt(validStr);
		if(valid == -1) {
			//return an empty array, invalid input
			return filteredCustomers;
		}

		int country = CustomerService.validCountryToInt(countryStr);
		if(country == -1) {
			//return an empty array, invalid input
			return filteredCustomers;
		}
		
		for(Customer customer : customers) {
			switch(country) {
				case 0: //get all countries
					if(isThisCustomerEligableForFiltering(customer,valid)) {
						filteredCustomers.add(customer);
					}
					break;
				case 1:
					if(customer.getPhone().matches(cameroonRegex)) {
						if(isThisCustomerEligableForFiltering(customer,country,valid)) {
							filteredCustomers.add(customer);
						}
					}
					break;
				case 2:
					if(customer.getPhone().matches(ethiopiaRegex)) {
						if(isThisCustomerEligableForFiltering(customer,country,valid)) {
							filteredCustomers.add(customer);
						}
					}
					break;
				case 3:
					if(customer.getPhone().matches(moroccoRegex)) {
						if(isThisCustomerEligableForFiltering(customer,country,valid)) {
							filteredCustomers.add(customer);
						}
					}
					break;
				case 4:
					if(customer.getPhone().matches(mozambiqueRegex)) {
						if(isThisCustomerEligableForFiltering(customer,country,valid)) {
							filteredCustomers.add(customer);
						}
					}
					break;
				case 5:
					if(customer.getPhone().matches(ugandaRegex)) {
						if(isThisCustomerEligableForFiltering(customer,country,valid)) {
							filteredCustomers.add(customer);
						}
					}
					break;
				default: break;
			}
		}
		
		return filteredCustomers;
	}
}