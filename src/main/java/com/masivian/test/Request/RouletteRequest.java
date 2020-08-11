package com.masivian.test.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;
import org.json.JSONObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RouletteRequest {
    @JsonProperty("roullete")
    @NotNull
    @NotEmpty
    private Document roullete;

    public Document getRoullete() {
        return roullete;
    }
}
