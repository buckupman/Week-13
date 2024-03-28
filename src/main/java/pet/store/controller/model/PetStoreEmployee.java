package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor

/*
 * @Data annotation is from Lombok automatically generates getter and setter methods, toString, 
 * equals, and hashCode methods for all fields in the class. 
 * 
 * @NoArgsConstructor: This annotation generates a no-argument constructor for the class.
 */

public class PetStoreEmployee {
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	
	// Constructor
	public PetStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhone = employee.getEmployeePhone();
		employeeJobTitle = employee.getEmployeeJobTitle();	
	}
	
	/*
	 * There is a constructor that takes an Employee object as input. It's used to create 
	 * a PetStoreEmployee object from an Employee entity.
	 * 
	 * Inside the constructor, it sets the properties of the PetStoreEmployee object 
	 * based on the corresponding properties of the Employee object.
	 */
}


/*
 In summary, this PetStoreEmployee class serves as a simplified representation of an employee, 
 specifically designed for use in the controller layer of the application. It uses Lombok 
 annotations to automatically generate boilerplate code, minimizing the need for manual coding. 
 Additionally, it provides a constructor to easily convert Employee entities into PetStoreEmployee 
 objects for handling in the controller.
 */
