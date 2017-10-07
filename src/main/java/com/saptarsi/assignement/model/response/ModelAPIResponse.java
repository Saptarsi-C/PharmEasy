package com.saptarsi.assignement.model.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author saptarsichaurashy
 *
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-04-25T14:13:37.920Z")

public class ModelAPIResponse implements Serializable {

    /**
     * 
     */
    public ModelAPIResponse(Map<String, Object> userIdentity, String message, String errorCode) {
        super();
        this.description = userIdentity;
        this.message = message;
        this.appCode = errorCode;
    }
    public ModelAPIResponse(){
    }
    private static final long serialVersionUID = 19870654123L;
    private static Gson gson = new Gson();
    
    @JsonProperty("description")
    @Expose
    private Map<String, Object> description = new HashMap<String, Object>();

    @NotBlank
    @Expose
    @JsonProperty("message")
    private String message;

    @NotBlank
    @JsonProperty("appCode")
    @Expose
    private String appCode;

    public ModelAPIResponse response(Map<String, Object> userIdentity) {
        this.description = userIdentity;
        return this;
    }

    /**
     * Get userIdentity
     * 
     * @return userIdentity
     **/
    @ApiModelProperty(required = true, value = "")
    public Map<String, Object> getDescription() {
        return description;
    }

    public void setDescription(Map<String, Object> userIdentity) {
        this.description = userIdentity;
    }

    public ModelAPIResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Error message to be passed in case of failure description
     * 
     * @return error
     **/
    @ApiModelProperty(example = "User Not Found", required = true, value = "Error message to be passed in case of failure description")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelAPIResponse errorCode(String errorCode) {
        this.appCode = errorCode;
        return this;
    }

    /**
     * Application error code
     * 
     * @return errorCode
     **/
    @ApiModelProperty(example = "601", required = true, value = "Application error code")
    public String getAppCode() {
        return appCode;
    }

    public void setErrorCode(String errorCode) {
        this.appCode = errorCode;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelAPIResponse _apIResponse = (ModelAPIResponse) o;
        return Objects.equals(this.description, _apIResponse.description)
                && Objects.equals(this.message, _apIResponse.message)
                && Objects.equals(this.appCode, _apIResponse.appCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, message, appCode);
    }

    @Override
    public String toString() {
    	return gson.toJson(this);
    }
}
