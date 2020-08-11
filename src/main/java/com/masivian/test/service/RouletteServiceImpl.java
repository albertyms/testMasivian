package com.masivian.test.service;

import com.masivian.test.model.Roulette;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class RouletteServiceImpl implements RouletteService {

    @Resource(name = "mongoTemplate")
    MongoTemplate mongoTemplate;


    @Override
    public Roulette createRoulette(Roulette roulette) {
        return mongoTemplate.save(roulette);
    }

    @Override
    public Roulette getRoulette(String id) {
        return mongoTemplate.findOne(Query.query(where("_id").is(id)), Roulette.class);
    }

    @Override
    public Roulette updateRoulette(Roulette roulette) {
        return mongoTemplate.save(roulette);
    }

    @Override
    public List<Roulette> getListRoulette() {
        return mongoTemplate.findAll(Roulette.class);
    }
}
