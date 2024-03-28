package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

/************************************************************************************
 * The controller is like the conductor of an orchestra in a pet store
 * application. It receives requests from clients (like web browsers or mobile
 * apps), decides what to do with them, and then tells the service layer what
 * actions to take.
 ************************************************************************************/

@RestController // tells Spring Boot that this class will handle HTTP requests and send back
				// responses.
@RequestMapping("/pet_store") // sets up a base URL path for all the end points in this controller.
								// So, all end points will start with /pet_store.
@Slf4j // This generates a logger named log for logging messages.
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;

	/*
	 * @Autowired -- This injects an instance of PetStoreService into this
	 * controller. It's like having a phone number of the service layer so this
	 * controller can call it whenever it needs.
	 */

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {

		log.info("Received request to create and update pet store data: {}", petStoreData);

		return petStoreService.savePetStore(petStoreData);
	}

	/*
	 * createPetStore: This method is called when a client sends a POST request to
	 * /pet_store. It expects some data (in the request body) about a pet store. It
	 * logs that it received the request, then it tells the petStoreService to save
	 * this pet store data, and finally returns the saved data.
	 */

	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStoreData(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		log.info("Updating pet store data for ID= {}: {}", petStoreId, petStoreData);
		petStoreData.setPetStoreId(petStoreId);
		return petStoreService.savePetStore(petStoreData);
	}

	/*
	 * updatePetStoreData: This method is called when a client sends a PUT request
	 * to /pet_store/{petStoreId} where {petStoreId} is the ID of the pet store
	 * being updated. It expects some updated data (in the request body) about the
	 * pet store. It logs that it received the request, then it updates the pet
	 * store data with the given ID using the petStoreService, and finally returns
	 * the updated data.
	 */

	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreEmployee addEmployeeToStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee) {

		log.info("Adding employee to pet store with ID={}: {}", petStoreId, petStoreEmployee);

		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}

	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreCustomer addCustomerToStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {

		log.info("Adding customer to pet store with ID={}: {}", petStoreId, petStoreCustomer);

		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}

	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
		return petStoreService.retrieveAllPetStores();
	}

	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		return petStoreService.retrievePetStoreById(petStoreId);
	}

	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with ID={}", petStoreId);

		petStoreService.deletePetStoreById(petStoreId);

		return Map.of("message", "Pet store with ID= " + petStoreId + " has been successfully deleted.");
	}

}
