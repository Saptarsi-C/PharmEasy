/**
 * 
 */
package com.saptarsi.assignement.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.saptarsi.assignement.utils.Role;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author saptarsichaurashy
 *
 */

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	@NotNull
	private Long id;
	@NotNull
	private String userName;

	@Version()
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Long version = 0L;

	@Basic(fetch = FetchType.LAZY)
	private String fname;
	@Basic(fetch = FetchType.LAZY)
	private String lname;
	@Basic(fetch = FetchType.LAZY)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@CreatedDate
	@Setter(AccessLevel.NONE)
	private Date joinedOn;
	@LastModifiedDate
	@Setter(AccessLevel.NONE)
	private Date updatedOn;

}
