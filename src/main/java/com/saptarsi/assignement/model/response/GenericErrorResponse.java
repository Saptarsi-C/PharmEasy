package com.saptarsi.assignement.model.response;

public class GenericErrorResponse extends ModelAPIResponse {

    private static final long serialVersionUID = 1L;

    public GenericErrorResponse(String message, String errorCode) {
        // TODO change with umspConstant for failed
        super(null, message, errorCode);
    }

    public ModelAPIResponse createBadResponse(String message, String errorCode) {
        return null;
    }

    public void logBadResponse(GenericErrorResponse badResponse) {

    }
}
