package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor

/*
 * @Data annotation is from Lombok automatically generates getter and setter methods, toString, 
 * equals, and hashCode methods for all fields in the class. 
 * 
 * @NoArgsConstructor: This annotation generates a no-argument constructor for the class.
 */

public class PetStoreCustomer {
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	
	public PetStoreCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
	}
	
	/*
	 * There is a constructor that takes a Customer object as input. It's used to create 
	 * a PetStoreCustomer object from a Customer entity.
	 * 
	 * Inside the constructor, it sets the properties of the PetStoreCustomer object 
	 * based on the corresponding properties of the Customer object.
	 */

}


/*
In summary, this PetStoreCustomer class serves as a simplified representation of a customer, 
specifically tailored for use in the controller layer of the application. It uses Lombok annotations 
to automatically generate boilerplate code, reducing the need for manual coding. 
Additionally, it provides a constructor to easily convert Customer entities into PetStoreCustomer 
objects for handling in the controller.
*/
