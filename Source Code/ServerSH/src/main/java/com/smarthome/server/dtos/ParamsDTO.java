package com.smarthome.server.dtos;

import com.smarthome.server.hal.generic.Params;

public class ParamsDTO {
    private Params params;

    public ParamsDTO() {
        this.params = new Params();
    }

    public ParamsDTO(Params params) {
        this.params = params;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
