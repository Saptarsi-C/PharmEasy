/**
 * 
 */
package com.saptarsi.assignement.reaponse;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public class BadResponse extends ModelAPIResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
//    private Gson gson = SpringBridge.getGson();

    public BadResponse(String message, String errorCode) {
        super(null, message, errorCode);
    }

    public ModelAPIResponse createBadResponse(String message, String errorCode) {
        return null;
    }

    public void logBadResponse(BadResponse badResponse) {

    }


    // public Serializable gHid() {
    // return null;
    // }
    //
    //
    // public Serializable gResponse() {
    // return gson.toJson(this);
    // }
}
