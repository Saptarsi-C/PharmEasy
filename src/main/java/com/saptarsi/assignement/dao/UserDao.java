/**
 * 
 */
package com.saptarsi.assignement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.utils.Role;

/**
 * @author saptarsichaurashy
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {
	
	User findById(Long id);
	List<User> findByUserNameAndPassword(String userName, String Password);
	List<User> findByUserName(String userName);
	List<User> findByIdIn(List<Long> ids);
	User findByUserNameAndRole(String userName, Role role); 
	List<User> findByUserNameIn(List<String> userName);

}
