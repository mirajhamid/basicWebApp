package com.miraj.basicWebApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.miraj.basicWebApp.model.Customer;

/*The Model and it's id datatype
better if you can use JPARepository because it has both and JPA functionalities 
JpaRepository extends PagingAndSortingRepository which in turn extends CrudRepository.
If you use JpaRepository annotate package in Applicationclass
@EnableJpaRepositories (basePackages = 'repository.package.name')*/
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	public Customer findById(int id);
	public List<Customer> findByIdGreaterThan(int id);

}
