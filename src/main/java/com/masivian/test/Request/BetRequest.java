package com.masivian.test.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BetRequest {
    @JsonProperty("bet")
    @NotNull
    @NotEmpty
    private Document bet;

    public Document getBet() {
        return bet;
    }
}
