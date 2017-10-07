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
public enum UserStatus {

	ACTIVE("active"),
	BLOCKED("blocked"),
	REQUESTED("requested");

	private String value;

	UserStatus(String value) {
		this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
    	return String.valueOf(value);
    }

    @JsonCreator
    public static UserStatus fromValue(String text) {
    	for (UserStatus b : UserStatus.values()) {
    		if (String.valueOf(b.value).equals(text)) {
    			return b;
    		}
    	}
     return null;
    }
}
