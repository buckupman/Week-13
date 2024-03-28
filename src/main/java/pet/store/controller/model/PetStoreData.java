package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor

/*
 * @Data annotation is from Lombok automatically generates getter and setter methods, toString, 
 * equals, and hashCode methods for all fields in the class. 
 * 
 * @NoArgsConstructor: This annotation generates a no-argument constructor for the class.
 */


public class PetStoreData {
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	private Set<PetStoreCustomer> customers = new HashSet<>();
	private Set<PetStoreEmployee> employees = new HashSet<>();
	
	/*
	 These fields are the properties (fields) of the PetStoreData class. They represent 
	 various attributes of a pet store such as ID, name, address, etc.
	 There are also sets (customers and employees) to hold related entities 
	 (customers and employees) of the pet store.
	 */
	
	
	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
	for (Customer customer : petStore.getCustomers()) {
		customers.add(new PetStoreCustomer(customer));
	}
	
	for (Employee employee : petStore.getEmployees()) {
		employees.add(new PetStoreEmployee(employee));
	}
	
	}	
	
	/*
	 * The constructor that takes a PetStore object as input. It's used to create a 
	 * PetStoreData object from a PetStore entity.
	 * 
	 * Inside the constructor, it sets the properties of the PetStoreData object based on the 
	 * corresponding properties of the PetStore object.
	 * 
	 * It also iterates over the customers and employees associated with the PetStore 
	 * and adds them to the respective sets in PetStoreData.
	 */
}

/*
 In summary, this PetStoreData class serves as a simplified representation of a pet store's data, 
 specifically tailored for use in the controller layer of the application. It uses Lombok annotations 
 to automatically generate boilerplate code, reducing the need for manual coding. Additionally, 
 it provides a constructor to easily convert PetStore entities into PetStoreData objects for handling 
 in the controller.
*/
