/**
 * 
 */
package com.saptarsi.assignement.reaponse;

import java.util.Map;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public class SuccessResponse extends ModelAPIResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    // private Gson gson = SpringBridge.getGson();
    //
    // private UmTokenService umTokenService = SpringBridge.getUmTokenService();

    public SuccessResponse(Map<String, Object> userIdentity, String message, String errorCode) {
        super(userIdentity, message, errorCode);
    }

    public ModelAPIResponse createSuccessResponse(String message, String errorCode) {
        return null;
    }

    public void logBadResponse(BadResponse badResponse) {

    }


//    public Serializable gHid() {
//        if (null != this.getDescription() && this.getDescription().containsKey("userIdentity"))
//            return gson
//                    .fromJson(umTokenService.decodeToken(this.getDescription().get("userIdentity").toString()).toString(),
//                            UserIdentity.class)
//                    .getHId();
//        return null;
//    }
//
//    
//    public Serializable gResponse() {
//        return gson.toJson(this);
//    }

}
