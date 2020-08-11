package com.masivian.test.model;

import org.bson.Document;
import org.springframework.data.annotation.Id;


import java.util.Date;
import java.util.List;

@org.springframework.data.mongodb.core.mapping.Document(collection = "roulette")
public class Roulette {

    @Id
    private String id;
    private Document roulette;
    private long createDate;

    public Roulette() {
    }

    public Roulette(Document roulette) {
        this.roulette = roulette;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Document getRoulette() {
        return roulette;
    }

    public void setRoulette(Document roulette) {
        this.roulette = roulette;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
