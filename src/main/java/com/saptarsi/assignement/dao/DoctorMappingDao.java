/**
 * 
 */
package com.saptarsi.assignement.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saptarsi.assignement.domain.DoctorMapping;

/**
 * @author saptarsichaurashy
 *
 */
@Repository
public interface DoctorMappingDao extends JpaRepository<DoctorMapping, Long> {

	DoctorMapping getByPIdAndDId(Long pId, Long dId);
	
	List<DoctorMapping> getByPId(Long pId, Pageable pageable);
	
	Long countByPId(Long pId); 
}
