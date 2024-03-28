package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.PetStore;

// specifies that this DAO interface will deal with entities of type PetStore and primary key of type Long.
public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}


/*
In summary, this PetStoreDao interface simplifies database operations for the PetStore entity 
by leveraging Spring Data JPA. It provides CRUD methods out-of-the-box, allowing easy interaction 
with the underlying database without the need for developers to write boilerplate code for these operations.
*/
