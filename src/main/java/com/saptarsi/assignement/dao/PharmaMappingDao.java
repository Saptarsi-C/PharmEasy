/**
 * 
 */
package com.saptarsi.assignement.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saptarsi.assignement.domain.PharmaMapping;

/**
 * @author saptarsichaurashy
 *
 */
@Repository
public interface PharmaMappingDao extends JpaRepository<PharmaMapping, Long> {

	PharmaMapping getByUIdAndPId(Long uId, Long pId);

	List<PharmaMapping> getByUId(Long pId, Pageable pageable);
	
	Long countByPId(long pId);
}
