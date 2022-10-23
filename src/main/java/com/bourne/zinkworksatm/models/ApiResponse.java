package com.bourne.zinkworksatm.models;

public class ApiResponse<T> {

    private T payload;
    private boolean success;

    public ApiResponse(T payload, boolean success) {
        this.payload = payload;
        this.success = success;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
