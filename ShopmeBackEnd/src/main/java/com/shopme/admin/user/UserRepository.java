package com.shopme.admin.user;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	//Query query = entityManager. createQuery("Select UserName from **UserDetails** "); 
	  @Query("SELECT u FROM User WHERE u.email = :email") 
	  public User getUserByEmail(@Param("email") String email);
	 
	
	
}
