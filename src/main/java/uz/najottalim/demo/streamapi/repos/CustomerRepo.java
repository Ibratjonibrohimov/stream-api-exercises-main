package uz.najottalim.demo.streamapi.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uz.najottalim.demo.streamapi.models.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

	List<Customer> findAll();
}
