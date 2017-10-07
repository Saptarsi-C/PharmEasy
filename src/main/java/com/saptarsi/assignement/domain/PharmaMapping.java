/**
 * 
 */
package com.saptarsi.assignement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.saptarsi.assignement.utils.UserStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @author saptarsichaurashy
 *
 */

@Embeddable
class MyKey implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long uId;
    private Long pId;
}

@Data
@Entity
@IdClass(MyKey.class)
@EntityListeners(AuditingEntityListener.class)
public class PharmaMapping {

	@Id 
	@NotNull
	private Long uId;
	@NotNull
	@Id private Long pId;
	
	@CreatedDate
	@Setter(AccessLevel.NONE)
	private Date addedOn;
	private Date deletedOn;
	@Enumerated(EnumType.STRING)
	private UserStatus status;
}
