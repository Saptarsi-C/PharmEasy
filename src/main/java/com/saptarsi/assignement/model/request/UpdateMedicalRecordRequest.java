/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
public class UpdateMedicalRecordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Serializable updateMedicalInfo;

}
