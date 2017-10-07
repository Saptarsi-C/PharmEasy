/**
 * 
 */
package com.saptarsi.assignement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saptarsi.assignement.domain.User;

/**
 * @author saptarsichaurashy
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {
	
	User findById(Long id);
	User findByUserNameAndPassword(String userName, String Password);
	User findByUserName(String userName);
	List<User> findByIdIn(List<Long> ids);

}
