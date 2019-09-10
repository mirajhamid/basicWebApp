package com.miraj.basicWebApp.repository;

import org.springframework.data.repository.CrudRepository;
import com.miraj.basicWebApp.model.Customer;

//The Model and it's id datatype
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
