/**
 * 
 */
package com.saptarsi.assignement.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author saptarsichaurashy
 *
 */
public enum Role {

	PATIENT("patient"),
	DOCTOR("doctor"),
	PHARMACIST("prarmacist");

	private String value;

	Role(String value) {
		this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
    	return String.valueOf(value);
    }

    @JsonCreator
    public static Role fromValue(String text) {
    	for (Role b : Role.values()) {
    		if (String.valueOf(b.value).equals(text)) {
    			return b;
    		}
    	}
     return null;
    }
}
