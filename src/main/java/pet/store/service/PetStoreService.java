package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/************************************************************************************************
 * This PetStoreService class acts like the brains of the operation in your pet
 * store application. It's responsible for handling the business logic related
 * to pet stores.
 ************************************************************************************************/

@Service
public class PetStoreService {

	/*
	 * @Service: This annotation tells Spring Boot that this class is a service
	 * component, and it should be managed by the Spring container. Essentially,
	 * it's marking this class as a candidate for auto-detection during class path
	 * scanning.
	 */

	@Autowired
	private PetStoreDao petStoreDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CustomerDao customerDao;

	/*
	 * @Autowired private PetStoreDao petStoreDao;: This injects an instance of
	 * PetStoreDao into this service. It allows the service to interact with the
	 * data access layer (DAO) for managing pet store entities.
	 */

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());

		copyPetStoreFields(petStore, petStoreData);

		petStore = petStoreDao.save(petStore);

		return new PetStoreData(petStore);
	}

	/*
	 * savePetStore: This method is responsible for saving or updating pet store
	 * data. It first finds an existing pet store based on the provided ID or
	 * creates a new one if the ID is not provided. Then, it copies the fields from
	 * the incoming PetStoreData object to the PetStore entity. After that, it saves
	 * the PetStore entity using the petStoreDao and returns the saved data in the
	 * form of a PetStoreData object.
	 */

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {

		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());

	}

	// copyPetStoreFields: This method copies the fields from a PetStoreData object
	// to a PetStore entity.

	private PetStore findOrCreatePetStore(Long petStoreId) {

		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			PetStore petStore = findPetStoreById(petStoreId);

			return petStore;
		}
	}

	/*
	 * findOrCreatePetStore: This method either finds an existing pet store by ID or
	 * creates a new one if the ID is not provided.
	 */

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " not found"));

		/*
		 * findPetStoreById: This method finds a pet store by ID using the petStoreDao.
		 * If no pet store with the given ID is found, it throws a
		 * NoSuchElementException.
		 */

	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);

		Long employeeId = petStoreEmployee.getEmployeeId();

		Employee employee = findOrCreateEmployee(petStoreId, employeeId);

		copyEmployeeFields(employee, petStoreEmployee);

		employee.setPetStore(petStore);

		petStore.getEmployees().add(employee);

		Employee dbEmployee = employeeDao.save(employee);

		return new PetStoreEmployee(dbEmployee);
	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {

		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(petStoreId, employeeId);

		}
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));

	}

	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);

		Long customerId = petStoreCustomer.getCustomerId();

		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		copyCustomerFields(customer, petStoreCustomer);

		customer.getPetStores().add(petStore);

		petStore.getCustomers().add(customer);

		Customer dbCustomer = customerDao.save(customer);

		return new PetStoreCustomer(dbCustomer);

	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {

		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		} else {
			return findCustomerById(petStoreId, customerId);

		}
	}

	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));

		boolean found = false;

		for (PetStore petStore : customer.getPetStores()) {
			if (petStore.getPetStoreId() == petStoreId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Customer with ID+" + customerId + " does not shop at this store");
		}
		return customer;

	}

	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStore = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();

		for (PetStore store : petStore) {
			PetStoreData psd = new PetStoreData(store);

			psd.getCustomers().clear();
			psd.getEmployees().clear();

			result.add(psd);
		}
		return result;
	}

	@Transactional
	public PetStoreData retrievePetStoreById(Long petStoreId) {

		return new PetStoreData(findPetStoreById(petStoreId));
	}

	public PetStore deletePetStoreById(Long petStoreId) {

		PetStore petStore = findPetStoreById(petStoreId);

		petStoreDao.delete(petStore);

		return petStore;
	}

}

/*
 * In summary, this service layer abstracts the logic for handling pet store
 * data operations. It coordinates with the data access layer (DAO) to perform
 * CRUD operations on pet store entities. Additionally, it encapsulates the
 * logic for creating, updating, and retrieving pet store data, ensuring
 * separation of concerns and maintainability of the application.
 */
