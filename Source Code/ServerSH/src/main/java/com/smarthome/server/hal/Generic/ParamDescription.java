package com.smarthome.server.hal.generic;

public class ParamDescription {
    private boolean isReadOnly;
    private String type;

    public ParamDescription() {
    }

    public ParamDescription(boolean isReadOnly, String type) {
        this.isReadOnly = isReadOnly;
        this.type = type;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
