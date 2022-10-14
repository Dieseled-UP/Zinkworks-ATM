package com.bourne.zinkworksatm.models;

import java.util.List;

public class ApiResponse {

    Object payload;
    boolean success;

    public ApiResponse(Object payload, boolean success) {
        this.payload = payload;
        this.success = success;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
