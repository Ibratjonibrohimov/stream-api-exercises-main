package uz.najottalim.demo.streamapi.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uz.najottalim.demo.streamapi.models.Product;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {

	List<Product> findAll();
}
