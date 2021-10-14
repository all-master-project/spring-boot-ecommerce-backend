package com.luv2code.ecommerce.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.ecommerce.entity.Product;

@Repository
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

	 /*
	  * SELECT Product.* FROM  Product p
	  * WHERE p.id =  id
	  */
	Page<Product> findByCategoryId(@RequestParam("id")Long id, Pageable pageable);
	  
	 /*
	  * SELECT * FROM Product p 
	  * WHERE p.name LIKE CONCAT('%',name,'%')
	  */
	 Page<Product> findByNameContaining(@RequestParam("name")String name, Pageable pageable);
}
  